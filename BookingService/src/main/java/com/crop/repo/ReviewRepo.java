package com.crop.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.crop.model.RatingsAndReviews;

//@Repository
//public interface ReviewRepo extends JpaRepository<RatingsAndReviews, Integer> {
//
//	@Query(value = "SELECT * FROM ratings_and_reviews r WHERE r.crop_id = :crop_id", nativeQuery = true)
//	List<RatingsAndReviews> findAllByCropId(int crop_id);
//	
//	@Query(value = "SELECT * FROM ratings_and_reviews r WHERE r.crop_name = :crop_name", nativeQuery = true)
//	List<RatingsAndReviews> findAllByCropName(String crop_name);
//
//	@Query(value = "SELECT * FROM ratings_and_reviews r WHERE r.dealer_id = :dealer_id", nativeQuery = true)
//	List<RatingsAndReviews> findByDealerId(int dealer_id);
//
//
//}

//@Repository
//public interface ReviewRepo extends JpaRepository<RatingsAndReviews, Integer> {
//	List<RatingsAndReviews> findAllByCropId(int cropId);
//	List<RatingsAndReviews> findAllByCropName(String cropName);
//	List<RatingsAndReviews> findByDealerId(int dealerId);
//}

@Repository
public interface ReviewRepo extends JpaRepository<RatingsAndReviews, Integer> {

	@Query(value = "SELECT * FROM ratings_and_reviews r WHERE r.crop_id = :crop_id", nativeQuery = true)
	List<RatingsAndReviews> findAllByCropId(@Param("crop_id") int crop_id);

	@Query(value = "SELECT * FROM ratings_and_reviews r WHERE r.crop_name = :crop_name", nativeQuery = true)
	List<RatingsAndReviews> findAllByCropName(@Param("crop_name") String crop_name);

	@Query(value = "SELECT * FROM ratings_and_reviews r WHERE r.dealer_id = :dealer_id", nativeQuery = true)
	List<RatingsAndReviews> findByDealerId(@Param("dealer_id") int dealer_id);
}

