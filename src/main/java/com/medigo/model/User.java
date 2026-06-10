package com.medigo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity // this creates table in database with the class name
public class User {

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

	@Id
	@Column(name = "email", length = 45, nullable = false) // making email as primary key here using Id annotation.
	private String email;

	@Column(name = "name", length = 45, nullable = false)
	private String name;

	@Column(name = "phone", length = 10, nullable = false)
	private String phone;

	@Column(name = "password", length = 45, nullable = false)
	private String password;

	@Column(name = "city", length = 45, nullable = false)
	private String city;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public User(String email, String name, String phone, String password, String city) {
		super();
		this.email = email;
		this.name = name;
		this.phone = phone;
		this.password = password;
		this.city = city;
	}

	public User() {
		/*
		 * super(); // TODO Auto-generated constructor stub
		 */}

}