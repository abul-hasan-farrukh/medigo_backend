package com.medigo.model;

import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Feedback {

	@Transient
	User user; // user object will not be stored in Feedback table. - 18 Mar, 2026

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // this annotation auto increments the id serial number.
	private int id;

	public int getId() {
		return id;
	}

	@Column(name = "email", length = 45, nullable = false)
	private String email;

	@Column(name = "rating", length = 5, nullable = false)
	private String rating;

	@Column(name = "review", length = 255, nullable = false)
	private String review;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public Feedback(String email, String rating, String review) {
		super();
		this.email = email;
		this.rating = rating;
		this.review = review;
	}

	public Feedback() {
//		super();
		// TODO Auto-generated constructor stub
	}

}