package com.medigo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medigo.model.User;

public interface UserRepository extends JpaRepository<User, String> {

	public User findByEmail(String email);

}