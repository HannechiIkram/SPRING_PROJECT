package com.example.project.service;

import com.example.project.models.Chamber;
import com.example.project.models.Etudiant;
import com.example.project.repository.ChamberRepository;
import com.example.project.repository.EtudiantRepository;
import com.example.project.repository.ReservationRepository;
/*import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;*/
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.example.project.models.Reservation ;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.swing.text.Document;
import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@AllArgsConstructor
@Service
public class ReservationService implements IReservationService {
    ReservationRepository reservationRepository ;

    @Override
    public Reservation addReservation(Reservation r) {
        return reservationRepository.save(r);
    }

    @Override
    public List<Reservation> addAllReservation(List<Reservation> ls) {
        return reservationRepository.saveAll(ls);
    }

    @Override
    public Reservation editReservation(Reservation r) {
        return reservationRepository.save(r);
    }

    @Override
    public List<Reservation> findAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation findByIdReservation(Integer id) {
        return reservationRepository.findById(id).orElse(Reservation.builder().build());
    }


    @PersistenceContext
    private EntityManager entityManager;
    ChamberRepository chamberRepository ;
    EtudiantRepository etudiantRepository ;
    @Transactional
    public Reservation ajouterReservationEtAssignerAChambreEtAEtudiant(Reservation r,int numChambre, long cin) {
        Reservation existingReservation = entityManager.find(Reservation.class, r.getIdReservation());
        if (existingReservation != null) {
            existingReservation.setAnneeReservation(r.getAnneeReservation());
            existingReservation.setEstValide(r.getEstValide());

            Set<Etudiant> existingEtudiants = existingReservation.getEtudiants();
            Etudiant e = etudiantRepository.findByCin(cin);
            existingEtudiants.clear();
            existingEtudiants.add(e);

            Chamber c = chamberRepository.findByNumerochamber(numChambre);
            existingReservation.setChamber(c);

            entityManager.merge(existingReservation);
            return r;
        }else {
            Etudiant e = etudiantRepository.findByCin(cin);
            Chamber c = chamberRepository.findByNumerochamber(numChambre);
            r.getEtudiants().add(e);
            r.setChamber(c);
            c.getReservations().add(r);
            chamberRepository.save(c);
            return r;
        }
    }

    @Override
    public Reservation annulerReservation(long cinEtudiant) {
        Etudiant e = etudiantRepository.findByCin(cinEtudiant);

        return null;
    }

    @Override
    public void deleteById(Integer id) {
        reservationRepository.deleteById(id);
    }

    @Transactional
    public void deleteReservation(Integer id) {
        Reservation r = reservationRepository.findById(id).get();
        chamberRepository.deleteByReservations(r);
        reservationRepository.delete(r);

    }
    @Override
    public List<Reservation> listerReservationsEtudiant(Long cinEtudiant) {
        Etudiant etudiant = etudiantRepository.findByCin(cinEtudiant);
        return reservationRepository.findByEtudiants(etudiant);
    }

    @Override
    public List<Reservation> filtrerReservationsParDate(Date dateReservation) {
        return reservationRepository.findByAnneeReservation(dateReservation);
    }
    @Override
    public List<Reservation> filtrerReservationsParAnneeEtValide(LocalDate annee, Boolean estValide) {
        return reservationRepository.findByAnneeReservationAndEstValide(annee, estValide);
    }

    @Override
    public Set<Reservation> listerReservationsPourChambre(Long idChamber) {
        Chamber chambre = chamberRepository.findById(idChamber)
                .orElseThrow(() -> new RuntimeException("Chambre non trouvée avec le numéro : " + idChamber));

        return new HashSet<>(chambre.getReservations());
    }


    @Transactional
    public void deleteReservationn(Integer reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElse(null);

        if (reservation != null) {
            Chamber chamber = reservation.getChamber();

            if (chamber != null) {
                Set<Reservation> reservations = chamber.getReservations();

                reservations.remove(reservation);

                chamber.setReservations(reservations);
                chamberRepository.save(chamber);
            }
            reservationRepository.deleteById(reservationId);
        }
    }

   // public static ByteArrayInputStream exportPdf(Reservation reservation) {
      //  Document document = new Document();
       // ByteArrayOutputStream out = new ByteArrayOutputStream();
       // try {
           // PdfWriter.getInstance(document, out);
           // document.open();

            // Title
           // Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLUE);
           // Paragraph title = new Paragraph("Reservation", titleFont);
           // title.setAlignment(Element.ALIGN_CENTER);
           // document.add(title);
            //document.add(Chunk.NEWLINE);

            // Add individual fields
           // addFieldParagraph(document, "Num Reservation", reservation.getIdReservation().toString());

            // Handle multiple Etudiants
          //  for (Etudiant etudiant : reservation.getEtudiants()) {
          //      addFieldParagraph(document, "Etudiant", etudiant.getNomEt());
            //}

           // addFieldParagraph(document, "Chambre", String.valueOf(reservation.getChamber().getNumerochamber()));
           // addFieldParagraph(document, "Type", reservation.getChamber().getTypeC().toString());
          //  addFieldParagraph(document, "Date Reservation", reservation.getAnneeReservation().toString());
           // addFieldParagraph(document, "Validité", reservation.getEstValide().toString());

           //// document.close();
       // } catch (DocumentException e) {
       //     e.printStackTrace();
       // }
      //  return new ByteArrayInputStream(out.toByteArray());
   // }

    //private static void addFieldParagraph(Document document, String fieldName, String value) throws DocumentException {
      //  Font fieldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.BLACK);
       //// Font valueFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.DARK_GRAY);

        // Field Name
     //   Paragraph fieldNameParagraph = new Paragraph(fieldName + ": ", fieldFont);
     //   fieldNameParagraph.setAlignment(Element.ALIGN_LEFT);
       // document.add(fieldNameParagraph);

        // Field Value
     //   Paragraph fieldValueParagraph = new Paragraph(value, valueFont);
      //  fieldValueParagraph.setAlignment(Element.ALIGN_LEFT);
       // document.add(fieldValueParagraph);

        // Add some space between fields
      //  document.add(Chunk.NEWLINE);
   // }


}
