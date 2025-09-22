package com.farmer.model;

import java.time.LocalDateTime;

public class CropDto {

	private int cropId;
	private int farmerId;
	private String cropName;
	private String cropType;
	private Double quantityAvailable;
	private Double pricePerKg;
	private Double quantityBooked;
	private String status;
	private LocalDateTime postedAt = LocalDateTime.now();
	private Double totalAvailableCost;
	private Double totalBookedCost;

	public CropDto() {
		super();
	}

	public CropDto(int cropId, int farmerId, Integer dealerId, String cropName, String cropType,
			Double quantityAvailable, Double pricePerKg, Double quantityBooked, String status, LocalDateTime postedAt,
			Double totalAvailableCost, Double totalBookedCost) {
		super();
		this.cropId = cropId;
		this.farmerId = farmerId;
		this.cropName = cropName;
		this.cropType = cropType;
		this.quantityAvailable = quantityAvailable;
		this.pricePerKg = pricePerKg;
		this.quantityBooked = quantityBooked;
		this.status = status;
		this.postedAt = postedAt;
		this.totalAvailableCost = totalAvailableCost;
		this.totalBookedCost = totalBookedCost;
	}

	public int getCropId() {
		return cropId;
	}

	public void setCropId(int cropId) {
		this.cropId = cropId;
	}

	public int getFarmerId() {
		return farmerId;
	}

	public void setFarmerId(int farmerId) {
		this.farmerId = farmerId;
	}

	public String getCropName() {
		return cropName;
	}

	public void setCropName(String cropName) {
		this.cropName = cropName;
	}

	public String getCropType() {
		return cropType;
	}

	public void setCropType(String cropType) {
		this.cropType = cropType;
	}

	public Double getQuantityAvailable() {
		return quantityAvailable;
	}

	public void setQuantityAvailable(Double quantityAvailable) {
		this.quantityAvailable = quantityAvailable;
	}

	public Double getPricePerKg() {
		return pricePerKg;
	}

	public void setPricePerKg(Double pricePerKg) {
		this.pricePerKg = pricePerKg;
	}

	public Double getQuantityBooked() {
		return quantityBooked;
	}

	public void setQuantityBooked(Double quantityBooked) {
		this.quantityBooked = quantityBooked;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getPostedAt() {
		return postedAt;
	}

	public void setPostedAt(LocalDateTime postedAt) {
		this.postedAt = postedAt;
	}

	public Double getTotalAvailableCost() {
		return totalAvailableCost;
	}

	public void setTotalAvailableCost(Double totalAvailableCost) {
		this.totalAvailableCost = totalAvailableCost;
	}

	public Double getTotalBookedCost() {
		return totalBookedCost;
	}

	public void setTotalBookedCost(Double totalBookedCost) {
		this.totalBookedCost = totalBookedCost;
	}

}
