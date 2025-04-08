package com.example.deesee.repository;

import com.example.deesee.domain.Identity;
import com.example.deesee.domain.Superhero;
import com.example.deesee.domain.Superpower;
import com.example.deesee.repository.impl.SuperheroRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SuperheroRepositoryTest {

    private SuperheroRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        repository = new SuperheroRepositoryImpl();
    }

    @Test
    void testSaveSuperhero() {
        Superhero superman = new Superhero(
                "superman",
                new Identity("clark", "kent"),
                LocalDate.of(1977, 4, 18),
                Arrays.asList(Superpower.FLIGHT, Superpower.STRENGTH, Superpower.INVULNERABILITY)
        );
        repository.save(superman);

        List<Superhero> all = repository.getAll();
        assertEquals(1, all.size());
        assertTrue(all.contains(superman));
    }

    @Test
    void testSaveSuperheroes() {
        Superhero superman = new Superhero(
                "superman",
                new Identity("clark", "kent"),
                LocalDate.of(1977, 4, 18),
                Arrays.asList(Superpower.FLIGHT, Superpower.STRENGTH, Superpower.INVULNERABILITY)
        );

        Superhero batman = new Superhero(
                "batman",
                new Identity("bruce", "wayne"),
                LocalDate.of(1915, 4, 17),
                Arrays.asList(Superpower.STRENGTH)
        );

        List<Superhero> superheroes = List.of(superman, batman);
        repository.saveAll(superheroes);

        List<Superhero> all = repository.getAll();
        assertEquals(superheroes.size(), all.size());
        assertTrue(all.containsAll(superheroes));
    }

    @Test
    void testGetAllReturnsEmptyListInitially() {
        assertTrue(repository.getAll().isEmpty());
    }

    @Test
    void testFindBySuperpowers() {
        Superhero superman = new Superhero(
                "superman",
                new Identity("clark", "kent"),
                LocalDate.of(1977, 4, 18),
                Arrays.asList(Superpower.FLIGHT, Superpower.STRENGTH, Superpower.INVULNERABILITY)
        );

        Superhero batman = new Superhero(
                "batman",
                new Identity("bruce", "wayne"),
                LocalDate.of(1915, 4, 17),
                Arrays.asList(Superpower.STRENGTH)
        );

        Superhero flash = new Superhero(
                "flash",
                new Identity("barry", "allen"),
                LocalDate.of(1992, 9, 30),
                Arrays.asList(Superpower.SPEED, Superpower.HEALING)
        );

        List<Superhero> superheroes = List.of(superman, batman, flash);
        repository.saveAll(superheroes);

        List<Superhero> result = repository.findBySuperpowers(List.of(Superpower.FLIGHT));

        assertEquals(1, result.size());
        assertTrue(result.contains(superman));

        List<Superhero> strengthOnly = repository.findBySuperpowers(List.of(Superpower.STRENGTH));
        assertEquals(2, strengthOnly.size());
        assertTrue(strengthOnly.containsAll(List.of(superman, batman)));
    }


    @Test
    void testFindBySuperpowersNoMatch() {
        Superhero superman = new Superhero(
                "superman",
                new Identity("clark", "kent"),
                LocalDate.of(1977, 4, 18),
                Arrays.asList(Superpower.FLIGHT, Superpower.STRENGTH, Superpower.INVULNERABILITY)
        );
        repository.save(superman);

        List<Superhero> superheroes = repository.findBySuperpowers(List.of(Superpower.HEALING));
        assertTrue(superheroes.isEmpty());
    }
}
