package com.banxedap.cdio3.Repository;

import com.banxedap.cdio3.Entities.Carts;
import com.banxedap.cdio3.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query(value = "select distinct manufacturer from product", nativeQuery = true)
    List<Object> getAllManufacturers();
    @Query(value = "select * from product where manufacturer = ?1", nativeQuery = true)
    List<Product> searchProduct(String manufacturer);
}
