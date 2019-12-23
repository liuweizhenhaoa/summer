package com.summer.springboot.jaxrs.exceptions;

import org.springframework.beans.factory.annotation.Value;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class StudentNotFoundExceptionMapper implements ExceptionMapper<StudentNotFoundException> {

    @Value("${message.studentNotfound}")
    String message;

    @Override
    public Response toResponse(StudentNotFoundException e) {
        return Response.serverError().entity(message).type(MediaType.APPLICATION_JSON).build();
    }
}
