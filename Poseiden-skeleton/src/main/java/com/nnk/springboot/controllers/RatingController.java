package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.dto.RatingDto;
import com.nnk.springboot.domain.dto.mapper.request.RatingMapper;
import com.nnk.springboot.service.RatingService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class RatingController {
    private static final Logger logger = LoggerFactory.getLogger(RatingController.class);

    @Autowired
    private RatingService ratingService;

    @RequestMapping("/rating/list")
    public String home(Model model) {
        model.addAttribute("ratings", ratingService.getRatingList());

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("username", username);
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(RatingDto rating, Model model) {
        model.addAttribute("RatingDto", rating);
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@ModelAttribute("RatingDto") @Valid RatingDto rating, BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.info("some fields are empty");
            model.addAttribute("RatingDto", rating);
            return "rating/add";
        }

        try {
            ratingService.addRating(rating);
            logger.info("rating successfully added, redirection...");
        } catch (Exception e) {
            logger.info("unable to add this rating");
            model.addAttribute("error", e.getMessage());
            return "rating/add";
        }

        home(model);
        return "rating/list";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Rating rating = ratingService.getRatingById(id);
        model.addAttribute("RatingDto", RatingMapper.toDto(rating));
        model.addAttribute("ratingId", id);

        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @ModelAttribute("RatingDto") @Valid RatingDto rating,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.info("some fields are empty");
            model.addAttribute("RatingDto", rating);
            model.addAttribute("ratingId", id);
            return "rating/update";
        }

        try {
            ratingService.updateRating(id, rating);
            logger.info("rating successfully updated, redirection...");
        } catch (Exception e) {
            logger.info("unable to update this rating");
            model.addAttribute("error", e.getMessage());
            return "rating/update";
        }

        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        ratingService.deleteRating(id);

        return "redirect:/rating/list";
    }
}
