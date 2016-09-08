package com.anke.ice.dao;

import java.util.List;
import java.util.Map;
import com.anke.ice.model.InstitutionModel;
import com.anke.ice.model.MYTreeNode;

public interface InstitutionDao extends BaseDao {

	/**
	 * 分页查询机构信息
	 */
	public Map<String, Object> institutionlist(int pageNum, int pageSize,int id,String name,String volunteertype,String speciality,int isvalid);

	/**
	 * 通过ID获取机构信息
	 */
	public InstitutionModel  get(int id);
	
	/**
	 * 修改机构信息
	 */
	public int saveOrUpdate(InstitutionModel bean);
	
	/**
	 * 通过ID获取合作机构信息
	 */
	public List<MYTreeNode> getCoopInstitution(int cetid);
	
	/**
	 *保存合作机构信息
	 */
	public boolean savecoopinstitution(int cetid,String institutionid,String loginame);
	

	
	/**
	 * 判断中心名称是否重复
	 */
	public int CenterRepeat(String cname,int centeri,String isname);
}
