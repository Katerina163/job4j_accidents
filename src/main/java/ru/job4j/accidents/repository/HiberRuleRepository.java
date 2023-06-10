package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.*;

@AllArgsConstructor
@Repository
public class HiberRuleRepository implements RuleRepository {
    private CrudRepository crud;

    @Override
    public Optional<Rule> findById(int id) {
        return Optional.of(crud.tx(session -> session.find(Rule.class, id)));
    }

    @Override
    public List<Rule> findAll() {
        return crud.query("from Rule", Rule.class);
    }

    @Override
    public Set<Rule> getRules(String[] ids) {
        String s = String.format("select * from rules where id in (%s)", String.join(", ", ids));
        List<Rule> list = crud.tx(session -> session.createSQLQuery(s).list());
        return new HashSet<>(list);
    }
}
