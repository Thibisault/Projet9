package com.example.Projet9.service;

import com.example.Projet9.Entity.*;
import com.example.Projet9.repository.NoteRepository;
import com.example.Projet9.repository.PatientRepository;
import com.example.Projet9.repository.RiskRapportRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RiskRapportServiceTest {
    @Autowired
    RiskRapportRepository riskRapportRepository;
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    NoteRepository noteRepository;

    @Autowired
    PatientService patientService;

    @Autowired
    NoteService noteService;
    @Autowired
    RiskRapportService riskRapportService;
    @Autowired
    RiskService riskService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void delteAll(){
        riskRapportRepository.deleteAll();
        noteRepository.deleteAll();
        patientRepository.deleteAll();
    }

    @Test
    void generateRapportForOnePatient() {
        Patient newPatient = new Patient();
        newPatient.setAddress("33 Rue de la paix");
        newPatient.setAge(29);
        newPatient.setRisk(Risk.NONE);
        newPatient.setSexe(Sexe.MALE);
        newPatient.setFirstName("Bobo");
        newPatient.setLastName("Obob");
        newPatient.setPhoneNumber(0606060606);
        patientService.addNewPatient(newPatient);

        Note note = new Note();
        String newNote = "Je suis une nouvelle note ForCreateGenerateRapportForOnePatient, rechute, vertige, fumeur";
        note = noteService.takeNote(newPatient, newNote, note);

        riskService.updateRiskPatient(newPatient);

        riskRapportService.generateRapportForOnePatient(newPatient);
        RiskRapport riskRapport = riskRapportService.findByIdPatientForRiskRapport(newPatient.getIdPatient());

        //patientService.deletePatient(newPatient);
        riskRapportService.deleteRiskRapport(riskRapport);
        noteService.deleteNote(note);
    }
}