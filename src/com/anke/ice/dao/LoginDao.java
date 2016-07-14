package com.anke.ice.dao;
import com.anke.ice.model.CheckLogin;
public interface LoginDao extends BaseDao{
	/**
	 * 判断登录信息
	 * 
	 * @param bean
	 * @return
	 */
	public String judgelogin(CheckLogin bean);

}
