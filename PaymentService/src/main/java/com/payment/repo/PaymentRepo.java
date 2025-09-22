package com.payment.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.payment.model.Payment;

public interface PaymentRepo extends JpaRepository<Payment, Integer> {
    List<Payment> findByDealerId(int dealerId);
}