package com.notify.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "WASHERSERVICE")
public interface DealerInterface {

	@GetMapping("/washer/emails")
	List<String> getAllDealerEmails();
	
}
