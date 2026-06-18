package com.medigo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medigo.model.Camp;

public interface CampRepository extends JpaRepository<Camp, Integer> {

}