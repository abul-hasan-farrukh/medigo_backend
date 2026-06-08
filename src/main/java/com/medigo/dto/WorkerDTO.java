package com.medigo.dto;

public class WorkerDTO {

	private String name;
	private String email;
	private String type;
	private String phone;
	private String qualification;
	private String experience;

	private String profilePic;

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public WorkerDTO(String name, String email, String type, String phone, String qualification, String experience) {
		super();
		this.name = name;
		this.email = email;
		this.type = type;
		this.phone = phone;
		this.qualification = qualification;
		this.experience = experience;
	}

	public WorkerDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

}