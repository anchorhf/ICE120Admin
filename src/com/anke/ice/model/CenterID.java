package com.anke.ice.model;
import javax.ws.rs.FormParam;

import com.anke.ice.inject.Id;
import com.anke.ice.inject.NotColumn;
import com.anke.ice.inject.Table;
@Table(tableName = "t_center")

public class CenterID {
	
	@Id
	@FormParam("centerid")
	private int centerid;

	public int getCenterid() {
		return centerid;
	}

	public void setCenterid(int centerid) {
		this.centerid = centerid;
	}
  
}
