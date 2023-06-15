package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.Authority;
import org.springframework.data.repository.CrudRepository;

public interface AuthorityRepository extends CrudRepository<Authority, Integer> {

    Authority findByAuthority(String authority);
}