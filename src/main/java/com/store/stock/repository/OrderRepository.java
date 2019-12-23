package com.store.stock.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.store.stock.entity.Order;
import com.store.stock.entity.User;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

	List<Order> findAllByUser(User user);

	Optional<Order> findByOtpValue(Integer otpValue);

}
