package com.anke.ice.dao;

import java.util.Map;

import com.anke.ice.model.Center;

public interface CenterDao extends BaseDao {

	/**
	 * 分页查询接入点列表
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param keyword
	 * @return
	 */
	public Map<String, Object> find(int pageNum, int pageSize, String keyword);

	/**
	 * 保存或修改接入点
	 * 
	 * @param bean
	 * @return
	 */
	public Center saveOrUpdate(Center bean);

	/**
	 * 通过ID获取接入点
	 * 
	 * @param id
	 * @return
	 */
	public Center get(int id);

	/**
	 * 通过ID删除接入点
	 * 
	 * @param id
	 * @return
	 */
	public int delete(int id);
	
	//修改接入点名称后，同时修改机构表的机构名称2016-08-16
	public int updateInstitutionName(int id,String name);
}
