package com.anke.ice.service;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.anke.ice.dao.DBHelper;
import com.anke.ice.model.MYTreeNode;


@Path("/serchleft")
@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")

public class SerchLeft {
	/** 
	 * 
	 * @param id
	 * @return
	 */
	@GET
	@Path("/selectleft")
	public List<MYTreeNode> selectleft(@QueryParam("roleid") String roleid) {
		return DBHelper.getInstance().getleftDao().select(roleid);
	}

}	 

	

