package com.medigo.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table
public class TestBooking {

	@Transient
	Test test;

	public Test getTest() {
		return test;
	}

	public void setTest(Test test) {
		this.test = test;
	}

	public Long getBookingId() {
		return bookingId;
	}

	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bookingId;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private Integer testId;

	@Column(nullable = false)
	private String bookingStatus;

	@Column(nullable = false, name = "date")
	private LocalDate date;

	// added tranasction_no - 14 April, 2026 ()
	@Column(name = "transaction_no")
	private String transactionNo;

	public String getTransactionNo() {
		return transactionNo;
	}

	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}

	// Default Constructor
	public TestBooking() {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getTestId() {
		return testId;
	}

	public void setTestId(Integer testId) {
		this.testId = testId;
	}

	public String getBookingStatus() {
		return bookingStatus;
	}

	public void setBookingStatus(String bookingStatus) {
		this.bookingStatus = bookingStatus;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public TestBooking(Test test, String email, Integer testId, String bookingStatus, LocalDate date) {
		super();
		this.test = test;
		this.email = email;
		this.testId = testId;
		this.bookingStatus = bookingStatus;
		this.date = date;
	}

	// Parameterized Constructor

	// Getters and Setters

}