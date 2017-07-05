package com.redislabs.springboot.service;

import org.springframework.stereotype.Service;

@Service

public class StubDBService {

    public Customer getCustomer(String id) {
        return new Customer(id, "firstname-" + id, "secondname-" + id, 50 );

    }
}
