package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.SpringAccidentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SpringAccidentService implements AccidentService {
    private SpringAccidentRepository repository;

    @Override
    public Optional<Accident> findById(int id) {

        return repository.findById(id);
    }

    @Override
    public List<Accident> findAll() {
        List<Accident> list = new ArrayList<>();
        repository.findAll().forEach(list::add);
        return list;
    }

    @Override
    public Accident create(Accident accident, String[] ids) {
        setRules(accident, ids);
        return repository.save(accident);
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
        var accident = new Accident();
        accident.setId(id);
        repository.delete(accident);
    }

    @Override
    public void modify(Accident accident, String[] ids) {
        setRules(accident, ids);
        repository.save(accident);
    }
}
