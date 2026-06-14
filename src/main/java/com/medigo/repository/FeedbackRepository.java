package com.medigo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medigo.model.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

}