package com.danceschool.booking.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "passes")
public class Pass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "pass_type", nullable = false)
    private PassType passType;

    @Column(name = "total_entries")
    private Integer totalEntries; 

    @Column(name = "remaining_entries")
    private Integer remainingEntries;

    @Column(name = "valid_until", nullable = false)
    private LocalDate validUntil;


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public PassType getPassType() { return passType; }
    public void setPassType(PassType passType) { this.passType = passType; }

    public Integer getTotalEntries() { return totalEntries; }
    public void setTotalEntries(Integer totalEntries) { this.totalEntries = totalEntries; }

    public Integer getRemainingEntries() { return remainingEntries; }
    public void setRemainingEntries(Integer remainingEntries) { this.remainingEntries = remainingEntries; }

    public LocalDate getValidUntil() { return validUntil; }
    public void setValidUntil(LocalDate validUntil) { this.validUntil = validUntil; }
}