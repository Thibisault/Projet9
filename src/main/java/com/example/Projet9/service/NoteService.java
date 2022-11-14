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
public class NoteService {

    @Autowired
    NoteRepository noteRepository;

    @Autowired
    PatientRepository patientRepository;
    @Autowired
    PatientService patientService;

    public List<Note> getAllNoteFromOnePatient(Patient patient){
        List<Note> noteList = new ArrayList<>();
        noteList.addAll(patientRepository.findByIdPatient(patient.getIdPatient()).orElse(null).getNoteList());
        return noteList;
    }
    public Note takeNote(Patient patient, String newNote, Note note) {
        note.setDateNote(new Date());
        note.setTakeNote(newNote);
        note.setIdPatientForNote(patient.getIdPatient());
        this.saveNewNote(note);
        patient.getNoteList().add(note);
        patientService.addNewPatient(patient);
        return note;
    }

    public Note findNoteById(Integer idNote){
        return noteRepository.findByIdNote(idNote).orElse(null);
    }
    public Note findNoteByIdPatientForNote(Integer idPatientForNote){
        return noteRepository.findByIdNote(idPatientForNote).orElse(null);
    }


    public void saveNewNote(Note note){
        noteRepository.save(note);
    }

    public void deleteNote(Note note) {
        noteRepository.delete(note);
    }

}
