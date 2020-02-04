package com.practice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.entity.Person.User;
import com.practice.entity.Person.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.List;
import java.util.Optional;

@RestController
public class Controller {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Connection conn = DriverManager.getConnection("jdbc:sqlserver://user-test-db.cdhdjqajyzz2.us-east-1.rds.amazonaws.com:1433;databaseName=api-user", "admin", "adminroot");
        System.out.println("test");
        Statement sta = conn.createStatement();
        String Sql = "select * from user_info";
        ResultSet rs = sta.executeQuery(Sql);
        while (rs.next()) {
            System.out.println(rs.getString("email"));
        }
    }

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private UserRepository repository;

    @GetMapping("/sb-api/users/get")
    public String getUser(@RequestParam(required = false) String email){

        try {
            if (email != null) {
                logger.info("lookup for  '{}'", email);
                final Optional<User> user = repository.findByEmail(email);
                logger.info(user.get().toString());
                return mapper.writeValueAsString(user.get());
            } else {
                logger.info("lookup for all users");
                final List<User> users = (List<User>) repository.findAll();
                logger.info(users.toString());
                return mapper.writeValueAsString(users);
            }
        }catch(Exception e){
            logger.info("User '{}' not found", email);
            return "No Data Found";
        }

    }

    @PostMapping("/sb-api/users/create")
    public boolean createUser(@RequestParam(required = false) String email, @RequestParam(required = false) String name){
        try {
            if (email != null && name != null) {
                logger.info("trying to save email/name {}/{}", email, name);
                repository.save(new User(name, email));
                return true;
            } else {
                logger.info("invalid email/name {}/{}", email, name);
                return false;
            }
        }catch(Exception e){
            logger.info("got exception email/name {}/{}", email, name);
            return false;
        }

    }
}
