package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Repository
public class AccidentMem implements AccidentRepository {
    private List<Accident> list;

    public AccidentMem() {
        list = new ArrayList<>();
        var random = new Random();
        for (int i = 1; i < 11; i++) {
            var accident = new Accident();
            accident.setId(i);
            accident.setName("Имя под номером " + i);
            accident.setAddress("Ул. Летняя, дом №" + i);
            accident.setText("Случилось непонятное в количестве " + random.nextInt(i * 10));
            list.add(accident);
        }
    }

    @Override
    public Accident getAccient(int id) {
        return list.get(id);
    }

    @Override
    public List<Accident> getList() {
        return list;
    }
}
