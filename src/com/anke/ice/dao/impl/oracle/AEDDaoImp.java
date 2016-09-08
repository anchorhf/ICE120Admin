package com.anke.ice.dao.impl.oracle;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.SQLException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.log4j.Logger;

import com.anke.ice.IDConstant;
import com.anke.ice.dao.AEDDao;
import com.anke.ice.dao.DBHelper;
import com.anke.ice.dao.InstitutionDao;
import com.anke.ice.dao.MsgDao;
import com.anke.ice.model.AEDModel;
import com.anke.ice.model.Center;
import com.anke.ice.model.CenterID;
import com.anke.ice.model.InstitutionModel;
import com.anke.ice.model.MYTreeNode;
import com.anke.ice.model.MsgModel;
import com.anke.ice.model.InstitutionCenterModel;

import com.anke.ice.util.LoggerUtil;
import com.anke.ice.util.WhereClauseUtility;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.ResultSet;

public class AEDDaoImp extends BaseDaoImpl implements AEDDao{
	private static final Logger logger = LoggerUtil.getInstance(AEDDaoImp.class);
	//protected static Connection conn;//建立数据库连接
	private static int id = 1;
	
	public int generiaID() {
		Connection conn = DBHelper.getInstance().getConnection();
		//int id=1;
		try {
			AEDModel model = runner.query(conn, "select * from t_aed order by id desc", new BeanHandler<AEDModel>(AEDModel.class));
			if (model != null) {
				if (id <= (model.getId()+ 1))
					id = model.getId() + 1;
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
	public Map<String, Object> AEDList(int pageNum, int pageSize,double mlongitude,double mlatitude,String marea,String maddress
			,String mbuilding,String mplace,int misvalid,String releasebegintime,String releaseendtime)
	{
		Map<String, Object> page = new HashMap<String, Object>();
		Connection conn = DBHelper.getInstance().getConnection();
		try {
			double longitudeval=serieschange(mlongitude);
			double latitudeval=serieschange(mlatitude);
			StringBuilder sbSQL = new StringBuilder();
			sbSQL.append(" select id as tabid,area as tabarea,address as tabaddress,building as tabbuilding,place as tabplace,");
			sbSQL.append(" longitude as tablongitude,latitude as tablatitude,createtime as tabcreatetime,remark as tabremark,");
			sbSQL.append(" (case when isvalid=1 then '有效' else '无效' end) as tabisvalid,rn");
			sbSQL.append(" from (");
			sbSQL.append(" select id,area,address,building,place,longitude,latitude,createtime,remark,isvalid,rownum rn");
			sbSQL.append(" from (");
			sbSQL.append(" select ta.id,ta.area,ta.address,ta.building,ta.place,ta.longitude,ta.latitude,ta.createtime,ta.remark,ta.isvalid");
			sbSQL.append(" from t_aed ta");
			sbSQL.append(" where 1=1");
			WhereClauseUtility.AddDateTimeGreaterThan("ta.createtime", releasebegintime, sbSQL);// 开始时间
			WhereClauseUtility.AddDateTimeLessThan("ta.createtime", releaseendtime, sbSQL);// 结束时间
			WhereClauseUtility.AddStringLike("ta.area", marea, sbSQL);// 
			WhereClauseUtility.AddStringLike("ta.address", maddress, sbSQL);// 
			WhereClauseUtility.AddStringLike("ta.building", mbuilding, sbSQL);// 
			WhereClauseUtility.AddStringLike("ta.place", mplace, sbSQL);// 
			WhereClauseUtility.AddDoubleEqual("ta.longitude", longitudeval, sbSQL);// 
			WhereClauseUtility.AddDoubleEqual("ta.latitude", latitudeval, sbSQL);// 
			WhereClauseUtility.AddIntEqual("ta.isvalid", misvalid, sbSQL);// 
			sbSQL.append(" order by ta.id DESC");
			sbSQL.append(" )t");
			sbSQL.append(" )tt");
			StringBuilder sbSQLR = new StringBuilder();
			sbSQLR.append(sbSQL +" where tt.rn>" + ((pageNum - 1) * pageSize) + " and tt.rn<=" + pageNum * pageSize);
		
			String sql = sbSQLR.toString();
//			System.out.println(sql);
			StringBuilder sbSQLT = new StringBuilder();//取总共条数
			sbSQLT.append("select count(*) from ("+sbSQL+")ttt");
			String countSql = sbSQLT.toString();

			int total = runner.query(conn, countSql, new ScalarHandler<BigDecimal>()).intValue();

			List<AEDModel> rows = runner.query(conn, sql,new BeanListHandler<AEDModel>(AEDModel.class));
			
			page.put("total", total);
			page.put("rows", rows);
			page.put("pages", (total % pageSize != 0 ? total / pageSize + 1 : total / pageSize));
			page.put("to", pageNum * pageSize);
		} catch (SQLException e) {
			logger.error("查询AED列表失败", e.getCause());
		}
		finally{
			DbUtils.closeQuietly(conn);
		}
		return page;
	}
	
	public double serieschange(double series){
		if(series==0.0){
			series=-1.0;
		}
		return series;
	}	
	
	@Override
	public AEDModel saveOrUpdate(AEDModel bean){	
		Connection conn = DBHelper.getInstance().getConnection();
		try { 
			bean.setTabid(null);   
			bean.setTablatitude(null);
			bean.setTablongitude(null);
			if (bean.getId()<= 0) {
				bean.setId(generiaID());
				bean.setCreatetime(new Date());
				sql = DBHelper.createInsertSql(bean);
				runner.insert(conn, sql, new BeanHandler<AEDModel>(AEDModel.class));
			} else {
				sql = DBHelper.createUpdateSql(bean);
				runner.update(conn, sql);
			   }
			} catch (SQLException e) {
			logger.error("保存AED失败", e);
			return null;
		}
		finally{
			DbUtils.closeQuietly(conn);
		}
		return bean;
	}
	
	
	public AEDModel get(int id){

		Connection conn = DBHelper.getInstance().getConnection();
		try { 
			StringBuilder sbSQL= new StringBuilder();
			sbSQL.append(" select ta.id,ta.area,ta.address,ta.building,ta.place,ta.longitude,ta.latitude,ta.isvalid,ta.remark");
		    sbSQL.append(" from t_aed ta");
			sbSQL.append(" where 1=1");
			WhereClauseUtility.AddIntEqual("ta.id", id, sbSQL);
			String sql = sbSQL.toString();
            return 	runner.query(conn, sql, new BeanHandler<AEDModel>(AEDModel.class));
		} catch (SQLException e) {
			logger.error("获取AED失败", e.getCause());
			return null;
		}
		finally{
			DbUtils.closeQuietly(conn);
		}
		
	}
}
