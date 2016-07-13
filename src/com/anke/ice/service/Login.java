package com.anke.ice.service;



import javax.ws.rs.BeanParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.anke.ice.dao.DBHelper;
import com.anke.ice.model.CheckLogin;


@Path("/loginfi")
@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
public class Login {
	@POST
	@Path("/loginse")
	public int judgelogin(@BeanParam CheckLogin bean) {
		return DBHelper.getInstance().getLoginDao().judgelogin(bean);

		}
}
