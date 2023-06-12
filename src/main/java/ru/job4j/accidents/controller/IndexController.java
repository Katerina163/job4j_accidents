package ru.job4j.accidents.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.AccidentTypeService;
import ru.job4j.accidents.service.RuleService;

@Controller
public class IndexController {
    private AccidentService service;

    private AccidentTypeService typeService;

    private RuleService ruleService;

    public IndexController(AccidentService springAccidentService, AccidentTypeService springAccidentTypeService,
                           RuleService springRuleService) {
        service = springAccidentService;
        typeService = springAccidentTypeService;
        ruleService = springRuleService;
    }

    @GetMapping({"/", "/index"})
    public String getIndexPage(Model model) {
        model.addAttribute("accidents", service.findAll());
        return "index";
    }

    @GetMapping("/save")
    public String getCreatePage(Model model) {
        model.addAttribute("types", typeService.findAll())
                .addAttribute("rules", ruleService.findAll());
        return "create";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        service.delete(id);
        return "redirect:/";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Accident accident, HttpServletRequest req) {
        service.create(accident, req.getParameterValues("rIds"));
        return "redirect:/index";
    }

    @GetMapping("/modify/{id}")
    public String getModifyPage(@PathVariable int id, Model model) {
        var optional = service.findById(id);
        if (optional.isEmpty()) {
            model.addAttribute("message", "Не удалось найти");
            return "error";
        }
        model.addAttribute("accident", optional.get())
                .addAttribute("types", typeService.findAll())
                .addAttribute("rules", ruleService.findAll());
        return "modify";
    }

    @PostMapping("/modify")
    public String modify(@ModelAttribute Accident accident, HttpServletRequest req) {
        service.modify(accident, req.getParameterValues("rIds"));
        return "redirect:/index";
    }
}