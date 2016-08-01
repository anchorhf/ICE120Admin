package com.anke.ice.model;

import java.util.Date;


import javax.ws.rs.FormParam;

import com.anke.ice.inject.Id;
import com.anke.ice.inject.NotColumn;
import com.anke.ice.inject.Table;

import oracle.sql.CLOB;

import java.io.Reader;
import java.sql.Clob;
@Table(tableName = "t_institution")
public class InstitutionModel {
	
	@Id
	@FormParam("ID")
	private int ID;
	@FormParam("name")
	private String name;
	@FormParam("UPDATETIME")
	private Date UPDATETIME;
	@NotColumn
	private String dupdatetime;
	@FormParam("detail")
	private Clob detail;
	
	@FormParam("volunteertype")
	private String volunteertype;
	@FormParam("speciality")
	private String speciality;
	@FormParam("centerid")
	private Integer centerid;
	@FormParam("isvalid")
	private String isvalid;
	@FormParam("centername")
	private String centername;
	@NotColumn
	private String thvolunteertype;
	@NotColumn
	private String thspeciality;
	@FormParam("strdetail")
	private String strdetail;
	@NotColumn
	private String istisvalid;
	@NotColumn
	private String volname;
	@NotColumn
	private String spename;
	@NotColumn
	private String cetname;

	


	public String getCentername() {
		return centername;
	}
	public void setCentername(String centername) {
		this.centername = centername;
	}
	public String getThvolunteertype() {
		return thvolunteertype;
	}
	public void setThvolunteertype(String thvolunteertype) {
		this.thvolunteertype = thvolunteertype;
	}
	public String getThspeciality() {
		return thspeciality;
	}
	public void setThspeciality(String thspeciality) {
		this.thspeciality = thspeciality;
	}
	public String getStrdetail() {
		return strdetail;
	}
	public void setStrdetail(String strdetail) {
		this.strdetail = strdetail;
	}
	public String getIstisvalid() {
		return istisvalid;
	}
	public void setIstisvalid(String istisvalid) {
		this.istisvalid = istisvalid;
	}
	public String getCetname() {
		return cetname;
	}
	public void setCetname(String cetname) {
		this.cetname = cetname;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVolname() {
		return volname;
	}
	public void setVolname(String volname) {
		this.volname = volname;
	}
	public String getSpename() {
		return spename;
	}
	public void setSpename(String spename) {
		this.spename = spename;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getVolunteertype() {
		return volunteertype;
	}
	public void setVolunteertype(String volunteertype) {
		this.volunteertype = volunteertype;
	}
	public String getSpeciality() {
		return speciality;
	}
	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}


	public Date getUPDATETIME() {
		return UPDATETIME;
	}
	public void setUPDATETIME(Date uPDATETIME) {
		UPDATETIME = uPDATETIME;
	}
	public String getDupdatetime() {
		return dupdatetime;
	}
	public void setDupdatetime(String dupdatetime) {
		this.dupdatetime = dupdatetime;
	}
	public Clob getDetail() {
		return detail;
	}
	public void setDetail(Clob detail) {
		this.detail = detail;
	}
	public Integer getCenterid() {
		return centerid;
	}
	public void setCenterid(Integer centerid) {
		this.centerid = centerid;
	}
	public String getIsvalid() {
		return isvalid;
	}
	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid;
	}


	
	

}
