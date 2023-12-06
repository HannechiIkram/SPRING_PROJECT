


package com.example.project.controllers;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

        import com.example.project.repository.ReservationRepository;
        import com.example.project.service.IChamberService;
        import com.example.project.service.IReservationService;
        import com.example.project.service.MailService;
        import com.example.project.service.ReservationService;
        import lombok.AllArgsConstructor;
        import org.springframework.core.io.InputStreamResource;
        import org.springframework.format.annotation.DateTimeFormat;
        import org.springframework.http.HttpHeaders;
        import org.springframework.http.MediaType;
        import org.springframework.http.ResponseEntity;
        import org.springframework.web.bind.annotation.*;
        import com.example.project.models.Reservation ;
        import java.io.ByteArrayInputStream;
        import java.io.IOException;
        import java.time.LocalDate;
        import java.util.Date;
        import java.util.List;
        import java.util.Set;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class    ResvrationRestController {
    IReservationService iReservationService;
    IChamberService iChamberService ;

    MailService mailService;

    ReservationService reservationService;
    ReservationRepository reservationRepository;


    @GetMapping("findAllReservation")
    List<Reservation> findAll(){

        return iReservationService.findAllReservations();
    }



    @PostMapping("addReservation")
    Reservation addReservation(@RequestBody Reservation r){

        return iReservationService.addReservation(r);
    }
    @GetMapping("findReservationByID/{id}")
    Reservation findbyIDReservation(@PathVariable("id") Integer id){
        return iReservationService.findByIdReservation(id);
    }
    @PostMapping("addAllReservation")
    List<Reservation> addAllReservation(@RequestBody List<Reservation> ls){

        return  iReservationService.addAllReservation(ls);
    }

    @PutMapping("updateReservation")
    Reservation updateReservation(@RequestBody Reservation r){
        return iReservationService.editReservation(r);
    }


  /*  @GetMapping("/exportpdf/{reservationId}")
    public ResponseEntity<InputStreamResource> exportPdf(@PathVariable("reservationId") Integer reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).get();
       // ByteArrayInputStream bais = reservationService.exportPdf(reservation);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=reservation.pdf");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(bais));

    }  */ //*/*/
    @DeleteMapping("dd/{id}")
    void DeleteReservation(@PathVariable("id") Integer id){
        iReservationService.deleteReservationn(id);
    }
    @DeleteMapping("DeleteReservation/{id}")
    void DeleteReservationByID(@PathVariable("id") Integer id){
        iReservationService.deleteById(id);
    }
    @PostMapping("generateReservation/{numchamber}/{cin}")
    Reservation addReseravationavecChamber(@RequestBody Reservation r,@PathVariable("numchamber") int numero ,  @PathVariable("cin") long cin){
      //  mailService.sendEmail("ikram.hannechi@esprit.tn","Reservation","Reservation ajoutee avec success");
        log.info("he4a num "+numero+cin);return iReservationService.ajouterReservationEtAssignerAChambreEtAEtudiant(r,numero, cin);
    }









    @GetMapping("/chambers/{id}/reservations")
    public Set<Reservation> listerReservationsPourChambre(@PathVariable Long id) {
        return iReservationService.listerReservationsPourChambre(id);
    }


    @GetMapping("/filterByDate")
    public List<Reservation> filterReservationsByDate(
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return iReservationService.filtrerReservationsParDate(date);

    }

    @GetMapping("/filter")
    public List<Reservation> filterreservations(
            @RequestParam("annee")  @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate annee,
            @RequestParam("valide") Boolean valide
    ) {
        return iReservationService.filtrerReservationsParAnneeEtValide(annee, valide);
    }

    @GetMapping("/listerReservationsEtudiant/{cin}")
    public List<Reservation> listerResEtud(@PathVariable Long cin) {
        return iReservationService.listerReservationsEtudiant(cin);
    }
}

