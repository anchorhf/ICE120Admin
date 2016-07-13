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
import com.anke.ice.dao.CenterDao;
import com.anke.ice.dao.DBHelper;
import com.anke.ice.model.Center;
import com.anke.ice.util.LoggerUtil;

public class CenterDaoImpl extends BaseDaoImpl implements CenterDao {
	private static final Logger logger = LoggerUtil.getInstance(DBHelper.class);

	@Override
	public int genericID() {
		try {
			Center model = runner.query(conn, "select * from t_center order by centerid desc", new BeanHandler<Center>(Center.class));
			if (model != null) {
				if (IDConstant.CENTER_ID <= (model.getCenterid() + 1))
					IDConstant.CENTER_ID = model.getCenterid() + 1;
				else
					IDConstant.CENTER_ID++;
			}
		} catch (SQLException e) {
			logger.error("生成接入点编码失败", e.getCause());
		}
		return IDConstant.CENTER_ID;
	}

	@Override
	public Map<String, Object> find(int pageNum, int pageSize, String keyword) {
		Map<String, Object> page = new HashMap<String, Object>();
		try {
			String sql = "select t.centerid,t.name,t.ip||':'||t.port as ipport, t.account,t.password,t.maxmemory,t.totalmemory,t.freememory,t.cpu,t.machinestatus from (select d.*,rownum rn from (select t.*,rownum from (select* from t_center order by centerid desc) t) d where name like '%"
					+ keyword + "%' order by centerid desc) t where t.rn>" + ((pageNum - 1) * pageSize) + " and t.rn<=" + pageNum * pageSize;
			String countSql = "select count(*) from t_center d where name like '%" + keyword + "%' order by centerid desc";
			int total = runner.query(conn, countSql, new ResultSetHandler<Integer>() {

				@Override
				public Integer handle(ResultSet rs) throws SQLException {
					if (rs.next())
						return rs.getInt(1);
					else
						return 1;
				}
			});
			List<Center> rows = runner.query(conn, sql, new BeanListHandler<Center>(Center.class));
			page.put("total", total);
			page.put("rows", rows);
			page.put("pages", (total % pageSize != 0 ? total / pageSize + 1 : total / pageSize));
			page.put("to", pageNum * pageSize);
		} catch (SQLException e) {
			logger.error("查询接入点列表失败", e.getCause());
		}
		return page;
	}

	@Override
	public Center saveOrUpdate(Center bean) {
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
		}
		return bean;
	}

	@Override
	public Center get(int id) {
		try {
			return runner.query(conn, "select * from t_center where centerid=" + id, new BeanHandler<Center>(Center.class));
		} catch (SQLException e) {
			logger.error("查询接入点失败", e.getCause());
			return null;
		}
	}

	@Override
	public int delete(int id) {
		try {
			return runner.update(conn, "delete from t_center where centerid=?", id);
		} catch (SQLException e) {
			logger.error("删除接入点失败", e.getCause());
			return 0;
		}
	}

}
