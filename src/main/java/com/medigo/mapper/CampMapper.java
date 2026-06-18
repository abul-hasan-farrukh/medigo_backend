package com.medigo.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;

import com.medigo.model.Camp;

public class CampMapper implements RowMapper<Camp> {
	// RowMapper maps table with Java Object - 23 Mar, 2026
	Camp camp = null;

	@Override
	public Camp mapRow(ResultSet rs, int rowNum) throws SQLException {

		System.out.println(rowNum);

		// fetching data from camp table only.
		// variable names

		String title = rs.getString("title");
		String venue = rs.getString("venue");
		String description = rs.getString("description");
		LocalDate date = rs.getDate("date").toLocalDate();

		// Creating object of Camp class
		camp = new Camp(title, venue, description, date);

		return camp;
	}
}