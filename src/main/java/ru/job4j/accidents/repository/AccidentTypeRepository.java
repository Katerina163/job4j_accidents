package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.AccidentType;

import java.util.Map;
import java.util.Optional;

public interface AccidentTypeRepository {
    Optional<AccidentType> getAccidentType(int id);

    Map<Integer, AccidentType> getMap();
}