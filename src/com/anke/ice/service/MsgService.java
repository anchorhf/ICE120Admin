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
import com.anke.ice.model.MsgModel;
import com.anke.ice.model.InstitutionModel;
import com.anke.ice.model.MYTreeNode;
import com.anke.ice.dao.DBHelper;


@Path("/msg")
@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
public class MsgService {
	/**
	 * 分页查询机构信息
	 */
	@POST
	@Path("/list")
	public Map<String, Object> query(@DefaultValue("1") @FormParam("page") int pageNum, @DefaultValue("15") @FormParam("rows") int pageSize
			 , @FormParam("minstitution") String minstitution, @FormParam("mtitle") String mtitle,@FormParam("mprofile") String mprofile
			 ,@FormParam("mtype") String mtype,@FormParam("mapproval") int mapproval
			 ,@FormParam("releasebegintime") String releasebegintime,@FormParam("releaseendtime") String releaseendtime) {
		return DBHelper.getInstance().getMsgDao().MsgList(pageNum, pageSize, minstitution,mtitle, mprofile, mtype,mapproval,releasebegintime,releaseendtime);
	}
	
	/**
	 * 修改机构信息
	 */
	@POST
	@Path("/modify")
	@Produces(MediaType.TEXT_PLAIN + ";charset=UTF-8")
	public boolean modifiy(@BeanParam MsgModel bean) {
		return DBHelper.getInstance().getMsgDao().saveOrUpdate(bean) != null;
	}
	
	/**
	 * 通过ID获取资讯信息
	 */
	@GET
	@Path("/get")
	public MsgModel getById(@QueryParam("id") int id) {
		return DBHelper.getInstance().getMsgDao().get(id);
	}
	
	/**
	 * 通过ID删除资讯信息
	 * 
	 * @param id
	 * @return
	 */
	@POST
	@Path("/delete")
	public int deleteById(@QueryParam("id") int id) {
		return DBHelper.getInstance().getMsgDao().delete(id);
	}
	
	/**
	 * 通过ID审核资讯信息
	 * 
	 * @param id
	 * @return
	 */
	@POST
	@Path("/check")
	public int checkById(@QueryParam("id") int id) {
		return DBHelper.getInstance().getMsgDao().check(id);
	}
	
}
