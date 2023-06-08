package ru.job4j.accidents.service;

import ru.job4j.accidents.model.Accident;

import java.util.Map;
import java.util.Optional;

public interface AccidentService {
    Optional<Accident> getAccient(int id);

    Map<Integer, Accident> getMap();

    Accident create(Accident accident);

    boolean delete(int id);

    boolean modify(Accident accident);
}
