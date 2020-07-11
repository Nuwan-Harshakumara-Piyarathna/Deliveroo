package com.pdn.eng;

import com.pdn.eng.Controller.PageController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

//this will scan all the packages in project
@ComponentScan("com.pdn.eng")
@EnableMongoRepositories(basePackages= {"com.pdn.eng.DAO"})
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        //create a directory at startup if it already doesn't exist
        new File(PageController.uploadDirectory).mkdir();
        SpringApplication.run(Main.class,args);
    }
}
