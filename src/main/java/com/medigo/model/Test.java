package com.medigo.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.math.BigDecimal;

@Entity
@Table(name = "test")
public class Test {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Auto Increment - 19 Mar, 2026
	@Column(name = "test_id")
	private int testId;

	@Column(name = "test_name", length = 200, nullable = false)
	private String testName;

	@Column(name = "test_type", length = 100)
	private String testType;

	@Column(name = "test_description", columnDefinition = "TEXT")
	private String testDescription;

	@Column(name = "preparation", columnDefinition = "TEXT")
	private String preparation;

	@Column(name = "sample_required", length = 100)
	private String sampleRequired;

	@Column(name = "test_price", precision = 10, scale = 2)
	private BigDecimal testPrice;

	@Column(name = "report_time", length = 50)
	private String reportTime;

	@Column(name = "status", length = 20)
	private String status;

	@Column(name = "created_at")
	private LocalDate createdAt;

	// Default Constructor
	public Test() {
	}

	// Parameterized Constructor
	public Test(String testName, String testType, String testDescription, String preparation, String sampleRequired,
			BigDecimal testPrice, String reportTime, String status, LocalDate createdAt) {
		this.testName = testName;
		this.testType = testType;
		this.testDescription = testDescription;
		this.preparation = preparation;
		this.sampleRequired = sampleRequired;
		this.testPrice = testPrice;
		this.reportTime = reportTime;
		this.status = status;
		this.createdAt = createdAt;
	}

	// Getters and Setters

	public int getTestId() {
		return testId;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getTestType() {
		return testType;
	}

	public void setTestType(String testType) {
		this.testType = testType;
	}

	public String getTestDescription() {
		return testDescription;
	}

	public void setTestDescription(String testDescription) {
		this.testDescription = testDescription;
	}

	public String getPreparation() {
		return preparation;
	}

	public void setPreparation(String preparation) {
		this.preparation = preparation;
	}

	public String getSampleRequired() {
		return sampleRequired;
	}

	public void setSampleRequired(String sampleRequired) {
		this.sampleRequired = sampleRequired;
	}

	public BigDecimal getTestPrice() {
		return testPrice;
	}

	public void setTestPrice(BigDecimal testPrice) {
		this.testPrice = testPrice;
	}

	public String getReportTime() {
		return reportTime;
	}

	public void setReportTime(String reportTime) {
		this.reportTime = reportTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}
}