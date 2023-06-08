package ru.job4j.accidents.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.AccidentTypeService;

import java.util.List;

@Controller
public class IndexController {
    private AccidentService service;

    private AccidentTypeService typeService;

    private List<Rule> rules;

    public IndexController(AccidentService memAccidentService, AccidentTypeService memAccidentTypeService) {
        service = memAccidentService;
        typeService = memAccidentTypeService;
        rules = List.of(
                new Rule(1, "Статья. 1"),
                new Rule(2, "Статья. 2"),
                new Rule(3, "Статья. 3")
        );
        var map = service.getMap();
        for (int i = 0; i < 11; i++) {
            var accident = map.get(i);
            accident.getRules().add(rules.get(1));
        }
    }

    @GetMapping({"/", "/index"})
    public String getIndexPage(Model model) {
        model.addAttribute("accidents", service.getMap().values());
        return "index";
    }

    @GetMapping("/save")
    public String getCreatePage(Model model) {
        model.addAttribute("types", typeService.getMap().values())
                .addAttribute("rules", rules);
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
        int id = accident.getType().getId();
        accident.setType(typeService.getAccidentType(id).get());
        service.create(accident);
        return "redirect:/index";
    }

    private void setRules(Accident accident, String[] ids) {
        for (var s : ids) {
            accident.getRules().add(rules.get(Integer.parseInt(s) - 1));
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
                .addAttribute("types", typeService.getMap().values())
                .addAttribute("rules", rules);
        return "modify";
    }

    @PostMapping("/modify")
    public String modify(@ModelAttribute Accident accident, HttpServletRequest req, Model model) {
        setRules(accident, req.getParameterValues("rIds"));
        int id = accident.getType().getId();
        accident.setType(typeService.getAccidentType(id).get());
        var isModify = service.modify(accident);
        if (!isModify) {
            model.addAttribute("message", "Не удалось изменить");
            return "error";
        }
        return "redirect:/index";
    }
}