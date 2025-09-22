package com.farmer.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.farmer.model.BankDetails;

public interface BankDetailsRepo extends JpaRepository<BankDetails, Integer> {

}
