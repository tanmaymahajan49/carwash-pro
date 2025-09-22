package com.farmer.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.farmer.model.CropDto;
import com.farmer.model.RatingsAndReviewsDTO;

@FeignClient("BOOKINGSERVICE")
public interface CropInterface {

	@GetMapping("booking/allCrops")
	public ResponseEntity<List<CropDto>> getAllCrops();

	@PostMapping("/booking/publish")
	public ResponseEntity<String> publishCrop(@RequestBody CropDto cropDto);

	@PutMapping("/booking/edit/{crop_id}")
	ResponseEntity<String> editCrop(@PathVariable("crop_id") int cropId, @RequestBody CropDto cropDto);

	@DeleteMapping("/booking/delete/{crop_id}")
	ResponseEntity<String> deleteCrop(@PathVariable("crop_id") int cropId);

	@GetMapping("/booking/farmer/{farmerId}")
	List<CropDto> getCropsByFarmer(@PathVariable int farmerId);

	@GetMapping("booking/status/{cropId}")
	public ResponseEntity<String> getCropStatus(@PathVariable int cropId);

	@GetMapping("booking/available")
	public ResponseEntity<List<CropDto>> getAvailableCrops();

	@GetMapping("booking/booked")
	public ResponseEntity<List<CropDto>> getFullyBookedCrops();

	@GetMapping("booking/reviews")
	public ResponseEntity<List<RatingsAndReviewsDTO>> allReviews();

	@GetMapping("booking/reviews/id/{crop_id}")
	public ResponseEntity<List<RatingsAndReviewsDTO>> allReviewsByCropId(@PathVariable int crop_id);
	
	@GetMapping("booking/reviews/{crop_name}")
	public ResponseEntity<List<RatingsAndReviewsDTO>> allReviewsByCropName(@PathVariable String crop_name);

	@GetMapping("booking/review/average-rating/{crop_id}")
	public ResponseEntity<String> getAverage(@PathVariable int crop_id);
}
