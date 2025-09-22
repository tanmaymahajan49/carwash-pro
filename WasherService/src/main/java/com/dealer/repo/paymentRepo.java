package com.dealer.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dealer.model.PaymentWrapper;

public interface paymentRepo extends JpaRepository<PaymentWrapper, Integer> {

}
