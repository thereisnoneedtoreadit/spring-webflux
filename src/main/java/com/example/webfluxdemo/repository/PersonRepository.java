package com.example.webfluxdemo.repository;

import com.example.webfluxdemo.model.Person;

import java.util.List;

public interface PersonRepository {

    List<Person> findAll();

    Person updateOne(Person person);

    Person save(Person person);

    Long delete(Long id);

}
