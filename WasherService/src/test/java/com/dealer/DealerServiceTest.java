package com.dealer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.dealer.feign.CropInterface;
import com.dealer.model.BankDetails;
import com.dealer.model.Booking;
import com.dealer.model.CropWrapper;
import com.dealer.model.Dealer;
import com.dealer.repo.BankDetailsRepo;
import com.dealer.repo.BookingRepo;
import com.dealer.repo.DealerRepo;
import com.dealer.service.DealerService;

public class DealerServiceTest {

	@InjectMocks
	private DealerService dealerService;

	@Mock
	private DealerRepo dealerRepo;

	@Mock
	private BankDetailsRepo bankRepo;

	@Mock
	private BookingRepo bookingRepo;

	@Mock
	private CropInterface dealerInterface;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetAllDealers() {
		List<Dealer> dealers = Arrays.asList(new Dealer(), new Dealer());
		when(dealerRepo.findAll()).thenReturn(dealers);
		ResponseEntity<List<Dealer>> response = dealerService.getAllDealers();
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(2, response.getBody().size());
	}

	@Test
	void testDeleteDealerById() {
		String response = dealerService.deleteDealerById(1);
		verify(dealerRepo, times(1)).deleteById(1);
		assertEquals("Dealer deleted succesfully", response);
	}

	@Test
	void testRegisterDealer() {
		Dealer dealer = new Dealer();
		ResponseEntity<String> response = dealerService.registerDealer(dealer);
		verify(dealerRepo).save(dealer);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	@Test
	void testGetDealerProfileDetailsById() {
		Dealer dealer = new Dealer();
		when(dealerRepo.findById(1)).thenReturn(Optional.of(dealer));
		ResponseEntity<Optional<Dealer>> response = dealerService.getDealerProfileDetailsById(1);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(response.getBody().isPresent());
	}

	@Test
	void testEditDealerProfileFound() {
		Dealer dealer = new Dealer();
		when(dealerRepo.existsById(1)).thenReturn(true);
		ResponseEntity<Map<String, String>> response = dealerService.editDealerProfile(1, dealer);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		verify(dealerRepo).save(dealer);
	}

	@Test
	void testEditDealerProfileNotFound() {
		Dealer dealer = new Dealer();
		when(dealerRepo.existsById(1)).thenReturn(false);
		ResponseEntity<Map<String, String>> response = dealerService.editDealerProfile(1, dealer);
		assertEquals("Dealer not found", response.getBody());
	}

	@Test
	void testAddBankDetailsSuccess() {
		Dealer dealer = new Dealer();
		BankDetails bankDetails = new BankDetails();
		when(dealerRepo.findById(1)).thenReturn(Optional.of(dealer));
		when(bankRepo.save(bankDetails)).thenReturn(bankDetails);

		ResponseEntity<Map<String, String>> response = dealerService.addBankDetails(1, bankDetails);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	void testAddBankDetailsDealerNotFound() {
		when(dealerRepo.findById(1)).thenReturn(Optional.empty());
		ResponseEntity<Map<String, String>> response = dealerService.addBankDetails(1, new BankDetails());
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	void testUpdateBankDetailsSuccess() {
		Dealer dealer = new Dealer();
		BankDetails oldDetails = new BankDetails();
		dealer.setBankDetails(oldDetails);
		BankDetails newDetails = new BankDetails(1, "BOM", "123456", "IFSC123", "upi@bank", "9988776650");

		when(dealerRepo.findById(1)).thenReturn(Optional.of(dealer));

		ResponseEntity<Map<String, String>> response = dealerService.updateBankDetails(1, newDetails);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		verify(bankRepo).save(oldDetails);
	}

	@Test
	void testUpdateBankDetailsNoExistingBank() {
		Dealer dealer = new Dealer();
		dealer.setBankDetails(null);
		when(dealerRepo.findById(1)).thenReturn(Optional.of(dealer));

		ResponseEntity<Map<String, String>> response = dealerService.updateBankDetails(1, new BankDetails());
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	void testBookCropSuccess() {
		when(dealerInterface.bookCropByDealer(1, 100)).thenReturn(new ResponseEntity<>("Booked", HttpStatus.OK));
		ResponseEntity<String> response = dealerService.bookCrop(1, 2, 100);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		verify(bookingRepo).save(any(Booking.class));
	}

	@Test
	void testCancelCropBookingSuccess() {
		Booking booking = new Booking();
		booking.setCropId(1);
		booking.setDealerId(2);
		booking.setQuantity(100);
		when(dealerInterface.cancelCropBooking(1, 100)).thenReturn(new ResponseEntity<>("Cancelled", HttpStatus.OK));
		when(bookingRepo.findByDealerId(2)).thenReturn(Collections.singletonList(booking));

		ResponseEntity<String> response = dealerService.cancelCropBooking(1, 2, 100);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		verify(bookingRepo).delete(booking);
	}

	@Test
	void testGetBookingsByDealer() {
		List<Booking> bookings = Arrays.asList(new Booking(), new Booking());
		when(bookingRepo.findByDealerId(1)).thenReturn(bookings);
		List<Booking> result = dealerService.getBookingsByDealer(1);
		assertEquals(2, result.size());
	}

	@Test
	void testGetBookedCropDetails() {
		Booking booking = new Booking();
		booking.setCropId(101);
		List<Booking> bookings = List.of(booking);
		CropWrapper crop = new CropWrapper();
		when(bookingRepo.findAll()).thenReturn(bookings);
		when(dealerInterface.getCropDetails(101)).thenReturn(ResponseEntity.ok(crop));

		List<CropWrapper> cropDetails = dealerService.getBookedCropDetails();
		assertEquals(1, cropDetails.size());
	}
}
