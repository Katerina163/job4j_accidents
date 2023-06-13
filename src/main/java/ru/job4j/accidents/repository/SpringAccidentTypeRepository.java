package ru.job4j.accidents.repository;

import jakarta.annotation.Nonnull;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.AccidentType;

import java.util.List;

public interface SpringAccidentTypeRepository extends CrudRepository<AccidentType, Integer> {

    @Nonnull
    List<AccidentType> findAll();
}
