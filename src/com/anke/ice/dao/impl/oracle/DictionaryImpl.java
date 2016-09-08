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

import com.anke.ice.dao.DBHelper;
import com.anke.ice.dao.DictionaryDao;
import com.anke.ice.model.DictionaryModel;
import com.anke.ice.model.DictionaryTypeModel;
import com.anke.ice.model.RoleMenuModel;
import com.anke.ice.util.LoggerUtil;
import com.anke.ice.util.WhereClauseUtility;

public class DictionaryImpl extends BaseDaoImpl implements DictionaryDao {
	private static final Logger logger = LoggerUtil.getInstance(DictionaryImpl.class);
	private static int id = 1;

	// 根据表名、类型编码和是否显示请选择来查询字典表信息
	public List<DictionaryModel> GetDictionary(String table, String typeCode, int isAddSelect) {

		List<DictionaryModel> rows = null;
		Connection conn = DBHelper.getInstance().getConnection();
		try {
			// System.out.println("conn：" + conn);

			StringBuilder sbSQL = new StringBuilder();
			sbSQL.append(" select ID,NAME,ORDERNO,ISVALID");
			sbSQL.append(" from (");
			if (isAddSelect == 1) {
				sbSQL.append(
						" select distinct  -1 as ID,'--请选择--' as NAME,-1 as ORDERNO,1 as ISVALID  from " + table + " ");
				sbSQL.append(" union all");
			}
			if (table.equals("T_CENTER")) {
				sbSQL.append(
						" select * from (" + "select CENTERID as ID,NAME,1 as ORDERNO,1 as ISVALID from " + table + "");
				sbSQL.append(" order by NLSSORT(name,'NLS_SORT = SCHINESE_PINYIN_M'))tt");
			} else if (table.equals("T_INSTITUTION")) {
				sbSQL.append(
						" select * from (" + "select ID,NAME,1 as ORDERNO,ISVALID from " + table + " where ISVALID=1");
				sbSQL.append(" order by NLSSORT(name,'NLS_SORT = SCHINESE_PINYIN_M'))tt");
			} else if (table.equals("T_MSGTYPE")) {
				sbSQL.append(" select TYPEID as ID,MSGTYPE as NAME,1 as ORDERNO,1 as ISVALID from " + table + "");
				// sbSQL.append(" order by ORDERNO");
			} else if (table.equals("T_ROLE")) {
				sbSQL.append(" select ID,NAME,ORDERNO,ISVALID from " + table + " where ISVALID=1");
				sbSQL.append(" order by id");
			} else {
				sbSQL.append(" select * from(" + "select ID,NAME,ORDERNO,ISVALID from " + table + " where TYPECODE='"
						+ typeCode + "' and ISVALID=1");
				sbSQL.append(" order by ORDERNO)tt");
			}
			sbSQL.append(" )t");

			rows = runner.query(conn, sbSQL.toString(), new BeanListHandler<DictionaryModel>(DictionaryModel.class));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("查询字典表信息失败", e.getCause());
		} finally {
			DbUtils.closeQuietly(conn);
		}
		return rows;
	}

	// 根据角色ID和菜单ID查看是否有使用权限
	@Override
	public int IFRole(String roleid, int menuid) {
		int ok = 0;
		String[] role = roleid.split(",");
		for (int i = 0; i < role.length; i++) {
			RoleMenuModel rolemenu = getRoleMenuModel(Integer.parseInt(role[i]), menuid);
			if (rolemenu != null) {
				ok = 1;
				break;
			}
		}
		return ok;
	}

	// 根据菜单编码和角色编码，获取是否有对应权限
	public RoleMenuModel getRoleMenuModel(int roleid, int menuid) {
		Connection conn = DBHelper.getInstance().getConnection();
		try {

			StringBuilder sbSQL = new StringBuilder();
			sbSQL.append(" select ROLEID,MENUID");
			sbSQL.append(" from T_ROLEMENU");
			sbSQL.append(" where ROLEID=" + roleid + "AND MENUID=" + menuid);
			return runner.query(conn, sbSQL.toString(), new BeanHandler<RoleMenuModel>(RoleMenuModel.class));
		} catch (SQLException e) {
			logger.error("查询菜单权限信息失败", e.getCause());
			return null;
		} finally {
			DbUtils.closeQuietly(conn);
		}
	}

	// 根据名称查询字典表类型列表
	@Override
	public Map<String, Object> getDictionaryTypeList(int pageNum, int pageSize, String name) {
		Map<String, Object> page = new HashMap<String, Object>();
		Connection conn = DBHelper.getInstance().getConnection();
		try {

			StringBuilder sbSQL = new StringBuilder();
			sbSQL.append(" select * from (");
			sbSQL.append(" select ID,TYPECODE,NAME,rownum rn");

			sbSQL.append(" from T_DICTIONARY_TYPE");
			sbSQL.append(" where 1=1");
			WhereClauseUtility.AddStringLike("NAME", name, sbSQL);// 名称

			sbSQL.append(" order by ID");
			sbSQL.append(" )t");

			StringBuilder sbSQLR = new StringBuilder();
			sbSQLR.append(sbSQL + " where t.rn>" + ((pageNum - 1) * pageSize) + " and t.rn<=" + pageNum * pageSize);

			String sql = sbSQLR.toString();

			StringBuilder sbSQLT = new StringBuilder();// 取总共条数
			sbSQLT.append("select count(*) from (" + sbSQL + ")ttt");
			String countSql = sbSQLT.toString();
			int total = runner.query(conn, countSql, new ScalarHandler<BigDecimal>()).intValue();

			List<DictionaryTypeModel> rows = runner.query(conn, sql,
					new BeanListHandler<DictionaryTypeModel>(DictionaryTypeModel.class));

			page.put("total", total);
			page.put("rows", rows);
			page.put("pages", (total % pageSize != 0 ? total / pageSize + 1 : total / pageSize));
			page.put("to", pageNum * pageSize);
		} catch (SQLException e) {
			logger.error("查询字典表类型列表失败", e.getCause());
		} finally {
			DbUtils.closeQuietly(conn);
		}
		return page;
	}

	// 根据类型编码查询对应字典表信息列表
	@Override
	public Map<String, Object> getDictionaryList(int pageNum, int pageSize, String typecode) {
		Map<String, Object> page = new HashMap<String, Object>();
		Connection conn = DBHelper.getInstance().getConnection();
		try {

			StringBuilder sbSQL = new StringBuilder();
			sbSQL.append(" select * from (");
			sbSQL.append(" select ID,TYPECODE,NAME,ORDERNO,ISVALID,rownum rn from (");
			sbSQL.append(" select ID,TYPECODE,NAME,ORDERNO,ISVALID");

			sbSQL.append(" from T_DICTIONARY");
			sbSQL.append(" where 1=1");
			WhereClauseUtility.AddStringEqual("TYPECODE", typecode, sbSQL);// 名称

			sbSQL.append(" order by ORDERNO");
			sbSQL.append(" )t");
			sbSQL.append(" )tt");

			StringBuilder sbSQLR = new StringBuilder();
			sbSQLR.append(sbSQL + " where tt.rn>" + ((pageNum - 1) * pageSize) + " and tt.rn<=" + pageNum * pageSize);

			String sql = sbSQLR.toString();

			StringBuilder sbSQLT = new StringBuilder();// 取总共条数
			sbSQLT.append("select count(*) from (" + sbSQL + ")ttt");
			String countSql = sbSQLT.toString();
			int total = runner.query(conn, countSql, new ScalarHandler<BigDecimal>()).intValue();

			List<DictionaryModel> rows = runner.query(conn, sql,
					new BeanListHandler<DictionaryModel>(DictionaryModel.class));

			page.put("total", total);
			page.put("rows", rows);
			page.put("pages", (total % pageSize != 0 ? total / pageSize + 1 : total / pageSize));
			page.put("to", pageNum * pageSize);
		} catch (SQLException e) {
			logger.error("查询字典表列表失败", e.getCause());
		} finally {
			DbUtils.closeQuietly(conn);
		}
		return page;
	}

	// 新增或者修改字典表信息
	@Override
	public DictionaryModel saveOrUpdate(DictionaryModel bean) {
		Connection conn = DBHelper.getInstance().getConnection();
		try {

			if (bean.getID() <= 0) {
				bean.setID(getdictionaryID());
				sql = DBHelper.createInsertSql(bean);
				runner.insert(conn, sql, new BeanHandler<DictionaryModel>(DictionaryModel.class));
			} else {
				sql = DBHelper.createUpdateSql(bean);
				runner.update(conn, sql);
			}
		} catch (SQLException e) {
			logger.error("保存字典信息失败", e);
			return null;
		} finally {
			DbUtils.closeQuietly(conn);
		}
		return bean;
	}

	// 获取字典表最大id
	public int getdictionaryID() {
		Connection conn = DBHelper.getInstance().getConnection();
		// int id = 1;
		try {
			// conn = DBHelper.getInstance().getConnection();
			DictionaryModel model = runner.query(conn, "select * from T_DICTIONARY order by id desc",
					new BeanHandler<DictionaryModel>(DictionaryModel.class));
			if (model != null) {
				if (id <= (model.getID() + 1))
					id = model.getID() + 1;
				else
					id++;
			}
		} catch (SQLException e) {
			logger.error("获取字典表最大编码失败", e.getCause());
		}
		finally {
			DbUtils.closeQuietly(conn);
		}

		return id;
	}

	// 修改字典表为有效或无效
	@Override
	public int updateDictionaryIsValid(int id, int isvalid) {
		Connection conn = DBHelper.getInstance().getConnection();
		try {

			return runner.update(conn, "update T_DICTIONARY set ISVALID=" + isvalid + " where ID=" + id);
		} catch (SQLException e) {
			logger.error("修改字典表有效或无效失败", e.getCause());
			return 0;
		} finally {
			DbUtils.closeQuietly(conn);
		}
	}
}
