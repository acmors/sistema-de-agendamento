package com.sistesmareserva.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sistesmareserva.model.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "checkin" ,nullable = false)
    private LocalDate checkinDate;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "checkout" ,nullable = false)
    private LocalDate checkoutDate;

    @Column(name = "check-in")
    private BigDecimal totalValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "reservationStatus")
    private ReservationStatus reservationStatus;


    @ManyToOne
    @JoinColumn(name = "client_id")
    @JsonIgnore
    private Client client;

    @ManyToOne
    @JoinColumn(name = "room_id")
    @JsonIgnore
    private Room room;

    @OneToOne(mappedBy = "reservation")
    private Payment payment;
}
