package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentRepository;

import java.util.Map;
import java.util.Optional;

@Service
public class MemAccidentService implements AccidentService {
    private AccidentRepository repository;
    private AccidentTypeService accidentTypeService;
    private RuleService ruleService;

    public MemAccidentService(AccidentRepository accidentMem, AccidentTypeService memAccidentTypeService,
                              RuleService memRuleService) {
        repository = accidentMem;
        accidentTypeService = memAccidentTypeService;
        ruleService = memRuleService;
    }

    @Override
    public Optional<Accident> getAccient(int id) {
        return repository.getAccient(id);
    }

    @Override
    public Map<Integer, Accident> getMap() {
        var map = repository.getMap();
        for (var accident : map.values()) {
            if (accident.getType() == null) {
                accident.setType(accidentTypeService.getAccidentType(1).get());
            }
            if (accident.getRules().isEmpty()) {
                accident.getRules().add(ruleService.getRule(1).get());
            }
        }
        return map;
    }

    @Override
    public Accident create(Accident accident) {
        return repository.create(accident);
    }

    @Override
    public boolean delete(int id) {
        return repository.delete(id);
    }

    @Override
    public boolean modify(Accident accident) {
        return repository.modify(accident);
    }
}