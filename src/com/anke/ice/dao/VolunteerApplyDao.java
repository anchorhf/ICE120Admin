package com.anke.ice.dao;

import java.util.List;
import java.util.Map;

import com.anke.ice.model.VolunteerApplyModel;
import com.anke.ice.model.VolunteerAttachmentModel;

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
			,String institution,int volunteertype,int applystate,String applybegintime
			,String applyendtime,String skill);

	public int updateApplyState(int id,int applyState,int volunteerid,String uname,String auditresult);
	public VolunteerApplyModel getVolunteerAndApply(int id);
	
	public List<VolunteerAttachmentModel> GetAttachment(int applyid);
}
