package com.anke.ice.model;

import javax.ws.rs.FormParam;

import com.anke.ice.inject.Id;
import com.anke.ice.inject.NotColumn;
import com.anke.ice.inject.Table;

@Table(tableName = "T_REGUSER")
public class Menber {

	@Id
	@FormParam("ltelphone")
	private String ltelphone;
	@FormParam("email")
	private String email;
	@FormParam("regtime")
	private String regtime;
	@FormParam("userid")
	private int userid;
	@FormParam("registrationaddress")
	private String registrationaddress;
	@FormParam("patientid")
	private String patientid;
	@FormParam("address")
	private String address;
	@FormParam("describe")
	private String describe;
	@FormParam("nickname")
	private String nickname;
	@FormParam("name")
	private String name;
	@FormParam("blood")
	private String blood;
	@FormParam("weight")
	private String weight;
	@FormParam("birthday")
	private String birthday;
	@FormParam("permanentaddress")
	private String permanentaddress;
	@FormParam("pic")
	private String pic;
	
	@FormParam("telphone")
	private String telphone;
	
	@FormParam("medicalhistory")
	private String medicalhistory;
	@FormParam("othermedicalhistory")
	private String othermedicalhistory;
	@FormParam("allergy")
	private String allergy;
	@FormParam("otherallergy")
	private String otherallergy;
	
	@FormParam("remark")
	private String remark;
	@FormParam("ctelphone")
	private String ctelphone;
	@FormParam("cnickname")
	private String cnickname;
	public String getTelphone() {
		return telphone;
	}
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRegtime() {
		return regtime;
	}
	public void setRegtime(String regtime) {
		this.regtime = regtime;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getRegistrationaddress() {
		return registrationaddress;
	}
	public void setRegistrationaddress(String registrationaddress) {
		this.registrationaddress = registrationaddress;
	}
	public String getPatientid() {
		return patientid;
	}
	public void setPatientid(String patientid) {
		this.patientid = patientid;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBlood() {
		return blood;
	}
	public void setBlood(String blood) {
		this.blood = blood;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getLtelphone() {
		return ltelphone;
	}
	public void setLtelphone(String ltelphone) {
		this.ltelphone = ltelphone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getPermanentaddress() {
		return permanentaddress;
	}
	public void setPermanentaddress(String permanentaddress) {
		this.permanentaddress = permanentaddress;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getMedicalhistory() {
		return medicalhistory;
	}
	public void setMedicalhistory(String medicalhistory) {
		this.medicalhistory = medicalhistory;
	}
	public String getOthermedicalhistory() {
		return othermedicalhistory;
	}
	public void setOthermedicalhistory(String othermedicalhistory) {
		this.othermedicalhistory = othermedicalhistory;
	}
	public String getAllergy() {
		return allergy;
	}
	public void setAllergy(String allergy) {
		this.allergy = allergy;
	}
	public String getOtherallergy() {
		return otherallergy;
	}
	public void setOtherallergy(String otherallergy) {
		this.otherallergy = otherallergy;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCtelphone() {
		return ctelphone;
	}
	public void setCtelphone(String ctelphone) {
		this.ctelphone = ctelphone;
	}
	public String getCnickname() {
		return cnickname;
	}
	public void setCnickname(String cnickname) {
		this.cnickname = cnickname;
	}

	
	
}
