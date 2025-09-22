package com.dealer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dealer.model.Dealer;
import com.dealer.service.DealerService;

@RestController
@RequestMapping("/washer")
public class WasherController {

    @Autowired
    DealerService dealerService;

    // Car-Wash alias for legacy /washer/allDealers
    @GetMapping("/all")
    public ResponseEntity<List<Dealer>> getAllWashers() {
        return dealerService.getAllDealers();
    }
}
