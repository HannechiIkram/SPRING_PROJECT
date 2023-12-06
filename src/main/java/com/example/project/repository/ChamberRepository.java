package com.example.project.repository;

import com.example.project.models.Bloc;
import com.example.project.models.Chamber;
import com.example.project.models.TypeChamber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.project.models.Reservation ;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

public interface ChamberRepository  extends JpaRepository<Chamber,Long> {
    void deleteByReservations(Reservation reservation);
    Chamber findByNumerochamberAndTypeC(int numero , TypeChamber type);
    // select * from chamber where numhamber> ?
    int countChamberByTypeCAndBloc_IdBloc(TypeChamber typeChamber , long idBloc);
    // @Query("select c from Chamber c where c.numerochamber = ?1")
    //@Query("select c from Chamber c where c.numerochamber = :numero")
    // KI NEKHDEMO BEL @ PARAMMMMM
    //  @Query(nativeQuery = true , value = "select * from chamber where numeroChamber=")
    //Chamber chamberByNumeroChamber(long numero);
    int countChamberByTypeC(TypeChamber type);

    Chamber findByNumerochamber(int numero);
    List<Chamber> findByTypeC(TypeChamber type);
    List<Chamber> findByBloc(Bloc b);
    List<Chamber> findByBlocAndTypeC(Bloc b , TypeChamber type);
    List<Chamber> getByNumerochamberGreaterThan(long value);
    //select *from Chamber where numberchamber != null
    List<Chamber> getByNumerochamberIsNotNull();



 /*List<Chamber> findByBlocFoyerUniversiteNomUniversiteAndResAnneeReservationAndResEtuNomEt(String nomUni , Date annee
    ,String nomET);*/

    List<Chamber> findChamberByBlocFoyerNomFoyerAndTypeCAndReservations_Empty(String NomFoyer , TypeChamber type);

    // Recherche par numéro de chamber
    @Query("select c from Chamber c where c.numerochamber=?1")
    List<Chamber> selectByNum(long num);


    @Query("select c from Chamber c where c.numerochamber=:num")
    List<Chamber> selectByNum2(@Param(value="num") long num)  ;

    @Query(value="select * from chamber where numeroChamber=?1" , nativeQuery = true)
    List<Chamber> selectByNumSQL( long num)  ;
}
