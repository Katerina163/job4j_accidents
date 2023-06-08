package ru.job4j.accidents.service;

import ru.job4j.accidents.model.AccidentType;

import java.util.Map;
import java.util.Optional;

public interface AccidentTypeService {
    Optional<AccidentType> getAccidentType(int id);

    Map<Integer, AccidentType> getMap();
}
