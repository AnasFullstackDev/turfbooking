package com.unitedsportturf.turfbooking.service;

import com.unitedsportturf.turfbooking.entity.Booking;
import com.unitedsportturf.turfbooking.entity.BookingStatus;
import com.unitedsportturf.turfbooking.repository.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BookingService bookingService;

    private Booking booking;

    @BeforeEach
    void setUp() {
        booking = new Booking();
        booking.setId(1L);
        booking.setCustomerName("John");
        booking.setPhone("12345");
        booking.setGame("Football");
        booking.setBookingDate(LocalDate.now().plusDays(1));
        booking.setStartTime(LocalTime.of(10, 0));
        booking.setDurationInHours(2);
        booking.setStatus(BookingStatus.PENDING);
    }

    @Test
    void createBookingShouldPersistWhenRequestIsValid() {
        when(bookingRepository.save(booking)).thenReturn(booking);

        Booking savedBooking = bookingService.createBooking(booking);

        assertEquals(booking, savedBooking);
        verify(bookingRepository).save(booking);
    }

    @Test
    void createBookingShouldThrowWhenDurationIsInvalid() {
        booking.setDurationInHours(0);

        assertThrows(IllegalArgumentException.class, () -> bookingService.createBooking(booking));
    }

    @Test
    void updateBookingStatusShouldThrowWhenBookingNotFound() {
        when(bookingRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> bookingService.updateBookingStatus(999L, BookingStatus.CONFIRMED));
    }

    @Test
    void updateBookingStatusShouldUpdateAndPersistStatus() {
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        when(bookingRepository.save(booking)).thenReturn(booking);

        Booking updatedBooking = bookingService.updateBookingStatus(1L, BookingStatus.CONFIRMED);

        assertEquals(BookingStatus.CONFIRMED, updatedBooking.getStatus());
        verify(bookingRepository).save(booking);
    }
}
