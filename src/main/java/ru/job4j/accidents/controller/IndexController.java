package ru.job4j.accidents.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;

@Controller
public class IndexController {
    private AccidentService service;

    public IndexController(AccidentService memAccidentService) {
        service = memAccidentService;
    }

    @GetMapping({"/", "/index"})
    public String getIndexPage(Model model) {
        model.addAttribute("accidents", service.getList());
        return "index";
    }

    @GetMapping("/save")
    public String getCreatePage() {
        return "create";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        service.delete(id);
        return "redirect:/";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Accident accident) {
        service.create(accident);
        return "redirect:/index";
    }

    @GetMapping("/modify/{id}")
    public String getModifyPage(@PathVariable int id, Model model) {
        model.addAttribute("accident", service.getAccient(id));
        return "modify";
    }

    @PostMapping("/modify")
    public String modify(@ModelAttribute Accident accident) {
        service.modify(accident);
        return "redirect:/index";
    }
}