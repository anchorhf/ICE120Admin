package com.anke.ice.model;



import javax.ws.rs.FormParam;
import com.anke.ice.inject.Id;
import com.anke.ice.inject.Table;



@Table(tableName = "T_MENU")
public class serchleft {
	@Id
	@FormParam("MENUID")
	private int MENUID;
	@FormParam("PARENTMENUID")
	private int PARENTMENUID;
	@FormParam("URL")
	private String URL;
	@FormParam("LABEL")
	private String LABEL;
	@FormParam("ROLE")
	private String ROLE;
	
	public int getPARENTMENUID() {
		return PARENTMENUID;
	}
	public void setPARENTMENUID(int pARENTMENUID) {
		PARENTMENUID = pARENTMENUID;
	}
	
	public int getMENUID() {
		return MENUID;
	}
	public void setMENUID(int mENUID) {
		MENUID = mENUID;
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
	public String getLABEL() {
		return LABEL;
	}
	public void setLABEL(String lABEL) {
		LABEL = lABEL;
	}

	
	
}
