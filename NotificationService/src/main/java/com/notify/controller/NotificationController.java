package com.notify.controller;

import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.notify.consumer.UserConsumer;
import com.notify.feign.DealerInterface;
import com.notify.model.CropDto;
import com.notify.model.Notification;
import com.notify.model.NotificationPayload;

import jakarta.mail.internet.MimeMessage;

@RestController
@RequestMapping("/notify")
public class NotificationController {

	@Autowired
	private DealerInterface dealerInterface;
	
	@Autowired
	UserConsumer userConsumer;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private RabbitTemplate template;

	@Value("${notification.exchange}")
	private String exchange;

	@Value("${notification.routing-key}")
	private String routingKey;
	
	@GetMapping("/allnotifications")
	public ResponseEntity<List<Notification>> allnotifications(){
		return userConsumer.allnotifications();
	}

	@PostMapping("/dealer")
	public ResponseEntity<String> notifyDealers(@RequestBody NotificationPayload payload) {
		try {
			List<String> allEmails = dealerInterface.getAllDealerEmails();

			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			helper.setSubject(payload.getSubject());
			helper.setText(payload.getBody(), true);
			helper.setFrom("kshitijmulay411@gmail.com");
			
			for (String email : allEmails) {
				helper.addBcc(email);
			}
			mailSender.send(message);
			return ResponseEntity.ok("Notification sent to all dealers");
		} catch (Exception e) {
			return ResponseEntity.status(500).body("Failed to send email: " + e.getMessage());
		}
	}

	@PostMapping("/publish")
	public String publishedCrop(@RequestBody CropDto crop) {
		template.convertAndSend(exchange, routingKey, crop);
		return "Crop Notification Send Successfully !!";
	}
}
