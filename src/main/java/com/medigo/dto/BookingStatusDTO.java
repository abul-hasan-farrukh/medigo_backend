package com.medigo.dto;

import com.medigo.model.Test;

public class BookingStatusDTO {

	private String email;
	private String bookingStatus;
	private String date;

	private String transactionNo;
	private Integer amount;
	private String tokenNumber;

	// added below two variables on 16th April, 2026
	private boolean reportUploaded;
	private String testReportFile;

	private Test test;

	// getters & setters

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBookingStatus() {
		return bookingStatus;
	}

	public void setBookingStatus(String bookingStatus) {
		this.bookingStatus = bookingStatus;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
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

	public String getTokenNumber() {
		return tokenNumber;
	}

	public void setTokenNumber(String tokenNumber) {
		this.tokenNumber = tokenNumber;
	}

	public Test getTest() {
		return test;
	}

	public void setTest(Test test) {
		this.test = test;
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

}