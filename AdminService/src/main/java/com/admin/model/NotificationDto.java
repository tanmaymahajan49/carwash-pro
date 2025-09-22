package com.admin.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {
	private int id;
	private String notification;
	private LocalDateTime time = LocalDateTime.now();
}
