package com.medigo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.medigo.dto.BookingPaymentDTO;
import com.medigo.dto.WorkerDTO;
import com.medigo.model.SampleCollection;
import com.medigo.model.Worker;
import com.medigo.service.WorkerService;

@RestController
@CrossOrigin("${ALLOWED_ORIGIN:*}")
@RequestMapping("/worker") // http://localhost:9090/worker/workerLogin
public class WorkerController {

	private WorkerService workerService;

	public WorkerController(WorkerService workerService) {
		this.workerService = workerService;
	}

	// API for displaying all sample request on executive worker dash-board - 25
	// Mar, 2026
	@GetMapping("/allSampleRequests")
	public List<SampleCollection> getAllRequests() {
		return workerService.getAllRequests();
	}

	// Get all sample collectors to assign request (via drop-down) - 25 Mar, 2026
	@GetMapping("/sampleCollectors")
	public List<Worker> getSampleCollectors() {
		return workerService.getSampleCollectors();
	}

	// Assign worker to a sample request - - 25 Mar, 2026
	@PutMapping("/assignRequest/{id}")
	public SampleCollection assignRequest(@PathVariable int id, @RequestBody Map<String, String> data) {

		return workerService.assignRequest(id, data);
	}

	// Mark request as completed - 25 Mar, 2026
	@PutMapping("/completeRequest/{id}")
	public SampleCollection completeRequest(@PathVariable int id) {
		return workerService.markCompleted(id);
	}

	// Delete the request (reject) - 25 Mar, 2026
	@DeleteMapping("/deleteRequest/{id}")
	public void deleteRequest(@PathVariable int id) {
		workerService.deleteRequest(id);
	}

	// API for Sample Collector Dashboard to show all the assigned request to each
	// sample collector - 26 Mar, 2026
	@GetMapping("/assignedRequests/{email}")
	public List<SampleCollection> getAssignedRequests(@PathVariable String email) {
		return workerService.getAssignedRequests(email);
	}

	// API for image Upload - 17 Mar, 2026
	@PostMapping("/uploadPic")
	public ResponseEntity<Map<String, String>> uploadPic(@RequestPart("profileImageDetail") Worker worker,
			@RequestPart("imageFile") MultipartFile imageFile) // assuming profileImageDetail and imageFile is coming
																// from frontend
	{
		System.out.println(worker.getDescription());
		System.out.println(imageFile.getOriginalFilename());
		System.out.println(imageFile.getSize());

		Map<String, String> imageMap = workerService.uploadPic(worker, imageFile); // passing data stored in worker and
																					// the imageFile.

		return ResponseEntity.ok(imageMap);
	}

	// API for sample report upload - 28 Mar, 2026
	@PostMapping("/uploadReport/{id}")
	public ResponseEntity<?> uploadReport(@PathVariable int id, @RequestPart("file") MultipartFile file) {

		long maxSize = 2 * 1024 * 1024; // 2MB

		// FILE SIZE VALIDATION
		if (file.getSize() > maxSize) {
			return ResponseEntity.badRequest().body("File size must be less than 2MB");
		}

		// Only PDF file allowed Logic
		if (!"application/pdf".equals(file.getContentType())) {
			return ResponseEntity.badRequest().body("Only PDF files are allowed");
		}

		Map<String, String> res = workerService.uploadReport(id, file);

		return ResponseEntity.ok(res);
	}

	// API for editProfile - 09 Mar, 2026
	@PutMapping("/editProfile/{email}")
	public ResponseEntity<Worker> editProfile(@RequestBody Worker worker, @PathVariable String email) {
		Worker workerObj = workerService.editProfile(worker, email);
		if (workerObj != null)
			return new ResponseEntity<Worker>(workerObj, HttpStatus.OK);
		else
			return new ResponseEntity<Worker>(HttpStatus.NOT_FOUND);
	}

	// API for WorkerLogin.jsx
	@PostMapping("/login")
	public String workerLogin(@RequestBody Worker worker) {

		/*
		 * System.out.println("Email is "+ user.getEmail());
		 * System.out.println("Password is "+ user.getPassword());
		 */

		String message = workerService.workerLogin(worker); // returning
		return message;

		// return "Admin Login Successfull";
	}

	// API for adding test details by the sample collector - 02 April, 2026
	@PostMapping("/sampleData")
	public String updateSample(@RequestBody SampleCollection samplecollection) {
		return workerService.updateSample(samplecollection);
	}

	// make changes below
	@GetMapping("/workerProfile/{email}") // value of email received from FrontEnd in React - 8 Mar, 2026
	public ResponseEntity<WorkerDTO> workerProfile(@PathVariable String email) {

		Worker worker = workerService.getProfile(email); // getProfile is defined in WorkerService.

		WorkerDTO dto = null;

		if (worker != null) {

			// creating object of WorkerDTO
			String name = worker.getName();
			String workerEmail = worker.getEmail();
			String type = worker.getType();
			String phone = worker.getPhone();
			String qualification = worker.getQualification();
			String experience = worker.getExperience();
			dto = new WorkerDTO(name, workerEmail, type, phone, qualification, experience);

			dto.setProfilePic(worker.getProfilePic());

//			below code is same as above
//			dto = new AdminDTO(admin.getName(), admin.getPhone());

			return ResponseEntity.ok(dto);

		}

		else {
			return new ResponseEntity<WorkerDTO>(dto, HttpStatus.NOT_FOUND);
		}

	}

	// ----Start coding related to testBookings--------------------
	// all pending booking request
	@GetMapping("/pendingRequests")
	public List<BookingPaymentDTO> getPendingRequests() {
		return workerService.getPendingRequests();
	}

	// verify payment
	@PostMapping("/verifyPayment/{transactionNo}")
	public ResponseEntity<String> verifyPayment(@PathVariable String transactionNo) {

		String response = workerService.verifyPayment(transactionNo);

		return ResponseEntity.ok(response);
	}

	// Get all confirmed Requests
	@GetMapping("/confirmedRequests")
	public List<BookingPaymentDTO> getConfirmedRequests() {
		return workerService.getConfirmedRequests();
	}

	@PostMapping("/updateVisitStatus/{txn}")
	public String updateVisit(@PathVariable String txn) {
		return workerService.updateVisitStatus(txn);
	}

	// view all Pending request for the users who have visited the pathology for
	// test
	@GetMapping("/pendingReports")
	public List<BookingPaymentDTO> getPendingReports() {
		return workerService.getPendingReports();
	}

	// correct the code for controller and service for upload test report for the
	// patient who has visited the pathology for test - 13 April, 2026
	@PostMapping("/uploadTestReport")
	public ResponseEntity<?> uploadTestReport(@RequestPart("file") MultipartFile file,
			@RequestParam("transactionNo") String txn) {

		long maxSize = 2 * 1024 * 1024; // 2MB

		// FILE SIZE VALIDATION
		if (file.getSize() > maxSize) {
			return ResponseEntity.badRequest().body("File size must be less than 2MB");
		}

		// ONLY PDF ALLOWED
		if (!"application/pdf".equals(file.getContentType())) {
			return ResponseEntity.badRequest().body("Only PDF files are allowed");
		}

		Map<String, String> res = workerService.uploadTestReport(file, txn);

		return ResponseEntity.ok(res);
	}

	// ----End coding related to testBookings--------------------
}