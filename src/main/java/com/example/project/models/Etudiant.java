package com.example.project.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="Etudiant")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Etudiant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idEtudiant ;

    @Column(name="nomEt")
    private String nomEt ;
    @Column(name="prenomEt")
    private String prenomEt;
    @Column(name="cin")
    private long cin ;
    @Column(name="ecole")
    private String ecole ;
    @Column(name="dateNaissance")
    private LocalDate dateNaissance ;

    @JsonIgnore
    @ManyToMany(mappedBy = "etudiants" , cascade =  CascadeType.ALL)
    private Set<Reservation> resservations = new HashSet<>();

}
