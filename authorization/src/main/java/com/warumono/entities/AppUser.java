package com.warumono.entities;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Table(name = "USERS", uniqueConstraints = @UniqueConstraint(columnNames = { "username" }))
@Entity
public class AppUser {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Size(max = 50)
	@Email
	@NotNull
	private String username;

	@JsonIgnore
	@NotNull
	private String password;

	@NotNull
	private String authorities;

	public AppUser() {};
	public AppUser(final long id, final String userName, final String password, final String authorities) {
		this.id = id;
		this.username  = userName;
		this.password = password;
		this.authorities = authorities;
	}

	public Collection<GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();

		if (StringUtils.isEmpty(grantedAuthorities)) {
			String[] _authorities = authorities.split(",");

			for (String authority : _authorities) {
				grantedAuthorities.add(new SimpleGrantedAuthority(authority));
			}
		}

		if (CollectionUtils.isEmpty(grantedAuthorities)) {
			grantedAuthorities.add(new SimpleGrantedAuthority("USER"));
		}

		return grantedAuthorities;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof AppUser)) {
			return false;
		}
		return false;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAuthorities(String authorities) {
		this.authorities = authorities;
	}
}
