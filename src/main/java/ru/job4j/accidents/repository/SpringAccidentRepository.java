package ru.job4j.accidents.repository;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Optional;

public interface SpringAccidentRepository extends CrudRepository<Accident, Integer> {

    @Nonnull
    @EntityGraph(value = "accident-with-rules", type = EntityGraph.EntityGraphType.LOAD)
    Optional<Accident> findById(@Nonnull Integer integer);

    @Nonnull
    @EntityGraph(value = "accident-with-rules", type = EntityGraph.EntityGraphType.LOAD)
    List<Accident> findAll();
}
