package com.medigo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medigo.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, String> {

}