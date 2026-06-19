package com.medigo.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "camp")
public class Camp {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Auto Increment - 23 Mar, 2026
	@Column(name = "camp_id")
	private int camp_id;

	@Column(name = "title", length = 300, nullable = false)
	private String title;

	@Column(name = "venue", length = 300, nullable = false)
	private String venue;

	@Column(name = "description", length = 3000, nullable = false)
	private String description;

	@Column(name = "date")
	private LocalDate date;

	public int getCampId() {
		return camp_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getVenue() {
		return venue;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Camp(String title, String venue, String description, LocalDate date) {
		super();
		this.title = title;
		this.venue = venue;
		this.description = description;
		this.date = date;
	}

	public Camp() {
		super();
	}

}