package com.medigo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.medigo.model.Camp;
import com.medigo.model.Contact;
import com.medigo.model.Feedback;
import com.medigo.model.Test;
import com.medigo.service.CommonService;

@RestController
//@RequestMapping("/common")
//@CrossOrigin({"http://localhost:5173"})
@CrossOrigin("*")
public class CommonController {

	// Controller is a waiter.
	// Request and Response are accepted by the controller or waiter.

	private CommonService commonService;

//	@Autowired
	public CommonController(CommonService commonService) {
		super();
		this.commonService = commonService;
	}

	@PostMapping("/addContact")
	public String addContact(@RequestBody Contact contact) {

		/*
		 * System.out.println("Name is "+ contact.getName());
		 * System.out.println("Email is "+ contact.getEmail());
		 * System.out.println("Phone is "+ contact.getPhone());
		 * System.out.println("Question is "+ contact.getQuestion());
		 */

		String message = commonService.addContact(contact); // returning
		return message;

	}

	// API for showing all the tests - 24 Mar, 2026
	@GetMapping("/show-tests")
	public List<Test> getAllTests() {
		return commonService.getAllActiveTests();
	}

	// Method to display all feedback on Home page - 17 Mar, 2026
	@GetMapping("/fetchFeedback")
	public ResponseEntity<List<Feedback>> fetchFeedback() {
		List<Feedback> fList = commonService.fetchFeedback();
		return ResponseEntity.ok(fList);
	}

	// Method to display all Camps on Home page - 23 Mar, 2026
	@GetMapping("/showCamps")
	public ResponseEntity<List<Camp>> showCamps() {
		List<Camp> cList = commonService.showCamps();
		return ResponseEntity.ok(cList);
	}
}