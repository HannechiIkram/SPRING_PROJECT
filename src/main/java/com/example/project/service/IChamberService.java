package com.example.project.service;

import com.example.project.models.Chamber;
import com.example.project.models.TypeChamber;


import java.util.List;
import java.util.Set;

public interface IChamberService {
    Chamber findByNumerochamberAndTypeC(int numero , TypeChamber type);
    Chamber addChamber(Chamber c);
    List<Chamber> addAllChambers(List<Chamber> ls);
    Chamber editChamber(Chamber c);
    List<Chamber> findAll() ;
    Chamber findById(long id);
    void deleteByID(long id);
    void delete(Chamber c);

    List<Chamber> getChambresParNomBloc( String nomBloc) ;
   // void listeChambreParBloc();
    void pourcentageChambreParTypeChambre();
    List<Chamber> getChambresNonReserveParNomFoyerEtTypeChambre( String nomFoyer,TypeChamber type) ;
    long nbChambreParTypeEtBloc( TypeChamber type, long idBloc) ;


}
