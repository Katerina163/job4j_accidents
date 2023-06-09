package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class JdbcTemplateRuleRepository implements RuleRepository {
    private final JdbcTemplate jdbc;
    private final RowMapper<Rule> actorRowMapper = (resultSet, rowNum) -> {
        var type = new Rule();
        type.setId(resultSet.getInt("id"));
        type.setName(resultSet.getString("name"));
        return type;
    };

    @Override
    public Optional<Rule> findById(int id) {
        return Optional.ofNullable(
                jdbc.queryForObject(
                        "select * from rules where id = ?",
                        actorRowMapper, (long) id));
    }

    @Override
    public List<Rule> findAll() {
        return jdbc.query(
                "select * from rules",
                actorRowMapper);
    }
}
