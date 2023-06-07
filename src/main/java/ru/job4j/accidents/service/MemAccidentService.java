package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentRepository;

import java.util.List;

@Service
public class MemAccidentService implements AccidentService {
    private AccidentRepository repository;

    public MemAccidentService(AccidentRepository accidentMem) {
        repository = accidentMem;
    }

    @Override
    public Accident getAccient(int id) {
        return repository.getAccient(id);
    }

    @Override
    public List<Accident> getList() {
        return repository.getList();
    }

    @Override
    public void create(Accident accident) {
        repository.create(accident);
    }

    @Override
    public void delete(int id) {
        repository.delete(id);
    }

    @Override
    public void modify(Accident accident) {
        repository.modify(accident);
    }
}