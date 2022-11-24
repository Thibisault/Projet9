package com.example.Projet9.controller;

import com.example.Projet9.Entity.Patient;
import com.example.Projet9.service.PatientService;
import com.example.Projet9.service.RiskRapportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class PatientController {

    @Autowired
    RiskRapportService riskRapportService;
    @Autowired
    PatientService patientService;

    /**
     * Obtenir tout les patients de la BDD
     */
    @GetMapping("/patient/allPatientList")
    public String patientList(Model model) {
        model.addAttribute("allPatient", patientService.findAllPatient());
        return "patient/allPatientList";
    }

    @GetMapping("/patient/addPatient")
    public String addPatientForm(Patient patient) {
        return "patient/addPatient";
    }

    @PostMapping("/patient/validate")
    public String validateAddPatient(Patient patient, BindingResult result, Model model) {
        if(result.hasErrors()){
            return "patient/validate";
        }else{
            patientService.addNewPatient(patient);
            model.addAttribute("newPatient", patient);
        }
        model.addAttribute("allPatient", patientService.findAllPatient());
        riskRapportService.generateRapportForOnePatient(patient);
        return "patient/allPatientList";
    }

    @GetMapping("/patient/updatePatient/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("updatePatient", patientService.findByPatientId(id));
        return "patient/updatePatient";
    }

    @PostMapping("/patient/updatePatient/{id}")
    public String updatePatient(@PathVariable("id") Integer id, Patient patient, BindingResult result, Model model) {
        if(result.hasErrors()){
            return "patient/updatePatient/{id}";
        }else{
            patient.setIdPatient(id);
            patientService.addNewPatient(patient);
            model.addAttribute("allPatient", patientService.findAllPatient());
        }
        riskRapportService.generateRapportForOnePatient(patient);
        return "patient/allPatientList";
    }

    @GetMapping("/patient/delete/{id}")
    public String showDeletePatient(@PathVariable("id") Integer id, Model model) {
        Patient patient = patientService.findByPatientId(id);
        patientService.deletePatient(patientService.findByPatientId(id));
        model.addAttribute("allPatient", patientService.findAllPatient());
        riskRapportService.deleteRiskRapportAfterActionInController(patient);
        return "patient/allPatientList";
    }

}
