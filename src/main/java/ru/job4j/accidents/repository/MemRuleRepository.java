package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
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
    public Optional<Rule> getRule(int id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public List<Rule> getAll() {
        return new ArrayList<>(map.values());
    }
}
