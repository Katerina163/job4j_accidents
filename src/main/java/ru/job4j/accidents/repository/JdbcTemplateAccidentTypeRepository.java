package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class JdbcTemplateAccidentTypeRepository implements AccidentTypeRepository {
    private final JdbcTemplate jdbc;
    private final RowMapper<AccidentType> actorRowMapper = (resultSet, rowNum) -> {
        var type = new AccidentType();
        type.setId(resultSet.getInt("id"));
        type.setName(resultSet.getString("name"));
        return type;
    };

    @Override
    public Optional<AccidentType> getAccidentType(int id) {
        return Optional.ofNullable(
                jdbc.queryForObject(
                        "select * from types where id = ?",
                        actorRowMapper, (long) id));
    }

    @Override
    public List<AccidentType> findAll() {
        return jdbc.query(
                "select * from types",
                actorRowMapper);
    }
}
