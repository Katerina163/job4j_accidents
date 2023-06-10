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
        accident.setId(resultSet.getInt("aid"));
        accident.setName(resultSet.getString("aname"));
        accident.setText(resultSet.getString("text"));
        accident.setAddress(resultSet.getString("address"));
        var type = new AccidentType();
        type.setId(resultSet.getInt("tid"));
        type.setName(resultSet.getString("tname"));
        accident.setType(type);
        return accident;
    };
    private final RowMapper<Rule> actorRowMapperRule = (resultSet, rowNum) -> {
        var type = new Rule();
        type.setId(resultSet.getInt("rulesid"));
        type.setName(resultSet.getString("name"));
        return type;
    };

    @Override
    public Optional<Accident> findById(int id) {
        String sql = "select a.id as aid, a.name as aname, text, address, t.id as tid, t.name as tname from accidents as a"
                + " left join types t on a.type_id = t.id where a.id = ?";
        var accident = jdbc.queryForObject(sql, actorRowMapperAccident, (long) id);
        if (accident == null) {
            return Optional.empty();
        }
        getListRules(accident);
        return Optional.of(accident);
    }

    private void getListRules(Accident accident) {
        String sql = "select rules.id as rulesid, name from rules left join accidents_rules"
                + " ar on rules.id = ar.rule_id where accident_id = ?";
        var listRules = jdbc.query(sql, actorRowMapperRule, (long) accident.getId());
        if (!listRules.isEmpty()) {
            accident.getRules().addAll(listRules);
        }
    }

    @Override
    public List<Accident> findAll() {
        String sql = "select a.id as aid, a.name as aname, text, address, t.id as tid, t.name as tname "
                + " from accidents as a left join types t on a.type_id = t.id";
        var result = jdbc.query(sql, actorRowMapperAccident);
        for (var accident : result) {
            getListRules(accident);
        }
        return result;
    }

    @Override
    public Accident create(Accident accident) {
        jdbc.update(
            "delete from accidents_rules where accident_id = ?",
            (long) accident.getId());
        jdbc.update(
                "insert into accidents(name, text, address, type_id) values (?, ?, ?, ?)",
                accident.getName(), accident.getText(), accident.getAddress(), accident.getType().getId());
        var id = jdbc.queryForObject("select id from accidents where name = ? and text = ? and address = ?",
                Integer.class, accident.getName(), accident.getText(), accident.getAddress());
        if (id == null || id.intValue() == 0) {
            return null;
        }
        accident.setId(id);
        for (var rule : accident.getRules()) {
            jdbc.update(
                    "insert into accidents_rules(accident_id, rule_id) values (?, ?)",
                    id, rule.getId());
        }
        return findById(id).get();
    }

    @Override
    public void delete(int id) {
        var rules = jdbc.update(
                "delete from accidents_rules where accident_id = ?",
                (long) id);
        var accident = jdbc.update(
                "delete from accidents where id = ?",
                (long) id);
    }

    @Override
    public void modify(Accident accident) {
        jdbc.update(
                "delete from accidents_rules where accident_id = ?",
                (long) accident.getId());
        var accidentCount = jdbc.update("update accidents set name = ?, text = ?, address = ?, type_id = ? where id = ?",
                accident.getName(), accident.getText(), accident.getAddress(), accident.getType().getId(), accident.getId());
        for (var rule : accident.getRules()) {
            jdbc.update(
                    "insert into accidents_rules(accident_id, rule_id) values (?, ?)",
                    accident.getId(), rule.getId());
        }
    }
}