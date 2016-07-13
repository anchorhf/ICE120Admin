package com.anke.ice.model;

import javax.ws.rs.FormParam;

import com.anke.ice.inject.Id;
import com.anke.ice.inject.Table;


@Table(tableName = "T_REGUSER")
public class CheckLogin {
	@Id
	@FormParam("userName")
	private String userName;
	@FormParam("password")
	private String password;
	public String getuserName() {
		return userName;
	}
	public void setuserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
