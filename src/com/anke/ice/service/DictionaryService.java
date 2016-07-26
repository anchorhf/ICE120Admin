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
import com.anke.ice.model.DictionaryModel;

@Path("/Dictionary")
@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
public class DictionaryService {
	
	@GET
	@Path("/get")
	public List<DictionaryModel> GetDictionary(@QueryParam("table") String table
			,@QueryParam("typeCode") String typeCode
			,@DefaultValue("1") @QueryParam("isAddSelect") int isAddSelect) {
		return DBHelper.getInstance().getDictionaryDao().GetDictionary(table,typeCode, isAddSelect);
	}
}
