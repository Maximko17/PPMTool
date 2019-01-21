package com.example.demo.controller;

import com.example.demo.domain.Customer;
import com.example.demo.exceptions.CustomerNotFoundException;
import com.example.demo.repositories.CustomerRepository;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "/v2")
public class CustomerController {

    private CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping
    ResponseEntity<Resources<Object>> root(){
        Resources<Object> objects = new Resources<>(Collections.emptyList());
        URI uri = MvcUriComponentsBuilder
                .fromMethodCall(MvcUriComponentsBuilder.on(getClass()).getAllCustomers()).build().toUri();
        Link link = new Link(uri.toString(),"customers");

        ControllerLinkBuilder linkTo = ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(this.getClass()).deleteCustomer(1L));

        objects.add(linkTo.withRel("all-customers"));
        objects.add(link);
        return ResponseEntity.ok(objects);
    }

    @GetMapping("/customers")
    ResponseEntity<List<Customer>> getAllCustomers(){

        return ResponseEntity.ok(customerRepository.findAll());
    }

    @GetMapping("/{id}")
    ResponseEntity<Resource<Customer>> getCustomer(@PathVariable Long id){
        Customer customer = customerRepository.findById(id).orElseThrow(()->new CustomerNotFoundException("Customer with id " + id + " not found"));
        Resource<Customer> resource = new Resource<>(customer);
        ControllerLinkBuilder linkTo = ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(this.getClass()).getAllCustomers());
        resource.add(linkTo.withRel("all-customers"));
        return ResponseEntity.ok(resource);
    }

    @PostMapping
    ResponseEntity<Customer> newCustomer(@RequestBody Customer customer){

        Customer customerToSave = customerRepository.save(new Customer(customer.getFirstName(),customer.getLastName()));

        URI uri = MvcUriComponentsBuilder.fromController(getClass()).path("/{id}").buildAndExpand(customer.getId()).toUri();

        return ResponseEntity.created(uri).body(customerToSave);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteCustomer(@PathVariable Long id){
        return customerRepository.findById(id).map(c->{
            customerRepository.delete(c);
            return ResponseEntity.noContent().build();
        }).orElse(null);
    }

    @PutMapping("/{id}")
    ResponseEntity<Customer> updateCustomer(@PathVariable Long id,@RequestBody Customer customer){
        return customerRepository
                .findById(id)
                .map(
                        existing->{
                            Customer updatedCustomer = customerRepository.save(new Customer(existing.getId(),customer.getFirstName(),customer.getLastName()));
                            URI selfLink = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
                            return ResponseEntity.created(selfLink).body(updatedCustomer);
                        }
                ).orElseThrow(()->new CustomerNotFoundException("Customer with id" + id + "not found"));
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.HEAD)
    ResponseEntity<?> checkCustomer(@PathVariable Long id){
        return customerRepository.findById(id).map(existing->ResponseEntity.noContent().build()).orElse(null);
    }

    @RequestMapping(method = RequestMethod.OPTIONS)
    ResponseEntity<?> options(){
        return ResponseEntity
                .ok()
                .allow(HttpMethod.GET,HttpMethod.DELETE,
                        HttpMethod.OPTIONS,HttpMethod.HEAD,
                        HttpMethod.PUT,HttpMethod.POST)
                .build();
    }
}
