package com.example.deesee.service;

import com.example.deesee.domain.Identity;
import com.example.deesee.domain.Superhero;
import com.example.deesee.domain.Superpower;
import com.example.deesee.repository.SuperheroRepository;
import com.example.deesee.service.encryption.EncryptionService;
import com.example.deesee.service.impl.SuperheroServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class SuperheroServiceTest {

    @Mock
    private SuperheroRepository superheroRepository;

    @Mock
    private EncryptionService encryptionService;

    @InjectMocks
    private SuperheroServiceImpl superheroService;

    private List<Superhero> testSuperheroes;
    private final int encryptionKey = 5;

    @BeforeEach
    void setUp() {

        testSuperheroes = new ArrayList<>();

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

        testSuperheroes.add(superman);
        testSuperheroes.add(batman);
        testSuperheroes.add(flash);

    }


    @Test
    void testGetAllSuperheroes() {
        when(superheroRepository.getAll()).thenReturn(testSuperheroes);

        List<Superhero> actualSuperheroes = superheroService.getAllSuperheroes();

        assertEquals(testSuperheroes.size(), actualSuperheroes.size());
        assertEquals(testSuperheroes, actualSuperheroes);
        verify(superheroRepository, times(1)).getAll();
    }

    @Test
    void testGetAllSuperheroesWithEncryptedIdentities() {
        when(superheroRepository.getAll()).thenReturn(testSuperheroes);
        configEncryptionService();

        List<Superhero> actualSuperheroes = superheroService.getAllSuperheroesWithEncryptedIdentities(encryptionKey);

        assertEquals(testSuperheroes.size(), actualSuperheroes.size());

        assertEquals("dmbsl", actualSuperheroes.get(0).getIdentity().getFirstName());
        assertEquals("lfou", actualSuperheroes.get(0).getIdentity().getLastName());

        assertEquals("csvdf", actualSuperheroes.get(1).getIdentity().getFirstName());
        assertEquals("xbzof", actualSuperheroes.get(1).getIdentity().getLastName());

        assertEquals("cbssz", actualSuperheroes.get(2).getIdentity().getFirstName());
        assertEquals("bmmfo", actualSuperheroes.get(2).getIdentity().getLastName());


        verify(superheroRepository, times(1)).getAll();
        verify(encryptionService, times(6)).encrypt(anyString(), eq(encryptionKey));
    }

    @Test
    void testGetSuperheroesWithSuperpowers() {
        List<Superpower> superpowers = Arrays.asList(Superpower.STRENGTH);
        List<Superhero> filteredSuperheroes = Arrays.asList(testSuperheroes.get(0), testSuperheroes.get(1));

        when(superheroRepository.findBySuperpowers(superpowers)).thenReturn(filteredSuperheroes);

        List<Superhero> actualSuperheroes = superheroService.getSuperheroesWithSuperpowers(superpowers);

        assertEquals(filteredSuperheroes.size(), actualSuperheroes.size());
        assertEquals(filteredSuperheroes.get(0), actualSuperheroes.get(0));
        assertEquals(filteredSuperheroes.get(1), actualSuperheroes.get(1));
        verify(superheroRepository, times(1)).findBySuperpowers(superpowers);
    }

    @Test
    void testGetSuperheroesWithSuperpowersAndEncryptedIdentities() {
        List<Superpower> superpowers = Arrays.asList(Superpower.HEALING, Superpower.SPEED);
        List<Superhero> filteredSuperheroes = Arrays.asList(testSuperheroes.get(2));

        when(superheroRepository.findBySuperpowers(superpowers)).thenReturn(filteredSuperheroes);
        when(encryptionService.encrypt("barry", encryptionKey)).thenReturn("cbssz");
        when(encryptionService.encrypt("allen", encryptionKey)).thenReturn("bmmfo");

        List<Superhero> actualSuperheroes = superheroService.getSuperheroesWithSuperpowersAndEncryptedIdentities(superpowers, encryptionKey);

        assertEquals(filteredSuperheroes.size(), actualSuperheroes.size());
        assertEquals(filteredSuperheroes.get(0).getName(), actualSuperheroes.get(0).getName());
        assertEquals("cbssz", actualSuperheroes.get(0).getIdentity().getFirstName());
        assertEquals("bmmfo", actualSuperheroes.get(0).getIdentity().getLastName());
        verify(superheroRepository, times(1)).findBySuperpowers(superpowers);
        verify(encryptionService, times(2)).encrypt(anyString(), eq(encryptionKey));
    }

    @Test
    void testSaveSuperheroes() {
        superheroService.saveSuperheroes(testSuperheroes);

        verify(superheroRepository, times(1)).saveAll(testSuperheroes);
    }

    @Test
    void testSaveSuperhero() {
        Superhero superhero = testSuperheroes.get(0);

        superheroService.saveSuperhero(superhero);

        verify(superheroRepository, times(1)).save(superhero);
    }

    private void configEncryptionService() {
        when(encryptionService.encrypt("clark", encryptionKey)).thenReturn("dmbsl");
        when(encryptionService.encrypt("kent", encryptionKey)).thenReturn("lfou");
        when(encryptionService.encrypt("bruce", encryptionKey)).thenReturn("csvdf");
        when(encryptionService.encrypt("wayne", encryptionKey)).thenReturn("xbzof");
        when(encryptionService.encrypt("barry", encryptionKey)).thenReturn("cbssz");
        when(encryptionService.encrypt("allen", encryptionKey)).thenReturn("bmmfo");
    }
}
