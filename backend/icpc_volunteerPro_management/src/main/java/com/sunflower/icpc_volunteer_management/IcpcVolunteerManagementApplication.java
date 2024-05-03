package com.sunflower.icpc_volunteer_management;

import com.sunflower.icpc_volunteer_management.demo.ws.WebSocket;
import org.dromara.x.file.storage.spring.EnableFileStorage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author yg
 */
@SpringBootApplication
@EnableFileStorage
public class IcpcVolunteerManagementApplication {

    public static void main(String[] args) {
//        SpringApplication.run(IcpcVolunteerManagementApplication.class, args);
        SpringApplication springApplication = new SpringApplication(IcpcVolunteerManagementApplication.class);
        ConfigurableApplicationContext applicationContext = SpringApplication.run(IcpcVolunteerManagementApplication.class, args);
        WebSocket.setApplicationContext(applicationContext);

    }

}
