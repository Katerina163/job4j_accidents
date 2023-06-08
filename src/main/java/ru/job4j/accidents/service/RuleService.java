package ru.job4j.accidents.service;

import ru.job4j.accidents.model.Rule;

import java.util.Map;
import java.util.Optional;

public interface RuleService {
    Optional<Rule> getRule(int id);

    Map<Integer, Rule> getMap();
}
