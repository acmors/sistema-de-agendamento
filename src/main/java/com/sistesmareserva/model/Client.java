package com.sistesmareserva.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client extends UserAccount{

    private String name;

    @Column(nullable = false, unique = true)
    private String cpf;

}
