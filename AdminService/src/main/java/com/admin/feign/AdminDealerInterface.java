package com.admin.feign;

import java.util.List;
import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.admin.model.DealerDto;

@FeignClient("WASHERSERVICE")
public interface AdminDealerInterface {

	@GetMapping("/washer/allDealers")
	public ResponseEntity<List<DealerDto>> getAllDealers();

	@GetMapping("/washer/profile/{id}")
	public ResponseEntity<Optional<DealerDto>> getDealerProfileById(@PathVariable int id);

	@PutMapping("/washer/profile/edit/{id}")
	public ResponseEntity<String> editProfileOfDealer(@PathVariable int id, @RequestBody DealerDto dealer);

	@DeleteMapping("/washer/delete/{id}")
	public ResponseEntity<String> deleteDealer(@PathVariable int id);

}
