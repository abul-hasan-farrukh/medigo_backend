package com.medigo.dto;

public class AdminDTO { // It is a subset of the model or entity, used to provide only specific fields
						// to the frontend

	private String name, phone; // variable names must be similar to Admin.java model, Only these variables will
								// we available to admin on frontend
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

	public AdminDTO(String name, String phone) {
		super();
		this.name = name;
		this.phone = phone;
	}

	public AdminDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

}