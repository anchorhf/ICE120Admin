package com.anke.ice.dao.impl.oracle;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.log4j.Logger;

import com.anke.ice.dao.DBHelper;
import com.anke.ice.dao.VolunteerApplyDao;
import com.anke.ice.model.VolunteerApplyModel;
import com.anke.ice.util.LoggerUtil;
import com.anke.ice.util.WhereClauseUtility;


public class VolunteerApplyImpl extends BaseDaoImpl implements VolunteerApplyDao{
	private static final Logger logger = LoggerUtil.getInstance(DBHelper.class);

	@Override
	public Map<String, Object> findVolunteerApply(int pageNum, int pageSize, String volunteer, String are
			,String institution,int volunteertype,int applystate,String applybegintime,String applyendtime) {
		Map<String, Object> page = new HashMap<String, Object>();
		try {

            StringBuilder sbSQL= new StringBuilder();
            sbSQL.append(" select ID,VOLUNTEERID,VOLUNTEER,AREAID,AREA");
            sbSQL.append(" ,INSTITUTIONID,INSTITUTION,TYPEID,VOLUNTEERTYPE");
            sbSQL.append(" ,VALIDPERIOD,APPLYSTATE,APPLYSTATENAME,SKILL,APPLYTIME,CHECKTIME,CHECKPERSON,rn");
            sbSQL.append(" from (");
            sbSQL.append(" select tva.ID,tva.VOLUNTEERID,tv.NAME as VOLUNTEER,AREAID,ta.NAME as AREA");
            sbSQL.append(" ,INSTITUTIONID,ti.NAME as INSTITUTION,TYPEID,tvt.NAME as VOLUNTEERTYPE");
            sbSQL.append(" ,VALIDPERIOD,APPLYSTATE");
            sbSQL.append(" ,(case when APPLYSTATE=0 then '待审核' when APPLYSTATE=1 then '审核通过' else '审核不通过' end) as APPLYSTATENAME");
            sbSQL.append(" ,SKILL,APPLYTIME,CHECKTIME,CHECKPERSON,rownum rn");
            sbSQL.append(" from T_VOLUNTEER_APPLY tva");
            sbSQL.append(" left join T_VOLUNTEER tv on tva.VOLUNTEERID=tv.ID");
            sbSQL.append(" left join T_AREA ta on tva.AREAID=ta.ID");
            sbSQL.append(" left join T_INSTITUTION ti on tva.INSTITUTIONID=ti.ID");
            sbSQL.append(" left join T_VOLUNTEER_TYPE tvt on tva.TYPEID=tvt.ID");
            sbSQL.append(" where 1=1");
            WhereClauseUtility.AddDateTimeGreaterThan("tva.APPLYTIME", applybegintime, sbSQL);//申请开始时间
            WhereClauseUtility.AddDateTimeLessThan("tva.APPLYTIME", applyendtime, sbSQL);//申请结束时间
            WhereClauseUtility.AddStringLike("tv.NAME", volunteer, sbSQL);//志愿者
            WhereClauseUtility.AddStringLike("ta.NAME", are, sbSQL);//申请地区
            WhereClauseUtility.AddStringLike("ti.NAME", institution, sbSQL);//申请机构
            WhereClauseUtility.AddIntEqual("tva.TYPEID", volunteertype, sbSQL);//志愿者类型
            WhereClauseUtility.AddIntEqual("tva.APPLYSTATE", applystate, sbSQL);//申请状态
            sbSQL.append(" order by tva.APPLYTIME");
            
            sbSQL.append(" )t where t.rn>" + ((pageNum - 1) * pageSize) + " and t.rn<=" + pageNum * pageSize);
			
            //sbSQL.append(" select count(*) from #tempX t ");
            //sbSQL.append(" drop table #tempX ");
            
            String sql = sbSQL.toString();
			/*String countSql = "select count(*) from #temp t ";
			int total = runner.query(conn, countSql, new ResultSetHandler<Integer>() {

				@Override
				public Integer handle(ResultSet rs) throws SQLException {
					if (rs.next())
						return rs.getInt(1);
					else
						return 1;
				}
			});*/
			List<VolunteerApplyModel> rows = runner.query(conn, sql, new BeanListHandler<VolunteerApplyModel>(VolunteerApplyModel.class));
			int total =rows.size();
			page.put("total", total);
			page.put("rows", rows);
			page.put("pages", (total % pageSize != 0 ? total / pageSize + 1 : total / pageSize));
			page.put("to", pageNum * pageSize);
		} catch (SQLException e) {
			logger.error("查询志愿者申请列表失败", e.getCause());
		}
		return page;
	}

}
