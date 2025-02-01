package ru.app.nutritionologycrm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class NutritionologyCrmApplication {

    public static void main(String[] args) {
        SpringApplication.run(NutritionologyCrmApplication.class, args);
    }

}
