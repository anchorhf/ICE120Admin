package com.anke.ice.dao;

import java.util.Map;

public interface VolunteerApplyDao extends BaseDao {
	
	/**
	 * 分页查询志愿者申请列表
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param 
	 * @return
	 */
	public Map<String, Object> findVolunteerApply(int pageNum, int pageSize, String volunteer, String are
			,String institution,int volunteertype,int applystate,String applybegintime,String applyendtime);

}
