package ru.job4j.accidents.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.Accident;

import java.util.Optional;

public interface SpringAccidentRepository extends CrudRepository<Accident, Integer> {

    @EntityGraph(value = "accident-with-rules", type = EntityGraph.EntityGraphType.LOAD)
    Optional<Accident> findById(Integer integer);

    @EntityGraph(value = "accident-with-rules", type = EntityGraph.EntityGraphType.LOAD)
    Iterable<Accident> findAll();
}
