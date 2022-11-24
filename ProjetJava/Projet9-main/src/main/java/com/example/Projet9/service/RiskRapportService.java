package com.example.Projet9.service;

import com.example.Projet9.Entity.Patient;
import com.example.Projet9.Entity.RiskRapport;
import com.example.Projet9.repository.PatientRepository;
import com.example.Projet9.repository.RiskRapportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class RiskRapportService {


    @Autowired
    PatientRepository patientRepository;

    @Autowired
    RiskRapportRepository riskRapportRepository;

    public void generateIdRiskRapportRandom(RiskRapport riskRapport){
    }

    public void generateRapportForOnePatient(Patient patient){
        RiskRapport riskRapport = new RiskRapport();
        if(riskRapportRepository.findByIdPatientForRiskRapport(patient.getIdPatient()).orElse(null) == null){
            riskRapport.getTerminologyList().addAll(patient.getTerminologyList());
            riskRapport.getNoteList().addAll(patient.getNoteList());
            riskRapport.setFirstName(patient.getFirstName());
            riskRapport.setLastName(patient.getLastName());
            riskRapport.setIdPatientForRiskRapport(patient.getIdPatient());
            riskRapport.setRisk(patient.getRisk());
            riskRapport.setAge(patient.getAge());
            riskRapport.setAddress(patient.getAddress());
            riskRapport.setPhoneNumber(patient.getPhoneNumber());
            riskRapport.setSexe(patient.getSexe());
        } else {
            riskRapport = riskRapportRepository.findByIdPatientForRiskRapport(patient.getIdPatient()).orElse(null);
            riskRapport.getTerminologyList().clear();
            riskRapport.getNoteList().clear();
            riskRapportRepository.delete(riskRapport);
            riskRapport.getTerminologyList().addAll(patient.getTerminologyList());
            riskRapport.getNoteList().addAll(patient.getNoteList());
            riskRapport.setFirstName(patient.getFirstName());
            riskRapport.setLastName(patient.getLastName());
            riskRapport.setRisk(patient.getRisk());
            riskRapport.setAge(patient.getAge());
            riskRapport.setAddress(patient.getAddress());
            riskRapport.setPhoneNumber(patient.getPhoneNumber());
            riskRapport.setSexe(patient.getSexe());
        }
        riskRapportRepository.save(riskRapport);
    }

    public void deleteRiskRapportAfterActionInController(Patient patient){
        RiskRapport riskRapport = riskRapportRepository.findByIdPatientForRiskRapport(patient.getIdPatient()).orElse(null);
        riskRapportRepository.delete(riskRapport);
    }

    public void deleteRiskRapport(RiskRapport riskRapport){
        riskRapportRepository.delete(riskRapport);
    }

    public RiskRapport findByRiskRapportId(String idRiskRapport){
        return riskRapportRepository.findById(idRiskRapport).orElse(null);
    }

    public RiskRapport findByIdPatientForRiskRapport(Integer idPatientForRiskRapport){
        return riskRapportRepository.findByIdPatientForRiskRapport(idPatientForRiskRapport).orElse(null);
    }

    public void saveRiskRapport(RiskRapport riskRapport){
        riskRapportRepository.save(riskRapport);
    }


}
