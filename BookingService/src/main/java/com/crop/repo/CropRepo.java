package com.crop.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crop.model.Crop;

@Repository
public interface CropRepo extends JpaRepository<Crop, Integer> {
	
	List<Crop> findByFarmerId(int farmerId);

	List<Crop> findByQuantityAvailableGreaterThan(double quantity);

	List<Crop> findByQuantityAvailableEquals(double quantity);

	List<Crop> findByCropName(String name);

}
