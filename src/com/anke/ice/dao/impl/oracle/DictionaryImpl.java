package com.anke.ice.dao.impl.oracle;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.log4j.Logger;

import com.anke.ice.dao.DictionaryDao;
import com.anke.ice.model.DictionaryModel;
import com.anke.ice.model.RoleMenuModel;
import com.anke.ice.util.LoggerUtil;

public class DictionaryImpl extends BaseDaoImpl implements DictionaryDao {
	private static final Logger logger = LoggerUtil.getInstance(DictionaryImpl.class);

	// 根据表名、类型编码和是否显示请选择来查询字典表信息
	public List<DictionaryModel> GetDictionary(String table, String typeCode, int isAddSelect) {
		StringBuilder sbSQL = new StringBuilder();
		sbSQL.append(" select ID,NAME,ORDERNO,ISVALID");
		sbSQL.append(" from (");
		if (isAddSelect == 1) {
			sbSQL.append(
					" select distinct  -1 as ID,'--请选择--' as NAME,-1 as ORDERNO,1 as ISVALID  from " + table + " ");
			sbSQL.append(" union");
		}
		if(table.equals("T_CENTER")){
			sbSQL.append(" select CENTERID as ID,NAME,1 as ORDERNO,1 as ISVALID from " + table + "");
		}
		else if(table.equals("T_INSTITUTION")){
			sbSQL.append(" select ID,NAME,1 as ORDERNO,ISVALID from " + table + " where ISVALID=1");
		}
		else{
			sbSQL.append(" select ID,NAME,ORDERNO,ISVALID from " + table + " where TYPECODE='" + typeCode + "' and ISVALID=1");
		}
		sbSQL.append(" )t");
		sbSQL.append(" order by ORDERNO");
		List<DictionaryModel> rows = null;
		try {
			return rows = runner.query(conn, sbSQL.toString(),
					new BeanListHandler<DictionaryModel>(DictionaryModel.class));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("查询字典表信息失败", e.getCause());
			return rows;
		}
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

	// 根据志愿者申请编码，获取志愿者所有信息
	public RoleMenuModel getRoleMenuModel(int roleid, int menuid) {
		try {
			StringBuilder sbSQL = new StringBuilder();
			sbSQL.append(" select ROLEID,MENUID");
			sbSQL.append(" from T_ROLEMENU");
			sbSQL.append(" where ROLEID=" + roleid + "AND MENUID=" + menuid);
			return runner.query(conn, sbSQL.toString(), new BeanHandler<RoleMenuModel>(RoleMenuModel.class));
		} catch (SQLException e) {
			logger.error("查询菜单权限信息失败", e.getCause());
			return null;
		}
	}
}
