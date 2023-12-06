package com.example.project.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Reservation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer idReservation;

    @Column(name="anneeReservation")
    private LocalDate anneeReservation ;

    @Column(name="estValide")
    private Boolean estValide ;
    @JsonIgnoreProperties("resservations")
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},fetch = FetchType.EAGER)
    public Set<Etudiant> etudiants = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("reservations")
    @JoinColumn(name = "chamber_id")
    private Chamber chamber;
}