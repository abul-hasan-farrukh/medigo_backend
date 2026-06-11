package com.medigo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medigo.model.Test;

public interface TestRepository extends JpaRepository<Test, Integer> {
	List<Test> findByStatus(String status); // Fetching all tests by active status - 24 Mar, 2026
}