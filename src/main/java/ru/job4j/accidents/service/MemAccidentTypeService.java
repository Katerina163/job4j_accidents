package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeRepository;

import java.util.Map;
import java.util.Optional;

@Service
public class MemAccidentTypeService implements AccidentTypeService {
    private AccidentTypeRepository repository;

    public MemAccidentTypeService(AccidentTypeRepository accidentTypeMem) {
        repository = accidentTypeMem;
    }

    @Override
    public Optional<AccidentType> getAccidentType(int id) {
        return repository.getAccidentType(id);
    }

    @Override
    public Map<Integer, AccidentType> getMap() {
        return repository.getMap();
    }
}
