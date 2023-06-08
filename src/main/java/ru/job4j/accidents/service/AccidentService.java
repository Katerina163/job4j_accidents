package ru.job4j.accidents.service;

import ru.job4j.accidents.model.Accident;

import java.util.Map;
import java.util.Optional;

public interface AccidentService {
    Optional<Accident> getAccient(int id);

    Map<Integer, Accident> getMap();

    void create(Accident accident);

    void delete(int id);

    void modify(Accident accident);
}
