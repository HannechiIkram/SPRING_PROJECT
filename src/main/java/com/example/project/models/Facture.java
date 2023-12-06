package com.example.project.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="Facture")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Facture  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer idFacture ;

    private String description;

    private boolean paid;

    private Integer prix;

    private int quantity;

    private String customerName;
}
