package com.banxedap.cdio3.Repository;

import com.banxedap.cdio3.Entities.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    @Query(value = "select * from order_detail where order_id = ?1", nativeQuery = true)
    List<OrderDetail> findAllByOrderId(int orderId);
}
