package com.payment.model;

public class BookingDto {

	private int dealerId;
	private int cropId;
	private double amount;
	private double quantityBooked;

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getDealerId() {
		return dealerId;
	}

	public void setDealerId(int dealerId) {
		this.dealerId = dealerId;
	}

	public int getCropId() {
		return cropId;
	}

	public void setCropId(int cropId) {
		this.cropId = cropId;
	}

	public double getQuantityBooked() {
		return quantityBooked;
	}

	public void setQuantityBooked(double quantityBooked) {
		this.quantityBooked = quantityBooked;
	}

	public BookingDto() {

	}

}
