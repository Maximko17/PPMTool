package com.example.demo.controller;

import com.example.demo.exceptions.CustomerNotFoundException;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@ControllerAdvice(annotations = RestController.class)
public class CustomerControllerAdvice {

    private MediaType vndErrorMediaType = MediaType.parseMediaType("application/vnd-error");

    @ExceptionHandler(CustomerNotFoundException.class)
    ResponseEntity<VndErrors> notFoundException(CustomerNotFoundException e){
        return error(e,HttpStatus.NOT_FOUND,"This customer not found");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<VndErrors> illegalArgumentException(CustomerNotFoundException e){
        return error(e,HttpStatus.NOT_FOUND,e.getLocalizedMessage());
    }


    private <E extends Exception> ResponseEntity<VndErrors> error(E error, HttpStatus httpStatus,String logs){
        String msg = Optional.of(error.getMessage()).orElse(error.getClass().getSimpleName());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(vndErrorMediaType);
        return new ResponseEntity<>(new VndErrors(logs,msg),httpHeaders,httpStatus);
    }
}
