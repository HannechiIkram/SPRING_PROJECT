package com.example.project.models;

import com.example.project.models.Bloc;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import com.example.project.models.Reservation ;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="chamber")
@Builder
public class Chamber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idChamber ;

    @Column(name="numeroChamber" ,unique = true)
    private int numerochamber ;

    @Column(name="TypeC")
    private TypeChamber typeC ;
    @JsonIgnore
    @ManyToOne
    Bloc bloc ;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "chamber")
    private Set<Reservation> reservations = new HashSet<>();

}
