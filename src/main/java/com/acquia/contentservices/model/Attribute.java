package com.acquia.contentservices.model;

import java.util.HashMap;

public class Attribute {
	
	private String type;
	private HashMap<String,String> value;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public HashMap<String, String> getValue() {
		return value;
	}
	public void setValue(HashMap<String, String> value) {
		this.value = value;
	}

}
