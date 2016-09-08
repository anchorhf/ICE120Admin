package com.anke.ice.dao.impl.oracle;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.log4j.Logger;

import com.anke.ice.IDConstant;
import com.anke.ice.dao.CoInstitutionDao;
import com.anke.ice.dao.DBHelper;
import com.anke.ice.dao.InstitutionDao;
import com.anke.ice.dao.MsgDao;
import com.anke.ice.model.Center;
import com.anke.ice.model.CenterID;
import com.anke.ice.model.CoInstitutionModel;
import com.anke.ice.model.InstitutionModel;
import com.anke.ice.model.MYTreeNode;
import com.anke.ice.model.MsgModel;
import com.anke.ice.model.InstitutionCenterModel;

import com.anke.ice.util.LoggerUtil;
import com.anke.ice.util.WhereClauseUtility;
import com.cloopen.rest.sdk.utils.encoder.BASE64Decoder;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.ResultSet;

public class CoInstitutionDaoImp extends BaseDaoImpl implements CoInstitutionDao{
	private static final Logger logger = LoggerUtil.getInstance(CoInstitutionDaoImp.class);
	//protected static Connection conn;//建立数据库连接
	
	@Override
	public Map<String, Object>CoInstitutionList(int pageNum, int pageSize,String mapplyperson,String mcheckperson,int mapplystate,
			int mapplyorcheck,int institutionid,String applystartTime,String applyendTime,int mcenter,int minstitution)
	   {
		Map<String, Object> page = new HashMap<String, Object>();
		Connection conn = DBHelper.getInstance().getConnection();
		try {
			
			int centeridval=-1;
			InstitutionModel detail=runner.query(conn,"select * from t_institution where id="+institutionid, new BeanHandler<InstitutionModel>(InstitutionModel.class));
			if(detail==null){
				mapplyorcheck=2;
			}else{
				centeridval=detail.getCenterid();
			}
			
			StringBuilder sbSQLT = new StringBuilder();
			sbSQLT.append(" select institutionid,centerid,applyperson,applytime,checktime,checkperson,applystate,centername,institutioname,applyorcheck,auditresult,rn");
			sbSQLT.append(" from (");
			sbSQLT.append(" select institutionid,centerid,applyperson,applytime,checktime,checkperson,applystate,centername,institutioname,applyorcheck,auditresult,rownum rn");
			sbSQLT.append(" from (");
			sbSQLT.append(" select tic.institutionid,tic.centerid,tic.applyperson,tic.applytime,tic.checktime,tic.checkperson,(case when tic.applystate=0 then '待同意' when tic.applystate=1 then '同意' else '不同意' end) as applystate,tc.name as centername,ti.name as institutioname,'待处理' as applyorcheck,tic.auditresult");
			sbSQLT.append(" from t_institution_center tic");
			sbSQLT.append(" left join t_center tc on tc.centerid=tic.centerid");
			sbSQLT.append(" left join t_institution ti on ti.id=tic.institutionid");
			sbSQLT.append(" where 1=1");
			WhereClauseUtility.AddDateTimeGreaterThan("tic.applytime", applystartTime, sbSQLT);
			WhereClauseUtility.AddDateTimeLessThan("tic.applytime", applyendTime, sbSQLT);
			WhereClauseUtility.AddStringLike("tic.checkperson", mcheckperson, sbSQLT);
			WhereClauseUtility.AddStringLike("tic.applyperson", mapplyperson, sbSQLT);
			WhereClauseUtility.AddIntEqual("tic.applystate", mapplystate, sbSQLT);
			WhereClauseUtility.AddIntEqual("tic.institutionid", minstitution, sbSQLT);
			WhereClauseUtility.AddIntEqual("tic.centerid", mcenter, sbSQLT);
			
		
			StringBuilder sbSQL = new StringBuilder();
			
			sbSQL.append(" select institutionid,centerid,applyperson,applytime,checktime,checkperson,applystate,centername,institutioname,applyorcheck,auditresult,rn");
			sbSQL.append(" from (");
			sbSQL.append(" select institutionid,centerid,applyperson,applytime,checktime,checkperson,applystate,centername,institutioname,applyorcheck,auditresult,rownum rn");
			sbSQL.append(" from (");
			sbSQL.append(" select tic.institutionid,tic.centerid,tic.applyperson,tic.applytime,tic.checktime,tic.checkperson,(case when tic.applystate=0 then '待同意' when tic.applystate=1 then '同意' else '不同意' end) as applystate,tc.name as centername,ti.name as institutioname,'申请同意' as applyorcheck,tic.auditresult");
			sbSQL.append(" from t_institution_center tic");
			sbSQL.append(" left join t_center tc on tc.centerid=tic.centerid");
			sbSQL.append(" left join t_institution ti on ti.id=tic.institutionid");
			sbSQL.append(" where 1=1");
			WhereClauseUtility.AddDateTimeGreaterThan("tic.applytime", applystartTime, sbSQL);
			WhereClauseUtility.AddDateTimeLessThan("tic.applytime", applyendTime, sbSQL);
			WhereClauseUtility.AddStringLike("tic.checkperson", mcheckperson, sbSQL);
			WhereClauseUtility.AddStringLike("tic.applyperson", mapplyperson, sbSQL);
			WhereClauseUtility.AddIntEqual("tic.applystate", mapplystate, sbSQL);
			WhereClauseUtility.AddIntEqual("tic.institutionid", minstitution, sbSQL);
			WhereClauseUtility.AddIntEqual("tic.centerid", mcenter, sbSQL);
			
			StringBuilder sbSQLV = new StringBuilder();
			
			if(mapplyorcheck==-1){
				
				sbSQLT.append(" and tic.institutionid="+institutionid+" and tic.centerid!="+centeridval+"");
				sbSQLT.append(" order by  tic.applytime DESC");
				sbSQLT.append(" )t");
				sbSQLT.append(" )tt");
				sbSQLT.append(" UNION "+sbSQL);
				sbSQLT.append(" and tic.centerid="+centeridval+"");
				sbSQLT.append(" order by  tic.applytime DESC");
				sbSQLT.append(" )t");
				sbSQLT.append(" )tt");
				
				sbSQLV=sbSQLT;
			}else if(mapplyorcheck==0){
				sbSQLT.append(" and tic.institutionid="+institutionid+" and tic.centerid!="+centeridval+"");
				sbSQLT.append(" order by  tic.applytime DESC");
				sbSQLT.append(" )t");
				sbSQLT.append(" )tt");
				sbSQLV=sbSQLT;
			}else if(mapplyorcheck==1){
				sbSQL.append(" and tic.centerid="+centeridval+"");
				sbSQL.append(" order by  tic.applytime DESC");
				sbSQL.append(" )t");
				sbSQL.append(" )tt");
				sbSQLV=sbSQL;
			}else{
				sbSQLT.append(" order by  tic.applytime DESC");
				sbSQLT.append(" )t");
				sbSQLT.append(" )tt");
				sbSQLV=sbSQLT;
			}
			StringBuilder sbSQLR = new StringBuilder();
			sbSQLR.append(sbSQLV +" where tt.rn>" + ((pageNum - 1) * pageSize) + " and tt.rn<=" + pageNum * pageSize);
		
			String sql = sbSQLR.toString();
			
//			System.out.println(sql);
			
			StringBuilder sbSQLC = new StringBuilder();//取总共条数
			sbSQLC.append("select count(*) from ("+sbSQLV+")ttt");
			String countSql = sbSQLC.toString();

			int total = runner.query(conn, countSql, new ScalarHandler<BigDecimal>()).intValue();

			List<CoInstitutionModel> rows = runner.query(conn, sql,new BeanListHandler<CoInstitutionModel>(CoInstitutionModel.class));
			
			page.put("total", total);
			page.put("rows", rows);
			page.put("pages", (total % pageSize != 0 ? total / pageSize + 1 : total / pageSize));
			page.put("to", pageNum * pageSize);
		} catch (SQLException e) {
			logger.error("查询合作机构列表失败", e.getCause());
		}
		finally{
			DbUtils.closeQuietly(conn);
		}
		return page;
	}
	
	public CoInstitutionModel get(int institutionid,int centerid){
		Connection conn = DBHelper.getInstance().getConnection();
		try { 
			StringBuilder sbSQL= new StringBuilder();
			sbSQL.append(" select tc.name as centername,ti.name as institutioname,tic.institutionid,tic.centerid,tic.applyperson,tic.applytime,tic.checktime,tic.checkperson,(case when tic.applystate=0 then '待同意' when tic.applystate=1 then '同意' else '不同意' end) as applystate,tic.auditresult");
			sbSQL.append(" from t_institution_center tic");
			sbSQL.append(" left join t_center tc on tc.centerid=tic.centerid");
			sbSQL.append(" left join t_institution ti on ti.id=tic.institutionid");
			sbSQL.append(" where 1=1");
			WhereClauseUtility.AddIntEqual("tic.institutionid", institutionid, sbSQL);
			WhereClauseUtility.AddIntEqual("tic.centerid", centerid, sbSQL);
			String sql = sbSQL.toString();
			CoInstitutionModel getinfo=runner.query(conn, sql, new BeanHandler<CoInstitutionModel>(CoInstitutionModel.class));
		
			return getinfo;
		} catch (SQLException e) {
			logger.error("得到合作机构信息失败", e.getCause());
			return null;
		}
		finally{
			DbUtils.closeQuietly(conn);
		}
		
	}
	
	
	@Override
	public int saveOrUpdate(int institutionid,int centerid,int applystate,String uname,String auditresult) {

		Connection conn = DBHelper.getInstance().getConnection();
		try {
			StringBuilder sbSQL = new StringBuilder();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			String nowdate = df.format(new Date());
			sbSQL.append("update t_institution_center set applystate=" + applystate);
			sbSQL.append(",checktime=to_date('" + nowdate + "','yyyy-mm-dd hh24:mi:ss')");
			sbSQL.append(",checkperson='" + uname + "'");
			sbSQL.append(",auditresult='" + auditresult + "' where institutionid=" + institutionid +" and centerid="+centerid);
			String sql = sbSQL.toString();
//			System.out.println(sql);
		    runner.update(conn, sql);
		    return 1;
		} catch (SQLException e) {
			logger.error("审核信息失败", e.getCause());
			return 0;
		}
		finally{
			DbUtils.closeQuietly(conn);
		}
	}
     
	
}
