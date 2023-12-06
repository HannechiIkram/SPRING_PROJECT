package com.example.project.service;

import com.example.project.models.Etudiant;

import java.util.List;

public interface IEtudiantService {
    Etudiant addEtudiant(Etudiant e) ;
    List<Etudiant> addAllEtudiant(List<Etudiant> ls);
    Etudiant editEtudiant (Etudiant e);
    List<Etudiant> findAll() ;
    Etudiant findById(long id);
    void deleteById(long id);
    void deleteEtudiant(Etudiant e);

}
