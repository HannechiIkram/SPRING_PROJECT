package com.example.project.controllers;

import com.example.project.models.Chamber;
import com.example.project.models.TypeChamber;
import com.example.project.service.IChamberService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@CrossOrigin("*")

@AllArgsConstructor
public class ChamberRestController {
    IChamberService iChamberService ;
    @GetMapping("findchamberByNumero/{numero}/{type}")
    Chamber findNumeroType(@PathVariable("numero") int num , @PathVariable("type") TypeChamber type){
        return iChamberService.findByNumerochamberAndTypeC(num , type);
    }

    @GetMapping("findAllChambers")
    List<Chamber> findAll(){
        return iChamberService.findAll();
    }



    @GetMapping("findChamberByID/{id}")
    Chamber findChamberByID(@PathVariable("id") long id){
        return iChamberService.findById(id);
    }
    @PostMapping("addChamber")
    Chamber addChamber(@RequestBody Chamber c){
        return iChamberService.addChamber(c);
    }

    @PostMapping("addAllChambers")
    List<Chamber> AddAllChambers(@RequestBody List<Chamber> ls){
        return iChamberService.addAllChambers(ls);
    }
    @PutMapping("updateChamber")
    Chamber editChamber(@RequestBody Chamber c){
        return iChamberService.editChamber(c);
    }

    @DeleteMapping("deleteChamberById/{id}")
    void DeleteChamberByID(@PathVariable("id") long id){
        iChamberService.deleteByID(id);
    }

    @DeleteMapping("deleteChamber")
    void DeleteChmber(@RequestBody Chamber c){
        iChamberService.delete(c);
    }

    @GetMapping("getChamberList/{nomBloc}")
    List<Chamber> getChambresParNomBloc(@PathVariable("nomBloc") String nomBloc){
        return iChamberService.getChambresParNomBloc(nomBloc);
    }
    @GetMapping("chamberListNonReserver/{type}/{nomFoyer}")
    List<Chamber> getChambresNonReserveParNomFoyerEtTypeChambre(@PathVariable("type") TypeChamber type , String nomFoyer)
    {
        return iChamberService.getChambresNonReserveParNomFoyerEtTypeChambre(nomFoyer , type);
    }

    @GetMapping("nbChambreParTypeEtBloc/{type}/{idBloc}")
    long nbChambreParTypeEtBloc(@PathVariable("type") TypeChamber type , @PathVariable("idBloc") long idBloc){
        return iChamberService.nbChambreParTypeEtBloc(type , idBloc);
    }
}
