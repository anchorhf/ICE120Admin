package com.anke.ice.model;

import java.util.List;
import javax.ws.rs.FormParam;
import com.anke.ice.inject.Table;


@Table(tableName = "T_MENU")
public class MYTreeNode {
	
	@FormParam("id")
	public int id;
	@FormParam("text")
	public String text;
	@FormParam("state")
	public String state;
	@FormParam("checked")
	public boolean checked;
	@FormParam("url")
	public String url;

	@FormParam("children")
	public List<MYTreeNode> children;
	
	
	public String geturl() {
		return url;
	}
	public void seturl(String url) {
		this.url = url;
	}

	public List<MYTreeNode> getChildren() {
		return children;
	}
	public void setChildren(List<MYTreeNode> children) {
		this.children = children;
	}

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public boolean getChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	}