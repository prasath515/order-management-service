package com.ecart.ordermanagementservice.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecart.ordermanagementservice.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{

	@Query(value = "SELECT * FROM Cart WHERE product_id = ?1", nativeQuery = true)
	Optional<Cart> findByProductId(long product_id);

}
