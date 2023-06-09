package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentRepository;
import ru.job4j.accidents.repository.RuleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class JdbcTemplateAccidentService implements AccidentService {
    private AccidentRepository repository;
    private RuleRepository ruleRepository;

    public JdbcTemplateAccidentService(AccidentRepository jdbcTemplateAccidentRepository,
                                       RuleRepository jdbcTemplateRuleRepository) {
        repository = jdbcTemplateAccidentRepository;
        ruleRepository = jdbcTemplateRuleRepository;
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
        repository.create(accident);
        ruleRepository.setRules(accident, ids);
        return accident;
    }

    @Override
    public boolean delete(int id) {
        return repository.delete(id);
    }

    @Override
    public boolean modify(Accident accident, String[] ids) {
        var accid = repository.modify(accident);
        var rule = ruleRepository.setRules(accident, ids);
        return accid && rule;
    }
}
