package com.anke.ice.dao;

import java.util.List;

import com.anke.ice.model.AreaModel;
import com.anke.ice.model.MYTreeNode;

public interface AreaDao extends BaseDao {
	public List<MYTreeNode> getAreaInfoList(String table);
	public AreaModel saveOrUpdate(AreaModel bean);
	//public <T> int saveOrUpdate(T bean);
	public int updateAreaIsValid(String table,int id, int isvalid);
}
