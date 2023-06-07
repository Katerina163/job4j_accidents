package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.Accident;

import java.util.List;

public interface AccidentRepository {
    Accident getAccient(int id);

    List<Accident> getList();

    void create(Accident accident);

    void delete(int id);

    void modify(Accident accident);
}
