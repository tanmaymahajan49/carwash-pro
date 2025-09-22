package com.dealer.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.dealer.model.PaymentWrapper;

@FeignClient("PAYMENTSERVICE")

public interface PaymentInterface {

	@PostMapping("/payment/pay")
	public ResponseEntity<PaymentWrapper> pay(@RequestBody PaymentWrapper payment);

}
