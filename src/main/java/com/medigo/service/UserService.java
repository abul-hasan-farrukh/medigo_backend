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

import com.medigo.dto.BookingStatusDTO;
import com.medigo.model.Feedback;
import com.medigo.model.Payment;
import com.medigo.model.SampleCollection;
import com.medigo.model.Test;
import com.medigo.model.TestBooking;
import com.medigo.model.User;
import com.medigo.repository.FeedbackRepository;
import com.medigo.repository.PaymentRepository;
import com.medigo.repository.SampleCollectionRepository;
import com.medigo.repository.TestBookingRepository;
import com.medigo.repository.TestRepository;
import com.medigo.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private FeedbackRepository feedbackRepository;

	@Autowired
	private SampleCollectionRepository sampleCollectionRepository;

	@Autowired
	private TestRepository testRepository;

	@Autowired
	private TestBookingRepository testBookingRepository;

	@Autowired
	private PaymentRepository paymentRepository;

//Saving Sample Collection details to Database
	public String sampleCollectionRequest(SampleCollection samplecollection) {
		// Auto set request date
		samplecollection.setRequest_date(LocalDate.now());

		// checking status is not null
		/*
		 * if (samplecollection.getStatus() == null ||
		 * samplecollection.getStatus().isEmpty()) {
		 * samplecollection.setStatus("pending"); }
		 */
		sampleCollectionRepository.save(samplecollection);
		return "Sample Collection Request Sent Successfully";
	}

//method to show all Tests to user - 06 April, 2026
	public List<Test> getAllTests() {
		return testRepository.findAll();
	}

//method to show all sample request send by user on his dash-board (RequestStatus.jsx) - 26 Mar, 2026 
	public List<SampleCollection> getRequestsByEmail(String email) {
		return sampleCollectionRepository.findByEmail(email);
	}

//Method for fetching all Users from Database, so admin can view them  - 26 Feb,2026
	public List<User> allUsers() { // returning List because ArrayList class implements List
		return userRepository.findAll(); // findAll is same as Select * from Contact of SQL.
	}

	// Method for fetching all Feedbacks from Database, so admin can view them - 26
	// Feb, 2026
	public List<Feedback> allFeedbacks() { // returning List because ArrayList class implements List
		return feedbackRepository.findAll(); // findAll is same as Select * from Contact of SQL.
	}

	// Check duplicate (same email + testId + date) or add new test booking - 08
	// April, 2026
	public String bookTest(TestBooking booking) {

		LocalDate today = LocalDate.now();

		boolean exists = testBookingRepository.existsByEmailAndTestIdAndDate(booking.getEmail(), booking.getTestId(),
				today);

		if (exists) {
			return "You have already booked this test today!";
		}

		// Save new Test booking
		booking.setBookingStatus("PENDING");
		booking.setDate(today);

		testBookingRepository.save(booking);

		return "Test booked successfully!";
	}

	// function to save payment details in DB - 08 April, 2026
	// booking date is coming from front end not system date
	public String payNow(Payment payment) {

		payment.setTokenNumber(null);
		payment.setVisitStatus(false);
		payment.setReportUploaded(false);
		payment.setTestReportFile(null);

		// saving payment details first - 14 April, 2026
		paymentRepository.save(payment);

		// link all PENDING bookings to this transaction - 14 April, 2026
		List<TestBooking> bookings = testBookingRepository.findByEmailAndBookingStatus(payment.getUserEmail(),
				"PENDING");

		for (TestBooking booking : bookings) {

			// Only assign txn_no to paid bookings
			if (booking.getTransactionNo() == null) {

				booking.setTransactionNo(payment.getTransactionNo());
			}
		}

		testBookingRepository.saveAll(bookings);

		return "Payment Successful!";
	}

	// Below code is related to user details showing on dashboard.
	// It will return object of User Class
	public User getProfile(String email) {
		Optional<User> opt = userRepository.findById(email); // Optional is a container, which is used when we don't
																// know whether data is available or not.

		User user = null;
		if (opt.isPresent()) { // if user email is present we will get it, otherwise null.
			user = opt.get();
		}
		return user;
	}

	public UserService(UserRepository userRepository, FeedbackRepository feedbackRepository) {
		super();
		this.feedbackRepository = feedbackRepository;
		this.userRepository = userRepository;
	}

//  //Parameterized constructor of userService class below
//  @Autowired //only for understanding, not needed because when a class have just one parameterized constructor 
//  then Spring container autowire it by default.
//  public UserService(UserRepository userRepository) {
//	super();
//	this.userRepository = userRepository;
//  }

	public String registration(User user) { // method name must be similar to the method in Controller.

		if (userRepository.existsById(user.getEmail())) {
			return "Email already exists";
		}

		userRepository.save(user);
		return "Registration Done";
	}

//method to prevent same emailId from registering on platform - 10th April, 2026
	public String checkEmail(String email) // taking email from frontend
	{
		if (userRepository.existsById(email)) {
			return "exists";
		}
		return "not_exists";
	}

	public String feedback(Feedback feedback) {
		feedbackRepository.save(feedback);
		return "Feedback Submission Successfull";
	}

	// Below code is related to User login
	public String login(User user) {

		String email = user.getEmail();
		String password = user.getPassword();

		// Case 5: Null values
		if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
			return "Email and Password required";
		}

		Optional<User> opt = userRepository.findById(email);

		// Email exists
		if (opt.isPresent()) {
			User dbUser = opt.get();

			// Case 2: Correct email, wrong password
			if (!dbUser.getPassword().equals(password)) {
				return "Invalid Password";
			}

			// Case 4: Correct email & password
			return "success";
		}

		// Email not found
		else {
			// Case 1 & 3 handled together
			return "Invalid Email";
		}
	}

//method to editProfile of User - 09 Mar, 2026
	public User editProfile(User modifiedUserObject, String email) { // modifiedAdminObject contains the new edited
																		// data.
		Optional<User> opt = userRepository.findById(email);
		User ur = null;

		if (opt.isPresent()) {
			ur = opt.get(); // fetch the User Object based on email id which is a primary key. "ur" contains
							// old User data.

			// extracting old data
			String modifiedName = modifiedUserObject.getName();
			String modifiedPhone = modifiedUserObject.getPhone();
			String modifiedCity = modifiedUserObject.getCity();
//  		   String modifiedPassword = modifiedAdminObject.getPassword();

			// overwrite old data with modfified values.
			ur.setName(modifiedName);
			ur.setPhone(modifiedPhone);
			ur.setCity(modifiedCity);
//  		   ur.setPassword(modifiedPassword);
			userRepository.save(ur); // saving modified data permanently to database.
		}
		return ur; // returning updated object
	}

	// method for uploadPic - 13 Mar, 2026
	public Map<String, String> uploadPic(User user, MultipartFile imageFile) {
		String fileName = imageFile.getOriginalFilename();
		long timeStamp = System.currentTimeMillis() % 100000000; // dividing the current time to get a number to be
																	// attached in the image to make it unique.
		System.out.println(timeStamp);

		String uniqueFileName = timeStamp + "_" + fileName; // this helps to identify the image uniquely.

		// Go to root directory
		String projectRoot = System.getProperty("user.dir");
		System.out.println("Project Root: " + projectRoot);

		// Reach to upload directory
		String uploadDir = projectRoot + "/uploads/userimages";

		Map<String, String> imageMap = new HashMap<>(); // using Map and HashMap to send or return multiple values to
														// the frontend

		try {

			// Setting the target folder for image upload with image name.
			File targetFile = new File(uploadDir, uniqueFileName);
			imageFile.transferTo(targetFile); // transferTo is built-in method of MultipartFile Interface

			// Generating image URL to send to FrontEnd developer
			String IMAGEURL = "http://localhost:9090/uploads/userimages/" + uniqueFileName;

			String email = user.getEmail(); // coming from React
			Optional<User> opt = userRepository.findById(email);

			if (opt.isPresent()) {
				User ur = opt.get();

				String desc = user.getDescription(); // coming from React

				// update User profile image with description
				ur.setProfilePic(uniqueFileName);
				ur.setDescription(desc);
				userRepository.save(ur); // saving to database

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

	// Get all Test bookings of user on BookedTest.jsx - 14 April, 2026
	public List<TestBooking> getPendingBookingsByEmail(String email) {

		// Fetch only PENDING bookings
		List<TestBooking> bookings = testBookingRepository.findByEmailAndBookingStatus(email, "PENDING");

		// Attach Test details
		for (TestBooking booking : bookings) {

			if (booking.getTransactionNo() == null) // to prevent useless data sending after hiding - 15 April, 2026s
			{
				System.out.println(booking.getTestId());
				System.out.println("tr " + booking.getTransactionNo());
				Optional<Test> opt = testRepository.findById(booking.getTestId());

				if (opt.isPresent()) {

					booking.setTest(opt.get());
				}
			}
		}
		System.out.println("in pending bookingd" + bookings.size());

		return bookings;
	}

	// Delete Test booking
	public boolean deleteBooking(Long id) {

		if (testBookingRepository.existsById(id)) {
			testBookingRepository.deleteById(id);
			return true;
		}

		return false;
	}

	// function to show all the tests booked by user and their status also - 14
	// April, 2026
	public List<BookingStatusDTO> getBookingStatus(String email) {

		List<Payment> payments = paymentRepository.findByUserEmail(email);

		List<BookingStatusDTO> list = new ArrayList<>();

		for (Payment payment : payments) {

			// FETCH ALL BOOKINGS (BOTH CONFIRMED and PENDING)
			List<TestBooking> bookings = testBookingRepository.findByTransactionNo(payment.getTransactionNo());

			for (TestBooking booking : bookings) {

				// ONLY MATCH SAME TRANSACTION
				if (booking.getTransactionNo() == null)
					continue;
				if (!booking.getTransactionNo().equals(payment.getTransactionNo()))
					continue;

				BookingStatusDTO dto = new BookingStatusDTO();

				dto.setEmail(email);
				dto.setBookingStatus(booking.getBookingStatus()); // this includes payment PENDING test also
				dto.setDate(payment.getDate().toString()); // using payment table date

				dto.setTransactionNo(payment.getTransactionNo());
				dto.setAmount(payment.getAmount());
				dto.setTokenNumber(payment.getTokenNumber());

				// added on 16 April, 2026 for test report download functionality
				dto.setReportUploaded(payment.isReportUploaded());
				dto.setTestReportFile(payment.getTestReportFile());

				Optional<Test> opt = testRepository.findById(booking.getTestId());
				opt.ifPresent(dto::setTest);

				list.add(dto);
			}
		}

		return list;
	}

}