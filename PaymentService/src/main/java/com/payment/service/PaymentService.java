package com.payment.service;

import com.payment.model.Payment;
import com.payment.repo.PaymentRepo;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

	@Autowired
	private PaymentRepo paymentRepo;

	@Value("${razorpay.api.key}")
	private String apiKey;

	@Value("${razorpay.api.secret}")
	private String apiSecret;

	public Payment makePayment(Payment payment) {
		payment.setTransactionId(0);
		payment.setStatus("SUCCESS");
		payment.setTime(LocalDateTime.now());

		try {
			createOrder(payment.getAmount(), "CropId" + payment.getCropId() + payment.getDealerId());
		} catch (RazorpayException e) {
			e.printStackTrace();
		}

		return paymentRepo.save(payment);
	}

	public String createOrder(double amount, String receiptId) throws RazorpayException {

		RazorpayClient razorpayClient = new RazorpayClient(apiKey, apiSecret);

		JSONObject orderRequest = new JSONObject();

		orderRequest.put("amount", amount * 100);
		orderRequest.put("currency", "INR");
		orderRequest.put("receipt", receiptId);

		Order order = razorpayClient.orders.create(orderRequest);

		return order.toString();

	}
	
//	public Map<String, Object> createRazorpayOrder(Payment payment) throws RazorpayException {
//	    RazorpayClient razorpayClient = new RazorpayClient(apiKey, apiSecret);
//
//	    JSONObject orderRequest = new JSONObject();
//	    orderRequest.put("amount", payment.getAmount() * 100); // Razorpay accepts amount in paise
//	    orderRequest.put("currency", "INR");
//	    orderRequest.put("receipt", "CropId" + payment.getCropId() + payment.getDealerId());
//
//	    Order order = razorpayClient.orders.create(orderRequest);
//
//	    Map<String, Object> response = new HashMap<>();
//	    response.put("orderId", order.get("id"));
//	    response.put("amount", order.get("amount"));
//	    response.put("currency", order.get("currency"));
//	    response.put("key", apiKey); // Needed in frontend Razorpay config
//	    response.put("dealerId", payment.getDealerId());
//	    response.put("cropId", payment.getCropId());
//
//	    return response;
//	}


	public Optional<Payment> getPaymentById(int id) {
		return paymentRepo.findById(id);
	}

	public List<Payment> getPaymentsByDealer(int dealerId) {
		return paymentRepo.findByDealerId(dealerId);
	}
}