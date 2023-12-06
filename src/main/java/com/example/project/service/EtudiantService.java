package com.example.project.service;

import com.example.project.models.Etudiant;
import com.example.project.repository.EtudiantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
@AllArgsConstructor
@Service
public class EtudiantService implements IEtudiantService{
    EtudiantRepository etudiantRepository ;
    @Override
    public Etudiant addEtudiant(Etudiant e) {
        return etudiantRepository.save(e);
    }

    @Override
    public List<Etudiant> addAllEtudiant(List<Etudiant> ls) {
        return etudiantRepository.saveAll(ls);
    }

    @Override
    public Etudiant editEtudiant(Etudiant e) {
        return etudiantRepository.save(e);
    }

    @Override
    public List<Etudiant> findAll() {
        return etudiantRepository.findAll();
    }

    @Override
    public Etudiant findById(long id) {
        return etudiantRepository.findById(id).orElse(Etudiant.builder().idEtudiant(0).nomEt("nuller").build());
    }

    @Override
    public void deleteById(long id) {
        etudiantRepository.deleteById(id);
    }

    @Override
    public void deleteEtudiant(Etudiant e) {
        etudiantRepository.delete(e);
    }
}
