package com.example.Projet9.controller;

import com.example.Projet9.Entity.Note;
import com.example.Projet9.Entity.Patient;
import com.example.Projet9.repository.PatientRepository;
import com.example.Projet9.repository.RiskRapportRepository;
import com.example.Projet9.service.NoteService;
import com.example.Projet9.service.PatientService;
import com.example.Projet9.service.RiskRapportService;
import com.example.Projet9.service.RiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;

@Controller
public class NoteController {

    @Autowired
    PatientRepository patientRepository;
    @Autowired
    PatientService patientService;

    @Autowired
    NoteService noteService;
    @Autowired
    RiskService riskService;
    @Autowired
    RiskRapportService riskRapportService;

    /**
     * Obtenir toutes les notes d'un patient
     */
    @GetMapping("/note/notePatient/{id}")
    public String noteList(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("patient", patientService.findByPatientId(id));
        model.addAttribute("allNote", noteService.getAllNoteFromOnePatient(patientService.findByPatientId(id)));
        return "note/notePatient";
    }

    /**
     * Afficher formuler d'ajout d'une note au patient
     * @param
     * @return
     */
    @GetMapping("/note/addNewNotePatient/{id}")
    public String addNotePatientForm(@PathVariable("id") Integer id, Model model) {
        Note note = new Note();
        model.addAttribute("note", note);
        model.addAttribute("patient", patientService.findByPatientId(id));
        return "note/addNewNotePatient";
    }

    /**
     * Formulaire de validation de création de la note au patient
     * @param
     * @param result
     * @param model
     * @return
     */
    @PostMapping("/note/addNewNotePatientValidate/{id}")
    public String validateAddNotePatient(@PathVariable("id") Integer id, Note note, BindingResult result, Model model) {
        Patient patient = patientService.findByPatientId(id);
        model.addAttribute("patient", patient);
        model.addAttribute("note", note.getTakeNote());
        if(result.hasErrors()){
            return "note/addNewNotePatient";
        }else{
            String newNote = model.getAttribute("note").toString();
            noteService.takeNote(patient, newNote, note);
            model.addAttribute("allNote", noteService.getAllNoteFromOnePatient(patient));
        }
        riskService.updateRiskPatient(patient);
        riskRapportService.generateRapportForOnePatient(patient);
        return "note/notePatient";
    }

    @GetMapping("/note/updateNote/{id}")
    public String showUpdateNoteForm(@PathVariable("id") Integer id, Model model) {
        Integer idPatient = noteService.findNoteById(id).getIdPatientForNote();
        Patient patient = patientService.findByPatientId(idPatient);
        model.addAttribute("patient", patient);
        model.addAttribute("updateNote", noteService.findNoteById(id));
        return "note/updateNote";
    }

    @PostMapping("/note/updateNote/{id}")
    public String updateNoteValidate(@PathVariable("id") Integer id, Note note, BindingResult result, Model model) {
        Integer idPatient = noteService.findNoteById(id).getIdPatientForNote();
        Note oldNote = noteService.findNoteById(id);
        Patient patient = patientService.findByPatientId(idPatient);
        model.addAttribute("patient", patient);
        model.addAttribute("note", note.getTakeNote());
        if(result.hasErrors()){
            return "note/updateNote";
        }else{
            oldNote.setIdNote(id);
            oldNote.setDateNote(oldNote.getDateNote());
            oldNote.setIdPatientForNote(patient.getIdPatient());
            oldNote.setTakeNote(model.getAttribute("note").toString());
            noteService.saveNewNote(oldNote);
            model.addAttribute("allNote", noteService.getAllNoteFromOnePatient(patient));
        }
        riskService.updateRiskPatient(patient);
        riskRapportService.generateRapportForOnePatient(patient);
        return "note/notePatient";
    }

    @GetMapping("/note/delete/{id}")
    public String showDeleteNotePAtient(@PathVariable("id") Integer id, Model model) {
        Integer idPatient = noteService.findNoteById(id).getIdPatientForNote();
        noteService.deleteNote(noteService.findNoteById(id));
        Patient patient = patientService.findByPatientId(idPatient);
        riskService.updateRiskPatient(patient);
        model.addAttribute("allNote", noteService.getAllNoteFromOnePatient(patient));
        model.addAttribute("patient", patient);
        riskRapportService.generateRapportForOnePatient(patient);
        return "/note/notePatient";
    }
}
