package com.admin.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {
	private int bookingId;
	private int dealerId;
	private int cropId;
	private double quantityBooked;
	private String status;
	private LocalDateTime Date;
//
//	public int getBookingId() {
//		return bookingId;
//	}
//
//	public void setBookingId(int bookingId) {
//		this.bookingId = bookingId;
//	}
//
//	public int getDealerId() {
//		return dealerId;
//	}
//
//	public void setDealerId(int dealerId) {
//		this.dealerId = dealerId;
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
//	public double getQuantityBooked() {
//		return quantityBooked;
//	}
//
//	public void setQuantityBooked(double quantityBooked) {
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
//	public LocalDateTime getDate() {
//		return Date;
//	}
//
//	public void setDate(LocalDateTime date) {
//		Date = date;
//	}
//
//	public BookingDto(int bookingId, int dealerId, int cropId, double quantityBooked, String status,
//			LocalDateTime date) {
//		super();
//		this.bookingId = bookingId;
//		this.dealerId = dealerId;
//		this.cropId = cropId;
//		this.quantityBooked = quantityBooked;
//		this.status = status;
//		Date = date;
//	}
//
//	public BookingDto() {
//
//	}

}
