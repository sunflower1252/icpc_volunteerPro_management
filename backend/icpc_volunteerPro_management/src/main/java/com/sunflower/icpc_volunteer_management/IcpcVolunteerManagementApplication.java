package com.sunflower.icpc_volunteer_management;

import org.dromara.x.file.storage.spring.EnableFileStorage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author yg
 */
@SpringBootApplication
@EnableFileStorage
public class IcpcVolunteerManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(IcpcVolunteerManagementApplication.class, args);
    }

}
