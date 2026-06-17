package com.medigo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.medigo.mapper.CampMapper;
import com.medigo.mapper.FeedbackMapper;
import com.medigo.model.Camp;
import com.medigo.model.Feedback;

//import com.medigo.model.Contact;

@Repository
public class CommonRepository {
	// Repository is inventory or database
	/*
	 * public String addContact(Contact contact) { return
	 * "Contact Added Successfully"; }
	 */

	@Autowired // now we can use all methods of JDBC template in CommonRepository - 17 Mar,
				// 2026
	private JdbcTemplate jdbcTemplate;

	/*
	 * //creating function to execute SQL query from single table - 17 Mar, 2026
	 * public List<Feedback> fetchFeedback() { String SQL =
	 * "select * from feedback where rating <= '4' order by id desc limit 5";
	 * //query to fetch the latest 3 feedback
	 * 
	 * List<Feedback> feedbackList = jdbcTemplate.query(SQL, new FeedbackMapper());
	 * 
	 * return feedbackList; }
	 */

	// creating function to execute SQL query from Multiple table - 18 Mar, 2026
	public List<Feedback> fetchFeedback() {
		// Alias means another name of table via which we can refer the table
		// Alias means nickname
		// user table -> u
		// feedback -> f

		String SQL = "select u.name, f.rating, f.review from user u, feedback f where u.email=f.email order by f.id desc"; // query
																															// to
																															// fetch
																															// the
																															// latest
																															// 3
																															// feedback

		List<Feedback> feedbackList = jdbcTemplate.query(SQL, new FeedbackMapper());

		return feedbackList;
	}

	/// creating function to execute SQL query from single table - 23 Mar, 2026
	public List<Camp> showCamps() {
		String SQL = "select * from camp order by date  desc";
		List<Camp> campList = jdbcTemplate.query(SQL, new CampMapper());
		return campList;
	}

}