package com.anke.ice.service;

import java.util.List;
import java.util.Map;

import javax.ws.rs.BeanParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.anke.ice.dao.DBHelper;
import com.anke.ice.model.Menber;

@Path("/sysmanage")
@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
public class SysManage {

	/**
	 * 分页查询会员信息
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param keyword
	 * @return
	 */
	@GET
	@Path("/menberlist")
	public Map<String, Object> query(@DefaultValue("1") @QueryParam("page") int pageNum, @DefaultValue("20") @QueryParam("rows") int pageSize, @QueryParam("telphone") String telphone, @QueryParam("email") String email,@QueryParam("address") String address) {
		return DBHelper.getInstance().getMenberDao().findmenber(pageNum, pageSize, telphone,email,address);
	}

//	/**
//	 * 修改接入点信息
//	 * 
//	 * @param bean
//	 * @return
//	 */
//	@POST
//	@Path("/regmodify")
//	public boolean modifiy(@BeanParam Menber bean) {
//		return DBHelper.getInstance().getMenberDao().SaOrUpdate(bean) != null;
//	}

	/**
	 * 通过ID获取接入点信息
	 * 
	 * @param id
	 * @return 
	 * @return
	 */
	@GET
	@Path("/reget")
	public  Menber getById(@QueryParam("id") int id) {
		return DBHelper.getInstance().getMenberDao().reget(id);
	}
	/**
	 * 通过ID获取接入点信息
	 * 
	 * @param id
	 * @return 
	 * @return
	 */
	@POST
	@Path("/regettab")
	public String gettabById(@QueryParam("id") int id) {
		return DBHelper.getInstance().getMenberDao().regettab(id);
	}
	
	/**
	 * 通过ID获取接入点信息
	 * 
	 * @param id
	 * @return 
	 * @return
	 */
	@GET
	@Path("/regetc")
	public  Menber getByIdc(@QueryParam("fid") int fid,@QueryParam("sid") int sid) {
		return DBHelper.getInstance().getMenberDao().regetc(fid,sid);
	}
	
	/**
	 * 通过ID获取接入点信息
	 * 
	 * @param id
	 * @return 
	 * @return
	 */
	@GET
	@Path("/regetem")
	public  List<Menber> getByIdem(@QueryParam("fid") int fid,@QueryParam("sid") int sid) {
		return DBHelper.getInstance().getMenberDao().regetem(fid,sid);
	}

//	/**
//	 * 通过ID删除接入点信息
//	 * 
//	 * @param id
//	 * @return
//	 */
//	@POST
//	@Path("/delete")
//	public int deleteById(@QueryParam("id") int id) {
//		return DBHelper.getInstance().getCenterDao().delete(id);
//	}

}
