package com.store.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.store.stock.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

}
