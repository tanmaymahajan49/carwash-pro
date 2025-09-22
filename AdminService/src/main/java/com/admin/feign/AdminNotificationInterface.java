package com.admin.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.admin.model.NotificationDto;

@FeignClient("NOTIFICATIONSERVICE")
public interface AdminNotificationInterface {
	
	@GetMapping("notify/allnotifications")
	public ResponseEntity<List<NotificationDto>> allnotifications();

}
