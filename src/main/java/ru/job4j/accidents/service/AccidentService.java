package ru.job4j.accidents.service;

import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Optional;

public interface AccidentService {
    Optional<Accident> getAccident(int id);

    List<Accident> findAll();

    Accident create(Accident accident, String[] ids);

    boolean delete(int id);

    boolean modify(Accident accident, String[] ids);
}
