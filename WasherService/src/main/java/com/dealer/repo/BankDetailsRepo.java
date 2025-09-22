package com.dealer.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dealer.model.BankDetails;

public interface BankDetailsRepo extends JpaRepository<BankDetails, Integer> {

}
