package com.dealer.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.dealer.model.CropWrapper;
import com.dealer.model.RatingsAndReviewsDTO;

@FeignClient("BOOKINGSERVICE")
public interface CropInterface {

	@GetMapping("booking/allCrops")
	ResponseEntity<List<CropWrapper>> getAllCrops();

	@PutMapping("booking/book/{id}/{quantity}")
	ResponseEntity<String> bookCropByDealer(@PathVariable("id") int cropId, @PathVariable("quantity") double quantity);

	@PutMapping("booking/cancel/{id}/{quantity}")
	ResponseEntity<String> cancelCropBooking(@PathVariable("id") int cropId, @PathVariable("quantity") double quantity);

	@GetMapping("/booking/{id}")
	ResponseEntity<CropWrapper> getCropDetails(@PathVariable("id") int cropId);

	@GetMapping("booking/status/{cropId}")
	public ResponseEntity<String> getCropStatus(@PathVariable int cropId);

	@GetMapping("booking/available")
	public ResponseEntity<List<CropWrapper>> getAvailableCrops();

	@GetMapping("booking/booked")
	public ResponseEntity<List<CropWrapper>> getFullyBookedCrops();

	@GetMapping("booking/reviews")
	public ResponseEntity<List<RatingsAndReviewsDTO>> allReviews();

	@GetMapping("booking/reviews/{crop_id}")
	public ResponseEntity<List<RatingsAndReviewsDTO>> allReviewsByCropId(@PathVariable int crop_id);

	@GetMapping("booking/review/average-rating/{crop_id}")
	public ResponseEntity<String> getAverage(@PathVariable int crop_id);

}
