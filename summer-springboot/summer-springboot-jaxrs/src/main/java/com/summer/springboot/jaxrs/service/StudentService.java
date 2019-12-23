package com.summer.springboot.jaxrs.service;


import com.summer.springboot.jaxrs.entity.Student;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

@Path("students")
@Produces(MediaType.APPLICATION_JSON)
public interface StudentService {

    @GET
    public Collection<Student> getAllStudents();

    @Path("{id}")
    @GET
    public Response getById(@PathParam("id") Long id);


}
