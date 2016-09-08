package com.anke.ice.dao.impl.oracle;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.log4j.Logger;

import com.anke.ice.dao.B_WorkerDao;
import com.anke.ice.dao.DBHelper;
import com.anke.ice.model.B_Work;
import com.anke.ice.util.LoggerUtil;
import com.anke.ice.util.WhereClauseUtility;

public class B_WorkerImpl extends BaseDaoImpl implements B_WorkerDao {
	private static final Logger logger = LoggerUtil.getInstance(B_WorkerImpl.class);
	//protected static Connection conn;//建立数据库连接
	private static int id = 1;
	

	// 根据查询条件查询对应信息列表
	@Override
	public Map<String, Object> getList(int pageNum, int pageSize, String loginname,int institutionid) {
		Map<String, Object> page = new HashMap<String, Object>();
		Connection conn = DBHelper.getInstance().getConnection();
		try {

			//if(institutionid==-1){
			//	institutionid=-2;
			//}
			StringBuilder sbSQL = new StringBuilder();
			sbSQL.append(" select * from (");
			sbSQL.append(" select ID,LOGINNAME,PASSWORD,ROLE,CENTERID,NEWROLE,INSTITUTIONID,INSTITUTION,rownum rn from (");
			sbSQL.append(" select t.ID,LOGINNAME,PASSWORD,ROLE,t.CENTERID,NEWROLE,INSTITUTIONID,ti.name as INSTITUTION");

			sbSQL.append(" from B_WORKER t");
			sbSQL.append(" left join T_INSTITUTION ti on ti.id=t.INSTITUTIONID");
			sbSQL.append(" where 1=1");
			WhereClauseUtility.AddStringEqual("LOGINNAME", loginname, sbSQL);// 登录名
			WhereClauseUtility.AddIntEqual("INSTITUTIONID", institutionid, sbSQL);// 机构ID

			sbSQL.append(" order by id");
			sbSQL.append(" )t");
			sbSQL.append(" )tt");

			StringBuilder sbSQLR = new StringBuilder();
			sbSQLR.append(sbSQL + " where tt.rn>" + ((pageNum - 1) * pageSize) + " and tt.rn<=" + pageNum * pageSize);

			String sql = sbSQLR.toString();

			StringBuilder sbSQLT = new StringBuilder();// 取总共条数
			sbSQLT.append("select count(*) from (" + sbSQL + ")ttt");
			String countSql = sbSQLT.toString();
			int total = runner.query(conn, countSql, new ScalarHandler<BigDecimal>()).intValue();

			List<B_Work> rows = runner.query(conn, sql,
					new BeanListHandler<B_Work>(B_Work.class));

			page.put("total", total);
			page.put("rows", rows);
			page.put("pages", (total % pageSize != 0 ? total / pageSize + 1 : total / pageSize));
			page.put("to", pageNum * pageSize);
		} catch (SQLException e) {
			logger.error("查询用户列表失败", e.getCause());
		}
		finally{
			DbUtils.closeQuietly(conn);
		}
		return page;
	}
	

	//新增或者修改用户信息
	@Override
	public B_Work saveOrUpdate(B_Work bean) {
		Connection conn = DBHelper.getInstance().getConnection();
		try {
			if (bean.getId() <= 0) {
				bean.setId(getMaxID());
				sql = DBHelper.createInsertSql(bean);
				runner.insert(conn, sql, new BeanHandler<B_Work>(B_Work.class));
			} else {
				StringBuilder sbSQL = new StringBuilder();
				sbSQL.append(" update B_WORKER set loginname='" + bean.getLoginname()
				        + "',password='" + bean.getPassword()
				        + "',newrole='" + bean.getNewrole()
				        + "',institutionid=" + bean.getInstitutionid()
						+ " where ID=" + bean.getId());
				//sql = DBHelper.createUpdateSql(bean);
				String sql = sbSQL.toString();
				logger.debug(sql);
				runner.update(conn, sql);
			}
		} catch (SQLException e) {
			logger.error("保存用户信息失败", e);
			return null;
		}
		finally{
			DbUtils.closeQuietly(conn);
		}
		return bean;
	}
	

	// 获取最大id
	public int getMaxID() {
		Connection conn = DBHelper.getInstance().getConnection();
		//int id = 1;
		try {
			//conn = DBHelper.getInstance().getConnection();
			B_Work model = runner.query(conn, "select * from B_WORKER order by id desc",
					new BeanHandler<B_Work>(B_Work.class));
			if (model != null) {
				if (id <= (model.getId() + 1))
					id = model.getId() + 1;
				else
					id++;
			}
		} catch (SQLException e) {
			logger.error("获取用户最大编码失败", e.getCause());
		}
		finally{
			DbUtils.closeQuietly(conn);
		}
		return id;
	}
	

	// 获取数据库用户表是否包含
	@Override
	public int getLoginname(String loginname) {
		Connection conn = DBHelper.getInstance().getConnection();
		int you = 0;
		try {
			B_Work model = runner.query(conn, "select * from B_WORKER where loginname='"+loginname+"'",
					new BeanHandler<B_Work>(B_Work.class));
			if (model != null) {
				you=1;
			}
		} catch (SQLException e) {
			logger.error("获取用户最大编码失败", e.getCause());
		}
		finally{
			DbUtils.closeQuietly(conn);
		}
		return you;
	}
}
