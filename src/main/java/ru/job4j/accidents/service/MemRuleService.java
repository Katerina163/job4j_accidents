package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleRepository;

import java.util.Map;
import java.util.Optional;

@Service
public class MemRuleService implements RuleService {
    private RuleRepository repository;

    public MemRuleService(RuleRepository ruleRepositoryMem) {
        repository = ruleRepositoryMem;
    }

    @Override
    public Optional<Rule> getRule(int id) {
        return repository.getRule(id);
    }

    @Override
    public Map<Integer, Rule> getMap() {
        return repository.getMap();
    }
}
