package com.medigo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
//import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.medigo.dto.AdminDTO;
import com.medigo.dto.PasswordDTO;
import com.medigo.model.Admin;
import com.medigo.model.Camp;
import com.medigo.model.Contact;
import com.medigo.model.Feedback;
import com.medigo.model.Test;
import com.medigo.model.User;
import com.medigo.model.Worker;
import com.medigo.service.AdminService;
import com.medigo.service.UserService;
import com.medigo.service.WorkerService;

//Controller gives the response.

@RestController
@CrossOrigin("${ALLOWED_ORIGIN:*}")
@RequestMapping("/admin") // http://localhost:9090/admin/login
public class AdminController {

	private AdminService adminService;

	private UserService userService;
	
	private WorkerService workerService;

	// @Autowired
	public AdminController(AdminService adminService, UserService userService, WorkerService workerService) {
		super();
		this.adminService = adminService;
		this.userService = userService;
		this.workerService = workerService;
	}

	// API for image Upload - 12 Mar, 2026
	@PostMapping("/uploadPic")
	// ? -> wild card character which we use when we return multiple data type based
	// on conditions - 26 Mar, 2026
	public ResponseEntity<?> uploadPic(@RequestPart("profileImageDetail") Admin admin,
			@RequestPart("imageFile") MultipartFile imageFile) // assuming profileImageDetail and imageFile is coming
																// from frontend
	{
		long maxSize = 2 * 1024 * 1024; // max file size 2MB

		if (imageFile.getSize() > maxSize) {
			return ResponseEntity.badRequest().body("File size exceed 2MB limit");
		}

		System.out.println(admin.getDescription());
		System.out.println(imageFile.getOriginalFilename());
		System.out.println(imageFile.getSize());

		Map<String, String> imageMap = adminService.uploadPic(admin, imageFile); // passing data stored in admin and the
																					// imageFile.

		return ResponseEntity.ok(imageMap);
	}

	// API for adding worker by the admin. - 12 Mar, 2026
	@PostMapping("/addWorker")
	public String addWorker(@RequestBody Worker worker) {
		String message = adminService.addWorker(worker); // calling addWorker method created in AdminService.
		return message;
	}

	// API for adding test by the admin. - 19 Mar, 2026
	@PostMapping("/addTest")
	public String addTest(@RequestBody Test test) {
		String message = adminService.addTest(test); // calling addTest method created in AdminService.
		return message;
	}

	// API for creating a Camp by Admin - 23 Mar, 2026
	@PostMapping("/addCamp")
	public String addCamp(@RequestBody Camp camp) {
		String message = adminService.addCamp(camp); // calling addCamp method created in AdminService.
		return message;
	}

	// API for Password Update
	@PatchMapping("/updatePassword/{email}")
	public String updatePassword(@RequestBody PasswordDTO pd, @PathVariable String email) {
		String message = adminService.updatePassword(pd, email);

		return message;
	}

	// API for editProfile - 09 Mar, 2026
	@PutMapping("/editProfile/{email}")
	public ResponseEntity<Admin> editProfile(@RequestBody Admin admin, @PathVariable String email) {
		Admin adminObj = adminService.editProfile(admin, email);
		if (adminObj != null)
			return new ResponseEntity<Admin>(adminObj, HttpStatus.OK);
		else
			return new ResponseEntity<Admin>(HttpStatus.NOT_FOUND);
	}

	// API for deleteContact by Admin - 27 Feb, 2026
	@DeleteMapping("/deleteContact/{id}")
	public ResponseEntity<String> deleteContact(@PathVariable Integer id) {
		adminService.deleteContact(id);
//		return ResponseEntity.ok("success"); //200 Ok response.
//		return ResponseEntity.noContent().build(); //200 Ok response.
		return new ResponseEntity<String>(HttpStatus.NO_CONTENT);// 204 response.
	}

	// API for deleteFeedback by Admin - 27 Feb, 2026
	@DeleteMapping("/deleteFeedback/{id}")
	public ResponseEntity<String> deleteFeedback(@PathVariable Integer id) {
		adminService.deleteFeedback(id);
		return new ResponseEntity<String>(HttpStatus.NO_CONTENT);// 204 response.
	}

	// API for viewing allContacts
	@GetMapping("/allContacts") // this end point will be passed to the front end
	public ResponseEntity<List<Contact>> allContacts() {
		List<Contact> contactList = adminService.allContacts(); // holding the list returned in a variable contactList
		if (contactList.size() > 0) {
			return ResponseEntity.ok(contactList);
		} else {
			return ResponseEntity.notFound().build(); // will return null.
		}
	}

	// API for viewing allUsers
	@GetMapping("/allUsers") // this end point will be passed to the front end
	public ResponseEntity<List<User>> allUsers() {
		List<User> userList = userService.allUsers(); // holding the list returned in a variable contactList
		if (userList.size() > 0) {
			return ResponseEntity.ok(userList);
		} else {
			return ResponseEntity.notFound().build(); // will return null.
		}
	}
	
	// API for viewing allWorkers - 28 Feb, 2026
		@GetMapping("/allWorkers") // this end point will be passed to the front end
		public ResponseEntity<List<Worker>> allWorkers() {
			List<Worker> workerList = workerService.allWorkers(); // holding the list returned in a variable contactList
			if (workerList.size() > 0) {
				return ResponseEntity.ok(workerList);
			} else {
				return ResponseEntity.notFound().build(); // will return null.
			}
		}

	// API to show allFeedbacks
	@GetMapping("/allFeedbacks") // this end point will be passed to the front end - 25 Feb, 2026
	public ResponseEntity<List<Feedback>> allFeedbacks() {
		List<Feedback> feedbackList = userService.allFeedbacks(); // holding the list returned in a variable contactList
		if (feedbackList.size() > 0) {
			return ResponseEntity.ok(feedbackList);
		} else {
			return ResponseEntity.notFound().build(); // will return null.
		}
	}

	// API for AdminLogin.jsx
	@PostMapping("/login")
	public String login(@RequestBody Admin admin) {

		/*
		 * System.out.println("Email is "+ user.getEmail());
		 * System.out.println("Password is "+ user.getPassword());
		 */

		String message = adminService.login(admin); // returning
		return message;

//		return "Admin Login Successfull";
	}

	// API for getting details of admin.
	@GetMapping("/adminProfile/{email}") // value received from FrontEnd in React
	public ResponseEntity<AdminDTO> adminProfile(@PathVariable String email) {

		Admin admin = adminService.getProfile(email);

		AdminDTO dto = null;

		if (admin != null) {

			// creating object of AdminDTO
			String name = admin.getName();
			String phone = admin.getPhone();
			dto = new AdminDTO(name, phone);
			dto.setProfilePic(admin.getProfilePic());

//			below code is same as above
//			dto = new AdminDTO(admin.getName(), admin.getPhone());

			return ResponseEntity.ok(dto);

		}

		else {
			return new ResponseEntity<AdminDTO>(dto, HttpStatus.NOT_FOUND);
		}

	}

}