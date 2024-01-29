package com.xisui.springbootneo4j.repository;

import com.xisui.springbootneo4j.doman.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

/**
 * name rule：
 * 1. name may be the same as the entity attribute name
 * 2. name may be the same as the entity children attribute name
 */
public interface PersonRepository extends Neo4jRepository<Person, Long> {
    Person findByName(String name);
    List<Person> findByTeammatesName(String name);
    List<Person> getByTeammatesName(String name);
}

