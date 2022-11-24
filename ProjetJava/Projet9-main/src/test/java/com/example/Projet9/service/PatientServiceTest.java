package com.example.Projet9.service;

import com.example.Projet9.Entity.Patient;
import com.example.Projet9.Entity.Risk;
import com.example.Projet9.Entity.Sexe;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class patientServiceTest {

    @Autowired
    PatientService patientService;

    @Test
    public void contextLoads() {
    }

    @Test
    void addNewPatient() {
        Patient newPatient = new Patient();
        newPatient.setAddress("33 Rue de la paix");
        newPatient.setAge(33);
        newPatient.setRisk(Risk.EARLYONSET);
        newPatient.setSexe(Sexe.MALE);
        newPatient.setFirstName("Bobo");
        newPatient.setLastName("Obob");
        newPatient.setPhoneNumber(0606060606);

        patientService.addNewPatient(newPatient);

        Integer idNewPatient = newPatient.getIdPatient();
        assertEquals(idNewPatient, patientService.findByPatientId(idNewPatient).getIdPatient());

        patientService.deletePatient(newPatient);
    }

    @Test
    void addAndDeletePatient() {
        Patient newPatient = new Patient();
        newPatient.setAddress("33 Rue de la paix");
        newPatient.setAge(33);
        newPatient.setRisk(Risk.EARLYONSET);
        newPatient.setSexe(Sexe.MALE);
        newPatient.setFirstName("Bobo");
        newPatient.setLastName("Obob");
        newPatient.setPhoneNumber(0606060606);

        patientService.addNewPatient(newPatient);
        Integer idNewPatient = newPatient.getIdPatient();
        assertEquals(idNewPatient, patientService.findByPatientId(idNewPatient).getIdPatient());

        patientService.deletePatient(newPatient);
        assertNull(patientService.findByPatientId(idNewPatient));
    }

}