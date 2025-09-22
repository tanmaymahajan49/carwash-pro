package com.farmer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
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

import com.farmer.feign.CropInterface;
import com.farmer.model.BankDetails;
import com.farmer.model.CropDto;
import com.farmer.model.Farmer;
import com.farmer.repo.BankDetailsRepo;
import com.farmer.repo.FarmerRepo;
import com.farmer.service.FarmerService;

public class FarmerServiceTest {

    @InjectMocks
    private FarmerService farmerService;

    @Mock
    private FarmerRepo farmerRepo;

    @Mock
    private BankDetailsRepo bankRepo;

    @Mock
    private CropInterface farmerInterface;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllFarmers_success() {
        List<Farmer> mockFarmers = Arrays.asList(new Farmer(), new Farmer());
        when(farmerRepo.findAll()).thenReturn(mockFarmers);

        ResponseEntity<List<Farmer>> response = farmerService.getAllFarmers();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void testDeleteFarmerById_success() {
        String response = farmerService.deleteFarmerById(1);
        verify(farmerRepo, times(1)).deleteById(1);
        assertEquals("Farmer deleted succesfully", response);
    }

    @Test
    public void testRegisterFarmer_success() {
        Farmer farmer = new Farmer();
        ResponseEntity<String> response = farmerService.registerFarmer(farmer);
        verify(farmerRepo, times(1)).save(farmer);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

//    @Test
//    public void testGetFarmerProfileDetailsById_found() {
//        Farmer farmer = new Farmer();
//        when(farmerRepo.findById(1)).thenReturn(Optional.of(farmer));
//
//        ResponseEntity<Farmer> response = farmerService.getFarmerProfileDetailsById(1);
//        assertTrue(response.getBody());
//    }

    @Test
    public void testEditFarmerProfile_success() {
        Farmer farmer = new Farmer();
        when(farmerRepo.existsById(1)).thenReturn(true);

        ResponseEntity<Map<String, String>> response = farmerService.editFarmerProfile(1, farmer);
        assertEquals("farmer profile updated successfully", response.getBody());
    }

    @Test
    public void testEditFarmerProfile_notFound() {
        Farmer farmer = new Farmer();
        when(farmerRepo.existsById(1)).thenReturn(false);

        ResponseEntity<Map<String, String>> response = farmerService.editFarmerProfile(1, farmer);
        assertEquals("farmer not found", response.getBody());
    }

    @Test
    public void testAddBankDetails_success() {
        Farmer farmer = new Farmer();
        farmer.setFarmer_id(1);
        BankDetails bank = new BankDetails();

        when(farmerRepo.findById(1)).thenReturn(Optional.of(farmer));
        when(bankRepo.save(bank)).thenReturn(bank);

        ResponseEntity<Map<String, String>> response = farmerService.addBankDetails(1, bank);
        assertEquals("Bank details added successfully", response.getBody());
    }

    @Test
    public void testUpdateBankDetails_noExistingBank() {
        Farmer farmer = new Farmer();
        when(farmerRepo.findById(1)).thenReturn(Optional.of(farmer));

        ResponseEntity<Map<String, String>> response = farmerService.updateBankDetails(1, new BankDetails());
        assertEquals("No existing bank details found.please add bank details", response.getBody());
    }

    @Test
    public void testPublishCrop_success() {
        CropDto cropDto = new CropDto();
        ResponseEntity<String> mockResponse = new ResponseEntity<>("Published", HttpStatus.OK);

        when(farmerInterface.publishCrop(any(CropDto.class))).thenReturn(mockResponse);

        ResponseEntity<String> response = farmerService.publishCrop(1, cropDto);
        assertEquals("Published", response.getBody());
    }

    @Test
    public void testGetCropsByFarmer() {
        List<CropDto> crops = Arrays.asList(new CropDto(), new CropDto());
        when(farmerInterface.getCropsByFarmer(1)).thenReturn(crops);

        ResponseEntity<List<CropDto>> response = farmerService.getCropsByFarmer(1);
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void testDeleteCropByFarmer_success() {
        when(farmerInterface.deleteCrop(10)).thenReturn(ResponseEntity.ok("Deleted"));
        ResponseEntity<String> response = farmerService.deleteCropByFarmer(1, 10);
        assertEquals("Deleted", response.getBody());
    }

    @Test
    public void testGetCropStatusByFarmer() {
        when(farmerInterface.getCropStatus(5)).thenReturn(ResponseEntity.ok("Approved"));
        ResponseEntity<String> response = farmerService.getCropStatusByFarmer(5);
        assertEquals("Approved", response.getBody());
    }
}
