package com.notify.consumer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.notify.config.RabbitConfig;
import com.notify.feign.DealerInterface;
import com.notify.model.CropDto;
import com.notify.model.Notification;
import com.notify.model.NotificationPayload;
import com.notify.repo.NotificationRepo;

import org.springframework.mail.javamail.JavaMailSender;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;

@Component
public class UserConsumer {

	@Value("${notification.queue}")
	private String queue;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	DealerInterface dealerInterface;

	@Autowired
	NotificationRepo notificationRepo;

	@RabbitListener(queues = RabbitConfig.QUEUE)
	public void consumeMessageFromQueue(CropDto crop) {

//		String message = "New crop is published Crop Name : " + crop.getCropName() + " Crop Type : "
//				+ crop.getCropType() + " Available Quantity : " + crop.getQuantityAvailable() + " Price per kg : "
//				+ crop.getPricePerKg() + " Rs " + " Check App/Website for more details. Notification from RabbitMQ...";

//		String message =
//			    "New Crop Published!\n\n" +
//			    "Crop Name       : " + crop.getCropName() + "\n" +
//			    "Crop Type       : " + crop.getCropType() + "\n" +
//			    "Available Qty   : " + crop.getQuantityAvailable() + " KG\n" +
//			    "Price per KG    : ₹" + crop.getPricePerKg() + "\n\n" +
//			    "Check the app/website for more details.";

		String message = "<h2>📢 New Crop Published !!!</h2>" + "<p><strong>🌾 Crop Name:</strong> "
				+ crop.getCropName() + "</p>" + "<p><strong>🌱 Crop Type:</strong> " + crop.getCropType() + "</p>"
				+ "<p><strong>📦 Available Quantity:</strong> " + crop.getQuantityAvailable() + " KG</p>"
				+ "<p><strong>💰 Price per KG:</strong> ₹" + crop.getPricePerKg() + "</p>"
				+ "<br><p>👉 Check the <a href='https://github.com/KshitijMulay/Crop-Deal'>Github</a> for more details.</p>";
		
		Notification notification = new Notification();
		notification.setNotification(message);
		notification.setTime(LocalDateTime.now());

		notificationRepo.save(notification);

//		emails.add("kshitijmulay411@gmail.com");
		
		List<String> emails;
		try {
			emails = dealerInterface.getAllDealerEmails();
		} catch (Exception e) {
			System.err.println("Failed to fetch dealer emails: " + e.getMessage());
			return;
		}

		
		
		NotificationPayload payload = new NotificationPayload();

		payload.setSubject("New Crop Published!");
		payload.setTo(emails);
		payload.setBody(message);

		notifyDealers(payload);
		
		

		System.out.println("Message received from queue and processed: " + crop);

	}

	public ResponseEntity<String> notifyDealers(NotificationPayload payload) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			helper.setSubject(payload.getSubject());
			helper.setText(payload.getBody(), true);
			helper.setFrom("kshitijmulay411@gmail.com");
			for (String email : payload.getTo()) {
				helper.addBcc(email);
			}

			mailSender.send(message);

			return ResponseEntity.ok("Notification sent to all dealers");
		} catch (MessagingException e) {
			return ResponseEntity.status(500).body("Failed to send notification: " + e.getMessage());
		}
	}

	public ResponseEntity<List<Notification>> allnotifications() {
	    List<Notification> notify = notificationRepo.findAll();
	    if (notify.isEmpty()) {
	        return new ResponseEntity<>(notify, HttpStatus.NO_CONTENT);
	    } else {
	        return new ResponseEntity<>(notify, HttpStatus.OK);
	    }
	}


}