package com.dealer.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dealer.model.Booking;

public interface BookingRepo extends JpaRepository<Booking, Integer> {
	
	List<Booking> findByDealerId(int dealerId);

	List<Booking> findByDealerIdAndIsCancelledFalse(int dealerId);

}