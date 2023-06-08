package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Optional;

public interface AccidentRepository {
    Optional<Accident> getAccient(int id);

    List<Accident> getAll();

    Accident create(Accident accident);

    boolean delete(int id);

    boolean modify(Accident accident);
}
