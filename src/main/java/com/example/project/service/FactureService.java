package com.example.project.service;

import com.example.project.models.Facture;
import com.example.project.repository.FactureRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class FactureService implements IFactureService {

    private final FactureRepository factureRepository;

    @Override
    public List<Facture> getAllFactures() {
        return factureRepository.findAll();
    }

    @Override
    public Facture getFactureById(Integer id) {
        return factureRepository.findById(id).orElse(null);
    }

    @Override
    public Facture addFacture(Facture facture) {
        return factureRepository.save(facture);
    }

    @Override
    public Facture updateFacture(Integer id, Facture updatedFacture) {
        Optional<Facture> existingFacture = factureRepository.findById(id);

        if (existingFacture.isPresent()) {
            updatedFacture.setIdFacture(existingFacture.get().getIdFacture());
            return factureRepository.save(updatedFacture);
        } else {
            return null;
        }
    }

    @Override
    public void deleteFacture(Integer id) {
        factureRepository.deleteById(id);
    }
}
