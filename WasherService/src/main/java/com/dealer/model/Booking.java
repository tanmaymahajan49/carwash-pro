package com.dealer.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int cropId;
	private int dealerId;
	private double quantity;
	private LocalDateTime time = LocalDateTime.now();
	private boolean isCancelled = false;
	private double price;
	
}

//	public int getId() {
//		return id;
//	}
//
//	public void setId(int id) {
//		this.id = id;
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
//	public int getDealerId() {
//		return dealerId;
//	}
//
//	public void setDealerId(int dealerId) {
//		this.dealerId = dealerId;
//	}
//
//	public double getQuantity() {
//		return quantity;
//	}
//
//	public void setQuantity(double quantity) {
//		this.quantity = quantity;
//	}
//
//	public LocalDateTime getTime() {
//		return time;
//	}
//
//	public void setTime(LocalDateTime time) {
//		this.time = time;
//	}
//
//	public boolean isCancelled() {
//		return isCancelled;
//	}
//
//	public void setCancelled(boolean isCancelled) {
//		this.isCancelled = isCancelled;
//	}
//
//	public double getPrice() {
//		return price;
//	}
//
//	public void setPrice(double price) {
//		this.price = price;
//	}
//
//	public Booking(int id, int cropId, int dealerId, double quantity, LocalDateTime time, boolean isCancelled,
//			double price) {
//		super();
//		this.id = id;
//		this.cropId = cropId;
//		this.dealerId = dealerId;
//		this.quantity = quantity;
//		this.time = time;
//		this.isCancelled = isCancelled;
//		this.price = price;
//	}
//
//	public Booking() {
//		super();
//	}

