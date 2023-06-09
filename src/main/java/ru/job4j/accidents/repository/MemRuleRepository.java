package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class MemRuleRepository implements RuleRepository {
    private Map<Integer, Rule> map = new ConcurrentHashMap<>();
    private AtomicInteger integer = new AtomicInteger(1);

    public MemRuleRepository() {
        map.put(integer.get(), new Rule(integer.get(), "Статья. 1"));
        map.put(integer.incrementAndGet(), new Rule(integer.get(), "Статья. 2"));
        map.put(integer.incrementAndGet(), new Rule(integer.get(), "Статья. 3"));
    }

    @Override
    public Optional<Rule> findById(int id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public boolean setRules(Accident accident, String[] ids) {
        for (var id : ids) {
            accident.getRules().add(map.get(Integer.parseInt(id)));
        }
        return !accident.getRules().isEmpty();
    }

    @Override
    public List<Rule> findAll() {
        return new ArrayList<>(map.values());
    }
}
