package com.admin.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.admin.model.CropDto;
import com.admin.model.DealerDto;
import com.admin.model.FarmerDto;
import com.admin.model.NotificationDto;
import com.admin.model.ReviewDto;
import com.admin.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	AdminService adminService;

	// ---------------------farmer-------------------------
	// 1. all farmers
	// 2. farmer by id
	// 3 edit profile details //can change status using this (active/inactive)
	// 4 delete farmer from list

	@GetMapping("/customer/allFarmers")
	public ResponseEntity<List<FarmerDto>> getAllFarmers() {
		return adminService.getAllFarmers();
	}

	// Car-Wash alias
	@GetMapping("/customer/all")
	public ResponseEntity<List<FarmerDto>> getAllCustomers() {
		return adminService.getAllFarmers();
	}

	@GetMapping("/customer/profile/{id}")	
	public ResponseEntity<?> getFarmerProfileById(@PathVariable int id) {
        try {
            FarmerDto farmer = adminService.getFarmerProfileDetailsById(id);
            return ResponseEntity.ok(farmer);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }


	@PutMapping("/customer/profile/edit/{id}")
	public ResponseEntity<String> editProfileOfFarmer(@PathVariable int id, @RequestBody FarmerDto farmer) {
		return adminService.editFarmerProfile(id, farmer);
	}

	@DeleteMapping("/customer/delete/{id}")
	public ResponseEntity<String> deleteFarmer(@PathVariable int id) {
		return adminService.deleteFarmerById(id);
	}

	// ---------------------dealer-------------------------
	// 1 get all dealers list
	// 2 getprofiledetails
	// 3 edit profile details
	// 4 delete dealer from list

	@GetMapping("/washer/allDealers")
	public ResponseEntity<List<DealerDto>> getAllDealers() {
		return adminService.getAllDealer();
	}

	// Car-Wash alias
	@GetMapping("/washer/all")
	public ResponseEntity<List<DealerDto>> getAllWashers() {
		return adminService.getAllDealer();
	}

	@GetMapping("/washer/profile/{id}")
	public ResponseEntity<Optional<DealerDto>> getDealerProfileById(@PathVariable int id) {
		return adminService.getDealerProfileDetailsById(id);
	}

	@PutMapping("/washer/profile/edit/{id}")
	public ResponseEntity<String> editProfileOfDealer(@PathVariable int id, @RequestBody DealerDto dealer) {
		return adminService.editDealerProfile(id, dealer);
	}

	@DeleteMapping("/washer/delete/{id}")
	public ResponseEntity<String> deleteDealer(@PathVariable int id) {
		return adminService.deleteDealerById(id);
	}

	// ---------------------crop-------------------------
	// 1. get all published crops
	// 2. get details of particular published crop by id

	@GetMapping("/booking/allCrops")
	public ResponseEntity<List<CropDto>> getAllCrops() {
		return adminService.getAllCrops();
	}

	// Car-Wash alias
	@GetMapping("/booking/all")
	public ResponseEntity<List<CropDto>> getAllBookings() {
		return adminService.getAllCrops();
	}

	@GetMapping("/booking/{id}")
	public ResponseEntity<Optional<CropDto>> getCropDetailsById(@PathVariable int id) {
		return adminService.getCropDetailsById(id);
	}

	// ---------------------review-------------------------
	// 1 get all reviews
	// 2 delete review

	@GetMapping("/booking/reviews")
	public ResponseEntity<List<ReviewDto>> allReviews() {
		return adminService.allReviews();
	}

	@DeleteMapping("/booking/review/delete/{review_id}")
	public ResponseEntity<String> deleteReview(int review_id) {
		return adminService.deleteReview(review_id);
	}

	// ---------------------notification-------------------------

	@GetMapping("notify/allnotifications")
	public ResponseEntity<List<NotificationDto>> allnotifications() {
		return adminService.allnotifications();
	}

}
