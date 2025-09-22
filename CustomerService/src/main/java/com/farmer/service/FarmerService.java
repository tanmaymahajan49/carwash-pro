package com.farmer.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.farmer.feign.CropInterface;
import com.farmer.feign.NotificationInterface;
import com.farmer.model.BankDetails;
import com.farmer.model.CropDto;
import com.farmer.model.Farmer;
import com.farmer.model.RatingsAndReviewsDTO;
import com.farmer.repo.BankDetailsRepo;
import com.farmer.repo.FarmerRepo;

@Service
public class FarmerService {

	@Autowired
	FarmerRepo farmerRepo;

	@Autowired
	BankDetailsRepo bankRepo;

	@Autowired
	CropInterface cropInterface;

	@Autowired
	NotificationInterface notificationInterface;

	public ResponseEntity<List<Farmer>> getAllFarmers() {
		try {
			return new ResponseEntity<>(farmerRepo.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
	}

	public String deleteFarmerById(int id) {
		farmerRepo.deleteById(id);
		return "Farmer deleted succesfully";
	}

	public ResponseEntity<String> registerFarmer(Farmer farmer) {
		farmerRepo.save(farmer);
		return new ResponseEntity<>(farmer.getFirst_name() + " you have registered successfully as Farmer",
				HttpStatus.CREATED);
	}

	public ResponseEntity<Farmer> getFarmerProfileDetailsById(int id) {
		try {
			Optional<Farmer> farmer = farmerRepo.findById(id);
			return new ResponseEntity<>(farmer.get(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
//		return new ResponseEntity<>("Farmer not found for ID: " + id, HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}

	

	public ResponseEntity<Map<String, String>> editFarmerProfile(int id, Farmer farmer) {
	    if (farmerRepo.existsById(id)) {
	        farmer.setFarmer_id(id);
	        farmerRepo.save(farmer);
	        return new ResponseEntity<>(Map.of("message", "farmer profile updated successfully"), HttpStatus.OK);
	    }
	    return new ResponseEntity<>(Map.of("message", "farmer not found"), HttpStatus.NOT_FOUND);
	}


	public ResponseEntity<Map<String, String>> addBankDetails(int farmerId, BankDetails bankDetails) {
		Optional<Farmer> optionalFarmer = farmerRepo.findById(farmerId);

		if (optionalFarmer.isPresent()) {
			Farmer farmer = optionalFarmer.get();

			BankDetails savedBank = bankRepo.save(bankDetails);

			farmer.setBankDetails(savedBank);
			farmerRepo.save(farmer);

			return new ResponseEntity<>(Map.of("message","Bank details added successfully"), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(Map.of("message","Farmer not found"), HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<Map<String, String>> updateBankDetails(int farmerId, BankDetails newDetails) {
		Optional<Farmer> optionalFarmer = farmerRepo.findById(farmerId);

		if (optionalFarmer.isPresent()) {
			Farmer farmer = optionalFarmer.get();

			BankDetails existing = farmer.getBankDetails();

			if (existing == null) {
				return new ResponseEntity<>(Map.of("message","No existing bank details found.please add bank details"),
						HttpStatus.NOT_FOUND);
			}

			existing.setBankName(newDetails.getBankName());
			existing.setAccountNumber(newDetails.getAccountNumber());
			existing.setIfscCode(newDetails.getIfscCode());
			existing.setUpiId(newDetails.getUpiId());
			existing.setUpiNumber(newDetails.getUpiNumber());

			bankRepo.save(existing);

			return new ResponseEntity<>(Map.of("message","Bank details updated successfully"), HttpStatus.OK);
		}
		return new ResponseEntity<>(Map.of("message","Farmer not found"), HttpStatus.NOT_FOUND);
	}

	public ResponseEntity<String> publishCrop(int farmer_id, CropDto cropDto) {
		cropDto.setFarmerId(farmer_id);

		ResponseEntity<String> result = cropInterface.publishCrop(cropDto);

		if (result.getStatusCode().is2xxSuccessful()) {
//			NotificationDto payload = new NotificationDto();
//			payload.setSubject("!!! New Crop Published !!!!");
//			payload.setBody("A new crop has been published by Farmer ID: " + farmer_id + "\nCrop Name: "
//					+ cropDto.getCropName() + "\nType: " + cropDto.getCropType() + "\nQuantity: "
//					+ cropDto.getQuantityAvailable() + " KG" + "\nPrice: ₹" + cropDto.getPricePerKg() + " per KG");

//			notificationInterface.notifyDealers(payload);
			notificationInterface.publishedCrop(cropDto);
		}

		return result;
	}

	public ResponseEntity<String> editCropByFarmer(int farmerId, int cropId, CropDto cropDto) {

		return cropInterface.editCrop(cropId, cropDto);
	}

	public ResponseEntity<String> deleteCropByFarmer(int farmerId, int cropId) {

		return cropInterface.deleteCrop(cropId);
	}

	public ResponseEntity<List<CropDto>> getAllCrops() {
		return cropInterface.getAllCrops();

	}

	public ResponseEntity<List<CropDto>> getCropsByFarmer(int farmerId) {
		List<CropDto> crops = cropInterface.getCropsByFarmer(farmerId);

		return ResponseEntity.ok(crops);
	}

	public ResponseEntity<String> getCropStatusByFarmer(int crop_id) {
		return cropInterface.getCropStatus(crop_id);

	}

	public ResponseEntity<List<CropDto>> getAvailableCrops() {
		return cropInterface.getAvailableCrops();
	}

	public ResponseEntity<List<CropDto>> getFullyBookedCrops() {
		return cropInterface.getFullyBookedCrops();
	}

	public ResponseEntity<List<RatingsAndReviewsDTO>> allReviews() {
		return cropInterface.allReviews();
	}

	public ResponseEntity<List<RatingsAndReviewsDTO>> allReviewsByCropId(int crop_id) {
		return cropInterface.allReviewsByCropId(crop_id);
	}
	
	public ResponseEntity<List<RatingsAndReviewsDTO>> allReviewsByCropName(String crop_name) {
		return cropInterface.allReviewsByCropName(crop_name);
	}

	public ResponseEntity<String> getAverage(int crop_id) {
		return cropInterface.getAverage(crop_id);
	}

	public ResponseEntity<Farmer> findByEmail(String email) {
	    try {
	        Farmer farmer = farmerRepo.getByEmail(email);
	        if (farmer != null) {
	            return new ResponseEntity<>(farmer, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	}


}
