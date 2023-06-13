package ru.job4j.accidents.repository;

import jakarta.annotation.Nonnull;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.Rule;

import java.util.List;

public interface SpringRuleRepository extends CrudRepository<Rule, Integer> {
    @Nonnull
    List<Rule> findAll();
}
