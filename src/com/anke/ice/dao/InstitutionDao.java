package com.anke.ice.dao;

import java.util.Map;

import javax.ws.rs.QueryParam;

import com.anke.ice.model.Center;
import com.anke.ice.model.InstitutionModel;

public interface InstitutionDao {

	/**
	 * 生成主键ID接口
	 * 
	 * @return
	 */
	public Map<String, Object> institutionlist(int pageNum, int pageSize,int id,String name,String volunteertype,String speciality,int isvalid);

	public InstitutionModel  get(int id);
	
	/**
	 * 保存或修改接入点
	 * 
	 * @param bean
	 * @return
	 */
	public InstitutionModel saveOrUpdate(InstitutionModel bean);
}
