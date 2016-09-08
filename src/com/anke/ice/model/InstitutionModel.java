package com.anke.ice.model;

import java.util.Date;


import javax.ws.rs.FormParam;

import com.anke.ice.inject.Id;
import com.anke.ice.inject.NotColumn;
import com.anke.ice.inject.Table;

import java.sql.Clob;
@Table(tableName = "t_institution")
public class InstitutionModel {
	
	@Id
	@FormParam("id")
	private int id;
	@FormParam("centername")
	private String centername;
	@FormParam("strdetail")
	private String strdetail;
	@FormParam("name")
	private String name;
	@FormParam("isvalid")
	private String isvalid;
	@FormParam("speciality")
	private String speciality;
	@FormParam("UPDATETIME")
	private Date UPDATETIME;
	@NotColumn
	private String thvolunteertype;
	@FormParam("detail")
	private String detail;
	@FormParam("username")
	private String username;
	@FormParam("centerid")
	private Integer centerid;
	@NotColumn
	private String thspeciality;
	@NotColumn
	private String istisvalid;
	@NotColumn
	private String volname;
	@NotColumn
	private String spename;
	@NotColumn
	private String cetname;
	@NotColumn
	private String dupdatetime;
	@NotColumn
	private Clob insdetail;
	@FormParam("volunteertype")
	private String volunteertype;
	
	@NotColumn
	private Integer tabcenterid;
	@NotColumn
	private String astate;



	public String getAstate() {
		return astate;
	}
	public void setAstate(String astate) {
		this.astate = astate;
	}
	public Integer getTabcenterid() {
		return tabcenterid;
	}
	public void setTabcenterid(Integer tabcenterid) {
		this.tabcenterid = tabcenterid;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCentername() {
		return centername;
	}
	public void setCentername(String centername) {
		this.centername = centername;
	}
	public String getStrdetail() {
		return strdetail;
	}
	public void setStrdetail(String strdetail) {
		this.strdetail = strdetail;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIsvalid() {
		return isvalid;
	}
	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid;
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
	public String getThvolunteertype() {
		return thvolunteertype;
	}
	public void setThvolunteertype(String thvolunteertype) {
		this.thvolunteertype = thvolunteertype;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public Integer getCenterid() {
		return centerid;
	}
	public void setCenterid(Integer centerid) {
		this.centerid = centerid;
	}
	public String getThspeciality() {
		return thspeciality;
	}
	public void setThspeciality(String thspeciality) {
		this.thspeciality = thspeciality;
	}
	public String getIstisvalid() {
		return istisvalid;
	}
	public void setIstisvalid(String istisvalid) {
		this.istisvalid = istisvalid;
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
	public String getCetname() {
		return cetname;
	}
	public void setCetname(String cetname) {
		this.cetname = cetname;
	}
	public String getDupdatetime() {
		return dupdatetime;
	}
	public void setDupdatetime(String dupdatetime) {
		this.dupdatetime = dupdatetime;
	}
	public Clob getInsdetail() {
		return insdetail;
	}
	public void setInsdetail(Clob insdetail) {
		this.insdetail = insdetail;
	}
	public String getVolunteertype() {
		return volunteertype;
	}
	public void setVolunteertype(String volunteertype) {
		this.volunteertype = volunteertype;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	
}
