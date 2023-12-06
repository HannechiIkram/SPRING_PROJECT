package com.example.project.controllers;


import com.example.project.models.Bloc;
import com.example.project.services.IBlocService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600,allowCredentials="true")
@RequestMapping("/api/blocs")
public class BlocRestController {
    IBlocService iBlocService;

    @GetMapping("/findAll")
    List<Bloc> findAll(){
        return iBlocService.findAll();
    }

    @PostMapping("/addBloc")
    Bloc addBloc(@RequestBody Bloc b){
        return iBlocService.addBloc(b);
    }

    @GetMapping("findById/{id}")
    Bloc findById(@PathVariable("id") long id){
        return iBlocService.findById(id);
    }

    @DeleteMapping("deleteByID/{id}")
    void deleteByID(@PathVariable("id") long id){
        iBlocService.deleteById(id);
    }

    @DeleteMapping("deleteB")
    void delete(@RequestBody Bloc b){
        iBlocService.delete(b);
    }

    @PutMapping("editBloc")
    Bloc editBloc(@RequestBody Bloc b){
        return iBlocService.editBloc(b);
    }

    @GetMapping("/blocs/search")
    public List<Bloc> searchByNomBloc(@RequestParam("nomBloc") String nomBloc) {
        return iBlocService.findByNomBloc(nomBloc);
    }

    @GetMapping("/blocs/findByCapaciteBloc")
    public List<Bloc> findByCapaciteBloc(@RequestParam("capaciteBloc") int capaciteBloc) {
        return iBlocService.findByCapaciteBloc(capaciteBloc);
    }

    @GetMapping("/blocs/searchByNomBlocAndCapaciteBloc")
    public List<Bloc> searchByNomBlocAndCapaciteBloc(
            @RequestParam("nomBloc") String nomBloc,
            @RequestParam("capaciteBloc") int capaciteBloc) {
        return iBlocService.findByNomBlocAndCapaciteBloc(nomBloc, capaciteBloc);
    }

    @GetMapping("/blocs/searchByNomBlocIgnoreCase")
    public List<Bloc> searchByNomBlocIgnoreCase(@RequestParam("nomBloc") String nomBloc) {
        return iBlocService.findByNomBlocIgnoreCase(nomBloc);
    }


    @GetMapping("/blocs/searchByCapaciteBlocGreaterThan")
    public List<Bloc> searchByCapaciteBlocGreaterThan(@RequestParam("capaciteBloc") int capaciteBloc) {
        return iBlocService.findByCapaciteBlocGreaterThan(capaciteBloc);
    }

    @GetMapping("/blocs/searchByNomBlocContaining")
    public List<Bloc> searchByNomBlocContaining(@RequestParam("subString") String subString) {
        return iBlocService.findByNomBlocContaining(subString);
    }

    @GetMapping("/blocs/findAllByOrderByNomBlocAsc")
    public List<Bloc> findAllByOrderByNomBlocAsc() {
        return iBlocService.findAllByOrderByNomBlocAsc();
    }

    @GetMapping("/blocs/searchByNomBlocOrCapaciteBloc")
    public List<Bloc> searchByNomBlocOrCapaciteBloc(
            @RequestParam("nomBloc") String nomBloc,
            @RequestParam("capaciteBloc") int capaciteBloc) {
        return iBlocService.findByNomBlocOrCapaciteBloc(nomBloc, capaciteBloc);
    }

    @PutMapping("/affecterChambresABloc/{nomBloc}/{numChambres}")
    public Bloc affecterChambresABloc(
            @PathVariable String nomBloc,
            @PathVariable List<String> numChambres
    ) {
        List<Long> chambreIds = numChambres.stream()
                .map(Long::parseLong)
                .collect(Collectors.toList());

        return iBlocService.affecterChambresABloc(chambreIds, nomBloc);
    }








}
