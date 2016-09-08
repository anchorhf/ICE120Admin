package com.anke.ice.model;

import java.sql.Clob;
import java.util.Date;

import javax.ws.rs.FormParam;

import com.anke.ice.inject.Id;
import com.anke.ice.inject.NotColumn;
import com.anke.ice.inject.Table;

@Table(tableName = "T_MSG")
public class MsgModel {

	@Id
	@FormParam("msgid")
	private int msgid;
	
	
	@NotColumn
	private String tabmsgid;
	@NotColumn
	private String tabtitle;
	@NotColumn
	private String tabmsgtype;
	@NotColumn
	private String tabprofile;
	@NotColumn
	private Integer tabinstitutionid;
	@NotColumn
	private String tabreleasetime;
	@NotColumn
	private String tabapproval;
	@NotColumn
	private String tabinstitution;

	@FormParam("title")
	private String title;

	@FormParam("msgtypeid")
	private int msgtypeid;
	@FormParam("profile")
	private String profile;
	@FormParam("approval")
	private int approval;
	@FormParam("centerid")
	private Integer centerid;
	@FormParam("releasetime")
	private Date releasetime;
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
	private Clob msgcontent;
	

	public int getMsgid() {
		return msgid;
	}
	public void setMsgid(int msgid) {
		this.msgid = msgid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getMsgtypeid() {
		return msgtypeid;
	}
	public void setMsgtypeid(int msgtypeid) {
		this.msgtypeid = msgtypeid;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public int getApproval() {
		return approval;
	}
	public void setApproval(int approval) {
		this.approval = approval;
	}
	public Integer getCenterid() {
		return centerid;
	}
	public void setCenterid(Integer centerid) {
		this.centerid = centerid;
	}
	public Date getReleasetime() {
		return releasetime;
	}
	public void setReleasetime(Date releasetime) {
		this.releasetime = releasetime;
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
	public Clob getMsgcontent() {
		return msgcontent;
	}
	public void setMsgcontent(Clob msgcontent) {
		this.msgcontent = msgcontent;
	}
	public String getTabinstitution() {
		return tabinstitution;
	}
	public void setTabinstitution(String tabinstitution) {
		this.tabinstitution = tabinstitution;
	}
	public String getTabmsgid() {
		return tabmsgid;
	}
	public void setTabmsgid(String tabmsgid) {
		this.tabmsgid = tabmsgid;
	}
	public String getTabtitle() {
		return tabtitle;
	}
	public void setTabtitle(String tabtitle) {
		this.tabtitle = tabtitle;
	}
	public String getTabmsgtype() {
		return tabmsgtype;
	}
	public void setTabmsgtype(String tabmsgtype) {
		this.tabmsgtype = tabmsgtype;
	}
	public String getTabprofile() {
		return tabprofile;
	}
	public void setTabprofile(String tabprofile) {
		this.tabprofile = tabprofile;
	}
	public String getTabreleasetime() {
		return tabreleasetime;
	}
	public void setTabreleasetime(String tabreleasetime) {
		this.tabreleasetime = tabreleasetime;
	}
	public String getTabapproval() {
		return tabapproval;
	}
	public void setTabapproval(String tabapproval) {
		this.tabapproval = tabapproval;
	}
	public Integer getTabinstitutionid() {
		return tabinstitutionid;
	}
	public void setTabinstitutionid(Integer tabinstitutionid) {
		this.tabinstitutionid = tabinstitutionid;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
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

}
