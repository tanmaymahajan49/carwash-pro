package com.farmer.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.farmer.model.BankDetails;
import com.farmer.model.CropDto;
import com.farmer.model.Farmer;
import com.farmer.model.RatingsAndReviewsDTO;
import com.farmer.service.FarmerService;

// Legacy disabled: was @RestController
@RequestMapping("/customer")
//@CrossOrigin(origins = "http://localhost:4200")

/** Legacy controller disabled. New controller: CustomerController */
class FarmerControllerLegacy {

	@Autowired
	FarmerService farmerService;

	// 1 get all farmers list -- admin
	// 2 delete farmerfrom list -- admin
	// 3 register/add farmer -- admin,farmer
	// 4 getprofiledetails -- admin,farmer
	// 5 edit profile deatils -- admin ,farmer
	// 6 addpayement details --farmer
	// 7 edit/update payement details -- farmer
	// 8 publish crop -- farmer
	// 9 edit published crop -- farmer
	// 10 deletepublish crop details-- farmer

	// 1
	@GetMapping("/allFarmers")
	public ResponseEntity<List<Farmer>> getAllFarmers() {
		return farmerService.getAllFarmers();
	}

	// New alias for Car-Wash domain: list all customers
	@GetMapping("/all")
	public ResponseEntity<List<Farmer>> getAllCustomers() {
		return farmerService.getAllFarmers();
	}

	// 2
	@DeleteMapping("/delete/{id}")
	public String deleteFarmer(@PathVariable int id) {
		return farmerService.deleteFarmerById(id);
	}

	// 3
	@PostMapping("/register")
	public ResponseEntity<String> registerFarmer(@RequestBody Farmer farmer) {
		return farmerService.registerFarmer(farmer);
	}

	// 4
	@GetMapping("/profile/{id}")
	public ResponseEntity<Farmer> getFarmerProfileById(@PathVariable int id) {
		return farmerService.getFarmerProfileDetailsById(id);
	}
	
	@GetMapping("/profile/email/{email}")
	public ResponseEntity<Farmer> getFarmerByEmail(@PathVariable String email) {
	    return farmerService.findByEmail(email);
	}


	// 5
	@PutMapping("/profile/edit/{id}")
	public ResponseEntity<Map<String, String>> editProfileOfFarmer(@PathVariable int id, @RequestBody Farmer farmer) {
		System.out.println("called");
		return farmerService.editFarmerProfile(id, farmer);
	}

	// 6
	@PostMapping("/{id}/add-bank-details")
	public ResponseEntity<Map<String, String>> addBankDetails(@PathVariable int id, @RequestBody BankDetails bankDetails) {
		return farmerService.addBankDetails(id, bankDetails);
	}

	// 7
	@PutMapping("/{id}/update-bank-details")
	public ResponseEntity<Map<String, String>> updateBankDetails(@PathVariable int id, @RequestBody BankDetails newDetails) {
		return farmerService.updateBankDetails(id, newDetails);
	}

	// get details by id - pending

	@GetMapping("/allCrops")
	public ResponseEntity<List<CropDto>> getAllCrops() {
		return farmerService.getAllCrops();
	}

	// 8
	@PostMapping("/{farmer_id}/publish-crop")
	public ResponseEntity<String> publishCrop(@PathVariable int farmer_id, @RequestBody CropDto cropDto) {
		return farmerService.publishCrop(farmer_id, cropDto);
	}

	// 9. Edit crop
	@PutMapping("/{farmer_id}/edit-crop/{crop_id}")
	public ResponseEntity<String> editCrop(@PathVariable int farmer_id, @PathVariable int crop_id,
			@RequestBody CropDto cropDto) {
		return farmerService.editCropByFarmer(farmer_id, crop_id, cropDto);
	}

	// 10. Delete crop
	@DeleteMapping("/{farmer_id}/delete-crop/{crop_id}")
	public ResponseEntity<String> deleteCrop(@PathVariable int farmer_id, @PathVariable int crop_id) {
		return farmerService.deleteCropByFarmer(farmer_id, crop_id);
	}

	@GetMapping("/{farmer_id}/crops")
	public ResponseEntity<List<CropDto>> getCropsByFarmer(@PathVariable int farmer_id) {
		return farmerService.getCropsByFarmer(farmer_id);
	}

	@GetMapping("/{crop_id}/crop-status")
	public ResponseEntity<String> getCropStatusByFarmer(@PathVariable int crop_id) {
		return farmerService.getCropStatusByFarmer(crop_id);
	}
	
	@GetMapping("/available")
	public ResponseEntity<List<CropDto>> getAvailableCrops() {
		return farmerService.getAvailableCrops();
	}
	
	@GetMapping("/booked")
	public ResponseEntity<List<CropDto>> getFullyBookedCrops() {
		return farmerService.getFullyBookedCrops();
	}

	@GetMapping("/reviews")
	public ResponseEntity<List<RatingsAndReviewsDTO>> allReviews() {
		return farmerService.allReviews();
	}

	@GetMapping("/review/id/{crop_id}")
	public ResponseEntity<List<RatingsAndReviewsDTO>> allReviewsByCropId(@PathVariable int crop_id) {
		return farmerService.allReviewsByCropId(crop_id);
	}
	
	@GetMapping("/review/{crop_name}")
	public ResponseEntity<List<RatingsAndReviewsDTO>> allReviewsByCropName(@PathVariable String crop_name) {
		return farmerService.allReviewsByCropName(crop_name);
	}
	
	@GetMapping("review/average-rating/{crop_id}")
	public ResponseEntity<String> getAverage(int crop_id){
		return farmerService.getAverage(crop_id);
	}
	
}
