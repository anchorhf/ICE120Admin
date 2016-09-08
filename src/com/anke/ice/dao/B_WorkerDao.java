package com.anke.ice.dao;

import java.util.Map;

import com.anke.ice.model.B_Work;

public interface B_WorkerDao extends BaseDao {
	public Map<String, Object> getList(int pageNum, int pageSize, String loginname,int institutionid);
	public B_Work saveOrUpdate(B_Work bean);

	public int getLoginname(String loginname);
}
