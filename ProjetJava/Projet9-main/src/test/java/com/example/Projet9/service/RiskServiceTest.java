package com.example.Projet9.service;

import com.example.Projet9.Entity.Note;
import com.example.Projet9.Entity.Patient;
import com.example.Projet9.Entity.Risk;
import com.example.Projet9.Entity.Sexe;
import com.example.Projet9.repository.NoteRepository;
import com.example.Projet9.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RiskServiceTest {

    @Autowired
    RiskService riskService;

    @Autowired
    PatientService patientService;

    @Autowired
    NoteService noteService;

    @Autowired
    NoteRepository noteRepository;
    @Autowired
    PatientRepository patientRepository;

    @Test
    public void contextLoads() {
    }

    @Test
    void viderLaBDD(){

        Note note1risk = new Note();
        Patient patientHLess30AgeLess2risk = new Patient();
        patientHLess30AgeLess2risk.setAddress(("33 Rue de la paix ForTestRisk"));
        patientHLess30AgeLess2risk.setAge(15);
        patientHLess30AgeLess2risk.setRisk(Risk.NONE);
        patientHLess30AgeLess2risk.setSexe(Sexe.MALE);
        patientHLess30AgeLess2risk.setFirstName(("Bobo ForTestRisk"));
        patientHLess30AgeLess2risk.setLastName(("Obob ForTestRisk"));
        patientHLess30AgeLess2risk.setPhoneNumber(0606060606);
        patientService.addNewPatient(patientHLess30AgeLess2risk);

        String newNote = "HémoglobineA1C, HemoglobineA1C,Hemoglobine_A1C , Microalbumine, Fumeur, Poids note ForTestRisk";
        note1risk = noteService.takeNote(patientHLess30AgeLess2risk, newNote, note1risk);
        riskService.determinePatientRisk(patientHLess30AgeLess2risk);
        patientService.addNewPatient(patientHLess30AgeLess2risk);
        System.out.println("Risque patient 1 : " + patientHLess30AgeLess2risk);

        noteRepository.deleteAll();
        patientRepository.deleteAll();
    }

    @Test
    void riskTerminologyList() {
    }

    @Test
    void fillRiskListForPatient() {
    }
}