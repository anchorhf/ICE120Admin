package com.anke.ice.dao;

import java.util.List;


import com.anke.ice.model.MYTreeNode;


public interface LeftDao extends BaseDao {
	public List<MYTreeNode> select(String roleid);

}