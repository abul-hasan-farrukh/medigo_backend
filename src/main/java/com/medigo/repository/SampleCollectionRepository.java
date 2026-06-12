package com.medigo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medigo.model.SampleCollection;

public interface SampleCollectionRepository extends JpaRepository<SampleCollection, Integer> {
	List<SampleCollection> findByEmail(String email); // to show all the sample request send by user on his dash-board

	// Get all requests assigned to a collector
	List<SampleCollection> findByWorkerEmail(String worker_email); // to get all assigned requests of specific sample
																	// collector via email.
}