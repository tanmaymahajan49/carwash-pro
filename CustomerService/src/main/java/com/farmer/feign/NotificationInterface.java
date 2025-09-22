package com.farmer.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.farmer.model.NotificationDto;
import com.farmer.model.CropDto;

@FeignClient(name = "NOTIFICATIONSERVICE")
public interface NotificationInterface {

	@PostMapping("/notify/dealer")
	public ResponseEntity<String> notifyDealers(@RequestBody NotificationDto payload);

	@PostMapping("/notify/publish")
	public String publishedCrop(@RequestBody CropDto crop);
}
