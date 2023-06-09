package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
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
    public boolean setRules(Accident accident, String[] ids) {
        var rules = jdbc.update(
                "delete from accidents_rules where accident_id = ?",
                (long) accident.getId());
        int ruleInsert = 0;
        for (var rule : ids) {
            ruleInsert += jdbc.update(
                    "insert into accidents_rules(accident_id, rule_id) values (?, ?)",
                    accident.getId(), Long.parseLong(rule));
        }
        Rule ruleById = null;
        for (var id : ids) {
            ruleById = jdbc.queryForObject(
                    "select * from rules where id = ?",
                    actorRowMapper, Long.parseLong(id));
            accident.getRules().add(ruleById);
        }
        return rules >= 1 && ruleInsert >= 1 && ruleById != null;
    }
}
