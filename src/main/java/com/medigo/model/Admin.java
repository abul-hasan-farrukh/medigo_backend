package com.medigo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity // this creates table in database with the class name
public class Admin {

	private String profilePic;
	private String description;

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Id // this creates email as primary key
	@Column(name = "email", length = 45)
	private String email;

	@Column(name = "password", length = 45, nullable = false)
	private String password;

	@Column(name = "name", length = 45, nullable = false)
	private String name;

	@Column(name = "phone", length = 15, nullable = false)
	private String phone;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Admin() {
		/*
		 * super(); TODO Auto-generated constructor stub
		 */
	}

}