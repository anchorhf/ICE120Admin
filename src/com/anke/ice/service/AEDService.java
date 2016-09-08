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
import com.anke.ice.model.AEDModel;
import com.anke.ice.model.InstitutionModel;
import com.anke.ice.model.MYTreeNode;
import com.anke.ice.dao.DBHelper;


@Path("/aed")
@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
public class AEDService {
	/**
	 * 分页查询AED信息
	 */
	@POST
	@Path("/list")
	public Map<String, Object> query(@DefaultValue("1") @FormParam("page") int pageNum, @DefaultValue("15") @FormParam("rows") int pageSize
			 , @FormParam("mlongitude") double mlongitude, @FormParam("mlatitude") double mlatitude,@FormParam("marea") String marea,@FormParam("maddress") String maddress
			 ,@FormParam("mbuilding") String mbuilding,@FormParam("mplace") String mplace , @FormParam("misvalid") int misvalid
			 ,@FormParam("releasebegintime") String releasebegintime,@FormParam("releaseendtime") String releaseendtime) {
		return DBHelper.getInstance().getAEDDao().AEDList(pageNum, pageSize, mlongitude,mlatitude,marea,maddress, mbuilding,mplace,misvalid,releasebegintime,releaseendtime);
	}
	
	/**
	 * 修改AED信息
	 */
	@POST
	@Path("/modify")
	@Produces(MediaType.TEXT_PLAIN + ";charset=UTF-8")
	public boolean modifiy(@BeanParam AEDModel bean) {
		return DBHelper.getInstance().getAEDDao().saveOrUpdate(bean) != null;
	}
	
	/**
	 * 通过ID获取AED信息
	 */
	@GET
	@Path("/get")
	public AEDModel getById(@QueryParam("id") int id) {
		return DBHelper.getInstance().getAEDDao().get(id);
	}
	
}
