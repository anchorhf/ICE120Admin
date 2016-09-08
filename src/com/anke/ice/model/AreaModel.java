package com.anke.ice.model;

import javax.ws.rs.FormParam;

import com.anke.ice.inject.Id;
import com.anke.ice.inject.Table;

@Table(tableName = "T_Area")
public class AreaModel {

	@Id
	@FormParam("ID")
	private int ID;
	@FormParam("NAME")
	private String NAME;
	@FormParam("FATHERID")
	private int FATHERID;
	@FormParam("ORDERNO")
	private int ORDERNO;
	@FormParam("ISVALID")
	private int ISVALID;
	

	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	public int getFATHERID() {
		return FATHERID;
	}
	public void setFATHERID(int fATHERID) {
		FATHERID = fATHERID;
	}
	public int getORDERNO() {
		return ORDERNO;
	}
	public void setORDERNO(int oRDERNO) {
		ORDERNO = oRDERNO;
	}
	public int getISVALID() {
		return ISVALID;
	}
	public void setISVALID(int iSVALID) {
		ISVALID = iSVALID;
	}
}
