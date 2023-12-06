package com.example.project.services;

import com.example.project.BlocRepository;
import com.example.project.models.Bloc;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Builder
@Service
@Slf4j
@AllArgsConstructor
public class BlocService implements IBlocService {

    BlocRepository blocRepository;


    @Override
    public Bloc addBloc(Bloc b) {

        b = blocRepository.save(b);

        return b; //on ajoute une ligne
    }

    @Override
    public List<Bloc> addBlocs(List<Bloc> blocs) {
        return blocRepository.saveAll(blocs); //on ajoute Des lignes dans la BD
    }

    @Override
    public Bloc editBloc(Bloc b) {
        return blocRepository.save(b); //pour faire la modification
    }



    @Override
    public List<Bloc> findAll() {
        return blocRepository.findAll();
    }

    @Override
    public Bloc findById(long id) {
        // return blocRepository.findById(id).get();
        //ou bien
        //ou bien
        return blocRepository.findById(id).orElse(Bloc.builder().idBloc(0).nomBloc("pas de bloc").build());


    }

    @Override
    public void deleteById(long id) {
        blocRepository.deleteById(id);

    }

    @Override
    public void delete(Bloc b) {
        blocRepository.delete(b);

    }

    @Override
    public List<Bloc> findByNomBloc(String nomBloc) {
        return blocRepository.findByNomBloc(nomBloc);
    }

    @Override
    public List<Bloc> findByCapaciteBloc(int capaciteBloc) {
        return blocRepository.findByCapaciteBloc(capaciteBloc);
    }

    @Override
    public List<Bloc> findByNomBlocAndCapaciteBloc(String nomBloc, int capaciteBloc) {
        return blocRepository.findByNomBlocAndCapaciteBloc(nomBloc, capaciteBloc);
    }

    @Override
    public List<Bloc> findByNomBlocIgnoreCase(String nomBloc) {
        return blocRepository.findByNomBlocIgnoreCase(nomBloc);
    }

    @Override
    public List<Bloc> findByCapaciteBlocGreaterThan(int capacite) {
        return blocRepository.findByCapaciteBlocGreaterThan(capacite);
    }

    @Override
    public List<Bloc> findByNomBlocContaining(String subString) {
        return blocRepository.findByNomBlocContaining(subString);
    }

    @Override
    public List<Bloc> findAllByOrderByNomBlocAsc() {
        return blocRepository.findAllByOrderByNomBlocAsc();
    }

    @Override
    public List<Bloc> findByNomBlocOrCapaciteBloc(String nomBloc, int capaciteBloc) {
        return blocRepository.findByNomBlocOrCapaciteBloc(nomBloc, capaciteBloc);
    }

    @Override
    public Bloc affecterChambresABloc(List<Long> numChambre, String nomBloc) {
        return null;
    }


/*
        @Scheduled(fixedRate = 60000)
        @Override
        public void listeChambresParBloc() {
                List<Bloc> listbloc = blocRepository.findAll();
                for (Bloc b : listbloc) {
                        List<Chambre> ch = b.getChambres();
                        for (Chambre c : ch) {
                                log.info(c.toString());
                        }
                }

        }*/

}
