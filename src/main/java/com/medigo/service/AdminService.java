package com.medigo.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.medigo.repository.FeedbackRepository;
import com.medigo.repository.TestRepository;
import com.medigo.repository.WorkerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.medigo.dto.PasswordDTO;
import com.medigo.model.Admin;
import com.medigo.model.Camp;
import com.medigo.model.Contact;
import com.medigo.model.Test;
import com.medigo.model.Worker;
import com.medigo.repository.AdminRepository;
import com.medigo.repository.CampRepository;
import com.medigo.repository.ContactRepository;

//Service gives the data, to implement business logic.

@Service
public class AdminService {

	private final FeedbackRepository feedbackRepository;

	@Autowired // implementing field injection
	private AdminRepository adminRepository;

	@Autowired // implementing field injection
	private ContactRepository contactRepository;

	// Using WorkerRepository to save worker in DB - 12 Mar, 2026
	@Autowired
	private WorkerRepository workerRepository;

	// Using TestRepository to save Test in DB. - 19 Mar, 2026
	@Autowired
	private TestRepository testRepository;

	// Using CampRepository to save Camp in DB - 23 Mar, 2026
	@Autowired
	private CampRepository campRepository;

	AdminService(FeedbackRepository feedbackRepository) {
		this.feedbackRepository = feedbackRepository;
	}

	// Saving worker to Database
	public String addWorker(Worker worker) {
		workerRepository.save(worker);
		return "Worker Added Successfully";
	}

	// Saving Test to Database
	public String addTest(Test test) {
		testRepository.save(test);
		return "Test Added Successfully";
	}

	// Saving Camp to Database
	public String addCamp(Camp camp) {
		campRepository.save(camp);
		return "Camp Created Successfully";
	}

	// method for Password update - 10 Mar, 2026
	// taking PasswordDTO as object and email to identify the admin
	public String updatePassword(PasswordDTO pd, String email) {

		Optional<Admin> opt = adminRepository.findById(email);
		String status = "";

		if (opt.isPresent()) {

			Admin ad = opt.get();
			String adminOldPassword = ad.getPassword(); // fetching old password here

			String reactOldPassword = pd.getOldpassword();

			if (adminOldPassword.equals(reactOldPassword)) {
				String cpassword = pd.getConfirmpassword();

				ad.setPassword(cpassword); // setting new password here
				adminRepository.save(ad);// saving the updated password in Database
				status = "success";
			} else {
				status = "error";
			}

		}

		return status;
	}

	// method to editProfile of Admin - 09 Mar, 2026
	public Admin editProfile(Admin modifiedAdminObject, String email) { // modifiedAdminObject contains the new edited
																		// data.
		Optional<Admin> opt = adminRepository.findById(email);
		Admin ad = null;

		if (opt.isPresent()) {
			ad = opt.get(); // fetch the admin Object based on email id which is a primary key. "ad"
							// contains old admin data.

			// extracting old data
			String modifiedName = modifiedAdminObject.getName();
			String modifiedPhone = modifiedAdminObject.getPhone();
//    		   String modifiedPassword = modifiedAdminObject.getPassword();

			// overwrite old data with modfified values.
			ad.setName(modifiedName);
			ad.setPhone(modifiedPhone);
//    		   ad.setPassword(modifiedPassword);
			adminRepository.save(ad); // saving modified data permanently to database.
		}

		return ad; // returning updated object

	}

	// Method for deleting contact from Database - 27 Feb, 2026
	public void deleteContact(Integer id) {
		contactRepository.deleteById(id);
	}

	// Method for fetching all Contacts, so admin can view them - 26 Feb, 2026
	public List<Contact> allContacts() { // returning List because ArrayList class implements List
		return contactRepository.findAll(); // findAll is same as Select * from Contact of SQL.
	}

	// Method for deleting feedback from Database by Admin - 27 Feb, 2026
	public void deleteFeedback(Integer id) {
		feedbackRepository.deleteById(id);
	}

	// It will return object of Admin.java class
	public Admin getProfile(String email) {
		Optional<Admin> opt = adminRepository.findById(email); // Optional is a container, which is used when we don't
																// know whether data is available or not.

		Admin admin = null;
		if (opt.isPresent()) { // if admin email is present, we will get it otherwise null.
			admin = opt.get();
		}
		return admin;
	}

	// Code for AdminLogin written below
	// Select * from admin where email = admin.getEmail() and
	// password=admin.getPassword() - this will be used in JDBC template
	public String login(Admin admin) { // method name must be similar to the method in Controller because service is
										// linked with controller.
		String email = admin.getEmail();
		String password = admin.getPassword();

		System.out.println(email + password);

		Optional<Admin> opt = adminRepository.findById(email);
		String message = "";
		if (opt.isPresent()) {
			Admin ad = opt.get(); // fetches the value from the container(Optional).
			if (ad.getPassword().equals(password)) // checking password stored in the Backend with the password entered
													// by the admin in Frontend.
				message = "success";
			else
				message = "Password didn't match";
		}

		else {
			message = "Invalid Email";
		}
		return message;
	}

	// method for uploadPic - 12 Mar, 2026
	public Map<String, String> uploadPic(Admin admin, MultipartFile imageFile) {
		String fileName = imageFile.getOriginalFilename();
		long timeStamp = System.currentTimeMillis() % 100000000; // dividing the current time to get a number to be
																	// attached in the image to make it unique.
		System.out.println(timeStamp);

		String uniqueFileName = timeStamp + "_" + fileName; // this helps to identify the image uniquely.

		// Go to root directory
		String projectRoot = System.getProperty("user.dir");
		System.out.println("Project Root: " + projectRoot);

		// Reach to upload directory
		String uploadDir = projectRoot + "/uploads/profileimages";

		Map<String, String> imageMap = new HashMap<>(); // using Map and HashMap to send or return multiple values to
														// the frontend

		try {

			// Setting the target folder for image upload with image name.
			File targetFile = new File(uploadDir, uniqueFileName);
			imageFile.transferTo(targetFile); // transferTo is built-in method of MultipartFile Interface
            
            String baseUrl = "${app.base-url}";

			// Generating image URL to send to FrontEnd developer
			String IMAGEURL = baseUrl + "/uploads/profileimages/" + uniqueFileName;

			String email = admin.getEmail(); // coming from React
			Optional<Admin> opt = adminRepository.findById(email);

			if (opt.isPresent()) {
				Admin ad = opt.get();

				String desc = admin.getDescription(); // coming from React

				// update Admin profile image with description
				ad.setProfilePic(uniqueFileName);
				ad.setDescription(desc);
				adminRepository.save(ad); // saving to database

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

}