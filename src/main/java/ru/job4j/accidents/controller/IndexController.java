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

    public IndexController(AccidentService memAccidentService, AccidentTypeService memAccidentTypeService,
                           RuleService memRuleService) {
        service = memAccidentService;
        typeService = memAccidentTypeService;
        ruleService = memRuleService;
    }

    @GetMapping({"/", "/index"})
    public String getIndexPage(Model model) {
        model.addAttribute("accidents", service.getAll());
        return "index";
    }

    @GetMapping("/save")
    public String getCreatePage(Model model) {
        model.addAttribute("types", typeService.getAll())
                .addAttribute("rules", ruleService.getAll());
        return "create";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id, Model model) {
        var isDelete = service.delete(id);
        if (!isDelete) {
            model.addAttribute("message", "Не удалось удалить");
            return "error";
        }
        return "redirect:/";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Accident accident, HttpServletRequest req) {
        setRules(accident, req.getParameterValues("rIds"));
        service.create(accident);
        return "redirect:/index";
    }

    private void setRules(Accident accident, String[] ids) {
        for (var s : ids) {
            accident.getRules().add(ruleService.getRule(Integer.parseInt(s)).get());
        }
    }

    @GetMapping("/modify/{id}")
    public String getModifyPage(@PathVariable int id, Model model) {
        var optional = service.getAccient(id);
        if (optional.isEmpty()) {
            model.addAttribute("message", "Не удалось найти");
            return "error";
        }
        model.addAttribute("accident", optional.get())
                .addAttribute("types", typeService.getAll())
                .addAttribute("rules", ruleService.getAll());
        return "modify";
    }

    @PostMapping("/modify")
    public String modify(@ModelAttribute Accident accident, HttpServletRequest req, Model model) {
        setRules(accident, req.getParameterValues("rIds"));
        var isModify = service.modify(accident);
        if (!isModify) {
            model.addAttribute("message", "Не удалось изменить");
            return "error";
        }
        return "redirect:/index";
    }
}