package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class MemAccidentTypeRepository implements AccidentTypeRepository {
    private Map<Integer, AccidentType> map = new ConcurrentHashMap<>();
    private AtomicInteger integer = new AtomicInteger(0);

    public MemAccidentTypeRepository() {
        map.put(integer.get(), new AccidentType(integer.get(), "Две машины"));
        map.put(integer.incrementAndGet(), new AccidentType(integer.get(), "Машина и человек"));
        map.put(integer.incrementAndGet(), new AccidentType(integer.get(), "Машина и велосипед"));
    }

    @Override
    public Optional<AccidentType> getAccidentType(int id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Map<Integer, AccidentType> getMap() {
        return new ConcurrentHashMap<>(map);
    }
}