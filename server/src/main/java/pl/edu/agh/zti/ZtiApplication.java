package pl.edu.agh.zti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Startup class.
 * It starts SpringApplication with scheduling enabled.
 */
@SpringBootApplication
@EnableScheduling
public class ZtiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZtiApplication.class, args);
    }
}
