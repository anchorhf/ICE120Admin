package com.anke.ice.service;

import java.util.Map;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.anke.ice.core.IcePasswordEncoder;
import com.anke.ice.dao.DBHelper;
import com.anke.ice.model.B_Work;

@Path("/worker")
@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
public class B_WorkerService {
	
	/**
	 * 分页查询用户信息
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param name
	 * @return
	 */
	@POST
	@Path("/list")
	public Map<String, Object> query(@DefaultValue("1") @FormParam("page") int pageNum, @DefaultValue("20") @FormParam("rows") int pageSize
			 , @FormParam("loginname") String loginname, @FormParam("institutionid") int institutionid){
		return DBHelper.getInstance().getB_WorkerDao().getList(pageNum, pageSize, loginname, institutionid);
	}
	
	//新增或者修改用户信息
	@POST
	@Path("/modify")
	public int modifiy(@FormParam("id") int id,@FormParam("loginname") String loginname
			,@FormParam("password") String password,@FormParam("role") String role
			,@FormParam("centerid") int centerid,@FormParam("passwordstate") int passwordstate
			,@FormParam("newrole") String newrole
			,@FormParam("oldpassword") String oldpassword,@FormParam("oldDBpassword") String oldDBpassword
			,@FormParam("institutionid") int institutionid){
		int state=0;
		int you=0;
		//如果是修改用户信息，并且密码按钮选择还原初始或者修改密码（如果是新增用户，只是加密下密码）
		if(passwordstate==2 || passwordstate==3|| passwordstate==11 || passwordstate==12){

			Object salt =16;
			password=new IcePasswordEncoder().encodePassword(password, salt);
			if(passwordstate==3){
				oldpassword=new IcePasswordEncoder().encodePassword(oldpassword, salt);
				if(!oldpassword.equals(oldDBpassword)){
					state=3;//原密码与数据库不同
					return state;
				}
			}
		}
		//如果是新增，并且用户名数据已经存在
		if(id==0){
			you=DBHelper.getInstance().getB_WorkerDao().getLoginname(loginname);
			if(you==1){
                state=9;//用户名数据库已经存在
				return state;
			}
		}
		B_Work bean = new B_Work();
		bean.setId(id);
		bean.setLoginname(loginname);
		bean.setPassword(password);
		bean.setRole(role);
		if(centerid==0){
			bean.setCenterid(null);
		}
		else{
			bean.setCenterid(centerid);
		}
		bean.setNewrole(newrole);
		/*if(newrole.indexOf("0")>=0){
			institutionid=-2;//如果包含超级管理员角色权限，机构ID为-2
		}*/
		bean.setInstitutionid(institutionid);
		DBHelper.getInstance().getB_WorkerDao().saveOrUpdate(bean);
		state=1;
		return state;
	}
	

	//对密码进行MD5加密
	@POST
	@Path("/MD5RawPass")
	public String GetMD5rawPass(@FormParam("password") String password){

		System.out.println(password);
		Object salt =16;
		password=new IcePasswordEncoder().encodePassword(password, salt);
		return password;
	}
}
