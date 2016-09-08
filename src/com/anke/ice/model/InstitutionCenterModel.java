package com.anke.ice.model;
import java.util.Date;

import javax.ws.rs.FormParam;
import com.anke.ice.inject.Table;
@Table(tableName = "t_institution_center")
public class InstitutionCenterModel {
	@FormParam("institutionid")
	private int institutionid;
	@FormParam("centerid")
	private int centerid;
	@FormParam("applystate")
	private int applystate;
	@FormParam("applyperson")
	private String applyperson;
	@FormParam("applytime")
	private Date applytime;
	@FormParam("checkperson")
	private String checkperson;
	@FormParam("checktaime")
	private Date checktaime;
	@FormParam("auditresult")
	private String auditresult;

	public int getInstitutionid() {
		return institutionid;
	}
	public void setInstitutionid(int institutionid) {
		this.institutionid = institutionid;
	}
	public int getCenterid() {
		return centerid;
	}
	public void setCenterid(int centerid) {
		this.centerid = centerid;
	}
	public int getApplystate() {
		return applystate;
	}
	public void setApplystate(int applystate) {
		this.applystate = applystate;
	}
	public String getApplyperson() {
		return applyperson;
	}
	public void setApplyperson(String applyperson) {
		this.applyperson = applyperson;
	}
	public Date getApplytime() {
		return applytime;
	}
	public void setApplytime(Date applytime) {
		this.applytime = applytime;
	}
	public String getCheckperson() {
		return checkperson;
	}
	public void setCheckperson(String checkperson) {
		this.checkperson = checkperson;
	}
	public Date getChecktaime() {
		return checktaime;
	}
	public void setChecktaime(Date checktaime) {
		this.checktaime = checktaime;
	}
	public String getAuditresult() {
		return auditresult;
	}
	public void setAuditresult(String auditresult) {
		this.auditresult = auditresult;
	}
	
	
	
}
