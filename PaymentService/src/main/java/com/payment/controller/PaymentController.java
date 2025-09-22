package com.payment.controller;

import com.payment.model.Payment;
import com.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/payment")
public class PaymentController {

	@Autowired
	private PaymentService paymentService;

	@PostMapping("/pay")
	public Payment pay(@RequestBody Payment payment) {
		System.out.println("Saving new payment: " + payment.getAmount() + " " + payment.getCropId() + " " + payment.getDealerId());
		return paymentService.makePayment(payment);
	}

	@GetMapping("/status/{id}")
	public ResponseEntity<?> getStatus(@PathVariable int id) {
		Optional<Payment> payment = paymentService.getPaymentById(id);
		return ResponseEntity.ok(payment);
	}

	@GetMapping("/dealer/{dealerId}")
	public ResponseEntity<List<Payment>> getPaymentsByDealer(@PathVariable int dealerId) {
		return ResponseEntity.ok(paymentService.getPaymentsByDealer(dealerId));
	}
	
//	@PostMapping("/create-order")
//	public ResponseEntity<?> createOrder(@RequestBody Payment payment) {
//	    try {
//	        return ResponseEntity.ok(paymentService.createRazorpayOrder(payment));
//	    } catch (Exception e) {
//	        return ResponseEntity.internalServerError().body("Order creation failed: " + e.getMessage());
//	    }
//	}
	
	

}