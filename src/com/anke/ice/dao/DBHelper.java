package com.anke.ice.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.Properties;


import org.apache.log4j.Logger;

import com.anke.ice.IDConstant;
import com.anke.ice.dao.impl.oracle.CenterDaoImpl;
import com.anke.ice.dao.impl.oracle.LeftDaoImpl;
import com.anke.ice.dao.impl.oracle.LoginDaoImpl;
import com.anke.ice.dao.impl.oracle.MenberDaoImpl;
import com.anke.ice.inject.Id;
import com.anke.ice.inject.NotColumn;
import com.anke.ice.inject.Table;
import com.anke.ice.util.LoggerUtil;
import com.anke.ice.util.StringBuilderUtil;




public class DBHelper {
	private static final Logger logger = LoggerUtil.getInstance(DBHelper.class);

	private CenterDao centerDao;

	public CenterDao getCenterDao() {
		return centerDao;
	}

	public void setCenterDao(CenterDao centerDao) {
		this.centerDao = centerDao;
	}
	private MenberDao menberDao;

	public MenberDao getMenberDao() {
		return menberDao;
	}

	public void setMenberDao(MenberDao menberDao) {
		this.menberDao = menberDao;
	}
	
	private LoginDao loginDao;

	public LoginDao getLoginDao() {
		return loginDao;
	}

	public void setLoginDao(LoginDao loginDao) {
		this.loginDao = loginDao;
	}
	
	private LeftDao leftDao;

	public LeftDao getleftDao() {
		return leftDao;
	}
	public void setleftDao(LeftDao leftDao) {
		this.leftDao = leftDao;
	}

	private static DBHelper instance;

	public static DBHelper getInstance() {
		return instance;
	}

	public static DBHelper init() {
		if (instance == null)
			instance = new DBHelper();
		if (instance.getCenterDao() == null) {
			instance.setCenterDao(new CenterDaoImpl());
			IDConstant.CENTER_ID = instance.getCenterDao().genericID();
		}
		if (instance.getleftDao() == null) {
			instance.setleftDao(new LeftDaoImpl());
			
		}
		if (instance.getLoginDao() == null) {
			instance.setLoginDao(new LoginDaoImpl());
			
		}
		if (instance.getMenberDao() == null) {
			instance.setMenberDao(new MenberDaoImpl());
			
		}
		return instance;
	}

	private DBHelper() {

	}

	public Connection getConnection() {
		Connection con = null;
		String driver_class = null;
		String driver_url = null;
		String database_user = null;
		String database_password = null;
		try {
			InputStream fis = this.getClass().getResourceAsStream("/db.properties");
			Properties p = new Properties();
			p.load(fis);

			driver_class = p.getProperty("driver_class");
			driver_url = p.getProperty("driver_url");
			database_user = p.getProperty("database_user");
			database_password = p.getProperty("database_password");

			Class.forName(driver_class);
			con = DriverManager.getConnection(driver_url, database_user, database_password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return con;
	}

	/**
	 * 生成insert语句
	 * 
	 * @param model
	 * @return
	 */
	public static <T> String createInsertSql(T model) {
		Table table = model.getClass().getAnnotation(Table.class);
		String sql = "insert into " + table.tableName() + "(";
		Field[] fields = model.getClass().getDeclaredFields();
		for (Field field : fields) {
			try {
				if (model.getClass().getMethod("get" + StringBuilderUtil.toUpperCaseFirst(field.getName())).invoke(model) != null)
					sql += field.getName() + ",";
			} catch (IllegalAccessException e) {
			} catch (IllegalArgumentException e) {
			} catch (InvocationTargetException e) {
			} catch (NoSuchMethodException e) {
			} catch (SecurityException e) {
			}
		}
		sql = sql.substring(0, sql.length() - 1) + ") values(";
		for (Field field : fields) {
			try {
				if (model.getClass().getMethod("get" + StringBuilderUtil.toUpperCaseFirst(field.getName())).invoke(model) != null && !field.isAnnotationPresent(NotColumn.class)) {
					if (field.getType() == String.class)
						sql += "'" + model.getClass().getMethod("get" + StringBuilderUtil.toUpperCaseFirst(field.getName())).invoke(model) + "',";
					else if (field.getType() == Date.class)
						sql += "to_date('" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(model.getClass().getMethod("get" + StringBuilderUtil.toUpperCaseFirst(field.getName())).invoke(model))
								+ "','YYYY-MM-DD HH24:MI:SS'),";
					else
						sql += model.getClass().getMethod("get" + StringBuilderUtil.toUpperCaseFirst(field.getName())).invoke(model) + ",";
				}
			} catch (IllegalAccessException e) {
			} catch (IllegalArgumentException e) {
			} catch (InvocationTargetException e) {
			} catch (NoSuchMethodException e) {
			} catch (SecurityException e) {
			}
		}
		sql = sql.substring(0, sql.length() - 1) + ")";
		logger.debug(sql);
		return sql;
	}

	/**
	 * 生成update语句
	 * 
	 * @param model
	 * @return
	 */
	public static <T> String createUpdateSql(T model) {
		Table table = model.getClass().getAnnotation(Table.class);
		String sql = "update " + table.tableName() + " set ";
		String where = " where ";
		Field[] fields = model.getClass().getDeclaredFields();
		for (Field field : fields) {
			try {
				if (model.getClass().getMethod("get" + StringBuilderUtil.toUpperCaseFirst(field.getName())).invoke(model) != null && !field.isAnnotationPresent(NotColumn.class) && !field.isAnnotationPresent(Id.class)) {
					if (field.getType() == String.class)
						sql += field.getName() + "='" + model.getClass().getMethod("get" + StringBuilderUtil.toUpperCaseFirst(field.getName())).invoke(model) + "',";
					else if (field.getType() == Date.class)
						sql += field.getName() + "=to_date('" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(model.getClass().getMethod("get" + StringBuilderUtil.toUpperCaseFirst(field.getName())).invoke(model))
								+ "','YYYY-MM-DD HH24:MI:SS'),";
					else
						sql += field.getName() + "=" + model.getClass().getMethod("get" + StringBuilderUtil.toUpperCaseFirst(field.getName())).invoke(model) + ",";
				}
				if (field.isAnnotationPresent(Id.class))
					where += field.getName() + "=" + model.getClass().getMethod("get" + StringBuilderUtil.toUpperCaseFirst(field.getName())).invoke(model);
			} catch (IllegalAccessException e) {
			} catch (IllegalArgumentException e) {
			} catch (InvocationTargetException e) {
			} catch (NoSuchMethodException e) {
			} catch (SecurityException e) {
			}
		}
		sql = sql.substring(0, sql.length() - 1) + where;
		logger.debug(sql);
		return sql;
	}

	
	
}
