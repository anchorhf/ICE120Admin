package com.anke.ice.service;

import java.util.Map;

import javax.ws.rs.BeanParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.anke.ice.model.Center;
import com.anke.ice.model.InstitutionModel;

import com.anke.ice.dao.DBHelper;



@Path("/institution")
@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
public class InstitutionService {
	/**
	 * 分页查询志愿者申请信息
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param keyword
	 * @return
	 */
	@POST
	@Path("/list")
	public Map<String, Object> query(@DefaultValue("1") @FormParam("page") int pageNum, @DefaultValue("20") @FormParam("rows") int pageSize, @FormParam("id") int id,
			@FormParam("name") String name,@FormParam("volunteertype") String volunteertype,@FormParam("speciality") String speciality,@FormParam("isvalid") int isvalid) {
		return DBHelper.getInstance().getInstitutionDao().institutionlist(pageNum, pageSize, id,name, volunteertype, speciality,isvalid);
	}
	
	/**
	 * 通过ID获取接入点信息
	 * 
	 * @param id
	 * @return
	 */
	@GET
	@Path("/get")
	public InstitutionModel getById(@QueryParam("id") int id) {
		return DBHelper.getInstance().getInstitutionDao().get(id);
	}
	
	/**
	 * 修改接入点信息
	 * 
	 * @param bean
	 * @return
	 */
	@POST
	@Path("/modify")
	public boolean modifiy(@BeanParam InstitutionModel bean) {
		return DBHelper.getInstance().getInstitutionDao().saveOrUpdate(bean) != null;
	}

	
	
}
