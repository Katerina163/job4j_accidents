package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.*;

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

    @Override
    public Set<Rule> getRules(String[] ids) {
        String inSql = String.join(", ", Collections.nCopies(ids.length, "?"));
        Integer[] arr = Arrays.stream(ids).map(Integer::parseInt).toArray(Integer[]::new);
        List<Rule> list = jdbc.query(
                String.format("select * from rules where id in (%s)", inSql),
                actorRowMapper, arr);
        return new HashSet<>(list);
    }
}
