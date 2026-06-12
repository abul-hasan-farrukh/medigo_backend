package com.medigo.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer pId;

	@Column(nullable = false)
	private String userEmail;

	@Column(nullable = false)
	private String transactionNo;

	@Column(nullable = false)
	private Integer amount;

	@Column(nullable = false, name = "date")
	private LocalDate date;// bookingDate given by User

	private String tokenNumber;

	private boolean visitStatus;
	private boolean reportUploaded;
	private String testReportFile;

	public boolean isVisitStatus() {
		return visitStatus;
	}

	public void setVisitStatus(boolean visitStatus) {
		this.visitStatus = visitStatus;
	}

	public boolean isReportUploaded() {
		return reportUploaded;
	}

	public void setReportUploaded(boolean reportUploaded) {
		this.reportUploaded = reportUploaded;
	}

	public String getTestReportFile() {
		return testReportFile;
	}

	public void setTestReportFile(String testReportFile) {
		this.testReportFile = testReportFile;
	}

	public String getTokenNumber() {
		return tokenNumber;
	}

	public void setTokenNumber(String tokenNumber) {
		this.tokenNumber = tokenNumber;
	}

	// Default Constructor
	public Payment() {
	}

	// Getters and Setters

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getTransactionNo() {
		return transactionNo;
	}

	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Payment(String userEmail, String transactionNo, Integer amount, LocalDate date) {
		super();
		this.userEmail = userEmail;
		this.transactionNo = transactionNo;
		this.amount = amount;
		this.date = date;
	}

}