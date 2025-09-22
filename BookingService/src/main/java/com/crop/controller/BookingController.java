package com.crop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crop.model.Crop;
import com.crop.service.CropService;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    CropService cropService;

    // Car-Wash alias for legacy /booking/allCrops
    @GetMapping("/all")
    public ResponseEntity<List<Crop>> getAll() {
        return cropService.getAllCrops();
    }
}
