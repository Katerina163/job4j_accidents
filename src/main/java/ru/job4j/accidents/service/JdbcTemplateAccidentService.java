package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentRepository;
import ru.job4j.accidents.repository.RuleRepository;

import javax.transaction.Transactional;
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

    @Transactional
    @Override
    public Optional<Accident> findById(int id) {
        return repository.findById(id);
    }

    @Transactional
    @Override
    public List<Accident> findAll() {
        return repository.findAll();
    }

    @Transactional
    @Override
    public Accident create(Accident accident, String[] ids) {
        accident.setRules(ruleRepository.getRules(ids));
        return repository.create(accident);
    }

    @Transactional
    @Override
    public boolean delete(int id) {
        return repository.delete(id);
    }

    @Transactional
    @Override
    public boolean modify(Accident accident, String[] ids) {
        accident.setRules(ruleRepository.getRules(ids));
        return repository.modify(accident);
    }
}
