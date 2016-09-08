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

import com.anke.ice.dao.DBHelper;
import com.anke.ice.model.Center;

@Path("/center")
@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
public class CenterService {

	/**
	 * 分页查询接入点信息
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param keyword
	 * @return
	 */
	@GET
	@Path("/list")
	public Map<String, Object> query(@DefaultValue("1") @QueryParam("page") int pageNum, @DefaultValue("20") @QueryParam("rows") int pageSize, @DefaultValue("") @QueryParam("keyword") String keyword) {
		return DBHelper.getInstance().getCenterDao().find(pageNum, pageSize, keyword);
	}

	/**
	 * 修改接入点信息
	 * 
	 * @param bean
	 * @return
	 */
	@POST
	@Path("/modify")
	public boolean modifiy(@BeanParam Center bean) {
		return DBHelper.getInstance().getCenterDao().saveOrUpdate(bean) != null;
	}

	/**
	 * 通过ID获取接入点信息
	 * 
	 * @param id
	 * @return
	 */
	@GET
	@Path("/get")
	public Center getById(@QueryParam("id") int id) {
		return DBHelper.getInstance().getCenterDao().get(id);
	}

	/**
	 * 通过ID删除接入点信息
	 * 
	 * @param id
	 * @return
	 */
	@POST
	@Path("/delete")
	public int deleteById(@QueryParam("id") int id) {
		return DBHelper.getInstance().getCenterDao().delete(id);
	}
	
	//修改接入点名称后，同时修改机构表的机构名称2016-08-16
	@POST
	@Path("/updateInstitutionName")
	public int updateInstitutionName(@FormParam("id") int id,@FormParam("name") String name) {
		return DBHelper.getInstance().getCenterDao().updateInstitutionName(id, name);
	}
}
