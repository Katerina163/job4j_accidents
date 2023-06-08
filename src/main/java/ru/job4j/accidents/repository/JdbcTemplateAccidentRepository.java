package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.*;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class JdbcTemplateAccidentRepository implements AccidentRepository {
    private final JdbcTemplate jdbc;
    private final RowMapper<Accident> actorRowMapperAccident = (resultSet, rowNum) -> {
        var accident = new Accident();
        accident.setId(resultSet.getInt("a.id"));
        accident.setName(resultSet.getString("a.name"));
        accident.setText(resultSet.getString("text"));
        accident.setAddress(resultSet.getString("address"));
        var type = new AccidentType();
        type.setId(resultSet.getInt("t.id"));
        type.setName(resultSet.getString("t.name"));
        accident.setType(type);
        return accident;
    };
    private final RowMapper<Rule> actorRowMapperRule = (resultSet, rowNum) -> {
        var type = new Rule();
        type.setId(resultSet.getInt("rules.id"));
        type.setName(resultSet.getString("name"));
        return type;
    };

    @Override
    public Optional<Accident> getAccident(int id) {
        String sql = "select a.id, a.name, text, address, t.id, t.name from accidents as a"
                + " left join types t on a.type_id = t.id where a.id = ?";
        var accident = jdbc.queryForObject(sql, actorRowMapperAccident, (long) id);
        if (accident == null) {
            return Optional.empty();
        }
        getListRules(accident);
        return Optional.of(accident);
    }

    private void getListRules(Accident accident) {
        var listRules = jdbc.query(
                "select rules.id, name from rules left join accidents_rules ar on rules.id = ar.rule_id where accident_id = ?",
                actorRowMapperRule, (long) accident.getId());
        if (!listRules.isEmpty()) {
            accident.getRules().addAll(listRules);
        }
    }

    @Override
    public List<Accident> getAll() {
        var result = jdbc.query(
                "select a.id, a.name, text, address, t.id, t.name from accidents as a left join types t on a.type_id = t.id",
                actorRowMapperAccident);
        for (var accident : result) {
            getListRules(accident);
        }
        return result;
    }

    @Override
    public Accident create(Accident accident) {
        jdbc.update(
                "insert into accidents(name, text, address, type_id) values (?, ?, ?, ?)",
                accident.getName(), accident.getText(), accident.getAddress(), accident.getType().getId());
        var id = jdbc.queryForObject("select id from accidents where name = ? and text = ? and address = ?",
                Integer.class, accident.getName(), accident.getText(), accident.getAddress());
        if (id == null || id.intValue() == 0) {
            return null;
        }
        for (var rule : accident.getRules()) {
            jdbc.update(
                    "insert into accidents_rules(accident_id, rule_id) values (?, ?)",
                    id, rule.getId());
        }
        return getAccident(id).get();
    }

    @Override
    public boolean delete(int id) {
        var accident = jdbc.update(
                "delete from accidents where id = ?",
                (long) id);
        var rules = jdbc.update(
                "delete from accidents_rules where accident_id = ?",
                (long) id);
        return accident == 1 && rules == 1;
    }

    @Override
    public boolean modify(Accident accident) {
        var accidentCount = jdbc.update("update accidents set name = ?, text = ?, address = ?, type_id = ? where id = ?",
                accident.getName(), accident.getText(), accident.getAddress(), accident.getType().getId(), accident.getId());
        var rules = jdbc.update(
                "delete from accidents_rules where accident_id = ?",
                (long) accident.getId());
        int ruleInsert = 0;
        for (var rule : accident.getRules()) {
           ruleInsert += jdbc.update(
                    "insert into accidents_rules(accident_id, rule_id) values (?, ?)",
                    accident.getId(), rule.getId());
        }
        return accidentCount == 1 && rules == 1 && ruleInsert >= 1;
    }
}