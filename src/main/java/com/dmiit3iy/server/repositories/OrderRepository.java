package com.dmiit3iy.server.repositories;

import com.dmiit3iy.server.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
}
