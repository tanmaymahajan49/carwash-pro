package com.payment.model;

import java.time.LocalDateTime;

public class Receipt {
	private Integer paymentId;
    private Integer dealerId;
    private Integer farmerId;
    private Integer cropId;
	private Double amount;
	private LocalDateTime paidAt;
	private String farmerName;
	private String dealerName;
	private String cropName;
	private double quantity;

	public Integer getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}

	public Integer getDealerId() {
		return dealerId;
	}

	public void setDealerId(Integer dealerId) {
		this.dealerId = dealerId;
	}

	public Integer getFarmerId() {
		return farmerId;
	}

	public void setFarmerId(Integer farmerId) {
		this.farmerId = farmerId;
	}

	public Integer getCropId() {
		return cropId;
	}

	public void setCropId(Integer cropId) {
		this.cropId = cropId;
	}
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public LocalDateTime getPaidAt() {
		return paidAt;
	}

	public void setPaidAt(LocalDateTime paidAt) {
		this.paidAt = paidAt;
	}

	public String getFarmerName() {
		return farmerName;
	}

	public void setFarmerName(String farmerName) {
		this.farmerName = farmerName;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public String getCropName() {
		return cropName;
	}

	public void setCropName(String cropName) {
		this.cropName = cropName;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	
	public Receipt(Integer paymentId, Integer dealerId, Integer farmerId, Integer cropId, Double amount,
			LocalDateTime paidAt, String farmerName, String dealerName, String cropName, double quantity) {
		super();
		this.paymentId = paymentId;
		this.dealerId = dealerId;
		this.farmerId = farmerId;
		this.cropId = cropId;
		this.amount = amount;
		this.paidAt = paidAt;
		this.farmerName = farmerName;
		this.dealerName = dealerName;
		this.cropName = cropName;
		this.quantity = quantity;
	}

	public Receipt() {

	}

}
