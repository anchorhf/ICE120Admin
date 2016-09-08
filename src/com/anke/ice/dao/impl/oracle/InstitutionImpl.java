package com.anke.ice.dao.impl.oracle;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.log4j.Logger;

import com.anke.ice.IDConstant;
import com.anke.ice.dao.DBHelper;
import com.anke.ice.dao.InstitutionDao;
import com.anke.ice.model.Center;
import com.anke.ice.model.CenterID;
import com.anke.ice.model.InstitutionModel;
import com.anke.ice.model.MYTreeNode;

import com.anke.ice.model.InstitutionCenterModel;

import com.anke.ice.util.LoggerUtil;
import com.anke.ice.util.StringBuilderUtil;
import com.anke.ice.util.WhereClauseUtility;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class InstitutionImpl extends BaseDaoImpl implements InstitutionDao {
	private static final Logger logger = LoggerUtil.getInstance(InstitutionImpl.class);
	// protected static Connection conn;//建立数据库连接
	private static int id = 1;//

	@Override
	public Map<String, Object> institutionlist(int pageNum, int pageSize, int id, String name, String volunteertype,
			String speciality, int isvalid) {
		Map<String, Object> page = new HashMap<String, Object>();
		Connection conn = DBHelper.getInstance().getConnection();
		try {
			StringBuilder sbSQL = new StringBuilder();
			sbSQL.append(" select ID,cetname,name,dupdatetime,thvolunteertype,thspeciality,ISTISVALID,tabcenterid,rn");
			sbSQL.append(" from(");
			sbSQL.append(
					" select ID,cetname,name,dupdatetime,thvolunteertype,thspeciality,ISTISVALID,tabcenterid,rownum rn");
			sbSQL.append(
					" from ( select ist.ID,cet.name as cetname,ist.NAME as name,ist.UPDATETIME as dupdatetime,ist.VOLUNTEERTYPE as thvolunteertype,ist.SPECIALITY as thspeciality,(case when ist.ISVALID=1 then '有效' else '无效' end) istisvalid,ist.centerid as tabcenterid");
			sbSQL.append(" from t_institution ist");
			sbSQL.append(" left join t_center cet on cet.centerid=ist.centerid");
			// sbSQL.append(" left join t_center cet on
			// cet.centerid=ist.centerid");
			// sbSQL.append(" where instr (speciality,'"+strspeciality+"')>0 and
			// instr (volunteertype,'"+strvolunteertype+"')>0");
			sbSQL.append(" where 1=1");
			if (id != -2) {
				WhereClauseUtility.AddIntEqual("ist.id", id, sbSQL);
			}
			WhereClauseUtility.AddStringLike("ist.name", name, sbSQL);
			// WhereClauseUtility.AddStringLike("ist.volunteertype",
			// volunteertype, sbSQL);
			// WhereClauseUtility.AddStringLike("ist.speciality",speciality,
			// sbSQL);

			WhereClauseUtility.AddIntEqual("ist.isvalid", isvalid, sbSQL);
			splitval("volunteertype", volunteertype, sbSQL);
			splitval("speciality", speciality, sbSQL);
			sbSQL.append(" order by UPDATETIME desc");
			sbSQL.append(" )t");
			sbSQL.append(" )tt");
			StringBuilder sbSQLR = new StringBuilder();
			sbSQLR.append(sbSQL + " where tt.rn>" + ((pageNum - 1) * pageSize) + " and tt.rn<=" + pageNum * pageSize);
			String sql = sbSQLR.toString();
			// System.out.println(sql);
			StringBuilder sbSQLT = new StringBuilder();// 取总共条数
			sbSQLT.append("select count(*) from (" + sbSQL + ")ttt");
			String countSql = sbSQLT.toString();

			int total = runner.query(conn, countSql, new ScalarHandler<BigDecimal>()).intValue();

			List<InstitutionModel> rows = runner.query(conn, sql,
					new BeanListHandler<InstitutionModel>(InstitutionModel.class));
			page.put("total", total);
			page.put("rows", rows);
			page.put("pages", (total % pageSize != 0 ? total / pageSize + 1 : total / pageSize));
			page.put("to", pageNum * pageSize);
		} catch (SQLException e) {
			logger.error("查询机构列表失败", e.getCause());
		} finally {
			DbUtils.closeQuietly(conn);
		}
		return page;
	}

	public StringBuilder splitval(String name, String strArray, StringBuilder sbSQL) {
		String[] wherecount = strArray.split(",");
		if (wherecount.length == 1) {
			WhereClauseUtility.AddStringLike("ist." + name + "", wherecount[0], sbSQL);
		} else {
			for (int i = 0; i < wherecount.length; i++) {
				if (i == 0) {
					sbSQL.append("AND (ist." + name + " like '%" + wherecount[i] + "%'");
				} else {
					WhereClauseUtility.OrStringLike("ist." + name + "", wherecount[i], sbSQL);

				}
			}
			sbSQL.append(")");
		}

		return sbSQL;
	}

	public InstitutionModel get(int id) {
		Connection conn = DBHelper.getInstance().getConnection();
		try {
			StringBuilder sbSQL = new StringBuilder();
			sbSQL.append(" select ist.ID as id,(case when ist.centerid=-1 then '--请选择--'else cet.name end ) as centername,");
			sbSQL.append("ist.NAME as name,ist.volunteertype,(case when ist.speciality is null then 'isnull' else ist.speciality end ) as speciality,ist.isvalid");
			sbSQL.append(" from t_institution ist");
			sbSQL.append(" left join t_center cet on cet.centerid=ist.centerid");
			sbSQL.append(" where 1=1");
			WhereClauseUtility.AddIntEqual("ist.id", id, sbSQL);
			String sql = sbSQL.toString();
			InstitutionModel getinstitution = runner.query(conn, sql,new BeanHandler<InstitutionModel>(InstitutionModel.class));
			InstitutionModel clobdetail = runner.query(conn,"select detail as insdetail from t_institution where id=" + id,new BeanHandler<InstitutionModel>(InstitutionModel.class));
			Clob clob = clobdetail.getInsdetail();
			if (clob == null) {
				getinstitution.setStrdetail(null);
			} else {
				String content;
				try {
					content = ClobToString(clob);
					getinstitution.setStrdetail(content);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
//			System.out.println(getinstitution.getStrdetail());
			return getinstitution;
		} catch (SQLException e) {
			logger.error("得到机构失败", e.getCause());
			return null;
		} finally {
			DbUtils.closeQuietly(conn);
		}
	}

	public String ClobToString(Clob clob) throws SQLException, IOException {

		String reString = "";
		Reader is = clob.getCharacterStream();// 得到流
		BufferedReader br = new BufferedReader(is);
		String str = br.readLine();
		StringBuffer sb = new StringBuffer();
		while (str != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
			sb.append(str);
			str = br.readLine();
		}
		reString = sb.toString();
		return reString;
	}

	@Override
	public int saveOrUpdate(InstitutionModel bean) {
		Connection conn = DBHelper.getInstance().getConnection();
		try {
			bean.setDetail(null);
			String getcount = bean.getStrdetail();
			bean.setStrdetail(null);
		
			bean.setTabcenterid(null);
			bean.setUPDATETIME(new Date());
			String getname = bean.getUsername();
			bean.setUsername(null);
			CenterID model = runner.query(conn,
					"select * from t_center tc where tc.name='" + bean.getCentername() + "'",
					new BeanHandler<CenterID>(CenterID.class));
			if (model == null) {
				bean.setCenterid(null);
			} else {
				bean.setCenterid(model.getCenterid());
				GetInstitutionCenter(bean.getCenterid(), bean.getId(), getname);
			}

			bean.setCentername(null);
			if (bean.getId() <= 0) {
				int IstitutionID = genericIstitutionID();
				bean.setId(IstitutionID);
				sql = DBHelper.createInsertSql(bean);
				runner.insert(conn, sql, new BeanHandler<InstitutionModel>(InstitutionModel.class));

				try {
					UpdateContent(getcount, bean.getId());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} else {
				sql = DBHelper.createUpdateSql(bean);
				runner.update(conn, sql);
				
				try {
					UpdateContent(getcount, bean.getId());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		} catch (SQLException e) {
			logger.error("保存机构失败", e);
			return 0;
		} finally {
			DbUtils.closeQuietly(conn);
		}
		return 1;
	}

	public int updatedetail(int institutionid, String strArray) {
		Connection conn = DBHelper.getInstance().getConnection();
		try {
			// connt = DBHelper.getInstance().getConnection();
			String updatesql = "update t_institution set detail=detail||'" + strArray + "' where id=" + institutionid
					+ "";
			runner.update(conn, updatesql);
			return 1;
		} catch (SQLException e) {
			logger.error("更新详细失败", e);
			return 0;
		} finally {
			DbUtils.closeQuietly(conn);
		}
	}

	public int GetInstitutionCenter(int centerid, int institutionid, String name) {
		Connection conn = DBHelper.getInstance().getConnection();
		try {
			// conn = DBHelper.getInstance().getConnection();
			if (institutionid <= 0) {
				int IstitutionID = genericIstitutionID();
				SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = new Date();
				String strdate = "to_date('" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date)
						+ "','YYYY-MM-DD HH24:MI:SS')";
				String insertsql = "insert into t_institution_center (institutionid,centerid,applystate,applyperson,applytime,checkperson,checktime) values ("
						+ IstitutionID + "," + centerid + ",1,'" + name + "'," + strdate + ",'" + name + "'," + strdate
						+ ")";
				runner.insert(conn, insertsql, new BeanHandler<InstitutionCenterModel>(InstitutionCenterModel.class));
			}
			// else
			// {
			// String updatesql ="update t_institution_center set
			// centerid="+centerid+" where institutionid="+institutionid+"";
			//
			// runner.update(conn, updatesql);
			// }
			return 1;
		} catch (SQLException e) {
			logger.error("插入合作机构失败", e);
			return 0;
		} finally {
			DbUtils.closeQuietly(conn);
		}

	}

	public int genericIstitutionID() {
		Connection conn = DBHelper.getInstance().getConnection();
		//int id = 1;
		try {
			InstitutionModel model = runner.query(conn, "select * from t_institution order by id desc",
					new BeanHandler<InstitutionModel>(InstitutionModel.class));
			if (model != null) {
				if (id <= (model.getId() + 1))
					id = model.getId() + 1;
				else
					id++;
			}
		} catch (SQLException e) {
			logger.error("生成机构编码失败", e.getCause());
		} finally {
			DbUtils.closeQuietly(conn);
		}
		return id;
	}

	@Override
	public List<MYTreeNode> getCoopInstitution(int cetid) {
		Connection conn = DBHelper.getInstance().getConnection();
		try {
			List<InstitutionModel> rows = runner.query(conn,
					"select * from t_institution order by nlssort(NAME, 'NLS_SORT=SCHINESE_PINYIN_M')",
					new BeanListHandler<InstitutionModel>(InstitutionModel.class));
			List<InstitutionModel> checkrows = runner.query(conn,
					"select ti.*,(case when tic.applystate=0 then '待审核'  when tic.applystate=1 then '审核通过' else  '审核不通过' end )as astate from t_institution ti left join t_institution_center tic on tic.institutionid=ti.id where tic.centerid="
							+ cetid + "",
					new BeanListHandler<InstitutionModel>(InstitutionModel.class));
			return ToTreeNodes(rows, checkrows, cetid);
		} catch (SQLException e) {
			logger.error("获取合作机构失败", e);
			return null;
		} finally {
			DbUtils.closeQuietly(conn);
		}
	}

	public static List<MYTreeNode> ToTreeNodes(List<InstitutionModel> rows, List<InstitutionModel> checkrows,
			int centerid) {
		List<MYTreeNode> listNodes = new ArrayList<MYTreeNode>();
		// 生成 树节点时，根据 pid=0的根节点 来生成
		LoadTreeNode(rows, listNodes, checkrows, centerid);
		return listNodes;
	}

	public static void LoadTreeNode(List<InstitutionModel> rows, List<MYTreeNode> listChildrenNode,
			List<InstitutionModel> checkrows, int centerid) {
		for (int i = 0; i < rows.size(); i++) {
			MYTreeNode node = null;
			// 将 权限转成 树节点
			node = ToTreeNode(rows.get(i));
			// 判断权限ParentID 是否和 传入参数相等
			for (int j = 0; j < checkrows.size(); j++) {
				if (rows.get(i).getId() == checkrows.get(j).getId()) {
					node = ToCheckTreeNode(checkrows.get(j), centerid);
					break;

				}

			}
			// 将节点加入到 树子节点集合
			listChildrenNode.add(node);
			// 递归 为这个新创建的 树节点找 子节点

		}

	}

	private static MYTreeNode ToTreeNode(InstitutionModel leftinfo) {
		// 对象的初始化器直接构造
		MYTreeNode treeNode = new MYTreeNode();
		treeNode.id = leftinfo.getId();
		treeNode.text = leftinfo.getName();
		treeNode.state = "open";
		treeNode.checked = false;
		return treeNode;
	}

	private static MYTreeNode ToCheckTreeNode(InstitutionModel leftinfo, int centerid) {
		String nckeck = "审核不通过";
		// 对象的初始化器直接构造
		MYTreeNode treeNode = new MYTreeNode();
		treeNode.id = leftinfo.getId();
		treeNode.text = leftinfo.getName();
		treeNode.state = "open";
		if (leftinfo.getAstate().equals(nckeck)) {
			treeNode.checked = false;
		} else {
			treeNode.checked = true;
		}
		if (leftinfo.getCenterid() == null) {
			treeNode.text = leftinfo.getName() + "(" + leftinfo.getAstate() + ")";
		} else {
			if (centerid == leftinfo.getCenterid()) {
				treeNode.text = leftinfo.getName();
			} else {
				treeNode.text = leftinfo.getName() + "(" + leftinfo.getAstate() + ")";
			}
		}
		return treeNode;
	}

	@Override
	public boolean savecoopinstitution(int cetid, String institutionid, String loginame) {
		Connection conn = DBHelper.getInstance().getConnection();
		try {
			List<InstitutionCenterModel> serchinsid = runner.query(conn,
					"select * from t_institution_center where centerid='" + cetid + "'",
					new BeanListHandler<InstitutionCenterModel>(InstitutionCenterModel.class));
			String[] institutionidval = institutionid.split(",");
			int strlen = institutionidval.length;
			for (int i = 0; i < strlen; i++) {
				int mark = 0;
				for (int j = 0; j < serchinsid.size(); j++) {
					// System.out.println(serchinsid.get(j).getInstitutionid());
					if (Integer.parseInt(institutionidval[i]) == serchinsid.get(j).getInstitutionid()) {
						if (serchinsid.get(j).getApplystate() == 2) {
							String deletesql = "delete from t_institution_center where institutionid="
									+ serchinsid.get(j).getInstitutionid() + " and centerid=" + cetid + "";
							runner.update(conn, deletesql);
							mark = 0;
							break;
						} else {
							mark = 1;
							break;
						}
					} else {
					}
				}
				if (mark == 0) {
					SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date date = new Date();
					String strdate = "to_date('" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date)
							+ "','YYYY-MM-DD HH24:MI:SS')";
					String insertsql = "insert into t_institution_center (institutionid,centerid,applystate,applyperson,applytime) values ("
							+ institutionidval[i] + "," + cetid + ",0,'" + loginame + "'," + strdate + ")";
					runner.insert(conn, insertsql,
							new BeanHandler<InstitutionCenterModel>(InstitutionCenterModel.class));
				}
			}
			List<InstitutionCenterModel> getinsid = runner.query(conn,
					"select * from t_institution_center where centerid='" + cetid + "'",
					new BeanListHandler<InstitutionCenterModel>(InstitutionCenterModel.class));

			for (int i = 0; i < getinsid.size(); i++) {
				boolean sign = true;
				for (int j = 0; j < strlen; j++) {

					if (Integer.parseInt(institutionidval[j]) == getinsid.get(i).getInstitutionid()) {
						sign = false;
						break;
					} else {
					}
				}
				if (sign) {
					if (getinsid.get(i).getApplystate() == 2) {

					} else {
						String deletesql = "delete from t_institution_center where institutionid="
								+ getinsid.get(i).getInstitutionid() + " and centerid=" + cetid + "";
						runner.update(conn, deletesql);
					}
				}
			}
			return true;
		} catch (SQLException e) {
			logger.error("保存合作机构失败", e);
			return false;
		} finally {
			DbUtils.closeQuietly(conn);
		}
	}

	@Override
	public int CenterRepeat(String cname, int centeri, String isname) {
		Connection conn = DBHelper.getInstance().getConnection();
		try {
			if (centeri > 0) {// 修改
				InstitutionModel serchname = runner.query(conn,
						"select * from t_institution where name='" + isname + "'",
						new BeanHandler<InstitutionModel>(InstitutionModel.class));
				if (serchname == null) {
					return 0;
				} else {
					// 根据机构ID查询机构名称
					InstitutionModel isnamerepeat = runner.query(conn,
							"select * from t_institution where id=" + centeri + "",
							new BeanHandler<InstitutionModel>(InstitutionModel.class));
					if (isnamerepeat.getName().equals(isname)) {
						return 0;
					} else {
						return 1;
					}
				}
			} else {// 新增

				InstitutionModel isnamerepeat = runner.query(conn,
						"select * from t_institution where name='" + isname + "'",
						new BeanHandler<InstitutionModel>(InstitutionModel.class));
				if (isnamerepeat == null) {
					return 0;
				} else {
					// System.out.println("hellow");
					return 1;
				}
			}

		} catch (SQLException e) {
			logger.error("判断是否重复失败", e.getCause());
			return -1;
		} finally {
			DbUtils.closeQuietly(conn);
		}
	}
	
	public void UpdateContent(String obj,int id) throws Exception  
    {  
		Connection conn = DBHelper.getInstance().getConnection();
        PreparedStatement pstmt = null;  
        try  
        {  
        	String updateSql = "update t_institution set detail=? where id="+id+"";
            pstmt = conn.prepareStatement(updateSql);  
            pstmt.setCharacterStream(1, new StringReader(obj));  
            pstmt.executeUpdate();  
        }  
        catch (Exception e)  
        {  
            logger.error("修改机构detail失败！");  
            throw e;  
        }  
        finally  
        {  
            if (null != pstmt)  
                pstmt.close();  
            if (null != conn)  
                conn.close();  
        }  
        
    }  
	
}
