package com.notify.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CropDto {

	private String cropName;
	private String cropType;
	private Double quantityAvailable;
	private Double pricePerKg;

}