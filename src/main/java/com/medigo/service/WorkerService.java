package com.medigo.service;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.medigo.dto.BookingPaymentDTO;
import com.medigo.model.Payment;
import com.medigo.model.SampleCollection;
import com.medigo.model.Test;
import com.medigo.model.TestBooking;
import com.medigo.model.User;
import com.medigo.model.Worker;
import com.medigo.repository.PaymentRepository;
import com.medigo.repository.SampleCollectionRepository;
import com.medigo.repository.TestBookingRepository;
import com.medigo.repository.TestRepository;
import com.medigo.repository.UserRepository;
import com.medigo.repository.WorkerRepository;

@Service
public class WorkerService {

	@Autowired
	private WorkerRepository workerRepository;

	@Autowired
	private SampleCollectionRepository sampleCollectionRepository;

	@Autowired
	PaymentRepository paymentRepository;
	@Autowired
	TestBookingRepository testBookingRepository;
	@Autowired
	UserRepository userRepository;

	@Autowired
	TestRepository testRepository;

	// method to fetch all sample requests from sample collection repository - 25
	// Mar, 2026
	public List<SampleCollection> getAllRequests() {
		return sampleCollectionRepository.findAll();
	}
	

	//Method for fetching all Workers from Database, so admin can view them  - 26 Feb,2026
		public List<Worker> allWorkers() { // returning List because ArrayList class implements List
			return workerRepository.findAll(); // findAll is same as Select * from Contact of SQL.
		}

	// Get all sample collectors to assign request by executive worker (via
	// drop-down) - 25 Mar, 2026
	public List<Worker> getSampleCollectors() {
		return workerRepository.findByType("Sample Collector");
	}

	// Assign worker - 25 Mar, 2026
	public SampleCollection assignRequest(int id, Map<String, String> data) {

		Optional<SampleCollection> opt = sampleCollectionRepository.findById(id);

		if (opt.isPresent()) {
			SampleCollection req = opt.get();

			req.setWorker_name(data.get("name"));
			req.setWorker_email(data.get("email"));
			req.setWorker_phone(data.get("phone"));

			req.setStatus("assigned");

			return sampleCollectionRepository.save(req);
		}

		return null;
	}

	// Mark Request as completed - 25 Mar, 2026
	public SampleCollection markCompleted(int id) {

		Optional<SampleCollection> opt = sampleCollectionRepository.findById(id);

		if (opt.isPresent()) {
			SampleCollection req = opt.get();

			// prevent re-completing the same request
			if (!"completed".equalsIgnoreCase(req.getStatus())) {
				req.setStatus("completed");
				return sampleCollectionRepository.save(req);
			}
		}

		return null;
	}

	// Delete request (reject) - 25 Mar, 2026
	public String deleteRequest(int id) {

		if (sampleCollectionRepository.existsById(id)) {
			sampleCollectionRepository.deleteById(id);
			return "Request Deleted";
		}

		return "Request Not Found";
	}

	// Get assigned requests for a specific sample collector - 26 Mar, 2026
	public List<SampleCollection> getAssignedRequests(String email) {
		return sampleCollectionRepository.findByWorkerEmail(email);
	}

	// Code for WorkerLogin written below
	// Select * from worker where email = worker.getEmail() and
	// password=worker.getPassword() - this will be used in JDBC template but we are
	// using Hibernate ORM

	// update worker login function on 25 Mar, 2026 to check worker login by type.
	public String workerLogin(Worker worker) { // method name must be similar to the method in Controller because
												// service is linked with controller.
		String email = worker.getEmail();
		String password = worker.getPassword();
		String type = worker.getType(); // to check worker type

		System.out.println(email + password + type);

		Optional<Worker> opt = workerRepository.findById(email);
		String message = "";

		if (opt.isPresent()) {
			Worker wk = opt.get(); // fetches the value from the container(Optional).

			if (!wk.getPassword().equals(password)) { // checking password stored in the back-end with the password
														// entered by the worker in front-end.
				message = "Password didn't match";
			} else if (!wk.getType().equalsIgnoreCase(type)) {
				message = "Invalid Worker Type"; //
			} else {
				message = "success";
			}
		} else {
			message = "Invalid Email";
		}
		return message;
	}

	// Below code is related to worker details showing on dash-board. - 8 Mar, 2026
	// It will return object of Worker Class
	public Worker getProfile(String email) {
		Optional<Worker> opt = workerRepository.findById(email); // Optional is a container, which is used when we don't
																	// know whether data is available or not.

		Worker worker = null;
		if (opt.isPresent()) { // if worker email is present we will get it, otherwise null.
			worker = opt.get();
		}
		return worker;
	}

	// method to editProfile of Worker - 09 Mar, 2026
	public Worker editProfile(Worker modifiedWorkerObject, String email) { // modifiedAdminObject contains the new
																			// edited data.
		Optional<Worker> opt = workerRepository.findById(email);
		Worker wr = null;

		if (opt.isPresent()) {
			wr = opt.get(); // fetch the worker Object based on email id which is a primary key. "wr"
							// contains old worker data.

			// extracting old data
			String modifiedName = modifiedWorkerObject.getName();
			String modifiedType = modifiedWorkerObject.getType();
			String modifiedPhone = modifiedWorkerObject.getPhone();
			String modifiedQualification = modifiedWorkerObject.getQualification();
			String modifiedExperience = modifiedWorkerObject.getExperience();
//			  		   String modifiedEmail = modifiedWorkerObject.getEmail();
//			  		   String modifiedPassword = modifiedAdminObject.getPassword();

			// overwrite old data with modfified values.
			wr.setName(modifiedName);
			wr.setType(modifiedType);
			wr.setPhone(modifiedPhone);
			wr.setQualification(modifiedQualification);
			wr.setExperience(modifiedExperience);
//			  		 wr.setEmail(modifiedEmail);
//			  		 wr.setPassword(modifiedPassword);
			workerRepository.save(wr); // saving modified data permanently to database.
		}

		return wr; // returning updated object

	}

	// method for uploadPic - 17 Mar, 2026
	public Map<String, String> uploadPic(Worker worker, MultipartFile imageFile) {
		String fileName = imageFile.getOriginalFilename();
		long timeStamp = System.currentTimeMillis() % 100000000; // dividing the current time to get a number to be
																	// attached in the image to make it unique.
		System.out.println(timeStamp);

		String uniqueFileName = timeStamp + "_" + fileName; // this helps to identify the image uniquely.

		// Go to root directory
		String projectRoot = System.getProperty("user.dir");
		System.out.println("Project Root: " + projectRoot);

		// Reach to upload directory
		String uploadDir = projectRoot + "/uploads/workerimages";

		Map<String, String> imageMap = new HashMap<>(); // using Map and HashMap to send or return multiple values to
														// the frontend

		try {

			// Setting the target folder for image upload with image name.
			File targetFile = new File(uploadDir, uniqueFileName);
			imageFile.transferTo(targetFile); // transferTo is built-in method of MultipartFile Interface

            String baseUrl = "${app.base-url}";

			// Generating image URL to send to FrontEnd developer
			String IMAGEURL = baseUrl + "/uploads/workerimages/" + uniqueFileName;

			String email = worker.getEmail(); // coming from React
			Optional<Worker> opt = workerRepository.findById(email);

			if (opt.isPresent()) {
				Worker wr = opt.get();

				String desc = worker.getDescription(); // coming from React

				// update User profile image with description
				wr.setProfilePic(uniqueFileName);
				wr.setDescription(desc);
				workerRepository.save(wr); // saving to database

				imageMap.put("imageURL", IMAGEURL);
				imageMap.put("email", email);

			}
		}

		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Image Upload Failed");
		}
		return imageMap;
	}

	// method for patient report file upload - 28 Mar, 2026
	public Map<String, String> uploadReport(int id, MultipartFile file) {

		String fileName = file.getOriginalFilename();
		long timeStamp = System.currentTimeMillis() % 100000000;

		String uniqueFileName = timeStamp + "_" + fileName;

		String projectRoot = System.getProperty("user.dir");
		String uploadDir = projectRoot + "/uploads/samplereports";

		Map<String, String> map = new HashMap<>();

		try {
			File targetFile = new File(uploadDir, uniqueFileName);
			file.transferTo(targetFile);

			Optional<SampleCollection> opt = sampleCollectionRepository.findById(id);

			if (opt.isPresent()) {
				SampleCollection req = opt.get();

				req.setReportFile(uniqueFileName); // save filename
				req.setReportStatus("uploaded"); // updating status of report in database.
				sampleCollectionRepository.save(req);

                String baseUrl = "${app.base-url}";

				String FILEURL = baseUrl + "/uploads/samplereports/" + uniqueFileName;

				map.put("fileName", uniqueFileName);
				map.put("fileURL", FILEURL);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return map;
	}

	// update sample request with testName details to be filled by sample collector
	// - 02 April, 2026
	public String updateSample(SampleCollection samplecollection) {

		Optional<SampleCollection> opt = sampleCollectionRepository.findById(samplecollection.getId());

		if (opt.isPresent()) {

			SampleCollection oldsample = opt.get();

			oldsample.setTestName(samplecollection.getTestName());

			sampleCollectionRepository.save(oldsample);

			return "Test details saved successfully";
		}

		return "Request not found";
	}

	// function to view allPending booking test request - 13 April, 2026
	public List<BookingPaymentDTO> getPendingRequests() {

		List<Payment> payments = paymentRepository.findAll();
		List<BookingPaymentDTO> list = new ArrayList<>();

		for (Payment payment : payments) {

			// Only pending payments
			if (payment.getTokenNumber() != null)
				continue;

			String email = payment.getUserEmail();

			// Fetch user details
			User user = userRepository.findByEmail(email);

			List<TestBooking> bookings = testBookingRepository.findByEmailAndBookingStatus(email, "PENDING");

			for (TestBooking booking : bookings) {

				/// 🔥 ONLY MATCH SAME TRANSACTION
				if (!payment.getTransactionNo().equals(booking.getTransactionNo()))
					continue;

				Optional<Test> opt = testRepository.findById(booking.getTestId());

				if (opt.isPresent()) {

					BookingPaymentDTO dto = new BookingPaymentDTO();

					dto.setEmail(email);
					dto.setName(user.getName());
					dto.setPhone(user.getPhone());

					dto.setBookingStatus(booking.getBookingStatus());
					dto.setDate(booking.getDate().toString());

					dto.setTransactionNo(payment.getTransactionNo());
					dto.setAmount(payment.getAmount());
					dto.setPaymentDate(payment.getDate().toString());

					dto.setTest(opt.get());

					list.add(dto);
				}
			}
		}

		return list;
	}

	// ================= PAYMENT VERIFICATION =================
	public String verifyPayment(String transactionNo) {

		Optional<Payment> optional = paymentRepository.findByTransactionNo(transactionNo);

		if (optional.isEmpty()) {
			return "Transaction not found";
		}

		Payment payment = optional.get();

		if (payment.getTokenNumber() != null) {
			return "Already Verified! Token: " + payment.getTokenNumber();
		}

		LocalDate today = LocalDate.now();
		long count = paymentRepository.countTodayTokens(today);

		String token = "MediGo" + (count + 1);

		payment.setTokenNumber(token);
		paymentRepository.save(payment);

		// only fetch bookings OF a particular transaction - 15 April, 2026
		List<TestBooking> bookings = testBookingRepository.findByTransactionNoAndBookingStatus(transactionNo,
				"PENDING");

		for (TestBooking booking : bookings) {

			// MAIN FIX
			booking.setBookingStatus("CONFIRMED");
//				        booking.setTransactionNo(transactionNo);
		}

		testBookingRepository.saveAll(bookings);

		return "Payment Verified! Token: " + token;
	}

	// get all confirmed bookings date wise
	// ================= CONFIRMED BOOKINGS =================
	public List<BookingPaymentDTO> getConfirmedRequests() {

		List<Payment> payments = paymentRepository.findByTokenNumberIsNotNull();

		List<BookingPaymentDTO> list = new ArrayList<>();

		for (Payment payment : payments) {

			String email = payment.getUserEmail();
			User user = userRepository.findByEmail(email);

			// 🔥 FIX: use transactionNo ONLY
			List<TestBooking> bookings = testBookingRepository
					.findByTransactionNoAndBookingStatus(payment.getTransactionNo(), "CONFIRMED");

			for (TestBooking booking : bookings) {

				Optional<Test> opt = testRepository.findById(booking.getTestId());

				if (opt.isPresent()) {

					BookingPaymentDTO dto = new BookingPaymentDTO();

					dto.setEmail(email);
					dto.setName(user.getName());
					dto.setPhone(user.getPhone());

					dto.setTransactionNo(payment.getTransactionNo());
					dto.setAmount(payment.getAmount());

					dto.setPaymentDate(payment.getDate().toString());
					dto.setDate(booking.getDate().toString());

					dto.setTokenNumber(payment.getTokenNumber());
					dto.setVisitStatus(payment.isVisitStatus());
					dto.setBookingStatus(booking.getBookingStatus());

					dto.setTest(opt.get());

					list.add(dto);
				}
			}
		}

		return list;
	}

	// update Visit status
	public String updateVisitStatus(String txn) {

		Optional<Payment> optional = paymentRepository.findByTransactionNo(txn);

		if (optional.isEmpty())
			return "Payment not found";

		Payment payment = optional.get();

		payment.setVisitStatus(true);

		paymentRepository.save(payment);

		return "Visit Updated";
	}

	// function to view pending reports to upload test reports - 13th April, 2026
	public List<BookingPaymentDTO> getPendingReports() {

		List<Payment> payments = paymentRepository.findAll();
		List<BookingPaymentDTO> list = new ArrayList<>();

		for (Payment payment : payments) {

			// Only visited but report not uploaded
			if (!payment.isVisitStatus() || payment.isReportUploaded())
				continue;

			String email = payment.getUserEmail();

			User user = userRepository.findByEmail(email);

			List<TestBooking> bookings = testBookingRepository.findByEmailAndBookingStatus(email, "CONFIRMED");

			for (TestBooking booking : bookings) {

				if (booking.getTransactionNo().equals(payment.getTransactionNo())
						&& payment.isReportUploaded() == false) {
					Optional<Test> opt = testRepository.findById(booking.getTestId());

					if (opt.isPresent()) {

						BookingPaymentDTO dto = new BookingPaymentDTO();

						dto.setName(user.getName());
						dto.setPhone(user.getPhone());
						dto.setTransactionNo(payment.getTransactionNo());
						dto.setPaymentDate(payment.getDate().toString());
						dto.setTest(opt.get());

						list.add(dto);
					}
				}
			}
		}

		return list;
	}

	// code to upload testReports - 15 April, 2026
	public Map<String, String> uploadTestReport(MultipartFile file, String txn) {

		Map<String, String> map = new HashMap<>();

		try {

			Optional<Payment> optional = paymentRepository.findByTransactionNo(txn);

			if (optional.isEmpty()) {
				map.put("message", "Payment not found");
				return map;
			}

			Payment payment = optional.get();

			// SAME LOGIC AS uploadReport()
			String fileName = file.getOriginalFilename();
			long timeStamp = System.currentTimeMillis() % 100000000;
			String uniqueFileName = timeStamp + "_" + fileName;

			String projectRoot = System.getProperty("user.dir");
			String uploadDir = projectRoot + "/uploads/testreports";

			File targetFile = new File(uploadDir, uniqueFileName);
			file.transferTo(targetFile);

			// Update DB
			payment.setTestReportFile(uniqueFileName);
			payment.setReportUploaded(true);

			paymentRepository.save(payment);

            String baseUrl = "${app.base-url}";

			// URL
			String FILEURL = baseUrl + "/uploads/testreports/" + uniqueFileName;

			map.put("fileName", uniqueFileName);
			map.put("fileURL", FILEURL);
			map.put("message", "Report Uploaded Successfully");

		} catch (Exception e) {
			e.printStackTrace();
			map.put("message", "Upload Failed");
		}

		return map;
	}

}