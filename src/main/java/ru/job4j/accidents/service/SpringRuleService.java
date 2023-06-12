package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.SpringRuleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SpringRuleService implements RuleService {
    private SpringRuleRepository repository;

    @Override
    public Optional<Rule> findById(int id) {
        return repository.findById(id);
    }

    @Override
    public List<Rule> findAll() {
        List<Rule> list = new ArrayList<>();
        repository.findAll().forEach(list::add);
        return list;
    }
}
