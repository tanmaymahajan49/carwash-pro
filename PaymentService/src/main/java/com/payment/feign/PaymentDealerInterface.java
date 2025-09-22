package com.payment.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("WASHERSERVICE")
public interface PaymentDealerInterface {

//	@GetMapping("/washer/profile/{id}")
//	public ResponseEntity<Optional<DealerDto>> getDealerProfileById(@PathVariable int id);
//
//	@GetMapping("/washer/bookInfo/{book_id}")
//	public ResponseEntity<Optional<BookingDto>> getBookDetails(@PathVariable int book_id);    //need change

}
