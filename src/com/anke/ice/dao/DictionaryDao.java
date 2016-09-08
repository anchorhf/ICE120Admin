package com.anke.ice.dao;

import java.util.List;
import java.util.Map;

import com.anke.ice.model.DictionaryModel;

public interface DictionaryDao extends BaseDao {
	public List<DictionaryModel> GetDictionary(String table,String typeCode,int isAddSelect);
	public int IFRole(String roleid, int menuid);
	
	public Map<String, Object> getDictionaryTypeList(int pageNum, int pageSize, String name);
	public Map<String, Object> getDictionaryList(int pageNum, int pageSize, String typecode);
	
	public DictionaryModel saveOrUpdate(DictionaryModel bean);
	public int updateDictionaryIsValid(int id, int isvalid);
}
