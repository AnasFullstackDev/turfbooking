package com.unitedsportturf.turfbooking.repository;

import com.unitedsportturf.turfbooking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
