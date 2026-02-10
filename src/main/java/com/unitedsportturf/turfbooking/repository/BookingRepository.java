package com.unitedsportturf.turfbooking.repository;

import com.unitedsportturf.turfbooking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByBookingDateOrderByStartTimeAsc(LocalDate bookingDate);
}
