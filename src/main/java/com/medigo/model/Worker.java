package com.medigo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity // this creates table in database with the class name
public class Worker {

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
//	@GeneratedValue(strategy = GenerationType.IDENTITY) //this annotation auto increments the id serial number.	
	@Column(name = "email", length = 45, nullable = false) // making email as primary key here using Id annotation.
	private String email;

	@Column(name = "name", length = 45, nullable = false)
	private String name;

	@Column(name = "password", length = 45, nullable = false)
	private String password;

	@Column(name = "phone", length = 10, nullable = false)
	private String phone;

	@Column(name = "age", length = 2, nullable = false)
	private String age;

	@Column(name = "gender", length = 10, nullable = false)
	private String gender;

	@Column(name = "address", length = 50, nullable = false)
	private String address;

	@Column(name = "qualification", length = 45, nullable = false)
	private String qualification;

	@Column(name = "experience", length = 10, nullable = false)
	private String experience;

	@Column(name = "type", length = 45, nullable = false)
	private String type;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}