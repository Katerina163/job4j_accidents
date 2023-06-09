package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;

import java.util.List;
import java.util.Optional;

public interface RuleRepository {
    Optional<Rule> findById(int id);

    List<Rule> findAll();

    boolean setRules(Accident accident, String[] ids);
}
