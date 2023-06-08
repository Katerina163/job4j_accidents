package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class RuleRepositoryMem implements RuleRepository {
    private Map<Integer, Rule> map;
    private AtomicInteger integer = new AtomicInteger(0);

    public RuleRepositoryMem() {
        map = new ConcurrentHashMap<>();
        map.put(integer.getAndIncrement(), new Rule(integer.get(), "Статья. 1"));
        map.put(integer.getAndIncrement(), new Rule(integer.get(), "Статья. 2"));
        map.put(integer.getAndIncrement(), new Rule(integer.get(), "Статья. 3"));
    }

    @Override
    public Optional<Rule> getRule(int id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Map<Integer, Rule> getMap() {
        return new ConcurrentHashMap<>(map);
    }
}
