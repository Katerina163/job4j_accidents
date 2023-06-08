package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class JdbcTemplateAccidentTypeService implements AccidentTypeService {
    private AccidentTypeRepository repository;

    public JdbcTemplateAccidentTypeService(AccidentTypeRepository jdbcTemplateAccidentTypeRepository) {
        repository = jdbcTemplateAccidentTypeRepository;
    }

    @Override
    public Optional<AccidentType> getAccidentType(int id) {
        return repository.getAccidentType(id);
    }

    @Override
    public List<AccidentType> getAll() {
        return repository.getAll();
    }
}
