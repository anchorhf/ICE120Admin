package com.anke.ice.model;

import java.util.Date;

import javax.ws.rs.FormParam;

import com.anke.ice.inject.Id;
import com.anke.ice.inject.NotColumn;
import com.anke.ice.inject.Table;

@Table(tableName = "T_AED")
public class AEDModel {
	@Id
	@FormParam("id")
	private int id;
	
	@FormParam("longitude")
	private double longitude;
	
	@FormParam("latitude")
	private double latitude;
	
	@FormParam("building")
	private String building;
	
	@FormParam("address")
	private String address;
	
	@FormParam("place")
	private String place;
	
	@FormParam("isvalid")
	private int isvalid;
	
	@FormParam("remark")
	private String remark;
	
	@FormParam("createtime")
	private Date createtime;
	
	@FormParam("area")
	private String area;
	
	@NotColumn
	private String tabisvalid;
	@NotColumn
	private String tabaddress;
	@NotColumn
	private String tabbuilding;
	@NotColumn
	private String tabplace;
	@NotColumn
	private String tabcreatetime;
	@NotColumn
	private String tabremark;
	@NotColumn
	private String tabarea;
	@NotColumn
	private Integer tabid;
	@NotColumn
	private Double tablongitude;
	@NotColumn
	private Double tablatitude;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public String getBuilding() {
		return building;
	}
	public void setBuilding(String building) {
		this.building = building;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public int getIsvalid() {
		return isvalid;
	}
	public void setIsvalid(int isvalid) {
		this.isvalid = isvalid;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getTabisvalid() {
		return tabisvalid;
	}
	public void setTabisvalid(String tabisvalid) {
		this.tabisvalid = tabisvalid;
	}
	public String getTabaddress() {
		return tabaddress;
	}
	public void setTabaddress(String tabaddress) {
		this.tabaddress = tabaddress;
	}
	public String getTabbuilding() {
		return tabbuilding;
	}
	public void setTabbuilding(String tabbuilding) {
		this.tabbuilding = tabbuilding;
	}
	public String getTabplace() {
		return tabplace;
	}
	public void setTabplace(String tabplace) {
		this.tabplace = tabplace;
	}
	public String getTabcreatetime() {
		return tabcreatetime;
	}
	public void setTabcreatetime(String tabcreatetime) {
		this.tabcreatetime = tabcreatetime;
	}
	public String getTabremark() {
		return tabremark;
	}
	public void setTabremark(String tabremark) {
		this.tabremark = tabremark;
	}
	public String getTabarea() {
		return tabarea;
	}
	public void setTabarea(String tabarea) {
		this.tabarea = tabarea;
	}
	public Integer getTabid() {
		return tabid;
	}
	public void setTabid(Integer tabid) {
		this.tabid = tabid;
	}
	public Double getTablongitude() {
		return tablongitude;
	}
	public void setTablongitude(Double tablongitude) {
		this.tablongitude = tablongitude;
	}
	public Double getTablatitude() {
		return tablatitude;
	}
	public void setTablatitude(Double tablatitude) {
		this.tablatitude = tablatitude;
	}
	
	
}
