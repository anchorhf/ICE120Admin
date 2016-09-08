package com.anke.ice.dao.impl.oracle;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.log4j.Logger;

import com.anke.ice.IDConstant;
import com.anke.ice.dao.CenterDao;
import com.anke.ice.dao.DBHelper;
import com.anke.ice.model.Center;
import com.anke.ice.util.LoggerUtil;

public class CenterDaoImpl extends BaseDaoImpl implements CenterDao {
	private static final Logger logger = LoggerUtil.getInstance(DBHelper.class);
	//protected static Connection conn;// 建立数据库连接
	private static int centerid = 1;

	@Override
	public int genericID() {
		//int centerid = 1;
		Connection conn = DBHelper.getInstance().getConnection();
		try {
			//conn = DBHelper.getInstance().getConnection();
			Center model = runner.query(conn, "select * from t_center order by centerid desc",
					new BeanHandler<Center>(Center.class));
			if (model != null) {
				if (centerid <= (model.getCenterid() + 1))
					centerid = model.getCenterid() + 1;
				else
					centerid++;
			}
		} catch (SQLException e) {
			logger.error("生成接入点编码失败", e.getCause());
		} 
		finally {
			DbUtils.closeQuietly(conn);
		}
		return centerid;
	}

	@Override
	public Map<String, Object> find(int pageNum, int pageSize, String keyword) {
		Map<String, Object> page = new HashMap<String, Object>();
		Connection conn = DBHelper.getInstance().getConnection();
		try {
			String sql;
			String countSql;
			// 如果查询参数为空
			if (keyword.length() == 0 && keyword.equals("")) {
				sql = "select t.centerid,t.name,t.area,t.workphone,t.ip,t.port,t.ip||':'||t.port as ipport, t.account,t.password,t.maxmemory,t.totalmemory,t.freememory,t.cpu,t.machinestatus from (select d.*,rownum rn from (select t.*,rownum from (select* from t_center order by centerid desc) t) d "
						+ " order by centerid desc) t where t.rn>" + ((pageNum - 1) * pageSize) + " and t.rn<="
						+ pageNum * pageSize;
				countSql = "select count(*) from t_center d order by centerid desc";
			} else {
				sql = "select t.centerid,t.name,t.area,t.workphone,t.ip,t.port,t.ip||':'||t.port as ipport, t.account,t.password,t.maxmemory,t.totalmemory,t.freememory,t.cpu,t.machinestatus from (select d.*,rownum rn from (select t.*,rownum from (select* from t_center order by centerid desc) t) d where name like '%"
						+ keyword + "%' order by centerid desc) t where t.rn>" + ((pageNum - 1) * pageSize)
						+ " and t.rn<=" + pageNum * pageSize;
				countSql = "select count(*) from t_center d where name like '%" + keyword + "%' order by centerid desc";

			}
			int total = runner.query(conn, countSql, new ScalarHandler<BigDecimal>()).intValue();
			
			List<Center> rows = runner.query(conn, sql, new BeanListHandler<Center>(Center.class));
			page.put("total", total);
			page.put("rows", rows);
			page.put("pages", (total % pageSize != 0 ? total / pageSize + 1 : total / pageSize));
			page.put("to", pageNum * pageSize);
		} catch (SQLException e) {
			logger.error("查询接入点列表失败", e.getCause());
		} finally {
			DbUtils.closeQuietly(conn);
		}
		return page;
	}

	@Override
	public Center saveOrUpdate(Center bean) {
		Connection conn = DBHelper.getInstance().getConnection();
		try {
			if (bean.getCenterid() <= 0) {
				bean.setCenterid(genericID());
				sql = DBHelper.createInsertSql(bean);
				runner.insert(conn, sql, new BeanHandler<Center>(Center.class));
			} else {
				sql = DBHelper.createUpdateSql(bean);
				runner.update(conn, sql);
			}
		} catch (SQLException e) {
			logger.error("保存接入点列表失败", e);
			return null;
		} finally {
			DbUtils.closeQuietly(conn);
		}
		return bean;
	}

	@Override
	public Center get(int id) {
		Connection conn = DBHelper.getInstance().getConnection();
		try {
			return runner.query(conn, "select * from t_center where centerid=" + id,
					new BeanHandler<Center>(Center.class));
		} catch (SQLException e) {
			logger.error("查询接入点失败", e.getCause());
			return null;
		}
		finally{
			DbUtils.closeQuietly(conn);
		}
	}

	@Override
	public int delete(int id) {
		Connection conn = DBHelper.getInstance().getConnection();
		try {
			return runner.update(conn, "delete from t_center where centerid=?", id);
		} catch (SQLException e) {
			logger.error("删除接入点失败", e.getCause());
			return 0;
		}
		finally{
			DbUtils.closeQuietly(conn);
		}
	}

	// 修改接入点名称后，同时修改机构表的机构名称2016-08-16
	@Override
	public int updateInstitutionName(int id, String name) {
		Connection conn = DBHelper.getInstance().getConnection();
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			String nowdate = df.format(new Date());
			return runner.update(conn, "update T_INSTITUTION set NAME='" + name + "',UPDATETIME=to_date('" + nowdate
					+ "','yyyy-mm-dd hh24:mi:ss') where centerid=?", id);
		} catch (SQLException e) {
			logger.error("修改接入点名称同时修改机构名称失败", e.getCause());
			return 0;
		}
		finally{
			DbUtils.closeQuietly(conn);
		}
	}
}
