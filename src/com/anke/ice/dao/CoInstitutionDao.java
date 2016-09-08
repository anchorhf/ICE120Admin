package com.anke.ice.dao;
import java.util.List;
import java.util.Map;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;

import com.anke.ice.model.MsgModel;
import com.anke.ice.model.CoInstitutionModel;
import com.anke.ice.model.InstitutionModel;
import com.anke.ice.model.MYTreeNode;
public interface CoInstitutionDao extends BaseDao{
	/**
	 * 分页查询信息
	 */
	public Map<String, Object> CoInstitutionList(int pageNum, int pageSize,String mapplyperson,String mcheckperson,int mapplystate
			,int mapplyorcheck,int institutionid,String applystartTime,String applyendTime,int mcenter,int minstitution);


	/**
	 * 通过ID获取信息
	 */
	public CoInstitutionModel get(int institutionid,int centerid);
	
	
	/**
	 * 修改信息
	 */
	public int saveOrUpdate(int institutionid,int centerid,int applystate,String uname,String auditresult);
	
}
