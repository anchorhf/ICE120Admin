package com.anke.ice.dao;

import java.util.List;


import com.anke.ice.model.MYTreeNode;


public interface LeftDao extends BaseDao {

	/**
	 * 
	 * 
	 * @param id
	 * @return
	 */
	public List<MYTreeNode> select(int id);

}