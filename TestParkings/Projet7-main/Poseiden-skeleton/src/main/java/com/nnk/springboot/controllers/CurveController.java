package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.service.CurvepointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class CurveController {

    @Autowired
    CurvepointService curvepointService;


    @RequestMapping("/curvePoint/list")
    public String home(Model model)
    {
        model.addAttribute("allCurvePoint", curvepointService.chercherTouteLesCurvesPoint());
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePoint bid) {
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        if (result.hasFieldErrors()){
            return "curvePoint/add";
        } else {
            curvepointService.creerNewCurvepointService(curvePoint);
            model.addAttribute("newCurvePoint", curvePoint);
        }
        return "curvePoint/add";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("updateCurvePoint", curvepointService.chercherById(id));
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                             BindingResult result, Model model) {
        if (result.hasErrors()){
            return "redirect:curvePoint/update/{id}";
        } else {
            curvePoint.setId(id);
            curvepointService.creerNewCurvepointService(curvePoint);
            model.addAttribute("allCurvePoint", curvepointService.chercherTouteLesCurvesPoint());
        }
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        curvepointService.supprimerBidList(curvepointService.chercherById(id));
        model.addAttribute("allCurvePoint", curvepointService.chercherTouteLesCurvesPoint());
        return "redirect:/curvePoint/list";
    }
}