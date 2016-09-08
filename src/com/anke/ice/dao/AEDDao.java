package com.anke.ice.dao;
import java.util.List;
import java.util.Map;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;

import com.anke.ice.model.MsgModel;
import com.anke.ice.model.AEDModel;
import com.anke.ice.model.InstitutionModel;
import com.anke.ice.model.MYTreeNode;
public interface AEDDao extends BaseDao{
	/**
	 * 分页查询AED信息
	 */
	public Map<String, Object> AEDList(int pageNum, int pageSize,double mlongitude,double mlatitude,String marea,String maddress,String mbuilding,String mplace,int misvalid,String releasebegintime,String releaseendtime);
	
	/**
	 * 修改AED信息
	 */
	public AEDModel saveOrUpdate(AEDModel bean);
	
	/**
	 * 通过ID获取AED信息
	 */
	public AEDModel get(int id);
	
	
}
