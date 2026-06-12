package com.medigo.model;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "sample_collection")
public class SampleCollection {

	// Created model on 24 Mar, 20256

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false, length = 100)
	private String userName;

	@Column(nullable = false, length = 100)
	private String email;

	@Column(nullable = false, length = 15)
	private String phoneNumber;

	@Column(nullable = false, length = 255)
	private String address;

	@Column(nullable = false, length = 500)
	private String message;

	@Column(nullable = false, length = 20)
	private String status;

	@Column(length = 100, name = "worker_name")
	private String workerName;

	@Column(length = 100, name = "worker_email")
	private String workerEmail;

	@Column(length = 15, name = "worker_phone")
	private String workerPhone;

	@Column(updatable = false)
	private LocalDate request_date;

	@Column(nullable = false)
	private LocalDate sample_collection_date;

	@Column(length = 50)
	private String preferred_time;

	@Column(nullable = false, length = 50) // added sample type on 25 Mar, 2026
	private String sample_type;

	@Column(length = 255)
	private String reportFile;

	private String reportStatus;

	@Column(length = 255) // added testName on 02 April, 2026
	private String testName;

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getReportFile() {
		return reportFile;
	}

	public void setReportFile(String reportFile) {
		this.reportFile = reportFile;
	}

	public String getReportStatus() {
		return reportStatus;
	}

	public void setReportStatus(String reportStatus) {
		this.reportStatus = reportStatus;
	}

	public SampleCollection() {
	}

	public int getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getWorker_name() {
		return workerName;
	}

	public void setWorker_name(String worker_name) {
		this.workerName = worker_name;
	}

	public String getWorker_email() {
		return workerEmail;
	}

	public void setWorker_email(String worker_email) {
		this.workerEmail = worker_email;
	}

	public String getWorker_phone() {
		return workerPhone;
	}

	public void setWorker_phone(String worker_phone) {
		this.workerPhone = worker_phone;
	}

	public LocalDate getRequest_date() {
		return request_date;
	}

	public void setRequest_date(LocalDate request_date) {
		this.request_date = request_date;
	}

	public LocalDate getSample_collection_date() {
		return sample_collection_date;
	}

	public void setSample_collection_date(LocalDate sample_collection_date) {
		this.sample_collection_date = sample_collection_date;
	}

	public String getPreferred_time() {
		return preferred_time;
	}

	public void setPreferred_time(String preferred_time) {
		this.preferred_time = preferred_time;
	}

	public String getSample_type() {
		return sample_type;
	}

	public void setSample_type(String sample_type) {
		this.sample_type = sample_type;
	}
}