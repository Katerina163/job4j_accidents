package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HiberAccidentRepository implements AccidentRepository {
    private CrudRepository crud;

    @Override
    public Optional<Accident> findById(int id) {
        return Optional.of(crud.tx(session -> session.find(Accident.class, id)));
    }

    @Override
    public List<Accident> findAll() {
        return crud.query("from Accident", Accident.class);
    }

    @Override
    public Accident create(Accident accident) {
        crud.run(session -> session.save(accident));
        return accident;
    }

    @Override
    public void delete(int id) {
        crud.run("delete from Accident where id = :fId", Map.of("fId", id));
    }

    @Override
    public void modify(Accident accident) {
        crud.run(session -> session.update(accident));
    }
}