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

    private int number;

    private Type type;

    private BigDecimal pricePerDay;

    private Status status;

    @OneToMany(mappedBy = "room")
    private List<Reservation> reservations;
}
