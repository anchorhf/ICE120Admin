package com.anke.ice.service;

import java.util.List;
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
import com.anke.ice.model.VolunteerApplyModel;
import com.anke.ice.model.VolunteerAttachmentModel;


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
			 ,@FormParam("applybegintime") String applybegintime,@FormParam("applyendtime") String applyendtime
			 ,@FormParam("skill") String skill,@DefaultValue("-1") @FormParam("institutionid") int institutionid) {
		return DBHelper.getInstance().getVolunteerApplyDao().findVolunteerApply(pageNum, pageSize, volunteer, are
				, institution, volunteertype, applystate, applybegintime
				, applyendtime,skill,institutionid);
	}
	
	/**
	 * 通过ID修改志愿者申请状态
	 * 
	 * @param id
	 * @return
	 */
	@POST
	@Path("/updateapplystate")
	public int deleteById(@FormParam("id") int id,@FormParam("applyState") int applyState
			,@FormParam("volunteerid") int volunteerid,@FormParam("uname") String uname
			,@FormParam("auditresult") String auditresult) {
		return DBHelper.getInstance().getVolunteerApplyDao().updateApplyState(id, applyState,volunteerid,uname,auditresult);
	}
	
	/**
	 * 通过ID获取志愿者信息
	 * 
	 * @param id
	 * @return
	 */
	@GET
	@Path("/get")
	public VolunteerApplyModel getById(@QueryParam("id") int id) {
		return DBHelper.getInstance().getVolunteerApplyDao().getVolunteerAndApply(id);
	}
	
	/**
	 * 通过志愿者ID获取志愿者附件信息
	 * 
	 * @param id
	 * @return
	 */
	@GET
	@Path("/getAttachment")
	public List<VolunteerAttachmentModel> GetDictionary(@QueryParam("applyid") int applyid) {
		return DBHelper.getInstance().getVolunteerApplyDao().GetAttachment(applyid);
	}
}
