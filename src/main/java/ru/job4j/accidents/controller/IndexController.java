package ru.job4j.accidents.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.service.AccidentService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Controller
public class IndexController {
    private AccidentService service;

    private List<AccidentType> typeList;

    private List<Rule> rules;

    public IndexController(AccidentService memAccidentService) {
        service = memAccidentService;
        var types = new ArrayList<AccidentType>();
        types.add(new AccidentType(1, "Две машины"));
        types.add(new AccidentType(2, "Машина и человек"));
        types.add(new AccidentType(3, "Машина и велосипед"));
        typeList = new CopyOnWriteArrayList<>(types);
        rules = List.of(
                new Rule(1, "Статья. 1"),
                new Rule(2, "Статья. 2"),
                new Rule(3, "Статья. 3")
        );
        for (var accident : service.getList()) {
            accident.setType(typeList.get(1));
            accident.getRules().add(rules.get(1));
        }
    }

    @GetMapping({"/", "/index"})
    public String getIndexPage(Model model) {
        model.addAttribute("accidents", service.getList());
        return "index";
    }

    @GetMapping("/save")
    public String getCreatePage(Model model) {
        model.addAttribute("types", typeList)
                .addAttribute("rules", rules);
        return "create";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        service.delete(id);
        return "redirect:/";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Accident accident, HttpServletRequest req) {
        setRules(accident, req.getParameterValues("rIds"));
        int id = accident.getType().getId();
        accident.setType(typeList.get(id));
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
        model.addAttribute("accident", service.getAccient(id))
                .addAttribute("types", typeList)
                .addAttribute("rules", rules);
        return "modify";
    }

    @PostMapping("/modify")
    public String modify(@ModelAttribute Accident accident, HttpServletRequest req) {
        setRules(accident, req.getParameterValues("rIds"));
        int id = accident.getType().getId();
        accident.setType(typeList.get(id - 1));
        service.modify(accident);
        return "redirect:/index";
    }
}