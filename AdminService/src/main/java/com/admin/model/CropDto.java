package com.admin.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CropDto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "crop_id")
	private int cropId;

	@Column(name = "farmer_id")
	private int farmerId;

	@Column(name = "crop_name")
	private String cropName;

	@Column(name = "crop_type")
	private String cropType;

	@Column(name = "quantity_available")
	private Double quantityAvailable;

	@Column(name = "price_per_kg")
	private Double pricePerKg;

	@Column(name = "quantity_booked")
	private Double quantityBooked;

	@Column(name = "status")
	private String status;

	@Column(name = "posted_at")
	private LocalDateTime postedAt = LocalDateTime.now();

	@Column(name = "total_available_cost")
	private Double totalAvailableCost;

	@Column(name = "total_booked_cost")
	private Double totalBookedCost;

}
