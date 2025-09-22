package com.user.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.user.model.UserDto;

@FeignClient("CUSTOMERSERVICE")
public interface UserFarmerInterface {

	@PostMapping("/customer/register")
	public ResponseEntity<String> registerFarmer(@RequestBody UserDto farmer);

}
