package com.sit.app.core.config.parameter.domain;

public enum DBLookup {
	ORA_OC("0");

	private String lookup;

	private DBLookup(String lookup) {
		this.lookup = lookup;
	}

	public String getLookup() {
		return lookup;
	}
	
}