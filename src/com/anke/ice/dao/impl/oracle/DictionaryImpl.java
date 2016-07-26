package com.anke.ice.dao.impl.oracle;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.log4j.Logger;

import com.anke.ice.dao.DictionaryDao;
import com.anke.ice.model.DictionaryModel;
import com.anke.ice.util.LoggerUtil;

public class DictionaryImpl extends BaseDaoImpl implements DictionaryDao{
	private static final Logger logger = LoggerUtil.getInstance(DictionaryImpl.class);

	//根据表名、类型编码和是否显示请选择来查询字典表信息
	public List<DictionaryModel> GetDictionary(String table,String typeCode,int isAddSelect)
	{
		StringBuilder sbSQL= new StringBuilder();
		sbSQL.append(" select ID,NAME,ORDERNO,ISVALID");
		sbSQL.append(" from (");
		if(isAddSelect==1){

	        sbSQL.append(" select distinct  -1 as ID,'--请选择--' as NAME,-1 as ORDERNO,1 as ISVALID  from "+table+" ");
	        sbSQL.append(" union");
		}
        sbSQL.append(" select ID,NAME,ORDERNO,ISVALID from "+table+" where TYPECODE='"+typeCode+"' and ISVALID=1");
        sbSQL.append(" )t");
        sbSQL.append(" order by ORDERNO");
        List<DictionaryModel> rows=null;
		try {
			return rows = runner.query(conn, sbSQL.toString(), new BeanListHandler<DictionaryModel>(DictionaryModel.class));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("查询字典表信息失败", e.getCause());
			return rows;
		}
	}
}
