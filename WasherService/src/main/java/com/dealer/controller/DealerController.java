package com.dealer.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dealer.model.BankDetails;
import com.dealer.model.Booking;
import com.dealer.model.CropWrapper;
import com.dealer.model.Dealer;
import com.dealer.model.PaymentWrapper;
import com.dealer.model.RatingsAndReviewsDTO;
import com.dealer.repo.DealerRepo;
import com.dealer.service.DealerService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/washer")
public class DealerController {

	@Autowired
	DealerService dealerService;

	@Autowired
	DealerRepo dealerRepo;

	// 1 get all delaers list -- admin
	// 2 delete dealer from list -- admin
	// 3 register/add dealer -- admin,dealer
	// 4 getprofiledetails -- admin,dealer
	// 5 edit profile deatils -- admin ,dealer
	// 6 addpayement details --dealer
	// 7 edit/update payement details -- dealer
	// 8 get list of published crop -- dealer
	// 9 book published crop -- dealer
	// 10 cancle booking publish crop details-- dealer

	// 1
	@GetMapping("/allDealers")
	public ResponseEntity<List<Dealer>> getAllDealers() {
		return dealerService.getAllDealers();
	}

	// 2
	@DeleteMapping("/delete/{id}")
	public String deleteDealer(@PathVariable int id) {
		return dealerService.deleteDealerById(id);
	}

	// 3
	@PostMapping("/register")
	public ResponseEntity<String> registerDealer(@RequestBody Dealer dealer) {
		return dealerService.registerDealer(dealer);
	}

	// 4
	@GetMapping("/profile/{id}")
	public ResponseEntity<Optional<Dealer>> getDealerProfileById(@PathVariable int id) {
		return dealerService.getDealerProfileDetailsById(id);
	}
	
	@GetMapping("/profile/email/{email}")
	public ResponseEntity<Dealer> getDealerByEmail(@PathVariable String email) {
	    return dealerService.getDealerByEmail(email);
	}


	// 5
	@PutMapping("/profile/edit/{id}")
	public ResponseEntity<Map<String, String>> editProfileOfDealer(@PathVariable int id, @RequestBody Dealer dealer) {
		System.out.println("called");
		return dealerService.editDealerProfile(id, dealer);
	}

	// 6
	@PostMapping("/{id}/add-bank-details")
	public ResponseEntity<Map<String, String>> addBankDetails(@PathVariable int id, @RequestBody BankDetails bankDetails) {
		return dealerService.addBankDetails(id, bankDetails);
	}

	// 7
	@PutMapping("/{id}/update-bank-details")
	public ResponseEntity<Map<String, String>> updateBankDetails(@PathVariable int id, @RequestBody BankDetails newDetails) {
		return dealerService.updateBankDetails(id, newDetails);
	}

	// get details by id - pending
	
	@GetMapping("/allCrops")
	@CircuitBreaker(name = "dealerInterface", fallbackMethod = "fallbackGetAllCrops")
	public ResponseEntity<List<CropWrapper>> getAllCrops() {
		return dealerService.getAllCrops();
	}

	@PostMapping("/book")
	public ResponseEntity<String> bookCrop(@RequestBody Booking booking) {
		return dealerService.bookCrop(booking.getCropId(), booking.getDealerId(), booking.getQuantity());
	}

	@PostMapping("/pay")
	public ResponseEntity<String> pay(@RequestBody PaymentWrapper payment) {
		return dealerService.pay(payment);
	}

	@PostMapping("/cancel")
	public ResponseEntity<String> cancelBooking(@RequestBody Booking booking) {
		return dealerService.cancelCropBooking(booking.getCropId(), booking.getDealerId(), booking.getQuantity());
	}

	@GetMapping("/{dealerId}/bookings")
	public ResponseEntity<List<Booking>> getBookingsByDealer(@PathVariable int dealerId) {
		return ResponseEntity.ok(dealerService.getBookingsByDealer(dealerId));
	}

	@GetMapping("/booked-crops/details")
	public ResponseEntity<List<CropWrapper>> getBookedCropDetails() {
		return ResponseEntity.ok(dealerService.getBookedCropDetails());
	}

	@GetMapping("crop/status/{cropId}")
	public ResponseEntity<String> getCropStatus(@PathVariable int cropId) {
		return dealerService.getCropStatus(cropId);
	}

	@GetMapping("crop/available")
	public ResponseEntity<List<CropWrapper>> getAvailableCrops() {
		return dealerService.getAvailableCrops();
	}

	@GetMapping("crop/booked")
	public ResponseEntity<List<CropWrapper>> getFullyBookedCrops() {
		return dealerService.getFullyBookedCrops();
	}

	@GetMapping("crop/reviews")
	public ResponseEntity<List<RatingsAndReviewsDTO>> allReviews() {
		return dealerService.allReviews();
	}

	@GetMapping("crop/reviews/{crop_id}")
	public ResponseEntity<List<RatingsAndReviewsDTO>> allReviewsByCropId(@PathVariable int crop_id) {
		return dealerService.allReviewsByCropId(crop_id);
	}

	@GetMapping("crop/review/average-rating/{crop_id}")
	public ResponseEntity<String> getAverage(@PathVariable int crop_id) {
		return dealerService.getAverage(crop_id);
	}

	@GetMapping("/emails")
	public ResponseEntity<List<String>> getAllDealerEmails() {
		List<String> emails = dealerRepo.findAll().stream().map(Dealer::getEmail).collect(Collectors.toList());
		return ResponseEntity.ok(emails);
	}

	@GetMapping("/export")
	public ResponseEntity<ByteArrayResource> exportDealerReport() {
		return dealerService.exportDealerReport();
	}
	
	public ResponseEntity<List<CropWrapper>> fallbackGetAllCrops(Exception e) {
		System.out.println("Fallback triggered due to: " + e.getMessage());
		return ResponseEntity.ok(new ArrayList<>());
	}

}
