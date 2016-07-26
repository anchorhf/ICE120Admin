package com.anke.ice.dao;

import java.util.List;
import java.util.Map;

import com.anke.ice.model.DictionaryModel;

public interface DictionaryDao extends BaseDao {
	public List<DictionaryModel> GetDictionary(String table,String typeCode,int isAddSelect);
}
