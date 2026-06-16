package com.medigo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medigo.model.Camp;
import com.medigo.model.Contact;
import com.medigo.model.Feedback;
import com.medigo.model.Test;
import com.medigo.repository.CommonRepository;
import com.medigo.repository.ContactRepository;
import com.medigo.repository.TestRepository;

@Service
public class CommonService {

	// Service is a master chef.

	@Autowired
	private ContactRepository contactRepository;

	@Autowired
	// Wrote on 17 Mar, 2026
	private CommonRepository commonRepository;

	@Autowired
	private TestRepository testRepository;

	public CommonService(ContactRepository contactRepository) {
		super();
		this.contactRepository = contactRepository;
	}

	public String addContact(Contact contact) { // method name must be similar to the method in Controller.

		// String message = commonRepository.addContact(contact);
		contactRepository.save(contact); // ORM, save method returns the object it saves.

		return "Contact added successfully";
	}

	// 24 Mar, 2026
	public List<Test> getAllActiveTests() {
		return testRepository.findByStatus("Active"); // must match DB value
	}

	// Method to fetch all feedback from Repo to display on Home page - 17 Mar, 2026
	public List<Feedback> fetchFeedback() {
		return commonRepository.fetchFeedback();
	}

	// Method to fetch all camps from Repository to display on Home page - 23 Mar,
	// 2026
	public List<Camp> showCamps() {
		return commonRepository.showCamps();
	}

}