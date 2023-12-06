package com.example.project.repository;

import com.example.project.models.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface EtudiantRepository extends JpaRepository<Etudiant,Long> {

    Etudiant findByNomEtAndPrenomEt(String nom , String prenom);
    Etudiant findByCin(long cin);
    List<Etudiant> findByDateNaissance(LocalDate date);


}
