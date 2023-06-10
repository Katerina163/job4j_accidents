package ru.job4j.accidents.service;

import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Optional;

public interface AccidentService {
    Optional<Accident> findById(int id);

    List<Accident> findAll();

    Accident create(Accident accident, String[] ids);

    void delete(int id);

    void modify(Accident accident, String[] ids);
}
