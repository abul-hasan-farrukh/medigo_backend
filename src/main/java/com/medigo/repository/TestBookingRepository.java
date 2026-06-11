package com.medigo.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.medigo.model.Test;
import com.medigo.model.TestBooking;

@Repository
public interface TestBookingRepository extends JpaRepository<TestBooking, Long> {

	// Get all bookings of a user - 06 April, 2026
	List<TestBooking> findByEmail(String email);

	// Get bookings by status (PENDING, COMPLETED, etc.) - 06 April, 2026
	List<TestBooking> findByBookingStatus(String bookingStatus);

	// Get bookings by email + status (useful for filtering) - 06 April, 2026
    @Query("SELECT t FROM TestBooking t WHERE t.email = :email AND t.bookingStatus = :bookingStatus")
	List<TestBooking> findByEmailAndBookingStatus(String email, String bookingStatus);

	// test existence for same email and same date 08 April,2026
	boolean existsByEmailAndTestIdAndDate(String email, Integer testId, LocalDate date);

	// to fetch test details based on transaction no - 14 April, 2026
	List<TestBooking> findByTransactionNoAndBookingStatus(String txn, String status);

	// to fetch bookings based on Transaction_no - 15 April, 2026
	List<TestBooking> findByTransactionNo(String transactionNo);

	Optional<Test> findByTestId(int testId);

}