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
import com.anke.ice.model.InstitutionModel;
import com.anke.ice.model.MYTreeNode;
import com.anke.ice.dao.DBHelper;



@Path("/institution")
@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
public class InstitutionService {
	/**
	 * 分页查询机构信息
	 */
	@POST
	@Path("/list")
	public Map<String, Object> query(@DefaultValue("1") @FormParam("page") int pageNum, @DefaultValue("15") @FormParam("rows") int pageSize, @FormParam("id") int id,
			@FormParam("name") String name,@FormParam("volunteertype") String volunteertype,@FormParam("speciality") String speciality,@FormParam("isvalid") int isvalid) {
		return DBHelper.getInstance().getInstitutionDao().institutionlist(pageNum, pageSize, id,name, volunteertype, speciality,isvalid);
	}
	
	/**
	 * 通过ID获取机构信息
	 */
	@GET
	@Path("/get")
	public InstitutionModel getById(@QueryParam("id") int id) {
		return DBHelper.getInstance().getInstitutionDao().get(id);
	}
	
	/**
	 * 修改机构信息
	 */
	@POST
	@Path("/modify")
	@Produces(MediaType.TEXT_PLAIN + ";charset=UTF-8")
	public boolean modifiy(@BeanParam InstitutionModel bean) {
		return DBHelper.getInstance().getInstitutionDao().saveOrUpdate(bean) != 0;
	}

	/**
	 * 通过ID获取合作机构信息
	 */
	@GET
	@Path("/getcinstitution")
	public List<MYTreeNode> getByCenterId(@QueryParam("id") int cetid) {
		return DBHelper.getInstance().getInstitutionDao().getCoopInstitution(cetid);
	}
	/**
	 *保存合作机构信息
	 */
	@POST
	@Path("/savecoopinstitution")
	public boolean savedata(@QueryParam("cetid") int cetid,@QueryParam("institutionid") String institutionid,@QueryParam("loginame") String loginame) {
		return DBHelper.getInstance().getInstitutionDao().savecoopinstitution(cetid,institutionid,loginame);
	}
	

	/**
	 * 判断中心名称是否重复
	 */
	@POST
	@Path("/getifRepeat")
	public int getifRepeat(@FormParam("cname") String cname,@FormParam("centeri") int centeri,@FormParam("isname") String isname) {
		return DBHelper.getInstance().getInstitutionDao().CenterRepeat(cname,centeri,isname);
	}
}
