package com.anke.ice.dao.impl.oracle;

import java.sql.SQLException;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.log4j.Logger;
import com.anke.ice.dao.LoginDao;
import com.anke.ice.model.CheckLogin;
import com.anke.ice.model.B_Work;
import com.anke.ice.util.LoggerUtil;
import com.anke.ice.IDConstant;
import com.anke.ice.core.IcePasswordEncoder;



public class LoginDaoImpl extends BaseDaoImpl implements LoginDao {
	private static final Logger logger = LoggerUtil.getInstance(LoginDaoImpl.class);
	@Override
	public String judgelogin(CheckLogin bean) {
		try {
			String rawPass=bean.getPassword();
			Object salt =16;
		    String retvalue=new IcePasswordEncoder().encodePassword(rawPass, salt);
//		    System.out.println(retvalue);
			String getresult=runner.query(conn,"select * from T_REGUSER d where TELPHONE='"+bean.getuserName()+"' and PASSWORD='"+retvalue+"'",new BeanHandler<String>(String.class));		
			if (getresult!=null) {
//			System.out.println(1);	
//			return "ROLE_USER";
				return "2";
			}
			else{
				String rawPasstwo=bean.getPassword();
				Object salttwo =16;
			    String retvaluetwo=new IcePasswordEncoder().encodePassword(rawPasstwo, salttwo);
				B_Work getdata=runner.query(conn,"select newrole,institutionid from B_WORKER t where LOGINNAME='"+bean.getuserName()+"' and PASSWORD='"+retvaluetwo+"'",new BeanHandler<B_Work>(B_Work.class));
//				System.out.println(getresulttwo);
				if (getdata != null) {
			        String contactdata=getdata.getNewrole()+"/"+getdata.getInstitutionid();
					System.out.println(contactdata);
					return contactdata;
//					return "ROLE_ADMIN";
					}
			    else
			    {return null;}
			}
			
		} catch (SQLException e) {
			logger.error("判断用户登录信息失败！", e.getCause());
			return null;
		}

    }

}
	

