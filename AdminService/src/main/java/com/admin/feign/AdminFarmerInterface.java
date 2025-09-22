package com.admin.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.admin.model.FarmerDto;

@FeignClient("CUSTOMERSERVICE")
public interface AdminFarmerInterface {

	@GetMapping("/customer/allFarmers")
	public ResponseEntity<List<FarmerDto>> getAllFarmers();

	@GetMapping("/customer/profile/{id}")
    public FarmerDto getFarmerProfileById(@PathVariable int id);

	@PutMapping("/customer/profile/edit/{id}")
	public ResponseEntity<String> editProfileOfFarmer(@PathVariable int id, @RequestBody FarmerDto farmer);

	@DeleteMapping("/customer/delete/{id}")
	public ResponseEntity<String> deleteFarmer(@PathVariable int id);

}
