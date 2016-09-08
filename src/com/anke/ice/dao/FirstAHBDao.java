package com.anke.ice.dao;
import java.util.List;
import java.util.Map;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;

import com.anke.ice.model.MsgModel;
import com.anke.ice.model.FirstAHBModel;
import com.anke.ice.model.InstitutionModel;
import com.anke.ice.model.MYTreeNode;
public interface FirstAHBDao extends BaseDao{
	/**
	 * 分页查询急救手册信息
	 */
	public Map<String, Object> FirstAHBList(int pageNum, int pageSize,String mname,int morderno,String mprofile,int misvolunteer,int misvalid);
	
	
	/**
	 * 修改急救手册信息
	 */
	public FirstAHBModel saveOrUpdate(FirstAHBModel bean);
	
	/**
	 * 通过ID获取急救手册信息
	 */
	public FirstAHBModel get(int id);
	
}
