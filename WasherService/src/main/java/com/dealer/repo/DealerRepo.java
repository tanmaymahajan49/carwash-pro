package com.dealer.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dealer.model.Dealer;

@Repository
public interface DealerRepo extends JpaRepository<Dealer, Integer> {
	Dealer findByEmail(String email);

}
