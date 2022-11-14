package com.example.Projet9.service;

import com.example.Projet9.Entity.Note;
import com.example.Projet9.Entity.Patient;
import com.example.Projet9.Entity.Risk;
import com.example.Projet9.Entity.Sexe;
import com.example.Projet9.repository.NoteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NoteServiceTest {

    @Autowired
    NoteRepository noteRepository;
    @Autowired
    PatientService patientService;

    @Autowired
    NoteService noteService;

    @Test
    public void contextLoads() {
    }

    @Test
    void takeNote(){
        Patient newPatient = new Patient();
        newPatient.setAddress("33 Rue de la paix");
        newPatient.setAge(33);
        newPatient.setRisk(Risk.EARLYONSET);
        newPatient.setSexe(Sexe.MALE);
        newPatient.setFirstName("Bobo");
        newPatient.setLastName("Obob");
        newPatient.setPhoneNumber(0606060606);


        patientService.addNewPatient(newPatient);

        String newNote = "Je suis une nouvelle note";
        //noteService.takeNote(newPatient, newNote);
    }

    @Test
    void createAndDeleteNote(){
        Patient newPatient = new Patient();
        newPatient.setAddress("33 Rue de la paix ForCreateAndDeleteNote");
        newPatient.setAge(33);
        newPatient.setRisk(Risk.EARLYONSET);
        newPatient.setSexe(Sexe.MALE);
        newPatient.setFirstName("Bobo ForCreateAndDeleteNote");
        newPatient.setLastName("Obob ForCreateAndDeleteNote");
        newPatient.setPhoneNumber(0606060606);
        patientService.addNewPatient(newPatient);

        Note note = new Note();
        String newNote = "Je suis une nouvelle note ForCreateAndDeleteNote";
        note = noteService.takeNote(newPatient, newNote, note);
        noteService.deleteNote(note);
        patientService.deletePatient(newPatient);

    }

}