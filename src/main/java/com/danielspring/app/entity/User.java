package com.danielspring.app.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class User implements Serializable {
	
	private static final long serialVersionUID = 7262546995217868203L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 50)
	private String name;
	
	private String pass;
	
	@Column(name = "email", nullable = false, length = 50, unique = true)
	private String email;
	
	private Boolean enabled;
	
	private String imagenPath;
	
	@Transient
	private String imagenUrl;
	
	private String cedPath;
	
	@Transient
	private String cedUrl;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getImagenPath() {
		return imagenPath;
	}

	public void setImagenPath(String imagenPath) {
		this.imagenPath = imagenPath;
	}

	public String getImagenUrl() {
		return imagenUrl;
	}

	public void setImagenUrl(String imagenUrl) {
		this.imagenUrl = imagenUrl;
	}

	public String getCedPath() {
		return cedPath;
	}

	public void setCedPath(String cedPath) {
		this.cedPath = cedPath;
	}

	public String getCedUrl() {
		return cedUrl;
	}

	public void setCedUrl(String cedUrl) {
		this.cedUrl = cedUrl;
	}
	
	

}
