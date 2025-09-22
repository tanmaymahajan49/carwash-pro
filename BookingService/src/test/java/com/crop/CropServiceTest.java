//package com.crop;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//import java.util.*;
//
//import com.crop.model.Crop;
//import com.crop.repo.CropRepo;
//import com.crop.service.CropService;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.*;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//class CropServiceTest {
//
//	@InjectMocks
//	private CropService cropService;
//
//	@Mock
//	private CropRepo cropRepo;
//
//	private Crop mockCrop;
//
//	@BeforeEach
//	void setUp() {
//		MockitoAnnotations.openMocks(this);
//		mockCrop = new Crop();
//		mockCrop.setCropId(1);
//		mockCrop.setQuantityAvailable(100.0);
//		mockCrop.setQuantityBooked(0.0);
//		mockCrop.setStatus("Available");
//		mockCrop.setFarmerId(10);
//	}
//
//	@Test
//	void testGetAllCrops() {
//		when(cropRepo.findAll()).thenReturn(List.of(mockCrop));
//		ResponseEntity<List<Crop>> response = cropService.getAllCrops();
//		assertEquals(HttpStatus.OK, response.getStatusCode());
//		assertFalse(response.getBody().isEmpty());
//	}
//
//	@Test
//	void testGetCropDetailsByIdFound() {
//		when(cropRepo.findById(1)).thenReturn(Optional.of(mockCrop));
//		ResponseEntity<Optional<Crop>> response = cropService.getCropDetailsById(1);
//		assertTrue(response.getBody().isPresent());
//	}
//
//	@Test
//	void testPublishCrop() {
//		when(cropRepo.save(mockCrop)).thenReturn(mockCrop);
//		ResponseEntity<String> response = cropService.publishCrop(mockCrop);
//		assertEquals("Crop registered successfully", response.getBody());
//	}
//
//	@Test
//	void testEditCropSuccess() {
//		when(cropRepo.existsById(1)).thenReturn(true);
//		when(cropRepo.save(any(Crop.class))).thenReturn(mockCrop);
//		ResponseEntity<String> response = cropService.editCrop(1, mockCrop);
//		assertEquals(HttpStatus.OK, response.getStatusCode());
//	}
//
//	@Test
//	void testEditCropNotFound() {
//		when(cropRepo.existsById(1)).thenReturn(false);
//		ResponseEntity<String> response = cropService.editCrop(1, mockCrop);
//		assertEquals("Crop not found", response.getBody());
//	}
//
//	@Test
//	void testDeleteCropSuccess() {
//		when(cropRepo.existsById(1)).thenReturn(true);
//		ResponseEntity<String> response = cropService.deleteCropById(1);
//		assertEquals("Crop deleted successfully", response.getBody());
//	}
//
//	@Test
//	void testDeleteCropNotFound() {
//		when(cropRepo.existsById(1)).thenReturn(false);
//		ResponseEntity<String> response = cropService.deleteCropById(1);
//		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//	}
//
//	@Test
//	void testBookCropSuccess() {
//		when(cropRepo.findById(1)).thenReturn(Optional.of(mockCrop));
//		ResponseEntity<String> response = cropService.bookCropByDealer(1, 10.0);
//		assertEquals("Crop booked successfully", response.getBody());
//		verify(cropRepo).save(any(Crop.class));
//	}
//
//	@Test
//	void testBookCropInsufficientQuantity() {
//		mockCrop.setQuantityAvailable(5.0);
//		when(cropRepo.findById(1)).thenReturn(Optional.of(mockCrop));
//		ResponseEntity<String> response = cropService.bookCropByDealer(1, 10.0);
//		assertEquals("Insufficient quantity", response.getBody());
//	}
//
//	@Test
//	void testCancelCropBooking() {
//		when(cropRepo.findById(1)).thenReturn(Optional.of(mockCrop));
//		ResponseEntity<String> response = cropService.cancelCropBooking(1, 5.0);
//		assertTrue(response.getBody().contains("Booking cancelled"));
//	}
//
//	@Test
//	void testGetCropsByFarmer() {
//		when(cropRepo.findByFarmerId(10)).thenReturn(List.of(mockCrop));
//		ResponseEntity<List<Crop>> response = cropService.getCropsByFarmer(10);
//		assertEquals(1, response.getBody().size());
//	}
//
//	@Test
//	void testGetAvailableCrops() {
//		when(cropRepo.findByQuantityAvailableGreaterThan(0.0)).thenReturn(List.of(mockCrop));
//		ResponseEntity<List<Crop>> response = cropService.getAvailableCrops();
//		assertEquals(HttpStatus.OK, response.getStatusCode());
//	}
//
//	@Test
//	void testGetCropStatus() {
//		when(cropRepo.findById(1)).thenReturn(Optional.of(mockCrop));
//		ResponseEntity<String> response = cropService.getCropStatus(1);
//		assertTrue(response.getBody().contains("Status"));
//	}
//}
