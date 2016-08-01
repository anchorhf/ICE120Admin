package com.anke.ice.model;

import javax.ws.rs.FormParam;

import com.anke.ice.inject.Table;

@Table(tableName = "T_ROLEMENU")
public class RoleMenuModel {

	@FormParam("ROLEID")
	private int ROLEID;
	@FormParam("MENUID")
	private int MENUID;
	
	public int getROLEID() {
		return ROLEID;
	}
	public void setROLEID(int ROLEID) {
		this.ROLEID = ROLEID;
	}
	public int getMENUID() {
		return MENUID;
	}
	public void setMENUID(int MENUID) {
		this.MENUID = MENUID;
	}
}
