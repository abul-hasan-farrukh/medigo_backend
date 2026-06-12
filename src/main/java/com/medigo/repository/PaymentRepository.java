package com.medigo.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.medigo.model.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

	// Get all payments of a user - 08 April, 2026
	List<Payment> findByUserEmail(String userEmail);

	// Find payment by transaction number - 08 April, 2026
	Optional<Payment> findByTransactionNo(String transactionNo);

	@Query("SELECT COUNT(p) FROM Payment p WHERE p.date = :date AND p.tokenNumber IS NOT NULL")
	long countTodayTokens(@Param("date") LocalDate date);

	public List<Payment> findByTokenNumberIsNotNull();

}