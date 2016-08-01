package com.anke.ice.dao.impl.oracle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.log4j.Logger;

import com.anke.ice.IDConstant;
import com.anke.ice.dao.DBHelper;
import com.anke.ice.dao.InstitutionDao;
import com.anke.ice.model.Center;
import com.anke.ice.model.CenterID;
import com.anke.ice.model.InstitutionModel;

import com.anke.ice.util.LoggerUtil;
import com.anke.ice.util.WhereClauseUtility;
import oracle.sql.CLOB;
import java.sql.Clob;
import java.sql.ResultSet;



public class InstitutionImpl extends BaseDaoImpl implements InstitutionDao{
	private static final Logger logger = LoggerUtil.getInstance(DBHelper.class);

	@Override
	public Map<String, Object> institutionlist(int pageNum, int pageSize, int id,String name,String volunteertype,String speciality,int isvalid) {
		Map<String, Object> page = new HashMap<String, Object>();
//		     System.out.println(volunteertype);
//	         System.out.println(speciality);
		try {
			 InstitutionModel molspeciality=runner.query(conn," select replace(wm_concat(dic.name),',',',') as spename from t_dictionary dic  where dic.id in('"+speciality+"') and dic.typecode='speciality'", new BeanHandler<InstitutionModel>(InstitutionModel.class));
		     String strspeciality=molspeciality.getSpename();
		     InstitutionModel molvolunteertype=runner.query(conn," select replace(wm_concat(dic.name),',',',') as volname from t_dictionary dic  where dic.id in('"+volunteertype+"') and dic.typecode='volunteertype'", new BeanHandler<InstitutionModel>(InstitutionModel.class));    
		     String strvolunteertype=molvolunteertype.getVolname();
//		     System.out.println(strvolunteertype);
//	         System.out.println(strspeciality);
		    StringBuilder sbSQL= new StringBuilder();
            sbSQL.append(" select ID,cetname,name,dupdatetime,thvolunteertype,thspeciality,ISTISVALID");
            sbSQL.append(" from ( select ist.ID,cet.name as cetname,ist.NAME as name,ist.UPDATETIME as dupdatetime,ist.VOLUNTEERTYPE as thvolunteertype,ist.SPECIALITY as thspeciality,(case when ist.ISVALID=1 then '有效' else '无效' end) istisvalid,rownum rn");
            sbSQL.append(" from t_institution ist");
            sbSQL.append(" left join t_center cet on cet.centerid=ist.centerid");
//            sbSQL.append(" where instr (speciality,'"+strspeciality+"')>0 and instr (volunteertype,'"+strvolunteertype+"')>0");
            sbSQL.append(" where 1=1");
            WhereClauseUtility.AddIntEqual("ist.id", id, sbSQL);//志愿者类型
            WhereClauseUtility.AddStringLike("ist.name", name, sbSQL);//申请地区
            WhereClauseUtility.AddStringLike("ist.volunteertype", strvolunteertype, sbSQL);//申请地区
            WhereClauseUtility.AddStringLike("ist.speciality", strspeciality, sbSQL);//申请地区
            WhereClauseUtility.AddIntEqual("ist.isvalid", isvalid, sbSQL);//志愿者类型
            sbSQL.append(" order by UPDATETIME desc");
            sbSQL.append(" )t where t.rn>" + ((pageNum - 1) * pageSize) + " and t.rn<=" + pageNum * pageSize);
            String sql = sbSQL.toString();
	
			List<InstitutionModel> rows = runner.query(conn, sql, new BeanListHandler<InstitutionModel>(InstitutionModel.class));
			int total =rows.size();
			page.put("total", total);
			page.put("rows", rows);
			page.put("pages", (total % pageSize != 0 ? total / pageSize + 1 : total / pageSize));
			page.put("to", pageNum * pageSize);
		} catch (SQLException e) {
			logger.error("查询机构列表失败", e.getCause());
		}
		return page;
	}
	
	
	public InstitutionModel  get(int id){
		
		try { 
			StringBuilder sbSQL= new StringBuilder();
			sbSQL.append(" select ist.ID,cet.name as centername,ist.NAME as name,ist.UPDATETIME,ist.volunteertype,ist.speciality,");
			sbSQL.append(" ist.isvalid ");
			sbSQL.append(" from t_institution ist");
			sbSQL.append(" left join t_center cet on cet.centerid=ist.centerid");
			sbSQL.append(" where 1=1");
			WhereClauseUtility.AddIntEqual("ist.id", id, sbSQL);//志愿者类型
			
			String sql = sbSQL.toString();
			
			  InstitutionModel getinstitution=runner.query(conn, sql, new BeanHandler<InstitutionModel>(InstitutionModel.class));
			  InstitutionModel clobdetail=runner.query(conn,"select detail from t_institution where id="+id, new BeanHandler<InstitutionModel>(InstitutionModel.class));
			  Clob clob = clobdetail.getDetail();
			   if(clob==null)
			   {
				   getinstitution.setStrdetail(null);   
			   }
			   else{  
			   String content;
			   try {
				content = ClobToString(clob);
				getinstitution.setStrdetail(content);
			   } catch (IOException e) {
				e.printStackTrace();
			   }   
			   }
			   System.out.println(getinstitution);
			   return getinstitution;
		} catch (SQLException e) {
			logger.error("查询接入点失败", e.getCause());
			return null;
		}
		
	}
	
	 public String ClobToString(Clob clob) throws SQLException, IOException {

	        String reString = "";
	        Reader is = clob.getCharacterStream();// 得到流
	        BufferedReader br = new BufferedReader(is);
	        String str = br.readLine();
	        StringBuffer sb = new StringBuffer();
	        while (str != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
	            sb.append(str);
	            str = br.readLine();
	        }
	        reString = sb.toString();
	        return reString;
	    }
	 
	 

		@Override
		public InstitutionModel saveOrUpdate(InstitutionModel bean) {
			try {
				 String strspec;
				 if(bean.getSpeciality().substring(0,1)==",")
				 {
					strspec=bean.getSpeciality().substring(1);
				 }
				 else
				 {	strspec=bean.getSpeciality();}
//				 System.out.println(strspe);
				 String strvolu=bean.getVolunteertype().substring(1);
				 InstitutionModel molspeciality=runner.query(conn," select replace(wm_concat(dic.name),',',',') as spename from t_dictionary dic  where dic.id in('"+strspec+"') and dic.typecode='speciality'", new BeanHandler<InstitutionModel>(InstitutionModel.class));
			     String strspeciality=molspeciality.getSpename();
			     bean.setSpeciality(strspeciality);
			     InstitutionModel molvolunteertype=runner.query(conn," select replace(wm_concat(dic.name),',',',') as volname from t_dictionary dic  where dic.id in('"+strvolu+"') and dic.typecode='volunteertype'", new BeanHandler<InstitutionModel>(InstitutionModel.class));    
			     String strvolunteertype=molvolunteertype.getVolname();
			     bean.setVolunteertype(strvolunteertype);
			     
			     String centersql="select cet.centerid as centerid from t_center cet where cet.name='"+bean.getCentername()+"'";
				 CenterID gci=runner.query(conn,centersql,new BeanHandler<CenterID>(CenterID.class));
				 if(gci==null)
				 {bean.setCenterid(null);}
				 else{ 
				 int comecenterid = gci.getCenterid();
				 bean.setCenterid(comecenterid);}
				
			     bean.setCentername(null);
			     
			     bean.setStrdetail(null);
			
			     bean.setUPDATETIME(new Date());

				if (bean.getID() <= 0) {
					int IstitutionID=genericIstitutionID();
					bean.setID(IstitutionID);
					sql = DBHelper.createInsertSql(bean);
					runner.insert(conn, sql, new BeanHandler<InstitutionModel>(InstitutionModel.class));
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
		
		public int genericIstitutionID() {
			try {
				InstitutionModel model = runner.query(conn, "select * from t_institution order by id desc", new BeanHandler<InstitutionModel>(InstitutionModel.class));
				if (model != null) {
					if (IDConstant.Institution_ID <= (model.getID() + 1))
						IDConstant.Institution_ID = model.getID() + 1;
					else
						IDConstant.Institution_ID++;
				}
			} catch (SQLException e) {
				logger.error("生成接入点编码失败", e.getCause());
			}
			return IDConstant.Institution_ID;
		}
}
