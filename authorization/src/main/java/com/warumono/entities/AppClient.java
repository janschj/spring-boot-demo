package com.warumono.entities;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Table(name = "CLIENTS", uniqueConstraints = @UniqueConstraint(columnNames = { "id" }))
@Entity
public class AppClient
{
	@Id
	private String id;
	
	@JsonIgnore
	@NotNull
	private String secret;
	
	@NotNull
	private String scopes;
	
	@NotNull
	private String grantTypes;

	public AppClient() {};
	public AppClient(String id, String secret, String scopes, String grantTypes) {
		this.id = id;
		this.secret = secret;
		this.scopes = scopes;
		this.grantTypes = grantTypes;
	}

	public Collection<String> getScopes()
	{
		if(StringUtils.isEmpty(scopes))
		{
			return null;
		}

		return Arrays.asList(scopes.split(","));
	}

	public Collection<String> getGrantTypes()
	{
		if(StringUtils.isEmpty(grantTypes))
		{
			return null;
		}
		
		return Arrays.asList(grantTypes.split(","));
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public void setScopes(String scopes) {
		this.scopes = scopes;
	}

	public void setGrantTypes(String grantTypes) {
		this.grantTypes = grantTypes;
	}
}
