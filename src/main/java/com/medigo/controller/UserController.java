package com.medigo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.medigo.dto.BookingStatusDTO;
import com.medigo.dto.UserDTO;
import com.medigo.model.Feedback;
import com.medigo.model.Payment;
import com.medigo.model.SampleCollection;
import com.medigo.model.Test;
import com.medigo.model.TestBooking;
import com.medigo.model.User;
import com.medigo.service.UserService;

@RestController
@CrossOrigin("*")
@RequestMapping("/user") // http://localhost:9090/user/registration

public class UserController {

	@Autowired
	private UserService userService;

	// @Autowired - Not needed because it is a default constructor.
	public UserController(UserService userService) {
		this.userService = userService;
	}

//--------------Codes start  for TestViewing and Booking Modules-----------------

	// API for showing all Tests to user - 06 April, 2026
	@GetMapping("/viewTests")
	public List<Test> getAllTests() {
		return userService.getAllTests();
	}

	// API for sending booked test data in Database Modified - 08 April, 2026
	@PostMapping("/bookTest")
	public ResponseEntity<String> bookTest(@RequestBody TestBooking booking) {

		String response = userService.bookTest(booking);

		if (response.contains("already")) {
			return ResponseEntity.badRequest().body(response);
		}

		return ResponseEntity.ok(response);
	}

	// API to view all booked test

	// Get test bookings by email
	@GetMapping("/bookings/{email}")
	public List<TestBooking> getBookings(@PathVariable String email) {
		System.out.println("email is " + email);
		return userService.getPendingBookingsByEmail(email);
	}

	// Delete booking API if user is deleting the test
	@DeleteMapping("/deleteBooking/{id}")
	public ResponseEntity<String> deleteBooking(@PathVariable Long id) {

		boolean deleted = userService.deleteBooking(id);

		if (deleted) {
			return ResponseEntity.ok("Deleted successfully");
		} else {
			return ResponseEntity.badRequest().body("Booking not found");
		}
	}

	@PostMapping("/payNow")
	public String payNow(@RequestBody Payment payment) {

		return userService.payNow(payment);
	}

	// View Booking Tests status

	@GetMapping("/bookingStatus/{email}")
	public List<BookingStatusDTO> getBookingStatus(@PathVariable String email) {
		return userService.getBookingStatus(email);
	}

//-------------Code ends for Test Booking and viewing modules----------------

	// API for RequestStatus.jsx to show all the sample request on user dash-board
	@GetMapping("/myRequests/{email}")
	public List<SampleCollection> getUserRequests(@PathVariable String email) {
		return userService.getRequestsByEmail(email);
	}

	// API for sending sample collection request by the user. - 24 Mar, 2026
	@PostMapping("/sampleCollectionRequest")
	public ResponseEntity<String> sampleCollectionRequest(@RequestBody SampleCollection samplecollection) {

		String message = userService.sampleCollectionRequest(samplecollection);

		return ResponseEntity.ok(message);
	}

	// API for editProfile - 09 Mar, 2026
	@PutMapping("/editProfile/{email}")
	public ResponseEntity<User> editProfile(@RequestBody User user, @PathVariable String email) {
		User userObj = userService.editProfile(user, email);
		if (userObj != null)
			return new ResponseEntity<User>(userObj, HttpStatus.OK);
		else
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
	}

	// API for image Upload - 13 Mar, 2026
	@PostMapping("/uploadPic")
	public ResponseEntity<Map<String, String>> uploadPic(@RequestPart("profileImageDetail") User user,
			@RequestPart("imageFile") MultipartFile imageFile) // assuming profileImageDetail and imageFile is coming
																// from frontend
	{
		System.out.println(user.getDescription());
		System.out.println(imageFile.getOriginalFilename());
		System.out.println(imageFile.getSize());

		Map<String, String> imageMap = userService.uploadPic(user, imageFile); // passing data stored in admin and the
																				// imageFile.

		return ResponseEntity.ok(imageMap);
	}

	// API for User Registration
	@PostMapping("/registration")
	public String regsitration(@RequestBody User user) {

		System.out.println("Email is " + user.getEmail());
		System.out.println("Name is " + user.getName());
		System.out.println("Phone is " + user.getPhone());
		System.out.println("Password is " + user.getPassword());
		System.out.println("City is " + user.getCity());

		String message = userService.registration(user);
		return message;
		// return "Registration Successfull";
	}

	// Feedback Submission
	@PostMapping("/feedback")
	public String feedback(@RequestBody Feedback feedback) {

		System.out.println("Email is " + feedback.getEmail());
		System.out.println("Rating is " + feedback.getRating());
		System.out.println("Review is " + feedback.getReview());

		String message = userService.feedback(feedback);
		return message;
		// return "Feedback Submitted Successfully";
	}

	@PostMapping("/login")
	public String login(@RequestBody User user) {

		System.out.println("Email is " + user.getEmail());
		System.out.println("Password is " + user.getPassword());

		String message = userService.login(user); // returning
		return message;

//		return "User Login Successfull";
	}

	@GetMapping("/userProfile/{email}") // value of email received from FrontEnd in React - 25 Feb, 2026
	public ResponseEntity<UserDTO> userProfile(@PathVariable String email) {

		User user = userService.getProfile(email); // getProfile is defined in UserService.

		UserDTO dto = null;

		if (user != null) {

			// creating object of AdminDTO
			String name = user.getName();
			String phone = user.getPhone();
			String city = user.getCity();
			dto = new UserDTO(name, phone, city);

			dto.setProfilePic(user.getProfilePic());

//			below code is same as above
//			dto = new AdminDTO(admin.getName(), admin.getPhone());

			return ResponseEntity.ok(dto);

		}

		else {
			return new ResponseEntity<UserDTO>(dto, HttpStatus.NOT_FOUND);
		}

	}

}