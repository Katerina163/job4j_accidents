package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class JdbcTemplateAccidentRepository implements AccidentRepository {
    private final JdbcTemplate jdbc;
    private final RowMapper<Accident> actorRowMapper = (resultSet, rowNum) -> {
        var accident = new Accident();
        accident.setId(resultSet.getInt("id"));
        accident.setName(resultSet.getString("name"));
        accident.setText(resultSet.getString("text"));
        accident.setAddress(resultSet.getString("address"));
        return accident;
    };

    @Override
    public Optional<Accident> getAccident(int id) {
        return Optional.ofNullable(
                jdbc.queryForObject(
                        "select * from accidents where id = ?",
                        actorRowMapper, (long) id));
    }

    @Override
    public List<Accident> getAll() {
        return jdbc.query(
                "select * from accidents",
                actorRowMapper);
    }

    @Override
    public Accident create(Accident accident) {
        jdbc.update(
                "insert into accidents(name, text, address) values (?, ?, ?)",
                accident.getName(), accident.getText(), accident.getAddress());
        return accident;
    }

    @Override
    public boolean delete(int id) {
        return jdbc.update(
                "delete from accidents where id = ?",
                (long) id) == 1;
    }

    @Override
    public boolean modify(Accident accident) {
        return jdbc.update("update accidents set name = ?, text = ?, address = ? where id = ?",
                accident.getName(), accident.getText(), accident.getAddress(), accident.getId()) == 1;
    }
}