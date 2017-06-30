package com.redislabs.springboot;

import com.redislabs.springboot.service.Customer;
import com.redislabs.springboot.service.CustomerRedisRepo;
import com.redislabs.springboot.service.RedisService;
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


    @RequestMapping("/ping")
    public String ping() {
        return "Pong";
    }

    @RequestMapping("/rping")
    public String rping() {
        rservice.setValue("foo", "bar");
        return "OK";
    }

    @RequestMapping("/rcustomer")
    public String testRepo() {


        Customer customer = new Customer("1", "Kamran", "Yousaf", 142);
        customerRepo.save(customer);
        customerRepo.findOne(customer.getId());
        customerRepo.count();
        customerRepo.delete(customer);
        return "OK";
    }


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


}
