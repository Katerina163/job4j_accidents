package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class JdbcTemplateAccidentService implements AccidentService {
    private AccidentRepository repository;

    public JdbcTemplateAccidentService(AccidentRepository jdbcTemplateAccidentRepository) {
        repository = jdbcTemplateAccidentRepository;
    }

    @Override
    public Optional<Accident> findById(int id) {
        return repository.findById(id);
    }

    @Override
    public List<Accident> findAll() {
        return repository.findAll();
    }

    @Override
    public Accident create(Accident accident, String[] ids) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return repository.delete(id);
    }

    @Override
    public boolean modify(Accident accident, String[] ids) {
        return false;
    }
}
