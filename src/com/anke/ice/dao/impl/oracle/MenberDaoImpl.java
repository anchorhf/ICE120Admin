package com.anke.ice.dao.impl.oracle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.log4j.Logger;

import com.anke.ice.IDConstant;
import com.anke.ice.dao.MenberDao;
import com.anke.ice.dao.DBHelper;
import com.anke.ice.model.Center;
import com.anke.ice.model.MYTreeNode;
import com.anke.ice.model.Menber;
import com.anke.ice.util.LoggerUtil;

public class MenberDaoImpl extends BaseDaoImpl implements MenberDao {
	private static final Logger logger = LoggerUtil.getInstance(DBHelper.class);

//	@Override
//	public int genericID() {
//		try {
//			Center model = runner.query(conn, "select * from t_center order by centerid desc", new BeanHandler<Center>(Center.class));
//			if (model != null) {
//				if (IDConstant.CENTER_ID <= (model.getCenterid() + 1))
//					IDConstant.CENTER_ID = model.getCenterid() + 1;
//				else
//					IDConstant.CENTER_ID++;
//			}
//		} catch (SQLException e) {
//			logger.error("生成接入点编码失败", e.getCause());
//		}
//		return IDConstant.CENTER_ID;
//	}

	@Override
	public Map<String, Object> findmenber(int pageNum, int pageSize, String telphone,String email,String address) {
		Map<String, Object> page = new HashMap<String, Object>();
		try {
//			System.out.println(telphone);
//			System.out.println(email);
//			System.out.println(address);
//			String sql = "select t.* from (select d.*,rownum rn from (select t.*,rownum from (select  tr.userid,tr.regtime,tr.email,tr.address,tr.describe,tr.registrationaddress,p.*,c.telphone as ctelphone,c.nickname as cnickname from T_REGUSER tr left join T_REG_PATIENT rp on rp.userid=tr.userid left join T_PATIENT p on rp.patientid=p.patientid left join T_CONTACT c on p.patientid=c.patientid) t) d where telphone like '%" + telphone + "%'  and  email like '%" + email + "%' and registrationaddress like '%" + address + "%') t where t.rn>" + ((pageNum - 1) * pageSize) + " and t.rn<=" + pageNum * pageSize;
			String sql = "select t.* from (select d.*,rownum rn from (select t.*,rownum from (select * from T_REGUSER) t) d where nvl(telphone,0) like '%" + telphone + "%'  and nvl(email,0) like '%" + email + "%' and nvl(registrationaddress,0) like '%" + address + "%') t where t.rn>" + ((pageNum - 1) * pageSize) + " and t.rn<=" + pageNum * pageSize;
			String countSql = "select count(*) from T_REGUSER d where nvl(telphone,0) like '%" + telphone + "%'  and nvl(email,0) like '%" + email + "%' and nvl(registrationaddress,0) like '%" + address + "%' ";
			int total = runner.query(conn, countSql, new ResultSetHandler<Integer>() {

				@Override
				public Integer handle(ResultSet rs) throws SQLException {
					if (rs.next())
						return rs.getInt(1);
					else
						return 1;
				}
			});
			List<Menber> rows = runner.query(conn, sql, new BeanListHandler<Menber>(Menber.class));
			page.put("total", total);
			page.put("rows", rows);
			page.put("pages", (total % pageSize != 0 ? total / pageSize + 1 : total / pageSize));
			page.put("to", pageNum * pageSize);
		} catch (SQLException e) {
			logger.error("查询会员列表失败", e.getCause());
		}
		return page;
	}

//	@Override
//	public Menber SaOrUpdate(Menber bean) {
//		try {
//			if (bean.getUserid() <= 0) {
//				bean.setUserid(genericID());
//				sql = DBHelper.createInsertSql(bean);
//				runner.insert(conn, sql, new BeanHandler<Menber>(Menber.class));
//			} else {
//				sql = DBHelper.createUpdateSql(bean);
//				runner.update(conn, sql);
//			}
//			} catch (SQLException e) {
//			logger.error("保存接入点列表失败", e);
//			return null;
//		}
//		return bean;
//	}

	@Override
	public String regettab(int id) {
		try {
			List<Menber>dacount=runner.query(conn, "select p.patientid from T_REGUSER tr left join T_REG_PATIENT rp on rp.userid=tr.userid left join T_PATIENT p on rp.patientid=p.patientid  where tr.userid=" + id,new BeanListHandler<Menber>(Menber.class));
			String tab="";
			for(int i=0;i<dacount.size();i++)
			{
				 tab=dacount.get(i).getPatientid()+","+tab;
			}
			return tab;
		} 
		catch (SQLException e) {
			logger.error("查询人员信息失败", e.getCause());
			return null;
		}
	}
	@Override
	public Menber reget(int id) {
		try {
		return runner.query(conn, "select tr.userid,tr.telphone as ltelphone,tr.email,tr.address,tr.describe,tr.registrationaddress,tr.regtime from T_REGUSER tr where userid=" + id, new BeanHandler<Menber>(Menber.class));
		} 
		catch (SQLException e) {
			logger.error("查询人员信息失败", e.getCause());
			return null;
		}
	}
	
	
	@Override
	public Menber regetc(int fid,int sid) {
		try {
//			System.out.println(fid);	
//			System.out.println(sid);
//			String s=" select * from T_REGUSER tr left join T_REG_PATIENT rp on rp.userid=tr.userid left join T_PATIENT p on rp.patientid=p.patientid  where tr.userid="+fid+"and p.patientid="+sid;
		return runner.query(conn, "select tr.userid,tr.telphone as ltelphone,tr.email,tr.address,tr.describe,tr.registrationaddress,tr.regtime,p.* from T_REGUSER tr left join T_REG_PATIENT rp on rp.userid=tr.userid left join T_PATIENT p on rp.patientid=p.patientid  where tr.userid=" +fid+"and p.patientid="+sid, new BeanHandler<Menber>(Menber.class));
        } 
		catch (SQLException e) {
			logger.error("查询人员信息失败", e.getCause());
			return null;
		}
	}
	@Override
	public List<Menber> regetem(int fid,int sid) {
		try {
			System.out.println(fid);	
			System.out.println(sid);
//			String s=" select * from T_REGUSER tr left join T_REG_PATIENT rp on rp.userid=tr.userid left join T_PATIENT p on rp.patientid=p.patientid  where tr.userid="+fid+"and p.patientid="+sid;
		return runner.query(conn, "select c.nickname as cnickname,c.telphone as ctelphone from T_REGUSER tr left join T_REG_PATIENT rp on rp.userid=tr.userid left join T_PATIENT p on rp.patientid=p.patientid left join T_CONTACT c on p.patientid=c.patientid where tr.userid=" +fid+"and p.patientid="+sid, new BeanListHandler<Menber>(Menber.class));
        } 
		catch (SQLException e) {
			logger.error("查询人员信息失败", e.getCause());
			return null;
		}
	}
//	@Override
//	public int delete(int id) {
//		try {
//			return runner.update(conn, "delete from t_center where centerid=?", id);
//		} catch (SQLException e) {
//			logger.error("删除接入点失败", e.getCause());
//			return 0;
//		}
//	}

}
