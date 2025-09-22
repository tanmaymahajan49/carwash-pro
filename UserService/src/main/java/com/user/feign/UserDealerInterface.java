package com.user.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.user.model.UserDto;

@FeignClient("WASHERSERVICE")
public interface UserDealerInterface {

	@PostMapping("/washer/register")
	public ResponseEntity<String> registerDealer(@RequestBody UserDto dealer);

}
