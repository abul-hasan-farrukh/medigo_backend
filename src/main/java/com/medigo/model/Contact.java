package com.medigo.model;

import jakarta.persistence.Id;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity // it will create a table with the name of Class i.e. "Contact"
//@Table (name="contact")
public class Contact {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name, email, phone, question;

	public int getId() {
		return id;
	}

	public Contact() {
		/*
		 * super(); // TODO Auto-generated constructor stub
		 */}

	public Contact(String name, String email, String phone, String question) {
		super();
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.question = question;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

}