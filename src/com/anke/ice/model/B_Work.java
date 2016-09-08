package com.anke.ice.model;
import javax.ws.rs.FormParam;

import com.anke.ice.inject.Id;
import com.anke.ice.inject.Table;

@Table(tableName = "B_Worker")
public class B_Work {
	
	@Id
	@FormParam("id")
	private int id;
	@FormParam("loginname")
	private String loginname;
	@FormParam("password")
	private String password;
	@FormParam("role")
	private String role;
	@FormParam("centerid")
	private Integer centerid;
	
	@FormParam("newrole")
	private String newrole;
    @FormParam("institutionid")
    private int institutionid;
    @FormParam("institution")
    private String institution;

	public String getInstitution() {
		return institution;
	}
	public void setInstitution(String institution) {
		this.institution = institution;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Integer getCenterid() {
		return centerid;
	}
	public void setCenterid(Integer centerid) {
		this.centerid = centerid;
	}
    
	public String getNewrole() {
		return newrole;
	}
	public void setNewrole(String newrole) {
		this.newrole = newrole;
	}
	public int getInstitutionid() {
		return institutionid;
	}
	public void setInstitutionid(int institutionid) {
		this.institutionid = institutionid;
	}
    

}
