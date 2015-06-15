package com.acquia.contentservices.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Entity {
	
	private String uuid;
	private String type;
	private String created;
	private String modified;
	private HashMap<String, Attribute> attributes;
	private ArrayList<Asset> assets;
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getModified() {
		return modified;
	}
	public void setModified(String modified) {
		this.modified = modified;
	}
	public HashMap<String, Attribute> getAttributes() {
		return attributes;
	}
	public void setAttributes(HashMap<String, Attribute> attributes) {
		this.attributes = attributes;
	}
	public ArrayList<Asset> getAssets() {
		return assets;
	}
	public void setAssets(ArrayList<Asset> assets) {
		this.assets = assets;
	}

}
