package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class JdbcTemplateRuleService implements RuleService {
    private RuleRepository repository;

    public JdbcTemplateRuleService(RuleRepository jdbcTemplateRuleRepository) {
        repository = jdbcTemplateRuleRepository;
    }

    @Override
    public Optional<Rule> findById(int id) {
        return repository.findById(id);
    }

    @Override
    public List<Rule> findAll() {
        return repository.findAll();
    }
}
