package com.payment.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("CUSTOMERSERVICE")
public interface PaymentFarmerInterface {

//	@GetMapping("farmer/profile/{id}")
//	public ResponseEntity<Optional<FarmerDto>> getFarmerProfileById(@PathVariable int id);

}
