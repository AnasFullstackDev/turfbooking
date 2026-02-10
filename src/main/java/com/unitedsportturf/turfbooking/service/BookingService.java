package com.unitedsportturf.turfbooking.service;

import com.unitedsportturf.turfbooking.entity.Booking;
import com.unitedsportturf.turfbooking.entity.BookingStatus;
import com.unitedsportturf.turfbooking.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public Booking createBooking(Booking booking) {
        validate(booking);
        return bookingRepository.save(booking);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public List<Booking> getBookingsByDate(LocalDate bookingDate) {
        return bookingRepository.findByBookingDateOrderByStartTimeAsc(bookingDate);
    }

    public Booking updateBookingStatus(Long id, BookingStatus status) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found: " + id));

        booking.setStatus(status);
        return bookingRepository.save(booking);
    }

    private void validate(Booking booking) {
        if (booking.getCustomerName() == null || booking.getCustomerName().isBlank()) {
            throw new IllegalArgumentException("Customer name is required.");
        }
        if (booking.getPhone() == null || booking.getPhone().isBlank()) {
            throw new IllegalArgumentException("Phone is required.");
        }
        if (booking.getGame() == null || booking.getGame().isBlank()) {
            throw new IllegalArgumentException("Game is required.");
        }
        if (booking.getBookingDate() == null) {
            throw new IllegalArgumentException("Booking date is required.");
        }
        if (booking.getStartTime() == null) {
            throw new IllegalArgumentException("Start time is required.");
        }
        if (booking.getDurationInHours() == null || booking.getDurationInHours() <= 0) {
            throw new IllegalArgumentException("Duration must be at least 1 hour.");
        }
    }
}
