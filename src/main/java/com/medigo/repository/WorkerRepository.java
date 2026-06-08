package com.medigo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.medigo.model.Worker;

public interface WorkerRepository extends JpaRepository<Worker, String> {
	List<Worker> findByType(String type);
	
	public Worker findByEmail(String email);
}