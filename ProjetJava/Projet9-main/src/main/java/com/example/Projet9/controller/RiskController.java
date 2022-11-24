package com.example.Projet9.controller;

import com.example.Projet9.Entity.Patient;
import com.example.Projet9.service.PatientService;
import com.example.Projet9.service.RiskRapportService;
import com.example.Projet9.service.RiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class RiskController {

    @Autowired
    RiskRapportService riskRapportService;

    @Autowired
    PatientService patientService;

    @Autowired
    RiskService riskService;


    @GetMapping("/risk/riskPatient/{id}")
    public String riskList(@PathVariable("id") Integer id, Model model) {
        Patient patient = patientService.findByPatientId(id);
        riskService.updateRiskPatient(patient);
        model.addAttribute("patient",patient);
        riskRapportService.generateRapportForOnePatient(patient);
        return "risk/riskPatient";
    }


}
