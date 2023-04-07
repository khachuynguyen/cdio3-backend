package com.banxedap.cdio3.Repository;

import com.banxedap.cdio3.Entities.Carts;
import com.banxedap.cdio3.Entities.CustomPrimaryKey.CartId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Carts, CartId> {
    @Query(value = "select * from carts c where c.user_id = ?1",nativeQuery = true)
    List<Carts> findCartsByUserId(int userId);
    @Query(value = "select * from carts c where c.user_id = ?1 and product_id= ?2",nativeQuery = true)
    Carts findByUserIdAndProductId(int userId, int productId);
}
