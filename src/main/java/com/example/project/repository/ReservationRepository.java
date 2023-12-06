package com.example.project.repository;

import com.example.project.models.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.project.models.Reservation;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation,Integer> {

    //  List<Reservation> findByEstValide();
    List<Reservation> findByEtudiants(Etudiant etudiant);
    List<Reservation> findByAnneeReservation(Date dateReservation);

    List<Reservation> findByAnneeReservationAndEstValide(LocalDate anneeReservation, Boolean estValide);

    //List<Reservation> findByAnneeReservationAndEstValide(int annee , Boolean valide);
}
