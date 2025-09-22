package com.admin.feign;

import java.util.List;
import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.admin.model.CropDto;
import com.admin.model.ReviewDto;

@FeignClient("BOOKINGSERVICE")
public interface AdminCropInterface {

	@GetMapping("/booking/allCrops")
	public ResponseEntity<List<CropDto>> getAllCrops();

	@GetMapping("/booking/{id}")
	public ResponseEntity<Optional<CropDto>> getCropDetailsById(@PathVariable int id);

	@GetMapping("/booking/reviews")
	public ResponseEntity<List<ReviewDto>> allReviews();

	@DeleteMapping("/booking/review/delete/{review_id}")
	public ResponseEntity<String> deleteReview(@PathVariable("review_id") int review_id);

}
