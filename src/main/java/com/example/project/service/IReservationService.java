package com.example.project.service;

import com.example.project.models.Reservation ;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

public interface IReservationService {
    Reservation addReservation(Reservation r);
    List<Reservation> addAllReservation(List<Reservation> ls);
    Reservation editReservation(Reservation r);

    List<Reservation> findAllReservations();

    Reservation findByIdReservation(Integer id);
    Reservation ajouterReservationEtAssignerAChambreEtAEtudiant (Reservation r,int numChambre, long cin) ;
    Reservation annulerReservation (long cinEtudiant) ;
    void deleteById(Integer id);
    void deleteReservation(Integer id);
    List<Reservation> listerReservationsEtudiant(Long cinEtudiant) ;
    List<Reservation> filtrerReservationsParDate(Date date);
    List<Reservation> filtrerReservationsParAnneeEtValide(LocalDate annee, Boolean estValide) ;
    Set<Reservation> listerReservationsPourChambre(Long idChamber);

     void deleteReservationn(Integer reservationId);
}