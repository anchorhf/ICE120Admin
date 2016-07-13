package com.anke.ice.model;

import javax.ws.rs.FormParam;

import com.anke.ice.inject.Id;
import com.anke.ice.inject.NotColumn;
import com.anke.ice.inject.Table;

@Table(tableName = "t_center")
public class Center {

	@Id
	@FormParam("centerid")
	private int centerid;
	@FormParam("name")
	private String name;
	@FormParam("area")
	private String area;
	@NotColumn
	private String ipport;
	@FormParam("ip")
	private String ip;
	@FormParam("port")
	private String port;
	@FormParam("workphone")
	private String workphone;
	@FormParam("contact")
	private String contact;
	@FormParam("mobilephone")
	private String mobilephone;
	@FormParam("fixedtelephone")
	private String fixedtelephone;
	@FormParam("isonline")
	private int isonline;
	@FormParam("telareano")
	private String telareano;
	@FormParam("account")
	private String account;
	@FormParam("password")
	private String password;
	@FormParam("orderambulance")
	private int orderambulance;
	@NotColumn
	private String totalmemory;
	@NotColumn
	private String maxmemory;
	@NotColumn
	private String freememory;
	@NotColumn
	private String cpu;
	@NotColumn
	private String machinestatus;

	public String getTotalmemory() {
		return totalmemory;
	}

	public void setTotalmemory(String totalmemory) {
		this.totalmemory = totalmemory;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public int getCenterid() {
		return centerid;
	}

	public void setCenterid(int centerid) {
		this.centerid = centerid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIpport() {
		return ipport;
	}

	public void setIpport(String ipport) {
		this.ipport = ipport;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMaxmemory() {
		return maxmemory;
	}

	public void setMaxmemory(String maxmemory) {
		this.maxmemory = maxmemory;
	}

	public String getFreememory() {
		return freememory;
	}

	public void setFreememory(String freememory) {
		this.freememory = freememory;
	}

	public String getCpu() {
		return cpu;
	}

	public void setCpu(String cpu) {
		this.cpu = cpu;
	}

	public String getMachinestatus() {
		return machinestatus;
	}

	public void setMachinestatus(String machinestatus) {
		this.machinestatus = machinestatus;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getWorkphone() {
		return workphone;
	}

	public void setWorkphone(String workphone) {
		this.workphone = workphone;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getFixedtelephone() {
		return fixedtelephone;
	}

	public void setFixedtelephone(String fixedtelephone) {
		this.fixedtelephone = fixedtelephone;
	}

	public int getIsonline() {
		return isonline;
	}

	public void setIsonline(int isonline) {
		this.isonline = isonline;
	}

	public String getTelareano() {
		return telareano;
	}

	public void setTelareano(String telareano) {
		this.telareano = telareano;
	}

	public int getOrderambulance() {
		return orderambulance;
	}

	public void setOrderambulance(int orderambulance) {
		this.orderambulance = orderambulance;
	}

}
