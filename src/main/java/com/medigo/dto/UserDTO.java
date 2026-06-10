package com.medigo.dto;

public class UserDTO { // It is a subset of the model or entity, used to provide only specific fields
						// to the frontend

	private String name, phone, city; // variable names must be similar to User.java model.
	private String profilePic;

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public UserDTO(String name, String phone, String city) {
		super();
		this.name = name;
		this.phone = phone;
		this.city = city;
	}

	public UserDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

}