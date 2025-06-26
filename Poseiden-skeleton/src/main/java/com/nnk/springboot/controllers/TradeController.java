package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.dto.TradeDto;
import com.nnk.springboot.domain.dto.mapper.request.TradeMapper;
import com.nnk.springboot.service.TradeService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class TradeController {
    private static final Logger logger = LoggerFactory.getLogger(TradeController.class);
    private TradeService tradeService;

    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @RequestMapping("/trade/list")
    public String home(Model model) {
        model.addAttribute("trades", tradeService.getTradeList());

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("username", username);

        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser(TradeDto bid, Model model) {
        model.addAttribute("tradeDto", bid);
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@ModelAttribute("tradeDto") @Valid TradeDto trade, BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.info("some fields are empty");
            model.addAttribute("tradeDto", trade);
            return "trade/add";
        }

        try {
            tradeService.addTrade(trade);
            logger.info("trade successfully added, redirection...");
        } catch (Exception e) {
            logger.info("unable to add this trade");
            model.addAttribute("error", e.getMessage());
            return "trade/add";
        }
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Trade trade = tradeService.getRuleNameById(id);
        model.addAttribute("tradeDto", TradeMapper.toDto(trade));
        model.addAttribute("tradeId", id);
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @ModelAttribute("tradeDto") @Valid TradeDto trade,
                              BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.info("some fields are empty (updating method)");
            model.addAttribute("tradeDto", trade);
            model.addAttribute("tradeId", id);
            return "trade/update";
        }

        try {
            tradeService.updateTrade(id, trade);
            logger.info("trade successfully updated, redirection...");
        } catch (Exception e) {
            logger.info("unable to update this trade");
            model.addAttribute("error", e.getMessage());
            return "trade/update";
        }

        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        tradeService.deleteTrade(id);
        return "redirect:/trade/list";
    }
}
