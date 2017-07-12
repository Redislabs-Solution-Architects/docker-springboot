package com.redislabs.springboot.service;

import org.springframework.stereotype.Service;

@Service

public class StubDBService {

    public Customer getCustomer(String id) {
        System.out.println("Backing Customer service called");
        return new Customer(id, "firstname-" + id, "secondname-" + id, 50 );

    }

    public Account getAccount(String id) {
        System.out.println("Backing Account service called");
        return new Account(id, "accountname-" + id);
    }
}
