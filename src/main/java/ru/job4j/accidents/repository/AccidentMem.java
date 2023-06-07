package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class AccidentMem implements AccidentRepository {
    private List<Accident> list;

    public AccidentMem() {
        list = new CopyOnWriteArrayList<>();
        var random = new Random();
        for (int i = 0; i < 11; i++) {
            var accident = new Accident();
            accident.setId(i);
            accident.setName("Имя под номером " + i);
            accident.setAddress("Ул. Летняя, дом №" + i);
            accident.setText("Случилось непонятное в количестве " + random.nextInt(i * 10 + 1));
            list.add(accident);
        }
    }

    @Override
    public Accident getAccient(int id) {
        for (var a : list) {
            if (a.getId() == id) {
                return a;
            }
        }
        return null;
    }

    @Override
    public List<Accident> getList() {
        return list;
    }

    @Override
    public void create(Accident accident) {
        list.add(accident);
    }

    @Override
    public void delete(int id) {
        for (var accident : list) {
            if (accident.getId() == id) {
                list.remove(id);
                break;
            }
        }
    }

    @Override
    public void modify(Accident accident) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == accident.getId()) {
                list.set(i, accident);
                break;
            }
        }
    }
}
