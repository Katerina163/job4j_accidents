package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentRepository;
import ru.job4j.accidents.repository.RuleRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
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
        accident.setRules(ruleRepository.getRules(ids));
        return repository.create(accident);
    }

    @Override
    public boolean delete(int id) {
        return repository.delete(id);
    }

    @Override
    public boolean modify(Accident accident, String[] ids) {
        accident.setRules(ruleRepository.getRules(ids));
        return repository.modify(accident);
    }
}
