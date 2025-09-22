package com.payment.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("BOOKINGSERVICE")
public interface PaymentCropInterface {

//	@GetMapping("/booking/{id}")
//	public ResponseEntity<Optional<CropDto>> getCropDetailsById(@PathVariable int id);

}
