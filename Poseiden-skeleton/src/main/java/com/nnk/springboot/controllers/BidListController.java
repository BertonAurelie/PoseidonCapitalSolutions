package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.dto.BidListDto;
import com.nnk.springboot.domain.dto.mapper.request.BidListMapper;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.service.BidListService;
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
public class BidListController {
    private static final Logger logger = LoggerFactory.getLogger(BidListController.class);

    @Autowired
    private BidListService bidListService;


    @RequestMapping("/bidList/list")
    public String home(Model model) {
        model.addAttribute("bidLists", bidListService.getBidList());

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("username", username);
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidListDto bid, Model model) {
        model.addAttribute("BidListDto", bid);
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@ModelAttribute("BidListDto")@Valid BidListDto bid, BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.info("error");
            model.addAttribute("BidListDto", bid);
            return "bidList/add";
        }

        try {
            bidListService.addBidList(bid);
            logger.info("bidList successfully added, redirection...");
        } catch (Exception e) {
            logger.info("unable to add this bidList");
            model.addAttribute("error", e.getMessage());
            return "bidList/add";
        }

        home(model);
        return "bidList/list";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        System.out.println("research id bid lists : " + bidListService.getBidListById(id));
        BidList bidList = bidListService.getBidListById(id);
        model.addAttribute("BidListDto", BidListMapper.toDto(bidList)); // mappe l'entité en DTO
        model.addAttribute("bidId", id);
        //model.addAttribute("bidList", bidListService.getBidListById(id));
        //model.addAttribute("BidListDto",bidListService.getBidListById(id));
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @ModelAttribute("BidListDto")@Valid BidListDto bid,
                            BindingResult result, Model model) {

        if (result.hasErrors()) {
            logger.info("errorUpdate");
            model.addAttribute("BidListDto", bid);
            model.addAttribute("bidId", id);
            return "bidList/update";
        }

        try {
            bidListService.updateBidList(id, bid);
            logger.info("bidList successfully updated, redirection...");
        } catch (Exception e) {
            logger.info("unable to update this bidList");
            model.addAttribute("error", e.getMessage());
            return "bidList/update/{id}";
        }
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        bidListService.deleteBidList(id);
        return "redirect:/bidList/list";
    }
}
