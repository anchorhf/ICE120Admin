package com.anke.ice.service;

import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.anke.ice.dao.DBHelper;
import com.anke.ice.model.AreaModel;
import com.anke.ice.model.MYTreeNode;

@Path("/area")
@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
public class AreaService {

	//根据表名获取列表信息
	@GET
	@Path("/list")
	public  List<MYTreeNode> getAreaInfoList(@QueryParam("table") String table){
		return DBHelper.getInstance().getAreaDao().getAreaInfoList(table);
	}
	
	//新增或者保存区域信息
	@POST
	@Path("/save")
	public boolean modifiy(@FormParam("id") int id,@FormParam("fatherid") int fatherid
			,@DefaultValue("255") @FormParam("orderno") int orderno,@FormParam("name") String name
			,@FormParam("isvalid") int isvalid) {
		AreaModel bean = new AreaModel();
		bean.setID(id);
		bean.setFATHERID(fatherid);
		bean.setORDERNO(orderno);
		bean.setNAME(name);
		bean.setISVALID(isvalid);
		return DBHelper.getInstance().getAreaDao().saveOrUpdate(bean) != null;
		//return DBHelper.getInstance().getAreaDao().saveOrUpdate(bean) != 0;
	}
	
	/**
	 * 修改区域是否有效
	 * 
	 * @param id
	 * @return
	 */
	@POST
	@Path("/isvalid")
	public int isvalid(@FormParam("table") String table,@FormParam("id") int id,@FormParam("isvalid") int isvalid) {
		return DBHelper.getInstance().getAreaDao().updateAreaIsValid(table, id, isvalid);
	}
}
