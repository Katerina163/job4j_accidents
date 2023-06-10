package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.AccidentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class HiberAccidentService implements AccidentService {
    private AccidentRepository repository;

    public HiberAccidentService(AccidentRepository hiberAccidentRepository) {
        repository = hiberAccidentRepository;
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
        setRules(accident, ids);
        return repository.create(accident);
    }

    private void setRules(Accident accident, String[] ids) {
        for (var s : ids) {
            var rule = new Rule();
            rule.setId(Integer.parseInt(s));
            accident.getRules().add(rule);
        }
    }

    @Override
    public void delete(int id) {
        repository.delete(id);
    }

    @Override
    public void modify(Accident accident, String[] ids) {
        setRules(accident, ids);
        repository.modify(accident);
    }
}
