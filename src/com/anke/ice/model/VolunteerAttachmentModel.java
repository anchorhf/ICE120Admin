package com.anke.ice.model;

import javax.ws.rs.FormParam;

import com.anke.ice.inject.Table;

@Table(tableName = "T_VOLUNTEER_APPLY")
public class VolunteerAttachmentModel {

	@FormParam("ID")
	private int ID;
	@FormParam("APPLYID")
	private int APPLYID;
	@FormParam("ATTACHMENTURL")
	private String ATTACHMENTURL;
	
	public int getID() {
		return ID;
	}
	public void setID(int ID) {
		this.ID = ID;
	}
	public int getAPPLYID() {
		return APPLYID;
	}
	public void setAPPLYID(int APPLYID) {
		this.APPLYID = APPLYID;
	}
	
	public String getATTACHMENTURL() {
		return ATTACHMENTURL;
	}
	public void setATTACHMENTURL(String ATTACHMENTURL) {
		this.ATTACHMENTURL = ATTACHMENTURL;
	}
	
}
