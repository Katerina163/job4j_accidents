package ru.job4j.accidents.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.service.AccidentService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class IndexController {
    private AccidentService service;

    private Map<Integer, AccidentType> map;

    public IndexController(AccidentService memAccidentService) {
        service = memAccidentService;
        map = new ConcurrentHashMap<>();
        map.put(1, new AccidentType(1, "Две машины"));
        map.put(2, new AccidentType(2, "Машина и человек"));
        map.put(3, new AccidentType(3, "Машина и велосипед"));
        for (var accident : service.getList()) {
            accident.setType(map.get(1));
        }
    }

    @GetMapping({"/", "/index"})
    public String getIndexPage(Model model) {
        model.addAttribute("accidents", service.getList());
        return "index";
    }

    @GetMapping("/save")
    public String getCreatePage(Model model) {
        model.addAttribute("types", map);
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
        accident.setType(map.get(id));
        service.create(accident);
        return "redirect:/index";
    }

    @GetMapping("/modify/{id}")
    public String getModifyPage(@PathVariable int id, Model model) {
        model.addAttribute("accident", service.getAccient(id))
                .addAttribute("types", map);
        return "modify";
    }

    @PostMapping("/modify")
    public String modify(@ModelAttribute Accident accident) {
        int id = accident.getType().getId();
        accident.setType(map.get(id));
        service.modify(accident);
        return "redirect:/index";
    }
}