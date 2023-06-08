package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class MemAccidentRepository implements AccidentRepository {
    private Map<Integer, Accident> map = new ConcurrentHashMap<>();
    private AtomicInteger integer = new AtomicInteger(1);

    public MemAccidentRepository() {
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
    public Optional<Accident> getAccident(int id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public List<Accident> getAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public Accident create(Accident accident) {
        accident.setId(integer.getAndIncrement());
        return map.put(accident.getId(), accident);
    }

    @Override
    public boolean delete(int id) {
        return map.remove(id) != null;
    }

    @Override
    public boolean modify(Accident accident) {
        return map.replace(accident.getId(), accident) != null;
    }
}
