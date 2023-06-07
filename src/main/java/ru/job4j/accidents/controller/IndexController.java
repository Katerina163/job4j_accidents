package ru.job4j.accidents.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.service.AccidentService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {
    private AccidentService service;

    private List<AccidentType> typeList;

    public IndexController(AccidentService memAccidentService) {
        service = memAccidentService;
        typeList = new ArrayList<>();
        typeList.add(new AccidentType(1, "Две машины"));
        typeList.add(new AccidentType(2, "Машина и человек"));
        typeList.add(new AccidentType(3, "Машина и велосипед"));
        for (var accident : service.getList()) {
            accident.setType(typeList.get(1));
        }
    }

    @GetMapping({"/", "/index"})
    public String getIndexPage(Model model) {
        model.addAttribute("accidents", service.getList());
        return "index";
    }

    @GetMapping("/save")
    public String getCreatePage(Model model) {
        model.addAttribute("types", typeList);
        return "create";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        service.delete(id);
        return "redirect:/";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Accident accident) {
        int id = accident.getType().getId();
        accident.setType(typeList.get(id - 1));
        service.create(accident);
        return "redirect:/index";
    }

    @GetMapping("/modify/{id}")
    public String getModifyPage(@PathVariable int id, Model model) {
        model.addAttribute("accident", service.getAccient(id))
                .addAttribute("types", typeList);
        return "modify";
    }

    @PostMapping("/modify")
    public String modify(@ModelAttribute Accident accident) {
        int id = accident.getType().getId();
        accident.setType(typeList.get(id - 1));
        service.modify(accident);
        return "redirect:/index";
    }
}