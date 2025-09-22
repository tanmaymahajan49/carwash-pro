package com.admin.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {

	private int reviewId;
	private int cropId;
	private int dealerId;
	private String comment;
	private int rating; 
	private LocalDateTime reviewAt;

//	public int getReviewId() {
//		return reviewId;
//	}
//
//	public void setReviewId(int reviewId) {
//		this.reviewId = reviewId;
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
//	public String getComment() {
//		return comment;
//	}
//
//	public void setComment(String comment) {
//		this.comment = comment;
//	}
//
//	public int getRating() {
//		return rating;
//	}
//
//	public void setRating(int rating) {
//		this.rating = rating;
//	}
//
//	public LocalDateTime getReviewAt() {
//		return reviewAt;
//	}
//
//	public void setReviewAt(LocalDateTime reviewAt) {
//		this.reviewAt = reviewAt;
//	}
//
//	public ReviewDto(int reviewId, int cropId, int dealerId, String comment, int rating, LocalDateTime reviewAt) {
//		super();
//		this.reviewId = reviewId;
//		this.cropId = cropId;
//		this.dealerId = dealerId;
//		this.comment = comment;
//		this.rating = rating;
//		this.reviewAt = reviewAt;
//	}
//
//	public ReviewDto() {
//
//	}

}
