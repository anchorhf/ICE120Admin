package com.anke.ice.service;

import java.util.List;
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
import com.anke.ice.model.DictionaryModel;

@Path("/Dictionary")
@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
public class DictionaryService {
	
	//根据表名、typeCode、isAddSelect来获取字典表信息
	@GET
	@Path("/get")
	public List<DictionaryModel> GetDictionary(@QueryParam("table") String table
			,@QueryParam("typeCode") String typeCode
			,@DefaultValue("1") @QueryParam("isAddSelect") int isAddSelect) {
		return DBHelper.getInstance().getDictionaryDao().GetDictionary(table,typeCode, isAddSelect);
	}
	
	//根据多角色字符串和菜单ID来获取是否有权限
	@POST
	@Path("/getIFRole")
	public int getIFRole(@FormParam("roleid") String roleid,@FormParam("menuid") int menuid) {
		return DBHelper.getInstance().getDictionaryDao().IFRole(roleid, menuid);
	}
	
	/**
	 * 分页查询字典表类型信息
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param name
	 * @return
	 */
	@POST
	@Path("/typelist")
	public Map<String, Object> query(@DefaultValue("1") @FormParam("page") int pageNum, @DefaultValue("20") @FormParam("rows") int pageSize
			 , @FormParam("name") String name){
		return DBHelper.getInstance().getDictionaryDao().getDictionaryTypeList(pageNum, pageSize, name);
	}

	/**
	 * 分页查询字典表信息
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param typecode
	 * @return
	 */
	@POST
	@Path("/list")
	public Map<String, Object> querydictionarylist(@DefaultValue("1") @FormParam("page") int pageNum, @DefaultValue("10") @FormParam("rows") int pageSize
			 , @FormParam("typecode") String typecode){
		return DBHelper.getInstance().getDictionaryDao().getDictionaryList(pageNum, pageSize, typecode);
	}
	
	/**
	 * 修改字典表信息
	 * 
	 * @param bean
	 * @return
	 */
	@POST
	@Path("/modify")
	public boolean modifiy(@FormParam("id") int id,@FormParam("typecode") String typecode
			,@FormParam("orderno") int orderno,@FormParam("name") String name,@FormParam("isvalid") int isvalid) {
		DictionaryModel bean = new DictionaryModel();
		bean.setID(id);
		bean.setTYPECODE(typecode);
		bean.setORDERNO(orderno);
		bean.setNAME(name);
		bean.setISVALID(isvalid);
		return DBHelper.getInstance().getDictionaryDao().saveOrUpdate(bean) != null;
	}
	

	/**
	 * 修改字典表是否有效
	 * 
	 * @param id
	 * @return
	 */
	@POST
	@Path("/isvalid")
	public int isvalid(@FormParam("id") int id,@FormParam("isvalid") int isvalid) {
		return DBHelper.getInstance().getDictionaryDao().updateDictionaryIsValid(id, isvalid);
	}
	
	
}
