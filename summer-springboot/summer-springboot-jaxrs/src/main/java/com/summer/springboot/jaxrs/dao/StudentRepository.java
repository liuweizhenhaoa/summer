package com.summer.springboot.jaxrs.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;

import com.summer.springboot.jaxrs.entity.Student;
import org.springframework.stereotype.Repository;


@Repository
public class StudentRepository {

    Map<Long, Student> students = new HashMap<>();

    @PostConstruct
    public void init() {
        students.put(101L, new Student(101L, "Jane", "Doe", "Junior"));
        students.put(102L, new Student(102L, "Martin", "Fowler", "Senior"));
        students.put(103L, new Student(103L, "Roy", "Fielding", "Freshman"));
    }

    public Collection<Student> findAll() {
        return students.values();
    }

    public Optional<Student> findById(Long id) {
        Student student = null;
        if (students.containsKey(id)) student = students.get(id);
        return Optional.ofNullable(student);
    }
}