package com.anke.ice.dao.impl.oracle;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

import com.anke.ice.dao.DBHelper;
import com.anke.ice.dao.VolunteerApplyDao;
import com.anke.ice.model.Center;
import com.anke.ice.model.DictionaryModel;
import com.anke.ice.model.VolunteerApplyModel;
import com.anke.ice.model.VolunteerAttachmentModel;
import com.anke.ice.util.LoggerUtil;
import com.anke.ice.util.WhereClauseUtility;

public class VolunteerApplyImpl extends BaseDaoImpl implements VolunteerApplyDao {
	private static final Logger logger = LoggerUtil.getInstance(VolunteerApplyImpl.class);
	//protected static Connection conn;//建立数据库连接

	@Override
	public Map<String, Object> findVolunteerApply(int pageNum, int pageSize, String volunteer, String are
			,String institution, int volunteertype, int applystate, String applybegintime, String applyendtime
			,String skill,int institutionid) {
		Map<String, Object> page = new HashMap<String, Object>();
		Connection conn = DBHelper.getInstance().getConnection();
		try {

			//System.out.println("institutionid:"+institutionid);
			StringBuilder sbSQL = new StringBuilder();
			sbSQL.append(" select ID,VOLUNTEERID,VOLUNTEER,AREAID,AREA");
			sbSQL.append(" ,INSTITUTIONID,INSTITUTION,TYPEID,VOLUNTEERTYPE,AUDITRESULT");
			sbSQL.append(" ,VALIDPERIOD,APPLYSTATE,APPLYSTATENAME,SKILL,APPLYTIME,CHECKTIME,CHECKPERSON,rn");
			sbSQL.append(" from (");
			sbSQL.append(" select ID,VOLUNTEERID,VOLUNTEER,AREAID,AREA");
			sbSQL.append(" ,INSTITUTIONID,INSTITUTION,TYPEID,VOLUNTEERTYPE,AUDITRESULT");
			sbSQL.append(" ,VALIDPERIOD,APPLYSTATE,APPLYSTATENAME,SKILL,APPLYTIME,CHECKTIME,CHECKPERSON,rownum rn");
			sbSQL.append(" from (");
			sbSQL.append(" select tva.ID,tva.USERID as VOLUNTEERID,tv.NAME as VOLUNTEER,AREAID,ta.NAME as AREA");
			sbSQL.append(" ,INSTITUTIONID,ti.NAME as INSTITUTION,TYPEID,tvt.NAME as VOLUNTEERTYPE");
			sbSQL.append(" ,VALIDPERIOD,APPLYSTATE");
			sbSQL.append(" ,(case when APPLYSTATE=0 then '待审核' when APPLYSTATE=1 then '审核通过' else '审核不通过' end) as APPLYSTATENAME");
			sbSQL.append(" ,SKILL,APPLYTIME,CHECKTIME,CHECKPERSON,AUDITRESULT");
			sbSQL.append(" from T_VOLUNTEER_APPLY tva");
			sbSQL.append(" left join T_VOLUNTEER tv on tva.USERID=tv.USERID");
			sbSQL.append(" left join T_AREA ta on tva.AREAID=ta.ID");
			sbSQL.append(" left join T_INSTITUTION ti on tva.INSTITUTIONID=ti.ID");
			sbSQL.append(" left join T_DICTIONARY tvt on tva.TYPEID=tvt.ID");
			sbSQL.append(" where 1=1");
			WhereClauseUtility.AddDateTimeGreaterThan("tva.APPLYTIME", applybegintime, sbSQL);// 申请开始时间
			WhereClauseUtility.AddDateTimeLessThan("tva.APPLYTIME", applyendtime, sbSQL);// 申请结束时间
			WhereClauseUtility.AddStringLike("tv.NAME", volunteer, sbSQL);// 志愿者
			WhereClauseUtility.AddStringLike("ta.NAME", are, sbSQL);// 申请地区
			WhereClauseUtility.AddStringLike("ti.NAME", institution, sbSQL);// 申请机构
			WhereClauseUtility.AddStringLike("tva.SKILL", skill, sbSQL);// 技能
			WhereClauseUtility.AddIntEqual("tva.TYPEID", volunteertype, sbSQL);// 志愿者类型
			WhereClauseUtility.AddIntEqual("tva.APPLYSTATE", applystate, sbSQL);// 申请状态
			//如果不是超级管理员，就查询本机构和合作机构的信息
			if(institutionid!=-2){
				sbSQL.append(" and (tva.APPLYSTATE=1 or tva.institutionid="+institutionid+")");
				sbSQL.append(" and (tva.institutionid="+institutionid+" or INSTR((select ',' || wm_concat(tic.institutionid)  || ','");
				sbSQL.append("     from T_INSTITUTION ti");
				sbSQL.append("     left join T_INSTITUTION_CENTER tic on ti.centerid = tic.centerid");
				sbSQL.append("     where tic.APPLYSTATE=1 and ti.id="+institutionid+"), ',' ||  TRIM(TO_CHAR(tva.INSTITUTIONID))  || ',')>0)");
			}
			/*if(applystate!=-1){
				sbSQL.append(" and tva.APPLYSTATE="+applystate);
			}*/
			sbSQL.append(" order by tva.APPLYTIME DESC");

			sbSQL.append(" )t");
			sbSQL.append(" )tt");
			StringBuilder sbSQLR = new StringBuilder();
			sbSQLR.append(sbSQL +" where tt.rn>" + ((pageNum - 1) * pageSize) + " and tt.rn<=" + pageNum * pageSize);

			String sql = sbSQLR.toString();
			
			StringBuilder sbSQLT = new StringBuilder();//取总共条数
			sbSQLT.append("select count(*) from ("+sbSQL+")ttt");
			String countSql = sbSQLT.toString();

			int total = runner.query(conn, countSql, new ScalarHandler<BigDecimal>()).intValue();

			List<VolunteerApplyModel> rows = runner.query(conn, sql,
					new BeanListHandler<VolunteerApplyModel>(VolunteerApplyModel.class));
			
			page.put("total", total);
			page.put("rows", rows);
			page.put("pages", (total % pageSize != 0 ? total / pageSize + 1 : total / pageSize));
			page.put("to", pageNum * pageSize);
		} catch (SQLException e) {
			logger.error("查询志愿者申请列表失败", e.getCause());
		}
		finally{
			DbUtils.closeQuietly(conn);
		}
		return page;
	}

	// 根据ID修改志愿者审核状态和修改志愿者表的是否有效
	@Override
	public int updateApplyState(int id, int applyState, int volunteerid, String uname,String auditresult) {

		Connection conn = DBHelper.getInstance().getConnection();
		try {
			StringBuilder sbSQL = new StringBuilder();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			String nowdate = df.format(new Date());
			Calendar curr = Calendar.getInstance();
			curr.set(Calendar.YEAR,curr.get(Calendar.YEAR)+1);//当前时间加一年
			String validperiod=df.format(curr.getTime());//有效期限
			
			sbSQL.append("update T_VOLUNTEER_APPLY set applystate=" + applyState);
			sbSQL.append(",CHECKTIME=to_date('" + nowdate + "','yyyy-mm-dd hh24:mi:ss')");//审核时间
			if (applyState == 1) {
				// sbSQL.append(";update T_VOLUNTEER set ISVALID=1 where
				// ID="+volunteerid+" ");
				//updateVolunteerIsValid(volunteerid, 1);//注释暂时不更新志愿者表的是否有效为有效
				sbSQL.append(",VALIDPERIOD=to_date('" + validperiod + "','yyyy-mm-dd hh24:mi:ss')");//有效期限
			}
			sbSQL.append(",CHECKPERSON='" + uname + "'");
			sbSQL.append(",AUDITRESULT='" + auditresult + "' where id=" + id);
			String sql = sbSQL.toString();
			// Object [] params = new Object[]{volunteerid};

			return runner.update(conn, sql);
		} catch (SQLException e) {
			/*if (applyState == 1) {
				updateVolunteerIsValid(volunteerid, 0);
			}*/
			logger.error("修改志愿者审核信息失败", e.getCause());
			return 0;
		}
		finally{
			DbUtils.closeQuietly(conn);
		}
	}

	// 修改志愿者为有效
	public int updateVolunteerIsValid(int id, int ISVALID) {
		Connection conn = DBHelper.getInstance().getConnection();
		try {

			return runner.update(conn, "update T_VOLUNTEER set ISVALID=" + ISVALID + " where USERID=" + id);
		} catch (SQLException e) {
			logger.error("修改志愿者为有效或无效失败", e.getCause());
			return 0;
		}
		finally{
			DbUtils.closeQuietly(conn);
		}
	}
	
	//根据志愿者申请编码，获取志愿者所有信息
	@Override
	public VolunteerApplyModel getVolunteerAndApply(int id) {

		Connection conn = DBHelper.getInstance().getConnection();
		try {
			StringBuilder sbSQL = new StringBuilder();
			sbSQL.append(" select tva.ID,tva.USERID as VOLUNTEERID,tv.NAME as VOLUNTEER,AREAID,ta.NAME as AREA");
			sbSQL.append(" ,INSTITUTIONID,ti.NAME as INSTITUTION,TYPEID,tvt.NAME as VOLUNTEERTYPE");
			sbSQL.append(" ,VALIDPERIOD,APPLYSTATE");
			sbSQL.append(" ,(case when APPLYSTATE=0 then '待审核' when APPLYSTATE=1 then '审核通过' else '审核不通过' end) as APPLYSTATENAME");
			sbSQL.append(" ,SKILL,APPLYTIME,CHECKTIME,CHECKPERSON");
			sbSQL.append(" ,tv.NAME,tv.SEX,tv.BIRTHDAY,tv.IDCARDNO,tv.SPECIALITY,tva.AUDITRESULT");
			sbSQL.append(" ,(case when tv.ISVALID=1 then '有效' else '无效' end) as ISVALID");
			sbSQL.append(" from T_VOLUNTEER_APPLY tva");
			sbSQL.append(" left join T_VOLUNTEER tv on tva.USERID=tv.USERID");
			sbSQL.append(" left join T_AREA ta on tva.AREAID=ta.ID");
			sbSQL.append(" left join T_INSTITUTION ti on tva.INSTITUTIONID=ti.ID");
			sbSQL.append(" left join T_DICTIONARY tvt on tva.TYPEID=tvt.ID");
			sbSQL.append(" where tva.ID="+id);
			
			return runner.query(conn, sbSQL.toString(), new BeanHandler<VolunteerApplyModel>(VolunteerApplyModel.class));
		} catch (SQLException e) {
			logger.error("查询志愿者信息失败", e.getCause());
			return null;
		}
		finally{
			DbUtils.closeQuietly(conn);
		}
	}
	
	//根据志愿者申请编码查询附件表信息
	@Override
	public List<VolunteerAttachmentModel> GetAttachment(int applyid)
	{
		Connection conn = DBHelper.getInstance().getConnection();
		//UTL_RAW.CAST_TO_VARCHAR2(CONTENT) as 
		StringBuilder sbSQL= new StringBuilder();
        sbSQL.append(" select ID,APPLYID,ATTACHMENTURL from T_VOLUNTEER_APPLY_ATTACHMENT where APPLYID="+applyid);
        List<VolunteerAttachmentModel> rows=null;
		try {
			rows = runner.query(conn, sbSQL.toString(), new BeanListHandler<VolunteerAttachmentModel>(VolunteerAttachmentModel.class));
			return rows;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("根据志愿者申请编码查询附件表信息失败", e.getCause());
			e.printStackTrace();
			return rows;
		}
		finally{
			DbUtils.closeQuietly(conn);
		}
	}
}
