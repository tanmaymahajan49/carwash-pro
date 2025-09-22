package com.farmer.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.farmer.model.Farmer;

@Repository
public interface FarmerRepo extends JpaRepository<Farmer, Integer> {

	@Query("SELECT f FROM Farmer f WHERE f.email = :email")
	Farmer getByEmail(@Param("email") String email);
	
	

}
 