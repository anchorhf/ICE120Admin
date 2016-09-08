package com.anke.ice.model;

import java.util.Date;


import javax.ws.rs.FormParam;

import com.anke.ice.inject.Id;
import com.anke.ice.inject.NotColumn;
import com.anke.ice.inject.Table;

import java.sql.Clob;
@Table(tableName = "t_institution")
public class CoInstitutionModel {
	
	@Id
	@FormParam("institutionid")
	private int institutionid;
	@Id
	@FormParam("centerid")
	private int centerid;
	@FormParam("applystate")
	private String applystate;
	@FormParam("applyperson")
	private String applyperson;
	@FormParam("applytime")
	private String applytime;
	@FormParam("checktime")
	private String checktime;
	@FormParam("checkperson")
	private String checkperson;
	@FormParam("auditresult")
	private String auditresult;
	@FormParam("institutioname")
	private String institutioname;
	@FormParam("centername")
	private String centername;
	@FormParam("applyorcheck")
	private String applyorcheck;

	
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
	public String getApplyperson() {
		return applyperson;
	}
	public void setApplyperson(String applyperson) {
		this.applyperson = applyperson;
	}
	public String getApplytime() {
		return applytime;
	}
	public void setApplytime(String applytime) {
		this.applytime = applytime;
	}
	public String getCheckperson() {
		return checkperson;
	}
	public void setCheckperson(String checkperson) {
		this.checkperson = checkperson;
	}
	public String getAuditresult() {
		return auditresult;
	}
	public void setAuditresult(String auditresult) {
		this.auditresult = auditresult;
	}
	public String getApplystate() {
		return applystate;
	}
	public void setApplystate(String applystate) {
		this.applystate = applystate;
	}
	public String getChecktime() {
		return checktime;
	}
	public void setChecktime(String checktime) {
		this.checktime = checktime;
	}
	public String getInstitutioname() {
		return institutioname;
	}
	public void setInstitutioname(String institutioname) {
		this.institutioname = institutioname;
	}
	public String getCentername() {
		return centername;
	}
	public void setCentername(String centername) {
		this.centername = centername;
	}
	public String getApplyorcheck() {
		return applyorcheck;
	}
	public void setApplyorcheck(String applyorcheck) {
		this.applyorcheck = applyorcheck;
	}

	
}
