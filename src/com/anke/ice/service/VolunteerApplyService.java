package com.anke.ice.service;

import java.util.Map;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.anke.ice.dao.DBHelper;


@Path("/VolunteerApply")
@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
public class VolunteerApplyService {

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
	public Map<String, Object> query(@DefaultValue("1") @FormParam("page") int pageNum, @DefaultValue("20") @FormParam("rows") int pageSize
			 , @FormParam("volunteer") String volunteer, @FormParam("are") String are,@FormParam("institution") String institution
			 ,@FormParam("volunteertype") int volunteertype,@FormParam("applystate") int applystate
			 ,@FormParam("applybegintime") String applybegintime,@FormParam("applyendtime") String applyendtime) {
		return DBHelper.getInstance().getVolunteerApplyDao().findVolunteerApply(pageNum, pageSize, volunteer, are, institution, volunteertype, applystate, applybegintime, applyendtime);
	}
}
