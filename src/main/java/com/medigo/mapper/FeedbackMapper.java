package com.medigo.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.medigo.model.Feedback;
import com.medigo.model.User;

public class FeedbackMapper implements RowMapper<Feedback> {

	// RowMapper maps table with Java Object - 17 Mar, 2026
	Feedback feedback = null;

	@Override
	public Feedback mapRow(ResultSet rs, int rowNum) throws SQLException {

		System.out.println(rowNum);

		// fetching data from feedback table only.
		// variable names
		/*
		 * String email = rs.getString("email"); String rating = rs.getString("rating");
		 * String review = rs.getString("review");
		 */

		// Creating object of Feedback class
//		feedback  = new Feedback(email, rating, review);

		// fetching data from user and feedback table
		String rating = rs.getString("rating");
		String review = rs.getString("review");

		String userName = rs.getString("name");

		// User has a name;
		// Feedback has a User;

		// User class object creation;
		User u = new User();
		u.setName(userName);

		// Feedback class object creation
		feedback = new Feedback();
		feedback.setRating(rating);
		feedback.setReview(review);
		feedback.setUser(u);

		return feedback;
	}

}