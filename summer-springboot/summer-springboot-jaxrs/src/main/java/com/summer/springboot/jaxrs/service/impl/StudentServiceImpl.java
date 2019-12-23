package com.summer.springboot.jaxrs.service.impl;

import java.util.Collection;
import javax.ws.rs.core.Response;
import com.summer.springboot.jaxrs.dao.StudentRepository;
import com.summer.springboot.jaxrs.entity.Student;
import com.summer.springboot.jaxrs.exceptions.StudentNotFoundException;
import com.summer.springboot.jaxrs.service.StudentService;

public class StudentServiceImpl implements StudentService {
     
    private final StudentRepository repository;

    public StudentServiceImpl(StudentRepository repository) {
        this.repository = repository;
    }
 
    @Override
    public Collection<Student> getAllStudents() {
        return repository.findAll();
    }
 
    @Override
    public Response getById(Long id) {
        Student student = repository.findById(id).orElseThrow(StudentNotFoundException::new);
        return Response.ok(student).build();
    }
 
}