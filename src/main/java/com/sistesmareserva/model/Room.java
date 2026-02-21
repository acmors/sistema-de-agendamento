package com.sistesmareserva.model;

import com.sistesmareserva.model.enums.Status;
import com.sistesmareserva.model.enums.Type;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number", nullable = false, unique = true)
    private Integer number;

    @Column(name = "type", nullable = false)
    private Type type;

    @Column(name = "pricePerDay", nullable = false)
    private BigDecimal pricePerDay;

    @Column(name = "status", nullable = false)
    private Status status;

    @OneToMany(mappedBy = "room")
    private List<Reservation> reservations;
}
