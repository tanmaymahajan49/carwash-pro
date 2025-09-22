package com.crop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.crop.model.Crop;
import com.crop.model.RatingsAndReviews;
import com.crop.repo.CropRepo;
import com.crop.repo.ReviewRepo;

@Service
public class CropService {

	@Autowired
	CropRepo cropRepo;

	@Autowired
	ReviewRepo reviewRepo;

	public ResponseEntity<List<Crop>> getAllCrops() {
		try {
			return new ResponseEntity<>(cropRepo.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<Optional<Crop>> getCropDetailsById(int id) {
		try {
			Optional<Crop> crop = cropRepo.findById(id);
			return new ResponseEntity<>(crop, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
	}
	
	public ResponseEntity<List<Crop>> getCropDetailsByName(String name) {
		try {
			return new ResponseEntity<>(cropRepo.findByCropName(name), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<String> publishCrop(Crop crop) {
		crop.setTotalAvailableCost(crop.getQuantityAvailable() * crop.getPricePerKg());
		crop.setTotalBookedCost(crop.getQuantityBooked() * crop.getPricePerKg());
		cropRepo.save(crop);
		return new ResponseEntity<>("Crop registered successfully and mail sent to all dealers", HttpStatus.CREATED);
	}

	public ResponseEntity<String> editCrop(int id, Crop crop) {
		if (cropRepo.existsById(id)) {
			crop.setCropId(id);
			cropRepo.save(crop);
			return new ResponseEntity<>("Crop updated successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>("Crop not found", HttpStatus.CREATED);
	}

	public ResponseEntity<String> deleteCropById(int id) {
		if (cropRepo.existsById(id)) {
			cropRepo.deleteById(id);
			return ResponseEntity.ok("Crop deleted successfully");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Crop with ID " + id + " does not exist");
		}
	}

//	public ResponseEntity<String> bookCropByDealer(int crop_id, double quantity) {
//	    Optional<Crop> optionalCrop = cropRepo.findById(crop_id);
//	    if (optionalCrop.isEmpty()) {
//	        return new ResponseEntity<>("Crop not found", HttpStatus.NOT_FOUND);
//	    }
//
//	    Crop crop = optionalCrop.get();
//	    if (crop.getQuantity_available() < quantity) {
//	        return new ResponseEntity<>("Insufficient quantity available", HttpStatus.BAD_REQUEST);
//	    }
//
//	    crop.setQuantity_available(crop.getQuantity_available() - quantity);
//	    crop.setQuantity_booked(crop.getQuantity_booked() + quantity);
//
//	    if (crop.getQuantity_available() == 0) {
//	        crop.setStatus("Booked");
//	    }
//
//	    cropRepo.save(crop);
//	    return new ResponseEntity<>("Crop booked: " + quantity + " KG", HttpStatus.OK);
//	}

	public ResponseEntity<String> bookCropByDealer(int crop_id, double quantity) {
		Optional<Crop> optionalCrop = cropRepo.findById(crop_id);
		if (optionalCrop.isEmpty()) {
			return new ResponseEntity<>("Crop not found", HttpStatus.NOT_FOUND);
		}

		Crop crop = optionalCrop.get();

		if (crop.getQuantityAvailable() == null)
			crop.setQuantityAvailable(0.0);
		if (crop.getQuantityBooked() == null)
			crop.setQuantityBooked(0.0);

		if (crop.getQuantityAvailable() < quantity) {
			return new ResponseEntity<>("Insufficient quantity", HttpStatus.BAD_REQUEST);
		}

		crop.setQuantityAvailable(crop.getQuantityAvailable() - quantity);
		crop.setQuantityBooked(crop.getQuantityBooked() + quantity);

		if (crop.getQuantityAvailable() == 0) {
			crop.setStatus("Booked");
		}

		crop.setTotalAvailableCost(crop.getQuantityAvailable() * crop.getPricePerKg());
		crop.setTotalBookedCost(crop.getQuantityBooked() * crop.getPricePerKg());

		cropRepo.save(crop);
		return new ResponseEntity<>("Crop booked successfully of " + quantity + " KG", HttpStatus.OK);
	}

	public ResponseEntity<String> cancelCropBooking(int crop_id, double quantity) {
		Optional<Crop> optionalCrop = cropRepo.findById(crop_id);
		if (optionalCrop.isEmpty()) {
			return new ResponseEntity<>("Crop not found", HttpStatus.NOT_FOUND);
		}

		Crop crop = optionalCrop.get();
		crop.setQuantityAvailable(crop.getQuantityAvailable() + quantity);
		crop.setQuantityBooked(crop.getQuantityBooked() - quantity);
		crop.setTotalAvailableCost(crop.getQuantityAvailable() * crop.getPricePerKg());
		crop.setTotalBookedCost(crop.getQuantityBooked() * crop.getPricePerKg());

		crop.setStatus("Available");
		cropRepo.save(crop);

		return new ResponseEntity<>("Booking of " + quantity + " KG cancelled and removed from records", HttpStatus.OK);
	}

	public ResponseEntity<List<Crop>> getCropsByFarmer(int farmerId) {
		return new ResponseEntity<>(cropRepo.findByFarmerId(farmerId), HttpStatus.OK);
	}

	public ResponseEntity<List<Crop>> getAvailableCrops() {
		return ResponseEntity.ok(cropRepo.findByQuantityAvailableGreaterThan(0.0));
	}

	public ResponseEntity<List<Crop>> getFullyBookedCrops() {
		return ResponseEntity.ok(cropRepo.findByQuantityAvailableEquals(0.0));
	}

	public ResponseEntity<String> getCropStatus(int cropId) {
		return cropRepo.findById(cropId).map(crop -> ResponseEntity.ok("Status: " + crop.getStatus()))
				.orElse(ResponseEntity.notFound().build());
	}

	public ResponseEntity<List<RatingsAndReviews>> allReviews() {
		try {
			return new ResponseEntity<>(reviewRepo.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<List<RatingsAndReviews>> allReviewsByCropId(int crop_id) {
		try {
			return new ResponseEntity<>(reviewRepo.findAllByCropId(crop_id), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
	}
	
	public ResponseEntity<List<RatingsAndReviews>> allReviewsByCropName(String crop_name) {
		try {
			return new ResponseEntity<>(reviewRepo.findAllByCropName(crop_name), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<String> postReview(int dealerId, int crop_id, RatingsAndReviews review) {
		review.setCropId(crop_id);
		review.setDealerId(dealerId);
		System.out.println(review.getComment());
		review.setComment(review.getComment());
		if (review.getRating() > 5)
			review.setRating(5);

		if (review.getRating() < 1)
			review.setRating(1);

		RatingsAndReviews saved = reviewRepo.save(review);
		if (saved != null)
			return new ResponseEntity<>("From Dealer ID " + dealerId + " to Crop ID " + crop_id + " your this review [" + review.getComment() + "] is posted successfully !!!", HttpStatus.CREATED);

		return new ResponseEntity<>("Not able to post review", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public ResponseEntity<String> editReview(int dealer_id, int crop_id, RatingsAndReviews review) {

		List<RatingsAndReviews> reviewList = reviewRepo.findByDealerId(dealer_id);
		RatingsAndReviews toBeEdited = null;

		for (RatingsAndReviews r : reviewList) {
			if (r.getCropId() == crop_id) {
				toBeEdited = r;
			}
		}

		if (toBeEdited != null) {
			review.setReviewId(toBeEdited.getReviewId());
			review.setCropId(crop_id);
			review.setDealerId(dealer_id);
			reviewRepo.save(review);
			return new ResponseEntity<>("review edited successfully", HttpStatus.CREATED);
		}
		return new ResponseEntity<>("Review not found", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public ResponseEntity<String> deleteReview(int review_id) {

		if (reviewRepo.existsById(review_id)) {
			reviewRepo.deleteById(review_id);
			return ResponseEntity.ok("review deleted successfully");
		} else
			return new ResponseEntity<>("Review with ID " + review_id + " does not exist", HttpStatus.NOT_FOUND);
	}

	public ResponseEntity<String> getAverage(int crop_id) {
	    List<RatingsAndReviews> reviewList = reviewRepo.findAllByCropId(crop_id);
	    Optional<Crop> cropname = cropRepo.findById(crop_id); // fetch crop

	    if (!cropname.isPresent()) {
	        return new ResponseEntity<>("Crop not found", HttpStatus.NOT_FOUND);
	    }

	    Crop crop = cropname.get();
	    double total = 0;
	    double average = 0;

	    if (!reviewList.isEmpty()) {
	        for (RatingsAndReviews r : reviewList) {
	            total += r.getRating();
	        }
	        average = total / reviewList.size();
	        return new ResponseEntity<>("Average rating for crop '" + crop.getCropName() + "' is: " + average, HttpStatus.OK);
	    }

	    return new ResponseEntity<>("No reviews found for crop '" + crop.getCropName() + "'", HttpStatus.NOT_FOUND);
	}

}
