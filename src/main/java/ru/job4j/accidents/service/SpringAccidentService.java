package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.SpringAccidentRepository;
import ru.job4j.accidents.repository.SpringRuleRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SpringAccidentService implements AccidentService {
    private SpringAccidentRepository repository;
    private SpringRuleRepository ruleRepository;

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
        accident.setRules(ruleRepository.findAllByIdIn(toList(ids)));
        return repository.save(accident);
    }

    @Override
    public void delete(int id) {
        var accident = new Accident();
        accident.setId(id);
        repository.delete(accident);
    }

    @Override
    public void modify(Accident accident, String[] ids) {
        accident.setRules(ruleRepository.findAllByIdIn(toList(ids)));
        repository.save(accident);
    }

    private List<Integer> toList(String[] ids) {
        return Arrays.stream(ids).map(Integer::parseInt).toList();
    }
}
