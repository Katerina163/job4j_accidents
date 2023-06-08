package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentRepository;

import java.util.Map;
import java.util.Optional;

@Service
public class MemAccidentService implements AccidentService {
    private AccidentRepository repository;

    public MemAccidentService(AccidentRepository accidentMem) {
        repository = accidentMem;
    }

    @Override
    public Optional<Accident> getAccient(int id) {
        return repository.getAccient(id);
    }

    @Override
    public Map<Integer, Accident> getMap() {
        return repository.getMap();
    }

    @Override
    public Accident create(Accident accident) {
        return repository.create(accident);
    }

    @Override
    public boolean delete(int id) {
        return repository.delete(id);
    }

    @Override
    public boolean modify(Accident accident) {
        return repository.modify(accident);
    }
}