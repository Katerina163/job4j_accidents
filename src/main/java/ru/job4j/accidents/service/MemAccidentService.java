package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentRepository;
import ru.job4j.accidents.repository.AccidentTypeRepository;
import ru.job4j.accidents.repository.RuleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MemAccidentService implements AccidentService {
    private AccidentRepository repository;
    private AccidentTypeRepository accidentTypeRepository;
    private RuleRepository ruleRepository;

    public MemAccidentService(AccidentRepository memAccidentRepository, AccidentTypeRepository memAccidentTypeRepository,
                              RuleRepository memRuleRepository) {
        repository = memAccidentRepository;
        accidentTypeRepository = memAccidentTypeRepository;
        ruleRepository = memRuleRepository;
    }

    @Override
    public Optional<Accident> findById(int id) {
        return repository.findById(id);
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
        accident.setType(accidentTypeRepository.findById(id).get());
        accident.setRules(ruleRepository.getRules(ids));
    }

    @Override
    public void delete(int id) {
        repository.delete(id);
    }

    @Override
    public void modify(Accident accident, String[] ids) {
        setTypeAndRules(accident, ids);
        repository.modify(accident);
    }
}