package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem implements AccidentRepository {
    private Map<Integer, Accident> map;
    private AtomicInteger integer = new AtomicInteger(0);

    public AccidentMem() {
        map = new ConcurrentHashMap<>();
        for (int i = 0; i < 11; i++) {
            var accident = new Accident();
            accident.setId(integer.get());
            accident.setName("Имя под номером " + i);
            accident.setAddress("Ул. Летняя, дом №" + i);
            accident.setText("Случилось непонятное в количестве " + i + 1);
            map.put(integer.getAndIncrement(), accident);
        }
    }

    @Override
    public Optional<Accident> getAccient(int id) {
        return Optional.of(map.get(id));
    }

    @Override
    public Map<Integer, Accident> getMap() {
        return new ConcurrentHashMap<>(map);
    }

    @Override
    public void create(Accident accident) {
        map.put(accident.getId(), accident);
    }

    @Override
    public void delete(int id) {
        map.remove(id);
    }

    @Override
    public void modify(Accident accident) {
        map.replace(accident.getId(), accident);
    }
}
