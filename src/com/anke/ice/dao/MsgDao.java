package com.anke.ice.dao;
import java.util.List;
import java.util.Map;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;

import com.anke.ice.model.MsgModel;
import com.anke.ice.model.InstitutionModel;
import com.anke.ice.model.MYTreeNode;
public interface MsgDao extends BaseDao{
	/**
	 * 分页查询机构信息
	 */
	public Map<String, Object> MsgList(int pageNum, int pageSize,String minstitution,String mtitle,String mprofile,String mtype,int mapproval,String releasebegintime,String releaseendtime);

	
	/**
	 * 修改机构信息
	 */
	public MsgModel saveOrUpdate(MsgModel bean);
	
	/**
	 * 通过ID获取资讯信息
	 */
	public MsgModel get(int id);
	
	
	/**
	 * 通过ID删除资讯
	 * 
	 * @param id
	 * @return
	 */
	public int delete(int id);
	
	/**
	 * 通过ID审核资讯
	 * 
	 * @param id
	 * @return
	 */
	public int check(int id);
	
}
