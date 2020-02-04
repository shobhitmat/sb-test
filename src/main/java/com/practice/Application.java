package com.practice;

import com.practice.entity.Person.User;
import com.practice.entity.Person.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    @Autowired
    private UserRepository repository;


    public static void main(String arg[]){

        SpringApplication.run(Application.class, arg);

       }

    @Override
    public void run(String... args) throws Exception {
        Optional<User> o = repository.findByEmail("mtr@gmail.com");
        if(!o.isPresent()){
            repository.save(new User("Mohit Mathur", "mtr@gmail.com"));

        }

        Optional<User> o1 = repository.findByEmail("smat@gmail.com");
        if(!o1.isPresent()){
            repository.save(new User("Shobhit Mathur", "smat@gmail.com"));
        }


        Optional<User> o2 = repository.findByEmail("akm@gmail.com");
        if(!o2.isPresent()){
            repository.save(new User("Ashok Mathur", "akm@gmail.com"));

        }

        log("\nfindAll()");
        repository.findAll().forEach(x -> log(x.toString()));

        log("\nfindById(1L)");
        repository.findById(1l).ifPresent(x -> log(x.toString()));

        log("\nfindByName('Node')");
        repository.findByEmail("mtr@gmail.com").ifPresent(x -> log(x.toString()));

    }

    private void log(String m){
        System.out.println(m);
        logger.info(m);
    }
}
