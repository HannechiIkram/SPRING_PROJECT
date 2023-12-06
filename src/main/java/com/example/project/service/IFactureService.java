package com.example.project.service;


import com.example.project.models.Facture;

import java.util.List;

public interface IFactureService {

    List<Facture> getAllFactures();

    Facture getFactureById(Integer id);

    Facture addFacture(Facture facture);

    Facture updateFacture(Integer id, Facture updatedFacture);

    void deleteFacture(Integer id);
}
