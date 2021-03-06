package com.redislabs.springboot;

import com.redislabs.springboot.service.Customer;
import com.redislabs.springboot.service.CustomerRedisRepo;
import com.redislabs.springboot.service.RedisService;
import com.redislabs.springboot.service.StubDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

    @Autowired
    private RedisService rservice;

    @Autowired
    private CustomerRedisRepo customerRepo;

    @Autowired
    private StubDBService backingService;

    @RequestMapping("/ping")
    public String ping() {
        return "Pong";
    }

    /**
     * Ping the db using template and simply return OK
     * @return just OK
     */
    @RequestMapping("/rping")
    public String rping() {
        rservice.setValue("foo", "bar");
        return "OK";
    }

    /**
     * Checks Redis if object not found gets object from backing service and save to Redis with TTL
     * @param id cutomer id
     * @return customer object
     */
    @RequestMapping("/getcustomer")
    public Customer getCustomer(String id) {

        Customer cus = customerRepo.findOne(id);
        if (cus == null) {
            System.out.println("Getting customer from the  backing database " + id);
            cus = backingService.getCustomer(id);
            cus.setTimeToLive(10L);
            System.out.println("Saving customer to redis " + id);
            customerRepo.save(cus);
        } else {
            System.out.println("Found customer in redis " + id);
        }
        return cus;
    }


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


}
