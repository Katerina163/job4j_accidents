package ru.job4j.accidents.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.Rule;

import java.util.List;
import java.util.Set;

public interface SpringRuleRepository extends CrudRepository<Rule, Integer> {

    List<Rule> findAll();

    Set<Rule> findAllByIdIn(List<Integer> ids);
}
