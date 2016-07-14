package com.anke.ice.model;
import javax.ws.rs.FormParam;

import com.anke.ice.inject.Id;
import com.anke.ice.inject.NotColumn;
import com.anke.ice.inject.Table;

@Table(tableName = "B_Work")
public class B_Work {
	@Id
	@FormParam("newrole")
	private int newrole;
    @FormParam("institutionid")
    private int institutionid;
	public int getNewrole() {
		return newrole;
	}
	public void setNewrole(int newrole) {
		this.newrole = newrole;
	}
	public int getInstitutionid() {
		return institutionid;
	}
	public void setInstitutionid(int institutionid) {
		this.institutionid = institutionid;
	}
    

}
