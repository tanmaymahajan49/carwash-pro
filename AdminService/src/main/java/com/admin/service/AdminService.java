package com.admin.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.admin.feign.AdminCropInterface;
import com.admin.feign.AdminDealerInterface;
import com.admin.feign.AdminFarmerInterface;
import com.admin.feign.AdminNotificationInterface;
import com.admin.model.CropDto;
import com.admin.model.DealerDto;
import com.admin.model.FarmerDto;
import com.admin.model.NotificationDto;
import com.admin.model.ReviewDto;

import feign.FeignException;

@Service
public class AdminService {

	@Autowired
	AdminFarmerInterface farmerInterface;

	@Autowired
	AdminDealerInterface dealer_interface;

	@Autowired
	AdminCropInterface crop_interface;
	
	@Autowired
	AdminNotificationInterface adminNotificationInterface;

//	farmer

	public ResponseEntity<List<FarmerDto>> getAllFarmers() {

		return farmerInterface.getAllFarmers();
	}
	
	public FarmerDto getFarmerProfileDetailsById(int id) {
        try {
            return farmerInterface.getFarmerProfileById(id);
        } catch (FeignException.NotFound e) {
            throw new RuntimeException("Farmer not found with ID: " + id);
        }
    }

	public ResponseEntity<String> editFarmerProfile(int id, FarmerDto farmer) {
		return farmerInterface.editProfileOfFarmer(id, farmer);
	}

	public ResponseEntity<String> deleteFarmerById(int id) {
		return farmerInterface.deleteFarmer(id);
	}

//	dealer

	public ResponseEntity<List<DealerDto>> getAllDealer() {
		return dealer_interface.getAllDealers();
	}

	public ResponseEntity<Optional<DealerDto>> getDealerProfileDetailsById(int id) {
		return dealer_interface.getDealerProfileById(id);
	}

	public ResponseEntity<String> editDealerProfile(int id, DealerDto dealer) {
		return dealer_interface.editProfileOfDealer(id, dealer);
	}

	public ResponseEntity<String> deleteDealerById(int id) {
		return dealer_interface.deleteDealer(id);
	}

//	crop

	public ResponseEntity<List<CropDto>> getAllCrops() {
		return crop_interface.getAllCrops();
	}

	public ResponseEntity<Optional<CropDto>> getCropDetailsById(int id) {
		return crop_interface.getCropDetailsById(id);
	}

	public ResponseEntity<List<ReviewDto>> allReviews() {
		return crop_interface.allReviews();
	}

	public ResponseEntity<String> deleteReview(int review_id) {
		return crop_interface.deleteReview(review_id);
	}
	
	public ResponseEntity<List<NotificationDto>> allnotifications() {
		return adminNotificationInterface.allnotifications();
	}

}
