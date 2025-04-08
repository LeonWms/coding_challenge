package com.example.deesee.config;

import com.example.deesee.domain.Superhero;
import com.example.deesee.domain.SuperheroDTO;
import com.example.deesee.repository.SuperheroRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadSuperheroData(SuperheroRepository repository) {
        return args -> {
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<SuperheroDTO>> typeReference = new TypeReference<>() {};
            InputStream inputStream = new ClassPathResource("superheroes.json").getInputStream();

            try {
                List<Superhero> superheroes = mapper.readValue(inputStream, typeReference).stream()
                        .map(hero -> hero.toSuperhero())
                        .collect(Collectors.toList());

                repository.saveAll(superheroes);
                System.out.println("Superheroes data loaded successfully!");
            } catch (IOException e) {
                System.out.println("Unable to load superheroes: " + e.getMessage());
            }
        };
    }
}