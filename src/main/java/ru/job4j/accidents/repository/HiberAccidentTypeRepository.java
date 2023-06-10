package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HiberAccidentTypeRepository implements AccidentTypeRepository {
    private CrudRepository crud;

    @Override
    public Optional<AccidentType> findById(int id) {
        return  Optional.of(crud.tx(session -> session.find(AccidentType.class, id)));
    }

    @Override
    public List<AccidentType> findAll() {
        return crud.query("from AccidentType", AccidentType.class);
    }
}
