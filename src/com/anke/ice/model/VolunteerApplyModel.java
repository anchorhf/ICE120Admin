package com.anke.ice.model;

import java.util.Date;

import javax.ws.rs.FormParam;

import com.anke.ice.inject.Table;

@Table(tableName = "T_VOLUNTEER_APPLY")
public class VolunteerApplyModel {
	
	//@Id
	@FormParam("ID")
	private int ID;
	@FormParam("VOLUNTEERID")
	private int VOLUNTEERID;
	@FormParam("VOLUNTEER")
	private String VOLUNTEER;
	@FormParam("AREAID")
	private int AREAID;
	@FormParam("AREA")
	private String AREA;
	@FormParam("INSTITUTIONID")
	private int INSTITUTIONID;
	@FormParam("INSTITUTION")
	private String INSTITUTION;
	@FormParam("TYPEID")
	private int TYPEID;
	@FormParam("VOLUNTEERTYPE")
	private String VOLUNTEERTYPE;
	@FormParam("VALIDPERIOD")
	private String VALIDPERIOD;
	@FormParam("APPLYSTATE")
	private int APPLYSTATE;
	@FormParam("APPLYSTATENAME")
	private String APPLYSTATENAME;
	@FormParam("SKILL")
	private String SKILL;
	@FormParam("APPLYTIME")
	private String APPLYTIME;
	@FormParam("CHECKTIME")
	private String CHECKTIME;
	@FormParam("CHECKPERSON")
	private String CHECKPERSON;
	@FormParam("AUDITRESULT")
	private String AUDITRESULT;
	

	public int getID() {
		return ID;
	}
	public void setID(int ID) {
		this.ID = ID;
	}
	public int getVOLUNTEERID() {
		return VOLUNTEERID;
	}
	public void setVOLUNTEERID(int VOLUNTEERID) {
		this.VOLUNTEERID = VOLUNTEERID;
	}
	
	//private String VOLUNTEER;
	public String getVOLUNTEER() {
		return VOLUNTEER;
	}
	public void setVOLUNTEER(String VOLUNTEER) {
		this.VOLUNTEER = VOLUNTEER;
	}
	
	public int getAREAID() {
		return AREAID;
	}
	public void setAREAID(int AREAID) {
		this.AREAID = AREAID;
	}
	
	//private String AREA;
	public String getAREA() {
		return AREA;
	}
	public void setAREA(String AREA) {
		this.AREA = AREA;
	}
	
	//private int INSTITUTIONID;//申请机构编码
	public int getINSTITUTIONID() {
		return INSTITUTIONID;
	}
	public void setINSTITUTIONID(int INSTITUTIONID) {
		this.INSTITUTIONID = INSTITUTIONID;
	}
	
	//private String INSTITUTION;//申请机构
	public String getINSTITUTION() {
		return INSTITUTION;
	}
	public void setINSTITUTION(String INSTITUTION) {
		this.INSTITUTION = INSTITUTION;
	}
	
	//@FormParam("TYPEID")
	//private int TYPEID;//志愿者类型编码
	public int getTYPEID() {
		return TYPEID;
	}
	public void setTYPEID(int TYPEID) {
		this.TYPEID = TYPEID;
	}
	
	//@FormParam("VOLUNTEERTYPE")
	//private String VOLUNTEERTYPE;//志愿者类型
	public String getVOLUNTEERTYPE() {
		return VOLUNTEERTYPE;
	}
	public void setVOLUNTEERTYPE(String VOLUNTEERTYPE) {
		this.VOLUNTEERTYPE = VOLUNTEERTYPE;
	}
	
	//@FormParam("VALIDPERIOD")
	//private Date VALIDPERIOD;//有效期限
	public String getVALIDPERIOD() {
		return VALIDPERIOD;
	}
	public void setVALIDPERIOD(String VALIDPERIOD) {
		this.VALIDPERIOD = VALIDPERIOD;
	}
	
	//@FormParam("APPLYSTATE")
	//private int APPLYSTATE;//申请状态
	public int getAPPLYSTATE() {
		return APPLYSTATE;
	}
	public void setAPPLYSTATE(int APPLYSTATE) {
		this.APPLYSTATE = APPLYSTATE;
	}

	//@FormParam("APPLYSTATENAME")
	//private String APPLYSTATENAME;
	public String getAPPLYSTATENAME() {
		return APPLYSTATENAME;
	}
	public void setAPPLYSTATENAME(String APPLYSTATENAME) {
		this.APPLYSTATENAME = APPLYSTATENAME;
	}
	
	//@FormParam("SKILL")
	//private String SKILL;//技能
	public String getSKILL() {
		return SKILL;
	}
	public void setSKILL(String SKILL) {
		this.SKILL = SKILL;
	}
	
	//@FormParam("APPLYTIME")
	//private Date APPLYTIME;//申请时刻
	public String getAPPLYTIME() {
		return APPLYTIME;
	}
	public void setAPPLYTIME(String APPLYTIME) {
		this.APPLYTIME = APPLYTIME;
	}
	
	//@FormParam("CHECKTIME")
	//private Date CHECKTIME;//审核时刻
	public String getCHECKTIME() {
		return CHECKTIME;
	}
	public void setCHECKTIME(String CHECKTIME) {
		this.CHECKTIME = CHECKTIME;
	}
	
	//@FormParam("CHECKPERSON")
	//private String CHECKPERSON;
	public String getCHECKPERSON() {
		return CHECKPERSON;
	}
	public void setCHECKPERSON(String CHECKPERSON) {
		this.CHECKPERSON = CHECKPERSON;
	}
	
	public String getAUDITRESULT() {
		return AUDITRESULT;
	}
	public void setAUDITRESULT(String AUDITRESULT) {
		this.AUDITRESULT = AUDITRESULT;
	}
	
	
	//拓展志愿者表
	@FormParam("NAME")
	private String NAME;
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String NAME) {
		this.NAME = NAME;
	}

	@FormParam("SEX")
	private String SEX;
	public String getSEX() {
		return SEX;
	}
	public void setSEX(String SEX) {
		this.SEX = SEX;
	}

	@FormParam("BIRTHDAY")
	private String BIRTHDAY;
	public String getBIRTHDAY() {
		return BIRTHDAY;
	}
	public void setBIRTHDAY(String BIRTHDAY) {
		this.BIRTHDAY = BIRTHDAY;
	}
	
	@FormParam("IDCARDNO")
	private String IDCARDNO;
	public String getIDCARDNO() {
		return IDCARDNO;
	}
	public void setIDCARDNO(String IDCARDNO) {
		this.IDCARDNO = IDCARDNO;
	}
	
	@FormParam("SPECIALITY")
	private String SPECIALITY;
	public String getSPECIALITY() {
		return SPECIALITY;
	}
	public void setSPECIALITY(String SPECIALITY) {
		this.SPECIALITY = SPECIALITY;
	}
	
	@FormParam("ISVALID")
	private String ISVALID;
	public String getISVALID() {
		return ISVALID;
	}
	public void setISVALID(String ISVALID) {
		this.ISVALID = ISVALID;
	}
	
	@FormParam("CONTENT")
	private String CONTENT;//申请附近内容
	public String getCONTENT() {
		return CONTENT;
	}
	public void setCONTENT(String CONTENT) {
		this.CONTENT = CONTENT;
	}
}
