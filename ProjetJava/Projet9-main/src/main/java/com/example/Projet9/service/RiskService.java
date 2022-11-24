package com.example.Projet9.service;

import com.example.Projet9.Entity.Note;
import com.example.Projet9.Entity.Patient;
import com.example.Projet9.Entity.Risk;
import com.example.Projet9.Entity.Sexe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RiskService {

    @Autowired
    PatientService patientService;

    public List<String> riskTerminologyList() {
        List<String> riskTerminologyList = new ArrayList<>();
        riskTerminologyList.add("HémoglobineA1C");
        riskTerminologyList.add("HémoglobineA1C");
        riskTerminologyList.add("HemoglobineA1C");
        riskTerminologyList.add("Hémoglobine_A1C");
        riskTerminologyList.add("Hemoglobine_A1C");
        riskTerminologyList.add("Hémoglobine A1C");
        riskTerminologyList.add("Hemoglobine A1C");
        riskTerminologyList.add("Microalbumine");
        riskTerminologyList.add("Taille");
        riskTerminologyList.add("Poids");
        riskTerminologyList.add("Fumeur");
        riskTerminologyList.add("Anormal");
        riskTerminologyList.add("Cholestérol");
        riskTerminologyList.add("Cholesterol");
        riskTerminologyList.add("Vertige");
        riskTerminologyList.add("Rechute");
        riskTerminologyList.add("Réaction");
        riskTerminologyList.add("Reaction");
        riskTerminologyList.add("Anticorps");
        riskTerminologyList.add("hémoglobineA1C");
        riskTerminologyList.add("hémoglobineA1C");
        riskTerminologyList.add("hemoglobineA1C");
        riskTerminologyList.add("hémoglobine_A1C");
        riskTerminologyList.add("hemoglobine_A1C");
        riskTerminologyList.add("hémoglobine A1C");
        riskTerminologyList.add("hemoglobine A1C");
        riskTerminologyList.add("microalbumine");
        riskTerminologyList.add("taille");
        riskTerminologyList.add("poids");
        riskTerminologyList.add("fumeur");
        riskTerminologyList.add("anormal");
        riskTerminologyList.add("cholestérol");
        riskTerminologyList.add("cholesterol");
        riskTerminologyList.add("vertige");
        riskTerminologyList.add("rechute");
        riskTerminologyList.add("réaction");
        riskTerminologyList.add("reaction");
        riskTerminologyList.add("anticorps");
        return riskTerminologyList;
    }

    public void fillRiskListForPatient(Patient patient) {
        patient.getTerminologyList().clear();
        List<String> riskTerminologyList = this.riskTerminologyList();
        int countPatientRisk = 0;
        for (Note note : patient.getNoteList()) {
            for (String riskTerminology : riskTerminologyList) {
                Boolean bool = false;
                if (note.getTakeNote().contains(riskTerminology)) {
                    countPatientRisk++;
                    if (riskTerminology.equals("HemoglobineA1C") || riskTerminology.equals("HémoglobineA1C") ||
                            riskTerminology.equals("Hemoglobine_A1C") || riskTerminology.equals("Hemoglobine A1C") || riskTerminology.equals("Hémoglobine_A1C")) {
                        riskTerminology = "Hémoglobine A1C";
                    }
                    if (riskTerminology.equals("Cholesterol")) {
                        riskTerminology = "Cholestérol";
                    }
                    if (riskTerminology.equals("Reaction")) {
                        riskTerminology = "Réaction";
                    }
                    for (String riskTerminologyContain : patient.getTerminologyList()) {
                        if (riskTerminologyContain.equalsIgnoreCase(riskTerminology)) {
                            bool = true;
                        }
                    }
                    if (bool == false) {
                        patient.getTerminologyList().add(riskTerminology);
                    }
                }
            }
        }
        patientService.addNewPatient(patient);
    }

    public void determinePatientRisk(Patient patient){
        this.fillRiskListForPatient(patient);
        int countPatientRisk = patient.getTerminologyList().size();

        if (countPatientRisk == 0 || countPatientRisk == 1) {
            patient.setRisk(Risk.NONE);
        } else {
            if (countPatientRisk == 2 && patient.getAge() <= 30) {
                patient.setRisk(Risk.BORDERLINE);
            } else {
                if (patient.getSexe() == Sexe.MALE && patient.getAge() < 30 && countPatientRisk == 3 ||
                        patient.getSexe() == Sexe.MALE && patient.getAge() < 30 && countPatientRisk == 4 ||
                        patient.getSexe() == Sexe.FEMALE && patient.getAge() < 30 && countPatientRisk == 4 ||
                        patient.getSexe() == Sexe.FEMALE && patient.getAge() < 30 && countPatientRisk == 5 ||
                        patient.getSexe() == Sexe.FEMALE && patient.getAge() < 30 && countPatientRisk == 6 ||
                        patient.getAge() >= 30 && countPatientRisk == 6 ||
                        patient.getAge() >= 30 && countPatientRisk == 7) {
                    patient.setRisk(Risk.INDANGER);
                } else {
                    if (patient.getSexe() == Sexe.MALE && patient.getAge() < 30 && countPatientRisk >= 5 ||
                            patient.getSexe() == Sexe.FEMALE && patient.getAge() < 30 && countPatientRisk >= 7 ||
                            patient.getAge() >= 30 && countPatientRisk >= 8) {
                        patient.setRisk(Risk.EARLYONSET);
                    }
                }
            }
        }
        patientService.addNewPatient(patient);
    }

    public void updateRiskPatient(Patient patient){
        this.determinePatientRisk(patient);
    }

}
