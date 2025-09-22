package com.dealer.service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dealer.feign.CropInterface;
import com.dealer.feign.PaymentInterface;
import com.dealer.model.BankDetails;
import com.dealer.model.Booking;
import com.dealer.model.CropWrapper;
import com.dealer.model.Dealer;
import com.dealer.model.PaymentWrapper;
import com.dealer.model.RatingsAndReviewsDTO;
import com.dealer.repo.BankDetailsRepo;
import com.dealer.repo.BookingRepo;
import com.dealer.repo.DealerRepo;
import com.dealer.repo.paymentRepo;

import feign.FeignException;
@Service
public class DealerService {

	@Autowired
	DealerRepo dealerRepo;

	@Autowired
	BankDetailsRepo bankRepo;

	@Autowired
	CropInterface cropInterface;

	@Autowired
	PaymentInterface paymentInterface;

	@Autowired
	BookingRepo bookingRepo;

	@Autowired
	paymentRepo paymentRepo;

	public ResponseEntity<List<Dealer>> getAllDealers() {
		try {
			return new ResponseEntity<>(dealerRepo.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
	}

	public String deleteDealerById(int id) {
		dealerRepo.deleteById(id);
		return "Dealer deleted succesfully";
	}

	public ResponseEntity<String> registerDealer(Dealer dealer) {
		dealerRepo.save(dealer);
		return new ResponseEntity<>(dealer.getFirst_name() + " you have registered successfully as a Dealer.",
				HttpStatus.CREATED);
	}

	public ResponseEntity<Optional<Dealer>> getDealerProfileDetailsById(int id) {
		try {
			Optional<Dealer> dealer = dealerRepo.findById(id);
			return new ResponseEntity<>(dealer, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<Map<String, String>> editDealerProfile(int id, Dealer dealer) {
		if (dealerRepo.existsById(id)) {
			dealer.setDealer_id(id);
			dealerRepo.save(dealer);
			return new ResponseEntity<>(Map.of("message",dealer.getFirst_name() + " your profile has updated successfully."),
					HttpStatus.OK);
		}
		return new ResponseEntity<>(Map.of("message","Dealer not found"), HttpStatus.CREATED);
	}

	public ResponseEntity<Map<String, String>> addBankDetails(int dealerId, BankDetails bankDetails) {
		Optional<Dealer> optionalDealer = dealerRepo.findById(dealerId);

		if (optionalDealer.isPresent()) {
			Dealer dealer = optionalDealer.get();

			BankDetails savedBank = bankRepo.save(bankDetails);

			dealer.setBankDetails(savedBank);
			dealerRepo.save(dealer);

			return new ResponseEntity<>(Map.of("message","Bank details added successfully"), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(Map.of("message","Dealer not found"), HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<Map<String, String>> updateBankDetails(int dealerId, BankDetails newDetails) {
		Optional<Dealer> optionalDealer = dealerRepo.findById(dealerId);

		if (optionalDealer.isPresent()) {
			Dealer dealer = optionalDealer.get();

			BankDetails existing = dealer.getBankDetails();

			if (existing == null) {
				return new ResponseEntity<>(Map.of("message","No existing bank details found.please add bank details"),
						HttpStatus.NOT_FOUND);
			}

			existing.setBankName(newDetails.getBankName());
			existing.setAccountNumber(newDetails.getAccountNumber());
			existing.setIfscCode(newDetails.getIfscCode());
			existing.setUpiId(newDetails.getUpiId());
			existing.setUpiNumber(newDetails.getUpiNumber());

			bankRepo.save(existing);

			return new ResponseEntity<>(Map.of("message","Bank details updated successfully"), HttpStatus.OK);
		}
		return new ResponseEntity<>(Map.of("message","Dealer not found"), HttpStatus.NOT_FOUND);
	}

	public ResponseEntity<List<CropWrapper>> getAllCrops() {
		return ResponseEntity.ok(cropInterface.getAllCrops()).getBody();
	}

	public ResponseEntity<String> bookCrop(int cropId, int dealerId, double quantity) {
		try {
			cropInterface.bookCropByDealer(cropId, quantity);

			Booking booking = new Booking();
			booking.setCropId(cropId);
			booking.setDealerId(dealerId);
			booking.setQuantity(quantity);

			Double pricePerKg = cropInterface.getCropDetails(cropId).getBody().getPricePerKg();
			booking.setPrice(quantity * pricePerKg);

			bookingRepo.save(booking);
			return new ResponseEntity<>(
					quantity + " KG Booking successful and recorded.\nTotal cost is " + (quantity * pricePerKg),
					HttpStatus.OK);

		} catch (FeignException.BadRequest ex) {
			return new ResponseEntity<>(ex.contentUTF8(), HttpStatus.BAD_REQUEST);
		} catch (FeignException ex) {
			return new ResponseEntity<>("Crop service error: " + ex.status(), HttpStatus.SERVICE_UNAVAILABLE);
		} catch (Exception ex) {
			return new ResponseEntity<>("Internal server error. " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<String> cancelCropBooking(int cropId, int dealerId, double quantity) {
		ResponseEntity<String> cropResponse = cropInterface.cancelCropBooking(cropId, quantity);
		Double pricePerKg = cropInterface.getCropDetails(cropId).getBody().getPricePerKg();
//	    System.out.println(cropInterface.getCropDetails(cropId).getBody().getPricePerKg());
		Booking booking = new Booking();
		if (cropResponse.getStatusCode().is2xxSuccessful()) {
			booking.setCropId(cropId);
			booking.setDealerId(dealerId);
			booking.setQuantity(booking.getQuantity() + quantity);
			booking.setCancelled(true);
			booking.setPrice(quantity * pricePerKg);
			bookingRepo.save(booking);
			return new ResponseEntity<>("Booking cancelled: " + quantity + " KG returned", HttpStatus.OK);
		}
		return cropResponse;
	}

	public List<Booking> getBookingsByDealer(int dealerId) {
		return bookingRepo.findByDealerId(dealerId);
	}

	public List<CropWrapper> getBookedCropDetails() {
		List<Booking> bookings = bookingRepo.findAll();
		return bookings.stream().map(b -> cropInterface.getCropDetails(b.getCropId()).getBody())
				.collect(Collectors.toList());
	}

	public ResponseEntity<String> pay(PaymentWrapper wrapper) {
		PaymentWrapper payment = new PaymentWrapper();
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
		System.out.println(wrapper.getCropId() + " " + wrapper.getDealerId());
		payment.setDealerId(wrapper.getDealerId());
		payment.setCropId(wrapper.getCropId());
		payment.setAmount(wrapper.getAmount());
		paymentRepo.save(payment);

		boolean cropMatch = false;
		boolean amountMatch = false;
		boolean dealerMatch = false;

		List<Booking> bookinglist = bookingRepo.findAll();
		for (Booking b : bookinglist) {
			System.out.println("Wrapper Crop ID: " + wrapper.getCropId() + ", Booking Crop ID: " + b.getCropId());
			System.out.println("Wrapper Amount: " + wrapper.getAmount() + ", Booking Price: " + b.getPrice());
			System.out
					.println("Wrapper Dealer ID: " + wrapper.getDealerId() + ", Booking Dealer ID: " + b.getDealerId());

			cropMatch = wrapper.getCropId() == b.getCropId();
			amountMatch = wrapper.getAmount() == b.getPrice();
			dealerMatch = wrapper.getDealerId() == b.getDealerId();

			System.out.println("Crop ID Match: " + cropMatch);
			System.out.println("Amount Match: " + amountMatch);
			System.out.println("Dealer ID Match: " + dealerMatch);

			if (cropMatch && amountMatch && dealerMatch) {
				System.out.println("Saving new payment");
				ResponseEntity<PaymentWrapper> response = paymentInterface.pay(payment);

				if (response.getStatusCode().is2xxSuccessful()) {
					String receipt = String.format(
							"!!! Payment Successful !!!\n" + "----- Payment Receipt -----\n" + "Dealer ID   : %d\n"
									+ "Crop ID     : %d\n" + "Amount Paid : %.2f\n" + "Booking ID  : %d\n"
									+ "Booking Date: %s\n" + "Booking Time: %s\n" + "----------------------------\n"
									+ "Thank you for your payment!",
							b.getDealerId(), b.getCropId(), b.getPrice(), b.getId(), b.getTime().toLocalDate(),
							b.getTime().toLocalTime().format(timeFormatter));
					return ResponseEntity.ok(receipt);
				}
			}
		}

		StringBuilder msg = new StringBuilder();

		if (cropMatch == false) {
			msg.append("Crop ID does not mach.\nPlease enter correct Crop ID\n");
		}
		if (amountMatch == false) {
			msg.append("Amount does not match.\nPlease enter correct amount\n");
		}
		if (dealerMatch == false) {
			msg.append("Dealer ID does not mach.\nPlease enter correct Dealer ID\n");
		}

//		if(cropMatch == false) return ResponseEntity.status(500).body("Crop ID does not mach.\nPlease enter correct Crop ID");
//		if(amountMatch == false) return ResponseEntity.status(500).body("Amount does not match.\nPlease enter correct amount");
//		if(dealerMatch == false) return ResponseEntity.status(500).body("Dealer ID does not mach.\nPlease enter correct Dealer ID");

		return ResponseEntity.status(500).body(msg.toString());
//		return ResponseEntity.status(500).body("No valid booking found for payment details.");
	}

	public ResponseEntity<ByteArrayResource> exportDealerReport() {
		try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("Dealers");

			Row header = sheet.createRow(0);
			header.createCell(0).setCellValue("Dealer ID");
			header.createCell(1).setCellValue("Name");
			header.createCell(2).setCellValue("Email");
			header.createCell(3).setCellValue("Phone");
			header.createCell(4).setCellValue("District");
			header.createCell(5).setCellValue("Total Bookings");
			header.createCell(6).setCellValue("Total Quantity");
			header.createCell(7).setCellValue("Total Amount");

			List<Dealer> dealers = dealerRepo.findAll();

			int rowNum = 1;
			for (Dealer dealer : dealers) {
				List<Booking> bookings = bookingRepo.findByDealerIdAndIsCancelledFalse(dealer.getDealer_id());
				double totalQty = bookings.stream().mapToDouble(Booking::getQuantity).sum();
				double totalAmt = bookings.stream().mapToDouble(Booking::getPrice).sum();

				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(dealer.getDealer_id());
				row.createCell(1).setCellValue(dealer.getFirst_name() + " " + dealer.getLast_name());
				row.createCell(2).setCellValue(dealer.getEmail());
				row.createCell(3).setCellValue(dealer.getPhone_no());
				row.createCell(4).setCellValue(dealer.getDistrict());
				row.createCell(5).setCellValue(bookings.size());
				row.createCell(6).setCellValue(totalQty);
				row.createCell(7).setCellValue(totalAmt);
			}

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			workbook.write(out);

			ByteArrayResource resource = new ByteArrayResource(out.toByteArray());
			HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=dealer_report.xlsx");

			return ResponseEntity.ok().headers(headers).contentLength(resource.contentLength()).body(resource);

		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}

	public ResponseEntity<String> getCropStatus(int cropId) {

		return cropInterface.getCropStatus(cropId);
	}

	public ResponseEntity<List<CropWrapper>> getAvailableCrops() {

		return cropInterface.getAvailableCrops();
	}

	public ResponseEntity<List<CropWrapper>> getFullyBookedCrops() {

		return cropInterface.getFullyBookedCrops();
	}

	public ResponseEntity<List<RatingsAndReviewsDTO>> allReviews() {

		return cropInterface.allReviews();
	}

	public ResponseEntity<List<RatingsAndReviewsDTO>> allReviewsByCropId(int crop_id) {
		return cropInterface.allReviewsByCropId(crop_id);
	}

	public ResponseEntity<String> getAverage(int crop_id) {
		return cropInterface.getAverage(crop_id);
	}

	public ResponseEntity<Dealer> getDealerByEmail(String email) {
		Dealer dealer = dealerRepo.findByEmail(email);
		if (dealer != null) {
			return new ResponseEntity<>(dealer, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
