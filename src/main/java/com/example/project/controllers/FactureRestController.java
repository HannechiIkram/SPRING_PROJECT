package com.example.project.controllers;

import com.example.project.models.Facture;
import com.example.project.service.IFactureService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
@Slf4j
@RestController
@AllArgsConstructor
@CrossOrigin("*")
@RequestMapping("/facture")
public class FactureRestController {

    private final IFactureService factureService;

    @GetMapping
    public List<Facture> getAllFactures() {
        return factureService.getAllFactures();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Facture> getFactureById(@PathVariable Integer id) {
        Facture facture = factureService.getFactureById(id);
        return facture != null
                ? new ResponseEntity<>(facture, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Facture> addFacture(@RequestBody Facture facture) {
        Facture addedFacture = factureService.addFacture(facture);log.info("he4a num "+facture);
        return new ResponseEntity<>(addedFacture, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Facture> updateFacture(@PathVariable Integer id, @RequestBody Facture updatedFacture) {
        Facture result = factureService.updateFacture(id, updatedFacture);
        return result != null
                ? new ResponseEntity<>(result, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFacture(@PathVariable Integer id) {
        factureService.deleteFacture(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
