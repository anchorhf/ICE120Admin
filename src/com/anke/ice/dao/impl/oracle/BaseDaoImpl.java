package com.anke.ice.dao.impl.oracle;

import java.sql.Connection;

import org.apache.commons.dbutils.QueryRunner;

import com.anke.ice.dao.BaseDao;
import com.anke.ice.dao.DBHelper;

public class BaseDaoImpl implements BaseDao {
	//protected Connection conn;
	protected QueryRunner runner;
	protected String sql;

	public BaseDaoImpl() {
		//conn = DBHelper.getInstance().getConnection();
		runner = new QueryRunner(true);
	}

	@Override
	public int genericID() {
		return 1;
	}
}
