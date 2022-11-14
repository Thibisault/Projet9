package com.example.Projet9.service;

import com.example.Projet9.Entity.Note;
import com.example.Projet9.Entity.Patient;
import com.example.Projet9.repository.NoteRepository;
import com.example.Projet9.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class PatientService {

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    NoteRepository noteRepository;

    public Patient addNewPatient(Patient newPatient){
        return patientRepository.save(newPatient);
    }

    public void deletePatient(Patient patient){
        patientRepository.delete(patient);
    }

    public List<Patient> findAllPatient(){
        List<Patient> patientList;
        patientList = patientRepository.findAll();
        return patientList;
    }

    public Patient findByPatientId(Integer idPatient){
        return patientRepository.findByIdPatient(idPatient).orElse(null);
    }

}
