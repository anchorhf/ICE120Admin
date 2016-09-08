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
import com.anke.ice.model.FirstAHBModel;
import com.anke.ice.model.InstitutionModel;
import com.anke.ice.model.MYTreeNode;
import com.anke.ice.dao.DBHelper;


@Path("/firstahb")
@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
public class FirstAHBService {
	/**
	 * 分页查询手册信息
	 */
	@POST
	@Path("/list")
	public Map<String, Object> query(@DefaultValue("1") @FormParam("page") int pageNum, @DefaultValue("15") @FormParam("rows") int pageSize
			 , @FormParam("mname") String mname, @FormParam("morderno") int morderno,@FormParam("mprofile") String mprofile
			 ,@FormParam("misvolunteer") int misvolunteer,@FormParam("misvalid") int misvalid
			) {
		return DBHelper.getInstance().getFirstahbDao().FirstAHBList(pageNum, pageSize, mname,morderno, mprofile, misvolunteer,misvalid);
	}
	
	/**
	 * 修改手册信息
	 */
	@POST
	@Path("/modify")
	@Produces(MediaType.TEXT_PLAIN + ";charset=UTF-8")
	public boolean modifiy(@BeanParam FirstAHBModel bean) {
		return DBHelper.getInstance().getFirstahbDao().saveOrUpdate(bean) != null;
	}

	/**
	 * 通过ID获取手册信息
	 */
	@GET
	@Path("/get")
	public FirstAHBModel getById(@QueryParam("id") int id) {
		return DBHelper.getInstance().getFirstahbDao().get(id);
	}
	

	
}
