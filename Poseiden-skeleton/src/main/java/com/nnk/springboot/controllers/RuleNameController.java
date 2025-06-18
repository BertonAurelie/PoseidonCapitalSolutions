package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.dto.RuleNameDto;
import com.nnk.springboot.domain.dto.mapper.request.RuleNameMapper;
import com.nnk.springboot.service.RuleNameService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class RuleNameController {
    private static final Logger logger = LoggerFactory.getLogger(RuleNameController.class);

    private RuleNameService ruleNameService;

    public RuleNameController(RuleNameService ruleNameService) {
        this.ruleNameService = ruleNameService;
    }

    @RequestMapping("/ruleName/list")
    public String home(Model model) {
        model.addAttribute("ruleNames", ruleNameService.getRuleNameList());

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("username", username);
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleNameDto bid, Model model) {
        model.addAttribute("RuleNameDto", bid);
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@ModelAttribute("RuleNameDto") @Valid RuleNameDto ruleName, BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.info("some fields are empty");
            model.addAttribute("RuleNameDto", ruleName);
            return "ruleName/add";
        }

        try {
            ruleNameService.addRuleName(ruleName);
            logger.info("ruleName successfully added, redirection...");
        } catch (Exception e) {
            logger.info("unable to add this ruleName");
            model.addAttribute("error", e.getMessage());
            return "ruleName/add";
        }

        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        RuleName ruleName = ruleNameService.getRuleNameById(id);
        model.addAttribute("RuleNameDto", RuleNameMapper.toDto(ruleName));
        model.addAttribute("bidId", id);
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @ModelAttribute("RuleNameDto") @Valid RuleNameDto ruleName,
                                 BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.info("some fields are empty (updating method)");
            model.addAttribute("RuleNameDto", ruleName);
            model.addAttribute("bidId", id);
            return "ruleName/update";
        }

        try {
            ruleNameService.updateRuleName(id, ruleName);
            logger.info("ruleName successfully updated, redirection...");
        } catch (Exception e) {
            logger.info("unable to update this ruleName");
            model.addAttribute("error", e.getMessage());
            return "ruleName/update";
        }
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        ruleNameService.deleteRuleName(id);
        return "redirect:/ruleName/list";
    }
}
