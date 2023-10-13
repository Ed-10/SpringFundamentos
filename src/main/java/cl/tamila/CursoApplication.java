package cl.tamila;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication//Principal, todo pasa por aqui
public class CursoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CursoApplication.class, args);
    }
    @Bean
    public RestTemplate restTemplate(){//Cleinte que usa SpringBoot
        return new RestTemplate();
    }
}
