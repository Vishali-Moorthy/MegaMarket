package com.store.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.store.stock.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{

}
