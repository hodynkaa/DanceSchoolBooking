package com.danceschool.booking.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "class_id", nullable = false)
    private DanceClass danceClass;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatus status;

    @Column(name = "reservation_time", updatable = false)
    private LocalDateTime reservationTime;

    @PrePersist
    protected void onCreate() {
        this.reservationTime = LocalDateTime.now();
    }



    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public DanceClass getDanceClass() { return danceClass; }
    public void setDanceClass(DanceClass danceClass) { this.danceClass = danceClass; }

    public ReservationStatus getStatus() { return status; }
    public void setStatus(ReservationStatus status) { this.status = status; }

    public LocalDateTime getReservationTime() { return reservationTime; }
}