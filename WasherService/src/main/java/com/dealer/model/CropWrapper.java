package com.dealer.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CropWrapper {

	private int cropId;
	private int farmerId;
	private String cropName;
	private String cropType;
	private Double quantityAvailable;
	private Double pricePerKg;
	private Double quantityBooked;
	private String status;
	private LocalDateTime postedAt = LocalDateTime.now();

//	public CropWrapper() {
//		super();
//	}
//
//	public CropWrapper(int cropId, int farmerId, Integer dealerId, String cropName, String cropType,
//			Double quantityAvailable, Double pricePerKg, Double quantityBooked, String status, LocalDateTime postedAt) {
//		super();
//		this.cropId = cropId;
//		this.farmerId = farmerId;
//		this.dealerId = dealerId;
//		this.cropName = cropName;
//		this.cropType = cropType;
//		this.quantityAvailable = quantityAvailable;
//		this.pricePerKg = pricePerKg;
//		this.quantityBooked = quantityBooked;
//		this.status = status;
//		this.postedAt = postedAt;
//	}
//
//	public int getCropId() {
//		return cropId;
//	}
//
//	public void setCropId(int cropId) {
//		this.cropId = cropId;
//	}
//
//	public int getFarmerId() {
//		return farmerId;
//	}
//
//	public void setFarmerId(int farmerId) {
//		this.farmerId = farmerId;
//	}
//
//	public Integer getDealerId() {
//		return dealerId;
//	}
//
//	public void setDealerId(Integer dealerId) {
//		this.dealerId = dealerId;
//	}
//
//	public String getCropName() {
//		return cropName;
//	}
//
//	public void setCropName(String cropName) {
//		this.cropName = cropName;
//	}
//
//	public String getCropType() {
//		return cropType;
//	}
//
//	public void setCropType(String cropType) {
//		this.cropType = cropType;
//	}
//
//	public Double getQuantityAvailable() {
//		return quantityAvailable;
//	}
//
//	public void setQuantityAvailable(Double quantityAvailable) {
//		this.quantityAvailable = quantityAvailable;
//	}
//
//	public Double getPricePerKg() {
//		return pricePerKg;
//	}
//
//	public void setPricePerKg(Double pricePerKg) {
//		this.pricePerKg = pricePerKg;
//	}
//
//	public Double getQuantityBooked() {
//		return quantityBooked;
//	}
//
//	public void setQuantityBooked(Double quantityBooked) {
//		this.quantityBooked = quantityBooked;
//	}
//
//	public String getStatus() {
//		return status;
//	}
//
//	public void setStatus(String status) {
//		this.status = status;
//	}
//
//	public LocalDateTime getPostedAt() {
//		return postedAt;
//	}
//
//	public void setPostedAt(LocalDateTime postedAt) {
//		this.postedAt = postedAt;
//	}

}
