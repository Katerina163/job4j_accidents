package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MemAccidentService implements AccidentService {
    private AccidentRepository repository;
    private AccidentTypeService accidentTypeService;
    private RuleService ruleService;

    public MemAccidentService(AccidentRepository memAccidentRepository, AccidentTypeService memAccidentTypeService,
                              RuleService memRuleService) {
        repository = memAccidentRepository;
        accidentTypeService = memAccidentTypeService;
        ruleService = memRuleService;
    }

    @Override
    public Optional<Accident> getAccident(int id) {
        return repository.getAccident(id);
    }

    @Override
    public List<Accident> findAll() {
        return repository.findAll();
    }

    @Override
    public Accident create(Accident accident, String[] ids) {
        setTypeAndRules(accident, ids);
        return repository.create(accident);
    }

    private void setTypeAndRules(Accident accident, String[] ids) {
        int id = accident.getType().getId();
        accident.setType(accidentTypeService.getAccidentType(id).get());
        for (var s : ids) {
            accident.getRules().add(ruleService.getRule(Integer.parseInt(s)).get());
        }
    }

    @Override
    public boolean delete(int id) {
        return repository.delete(id);
    }

    @Override
    public boolean modify(Accident accident, String[] ids) {
        setTypeAndRules(accident, ids);
        return repository.modify(accident);
    }
}