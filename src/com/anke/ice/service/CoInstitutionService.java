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
import com.anke.ice.model.CoInstitutionModel;
import com.anke.ice.model.InstitutionModel;
import com.anke.ice.model.MYTreeNode;
import com.anke.ice.dao.DBHelper;


@Path("/coinstitution")
@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
public class CoInstitutionService {
	/**
	 * 分页查询信息
	 */
	@POST
	@Path("/list")
	public Map<String, Object> query(@DefaultValue("1") @FormParam("page") int pageNum, @DefaultValue("15") @FormParam("rows") int pageSize
			 ,@FormParam("mapplyperson") String mapplyperson, @FormParam("mcheckperson") String mcheckperson
			 ,@FormParam("mapplystate") int mapplystate,@FormParam("mapplyorcheck") int mapplyorcheck,@FormParam("institutionid") int institutionid
			 ,@FormParam("applystartTime") String applystartTime,@FormParam("applyendTime") String applyendTime
			 ,@FormParam("mcenter") int mcenter,@FormParam("minstitution") int minstitution) {
		return DBHelper.getInstance().getCoinstitutionDao().CoInstitutionList(pageNum, pageSize, mapplyperson,mcheckperson, mapplystate, mapplyorcheck,institutionid,applystartTime,applyendTime,mcenter,minstitution);
	}
	

	
	/**
	 * 通过ID获取信息
	 */
	@GET
	@Path("/get")
	public CoInstitutionModel getById(@QueryParam("institutionid") int institutionid,@QueryParam("centerid") int centerid) {
		return DBHelper.getInstance().getCoinstitutionDao().get(institutionid,centerid);
	}
	

	
	@POST
	@Path("/modify")
	public int modifiy(@FormParam("institutionid") int institutionid,@FormParam("centerid") int centerid,@FormParam("applystate") int applystate
			,@FormParam("uname") String uname,@FormParam("auditresult") String auditresult)  {
		return DBHelper.getInstance().getCoinstitutionDao().saveOrUpdate(institutionid,centerid,applystate,uname,auditresult);
	}
	

	
}
