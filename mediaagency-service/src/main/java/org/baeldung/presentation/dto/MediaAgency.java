package org.baeldung.presentation.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class MediaAgency {

	public MediaAgency(String id) {
		this.id = id;
	}

	public MediaAgency() {};
	public MediaAgency(final String id, final String name, final String email) {
		this.id = id;
		this.name = name;
		this.email = email;
	}

	private String id;

	private String name;

	private String email;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
