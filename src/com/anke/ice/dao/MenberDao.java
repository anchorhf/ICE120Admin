package com.anke.ice.dao;

import java.util.List;
import java.util.Map;

import com.anke.ice.model.Menber;

public interface MenberDao extends BaseDao {

	/**
	 * 分页查询接入点列表
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param keyword
	 * @return
	 */
	public Map<String, Object> findmenber(int pageNum, int pageSize, String telphone, String email, String address);

//	/**
//	 * 保存或修改会员
//	 * 
//	 * @param bean
//	 * @return
//	 */
//	public Menber SaOrUpdate(Menber bean);

	/**
	 * 通过ID获取
	 * 
	 * @param id
	 * @return
	 */
	public Menber reget(int id);
	/**
	 * 通过ID获取
	 * 
	 * @param id
	 * @return
	 */
	public String regettab(int id);
	


	/**
	 * 通过ID获取
	 * 
	 * @param id
	 * @return
	 */
	public Menber regetc(int fid,int sid);
	/**
	 * 通过ID获取
	 * 
	 * @param id
	 * @return
	 */
	public List<Menber> regetem(int fid,int sid);


//	/**
//	 * 通过ID删除接入点
//	 * 
//	 * @param id
//	 * @return
//	 */
//	public int delete(int id);

}