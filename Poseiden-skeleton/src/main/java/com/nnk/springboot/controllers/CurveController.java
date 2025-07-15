package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.dto.CurvePointDto;
import com.nnk.springboot.service.CurveService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
public class CurveController {
    private static final Logger logger = LoggerFactory.getLogger(CurveController.class);

    private CurveService curveService;

    public CurveController(CurveService curveService) {
        this.curveService = curveService;
    }

    @RequestMapping("/curvePoint/list")
    public String home(Model model) {
        model.addAttribute("curvePoints", curveService.getCurvePointList());

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("username", username);
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePointDto curvePointDto, Model model) {
        model.addAttribute("CurvePointDto", curvePointDto);

        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@ModelAttribute("CurvePointDto") @Valid CurvePointDto curvePoint, BindingResult result, Model model) {


        if (result.hasErrors()) {
            logger.info("error");
            model.addAttribute("CurvePointDto", curvePoint);
            return "curvePoint/add";
        }

        try {
            curveService.addCurvePoint(curvePoint);
            logger.info("curve point successfully added, redirection...");
        } catch (Exception e) {
            logger.info("unable to add this curve point");
            model.addAttribute("error", e.getMessage());
            return "curvePoint/add";
        }

        home(model);
        return "/curvePoint/list";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("curvePoint", curveService.getCurvePointById(id));
        model.addAttribute("CurvePointDto", curveService.getCurvePointById(id));
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @ModelAttribute("CurvePointDto") @Valid CurvePointDto curvePoint,
                            BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.info("errorUpdate");
            model.addAttribute("CurvePointDto", curvePoint);
            return "curvePoint/update/{id}";
        }

        try {
            curveService.updateCurvePoint(id, curvePoint);
            logger.info("curve point successfully updated, redirection...");
        } catch (Exception e) {
            logger.info("unable to update this curve point");
            model.addAttribute("error", e.getMessage());
            return "curvePoint/update/{id}";
        }
        // TODO: check required fields, if valid call service to update Curve and return Curve list
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        curveService.deleteCurvePoint(id);
        return "redirect:/curvePoint/list";
    }
}
