package com.example.webfluxdemo.repository;

import com.example.webfluxdemo.exception.AlreadyExistsException;
import com.example.webfluxdemo.exception.NotFoundResourceException;
import com.example.webfluxdemo.model.Person;
import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PersonRepositoryImpl implements PersonRepository {

    private Map<Long, Person> data;

    public PersonRepositoryImpl() {
        data = new HashMap<>();
        data.put(1L, new Person(1L, "foo1", "bar1", 50));
        data.put(2L, new Person(2L, "foo2", "bar2", 50));
    }

    @Override
    public List<Person> findAll() {
        return new ArrayList<>(data.values());
    }

    @SneakyThrows
    @Override
    public Person updateOne(Person person) {
        if (data.get(person.getId()) == null) throw new NotFoundResourceException(person.getId().toString());
        data.put(person.getId(), person);
        return person;
    }

    @SneakyThrows
    @Override
    public Person save(Person person) {
        if (data.get(person.getId()) != null) throw new AlreadyExistsException("Person with id = " + person.getId() + " already exists");
        data.put(person.getId(), person);
        return person;
    }

    @SneakyThrows
    @Override
    public Long delete(Long id) {
        if (data.get(id) == null) throw new NotFoundResourceException(id.toString());
        data.remove(id);
        return id;
    }

}


