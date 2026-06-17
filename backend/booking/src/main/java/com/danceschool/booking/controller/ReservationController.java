package com.danceschool.booking.controller;

import com.danceschool.booking.entity.*;
import com.danceschool.booking.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
@CrossOrigin(origins = "*")
public class ReservationController {

    private final ReservationRepository reservationRepository;
    private final DanceClassRepository danceClassRepository;
    private final UserRepository userRepository; // Репозиторий пользователей добавлен для "оживления" объекта

    public ReservationController(ReservationRepository reservationRepository, 
                                 DanceClassRepository danceClassRepository,
                                 UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.danceClassRepository = danceClassRepository;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<?> createReservation(@RequestBody Reservation reservation) {
        // "Оживляем" объекты из базы данных по их ID, чтобы избежать ошибок NullPointerException
        User user = userRepository.findById(reservation.getUser().getId())
                .orElseThrow(() -> new RuntimeException("Nie znaleziono użytkownika"));
        
        DanceClass danceClass = danceClassRepository.findById(reservation.getDanceClass().getId())
                .orElseThrow(() -> new RuntimeException("Nie znaleziono zajęć"));

        // Логика проверки наличия мест
        if (danceClass.getMaxCapacity() > 0) {
            danceClass.setMaxCapacity(danceClass.getMaxCapacity() - 1);
            danceClassRepository.save(danceClass); 
            
            // Привязываем полноценные объекты к резервации перед сохранением
            reservation.setUser(user);
            reservation.setDanceClass(danceClass);
            reservation.setStatus(ReservationStatus.CONFIRMED);
            
            return ResponseEntity.ok(reservationRepository.save(reservation));
        } else {
            return ResponseEntity.badRequest().body("Przepraszamy, brak wolnych miejsc!");
        }
    }
}