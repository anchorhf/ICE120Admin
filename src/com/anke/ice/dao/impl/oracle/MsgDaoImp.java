package com.anke.ice.dao.impl.oracle;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
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
import com.anke.ice.dao.MsgDao;
import com.anke.ice.model.Center;
import com.anke.ice.model.CenterID;
import com.anke.ice.model.InstitutionModel;
import com.anke.ice.model.MYTreeNode;
import com.anke.ice.model.MsgModel;
import com.anke.ice.model.InstitutionCenterModel;

import com.anke.ice.util.LoggerUtil;
import com.anke.ice.util.WhereClauseUtility;
import com.cloopen.rest.sdk.utils.encoder.BASE64Decoder;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MsgDaoImp extends BaseDaoImpl implements MsgDao {
	private static final Logger logger = LoggerUtil.getInstance(MsgDaoImp.class);
	// protected static Connection conn;//建立数据库连接
	private static int id = 1;//

	public int generimID() {
		Connection conn = DBHelper.getInstance().getConnection();
		// int id=1;
		try {
			MsgModel model = runner.query(conn, "select * from t_msg order by msgid desc",
					new BeanHandler<MsgModel>(MsgModel.class));
			if (model != null) {
				if (id <= (model.getMsgid() + 1))
					id = model.getMsgid() + 1;
				else
					id++;
			}
		} catch (SQLException e) {
			logger.error("生成资讯编码失败", e.getCause());
		} finally {
			DbUtils.closeQuietly(conn);
		}

		return id;
	}

	@Override
	public Map<String, Object> MsgList(int pageNum, int pageSize, String minstitution, String mtitle, String mprofile,
			String mtype, int mapproval, String releasebegintime, String releaseendtime) {
		Map<String, Object> page = new HashMap<String, Object>();
		Connection conn = DBHelper.getInstance().getConnection();
		try {
			if (minstitution.equals("-2") || minstitution.equals("")) {
				minstitution = null;
			} else if (minstitution.equals("0")) {
				minstitution = "-2";
			}
			if (mtype.equals("")) {
				mtype = null;
			}

			StringBuilder sbSQL = new StringBuilder();
			sbSQL.append(" select msgid as tabmsgid,title as tabtitle,msgtype as tabmsgtype,profile as tabprofile");
			sbSQL.append(
					" ,releasetime as tabreleasetime,approval as tabapproval,name as tabinstitution,centerid as tabinstitutionid,rn");
			sbSQL.append(" from (");
			sbSQL.append(" select msgid,title,msgtype,profile,releasetime,approval,name,centerid,rownum rn");
			sbSQL.append(" from (");
			sbSQL.append(
					" select tm.msgid,tm.title,tmt.msgtype,tm.profile,tm.releasetime,(case when tm.approval=1 then '已审核' else '未审核' end) approval,ti.name,tm.centerid");
			sbSQL.append(" from t_msg tm");
			sbSQL.append(" left join t_msgtype tmt on tm.msgtypeid=tmt.typeid");
			sbSQL.append(" left join t_institution ti on tm.centerid=ti.id");
			sbSQL.append(" where 1=1");
			WhereClauseUtility.AddDateTimeGreaterThan("tm.releasetime", releasebegintime, sbSQL);// 开始时间
			WhereClauseUtility.AddDateTimeLessThan("tm.releasetime", releaseendtime, sbSQL);// 结束时间
			WhereClauseUtility.AddStringLike("tm.title", mtitle, sbSQL);//
			WhereClauseUtility.AddStringLike("tm.profile", mprofile, sbSQL);//
			WhereClauseUtility.AddInSelectQuery("tm.msgtypeid", mtype, sbSQL);// 消息类型
			WhereClauseUtility.AddInSelectQuery("tm.centerid", minstitution, sbSQL);// 所属机构
			WhereClauseUtility.AddIntEqual("tm.approval", mapproval, sbSQL);//
			sbSQL.append(" order by tm.releasetime DESC");

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

			List<MsgModel> rows = runner.query(conn, sql, new BeanListHandler<MsgModel>(MsgModel.class));

			page.put("total", total);
			page.put("rows", rows);
			page.put("pages", (total % pageSize != 0 ? total / pageSize + 1 : total / pageSize));
			page.put("to", pageNum * pageSize);
		} catch (SQLException e) {
			logger.error("查询资讯列表失败", e.getCause());
		} finally {
			DbUtils.closeQuietly(conn);
		}
		return page;
	}

	@Override
	public MsgModel saveOrUpdate(MsgModel bean) {
		Connection conn = DBHelper.getInstance().getConnection();
		try {
			int imgcount = bean.getPicname().lastIndexOf("/");
			String imgname;
			if (imgcount == -1) {
				imgname = bean.getPicname();
			} else {
				imgname = bean.getPicname().substring(imgcount + 1);
			}

			try {
				SaveUrl(bean.getStrurl(), imgname);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			InputStream fis = this.getClass().getResourceAsStream("/db.properties");
			Properties p = new Properties();
			try {
				p.load(fis);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String img_url = p.getProperty("img_url");

			String urlpath;
			if (imgname.equals("")) {
				urlpath = "";
			} else {
				urlpath = img_url + imgname;
			}

			bean.setUrl(urlpath);
			bean.setStrurl(null);
			bean.setPicname(null);
			bean.setContent(null);
			bean.setTabinstitutionid(null);
			
			String mycontent=bean.getStrcontent();
			
			bean.setStrcontent(null);
            if(bean.getCenterid()==-1){
            	bean.setCenterid(null);
            }
			if (bean.getMsgid() <= 0) {
				bean.setMsgid(generimID());
				bean.setReleasetime(new Date());
				bean.setApproval(0);

				sql = DBHelper.createInsertSql(bean);
				runner.insert(conn, sql, new BeanHandler<MsgModel>(MsgModel.class));


				
				try {
					UpdateContent(mycontent, bean.getMsgid());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				

			} else {

				sql = DBHelper.createUpdateSql(bean);
				runner.update(conn, sql);

				try {
					UpdateContent(mycontent, bean.getMsgid());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			logger.error("保存资讯列表失败"+e.toString(), e);
			return null;
		} finally {
			DbUtils.closeQuietly(conn);
		}
		return bean;
	}

	public void SaveUrl(String strurl, String url) throws IOException {

		String[] piccode = strurl.split(",", 2);

		BASE64Decoder decoder = new BASE64Decoder();

		try {
			// Base64解码
			byte[] bytes = decoder.decodeBuffer(piccode[1]);
			for (int i = 0; i < bytes.length; ++i) {
				if (bytes[i] < 0) {// 调整异常数据
					bytes[i] += 256;
				}
			}
			String savepath = "../webapps/picfiles/icons/" + url;
			OutputStream out = new FileOutputStream(savepath);
			out.write(bytes);
			out.flush();
			out.close();
		} catch (Exception e) {
		}
	}

	public int updatecontent(int msgid, String strArray) {
		Connection conn = DBHelper.getInstance().getConnection();
		try {
			String updatesql = "update t_msg set content=content||'" + strArray + "' where msgid=" + msgid + "";
			runner.update(conn, updatesql);
			return 1;
		} catch (SQLException e) {
			logger.error("更新资讯内容失败", e);
			return 0;
		} finally {
			DbUtils.closeQuietly(conn);
		}

	}

	public MsgModel get(int id) {
		Connection conn = DBHelper.getInstance().getConnection();

		try {
			StringBuilder sbSQL = new StringBuilder();
			sbSQL.append(" select tm.msgid,tm.title,tm.msgtypeid,tm.profile,(case when tm.centerid is null then -1 else tm.centerid end) as centerid,tm.approval,tm.url as picname");
			sbSQL.append(" from t_msg tm");
			sbSQL.append(" where 1=1");
			WhereClauseUtility.AddIntEqual("tm.msgid", id, sbSQL);
			String sql = sbSQL.toString();
			MsgModel getmsginfo = runner.query(conn, sql, new BeanHandler<MsgModel>(MsgModel.class));
			
			MsgModel clobconetent = runner.query(conn, "select content as msgcontent from t_msg where msgid=" + id,new BeanHandler<MsgModel>(MsgModel.class));
			Clob clob = clobconetent.getMsgcontent();
			if (clob == null) {
				getmsginfo.setStrcontent(null);
			} else {
				String msgdetail;
				try {
					msgdetail = ClobToString(clob);
					getmsginfo.setStrcontent(msgdetail);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return getmsginfo;
		} catch (SQLException e) {
			logger.error("得到资讯失败", e.getCause());
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

	public int delete(int id) {
		Connection conn = DBHelper.getInstance().getConnection();
		try {
			return runner.update(conn, "delete from t_msg where msgid=?", id);
		} catch (SQLException e) {
			logger.error("删除资讯失败", e.getCause());
			return 0;
		} finally {
			DbUtils.closeQuietly(conn);
		}
	}

	public int check(int id) {
		Connection conn = DBHelper.getInstance().getConnection();
		try {
			String updatesql = "update t_msg set approval='1' where msgid=" + id + "";
			return runner.update(conn, updatesql);
		} catch (SQLException e) {
			logger.error("审核资讯失败", e.getCause());
			return 0;
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
            String updateSql="update t_msg set content=? where msgid="+id+ "";
            pstmt = conn.prepareStatement(updateSql);  
            pstmt.setCharacterStream(1, new StringReader(obj));  
            pstmt.executeUpdate();  
        }  
        catch (Exception e)  
        {  
            logger.error("修改资讯content失败！");  
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
