package com.redislabs.springboot.service;


import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "customers")
public interface CustomerRedisRepo extends CrudRepository<Customer, String> {


}
