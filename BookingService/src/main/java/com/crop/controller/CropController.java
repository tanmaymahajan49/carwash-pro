package com.crop.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crop.model.Crop;
import com.crop.model.RatingsAndReviews;
import com.crop.service.CropService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Crop APIs", description = "CRUD Operation and more APIs")
@RequestMapping("/booking")
public class CropController {

	// we are storing published crops from different farmers in crop table in crop
	// service
	// 1. get all published crops -- admin , farmer , dealer
	// 2. get details of particular published crop by id -- admin , farmer , dealer
	// 3. get farmer details of that crop -- dealer ////
	// 4. publish crop -- farmer
	// 5. edit published crop -- farmer
	// 6. delete published crop -- farmer
	// 7. book crop -- dealer
	// 8. Cancel booking --dealer
	// 9. get list of crops pubished by particular farmer ////

	// 1 get all reviews
	// 2 get all reviews by crop id
	// 3.post review -- dealer
	// 4.edit review -- dealer / admin
	// 5.delete review -- admin
	// 6.get average rating of crop

	@Autowired
	CropService cropService;

	// 1
	@GetMapping("/allCrops")
	public ResponseEntity<List<Crop>> getAllCrops() {
		return cropService.getAllCrops();
	}

	// 2
	@GetMapping("/{id}")
	public ResponseEntity<Optional<Crop>> getCropProfileById(@PathVariable int id) {
		return cropService.getCropDetailsById(id);
	}

	@GetMapping("/name/{name}")
	public ResponseEntity<List<Crop>> getCropProfileByName(@PathVariable String name) {
		return cropService.getCropDetailsByName(name);
	}

	// 3

	// 4
	@PostMapping("/publish")
	public ResponseEntity<String> publishCrop(@RequestBody Crop crop) {
		return cropService.publishCrop(crop);
	}

	// 5
	@PutMapping("/edit/{id}")
	public ResponseEntity<String> editCrop(@PathVariable int id, @RequestBody Crop crop) {
		return cropService.editCrop(id, crop);
	}

	// 6
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteCrop(@PathVariable int id) {
		return cropService.deleteCropById(id);
	}

	// 7
	@PutMapping("/book/{id}/{quantity}")
	public ResponseEntity<String> bookCrop(@PathVariable int id, @PathVariable double quantity) {
		return cropService.bookCropByDealer(id, quantity);
	}

	// 8
	@PutMapping("/cancel/{id}/{quantity}")
	public ResponseEntity<String> cancelBooking(@PathVariable int id, @PathVariable double quantity) {
		return cropService.cancelCropBooking(id, quantity);
	}

	// 9
	@GetMapping("/farmer/{farmerId}")
	public ResponseEntity<List<Crop>> getCropsByFarmer(@PathVariable int farmerId) {
		return cropService.getCropsByFarmer(farmerId);
	}

	//both farmer and dealer
	@GetMapping("/status/{cropId}")
	public ResponseEntity<String> getCropStatus(@PathVariable int cropId) {
		return cropService.getCropStatus(cropId);
	}

	//both farmer and dealer
	@GetMapping("/available")
	public ResponseEntity<List<Crop>> getAvailableCrops() {
		return cropService.getAvailableCrops();
	}

	//both farmer and dealer
	@GetMapping("/booked")
	public ResponseEntity<List<Crop>> getFullyBookedCrops() {
		return cropService.getFullyBookedCrops();
	}

	//both farmer and dealer
	@GetMapping("/reviews")
	public ResponseEntity<List<RatingsAndReviews>> allReviews() {
		return cropService.allReviews();
	}

	//both farmer and dealer
	@GetMapping("/reviews/id/{crop_id}")
	public ResponseEntity<List<RatingsAndReviews>> allReviewsByCropId(@PathVariable int crop_id) {
		return cropService.allReviewsByCropId(crop_id);
	}
	
	@GetMapping("/reviews/{crop_name}")
	public ResponseEntity<List<RatingsAndReviews>> allReviewsByCropName(@PathVariable String crop_name) {
		return cropService.allReviewsByCropName(crop_name);
	}

	//both farmer and dealer
	@GetMapping("/review/average-rating/{crop_id}")
	public ResponseEntity<String> getAverage(@PathVariable("crop_id") int crop_id) {
		return cropService.getAverage(crop_id);
	}
	
	@DeleteMapping("/review/delete/{review_id}")
	public ResponseEntity<String> deleteReview(@PathVariable("review_id") int review_id) {
		return cropService.deleteReview(review_id);
	}

	@PostMapping("/review/post/{crop_id}")
	public ResponseEntity<String> postReview(@RequestParam("dealer_id") int dealerId,
			@PathVariable("crop_id") int crop_id, @RequestBody RatingsAndReviews review) {
		return cropService.postReview(dealerId, crop_id, review);
	}

}
