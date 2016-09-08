package com.anke.ice.model;

import java.sql.Clob;
import java.util.Date;

import javax.ws.rs.FormParam;

import com.anke.ice.inject.Id;
import com.anke.ice.inject.NotColumn;
import com.anke.ice.inject.Table;

@Table(tableName = "t_manual")
public class FirstAHBModel {
	@Id
	@FormParam("id")
	private int id;
	@FormParam("name")
	private String name;
	@FormParam("profile")
	private String profile;
	@FormParam("isvalid")
	private int isvalid;
	@FormParam("isvolunteer")
	private int isvolunteer;
	@FormParam("orderno")
	private int orderno;
	@FormParam("strcontent")
	private String strcontent;
	@FormParam("content")
	private String content;
	@FormParam("url")
	private String url;
	@FormParam("picname")
	private String picname;
	@FormParam("strurl")
	private String strurl;
	
	@NotColumn
	private Clob firstahbcontent;
	@NotColumn
	private Integer tabid;
	@NotColumn
	private String tabname;
	@NotColumn
	private String tabprofile;
	@NotColumn
	private String tabisvolunteer;
	@NotColumn
	private String tabisvalid;
	@NotColumn
	private Integer taborderno;
	@NotColumn
	private String taburl;


	public String getPicname() {
		return picname;
	}
	public void setPicname(String picname) {
		this.picname = picname;
	}
	public String getStrurl() {
		return strurl;
	}
	public void setStrurl(String strurl) {
		this.strurl = strurl;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public int getIsvalid() {
		return isvalid;
	}
	public void setIsvalid(int isvalid) {
		this.isvalid = isvalid;
	}
	public int getIsvolunteer() {
		return isvolunteer;
	}
	public void setIsvolunteer(int isvolunteer) {
		this.isvolunteer = isvolunteer;
	}
	public int getOrderno() {
		return orderno;
	}
	public void setOrderno(int orderno) {
		this.orderno = orderno;
	}
	public String getStrcontent() {
		return strcontent;
	}
	public void setStrcontent(String strcontent) {
		this.strcontent = strcontent;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Clob getFirstahbcontent() {
		return firstahbcontent;
	}
	public void setFirstahbcontent(Clob firstahbcontent) {
		this.firstahbcontent = firstahbcontent;
	}
	public Integer getTabid() {
		return tabid;
	}
	public void setTabid(Integer tabid) {
		this.tabid = tabid;
	}
	public String getTabname() {
		return tabname;
	}
	public void setTabname(String tabname) {
		this.tabname = tabname;
	}
	public String getTabprofile() {
		return tabprofile;
	}
	public void setTabprofile(String tabprofile) {
		this.tabprofile = tabprofile;
	}
	public String getTabisvolunteer() {
		return tabisvolunteer;
	}
	public void setTabisvolunteer(String tabisvolunteer) {
		this.tabisvolunteer = tabisvolunteer;
	}
	public String getTabisvalid() {
		return tabisvalid;
	}
	public void setTabisvalid(String tabisvalid) {
		this.tabisvalid = tabisvalid;
	}
	public Integer getTaborderno() {
		return taborderno;
	}
	public void setTaborderno(Integer taborderno) {
		this.taborderno = taborderno;
	}
	public String getTaburl() {
		return taburl;
	}
	public void setTaburl(String taburl) {
		this.taburl = taburl;
	}

	
}
