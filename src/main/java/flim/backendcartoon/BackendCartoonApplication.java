package flim.backendcartoon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class BackendCartoonApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendCartoonApplication.class, args);
    }

}
