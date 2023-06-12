package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.SpringAccidentTypeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SpringAccidentTypeService implements AccidentTypeService {
    private SpringAccidentTypeRepository repository;

    @Override
    public Optional<AccidentType> findById(int id) {
        return repository.findById(id);
    }

    @Override
    public List<AccidentType> findAll() {
        List<AccidentType> list = new ArrayList<>();
        repository.findAll().forEach(list::add);
        return list;
    }
}
