package com.example.deesee.service;

import com.example.deesee.domain.Superhero;
import com.example.deesee.domain.Superpower;

import java.util.List;

public interface SuperheroService {

    List<Superhero> getAllSuperheroes();

    List<Superhero> getAllSuperheroesWithEncryptedIdentities(int key);

    List<Superhero> getSuperheroesWithSuperpowers(List<Superpower> superpowers);

    List<Superhero> getSuperheroesWithSuperpowersAndEncryptedIdentities(List<Superpower> superpowers, int key);

    void saveSuperheroes(List<Superhero> superheroes);

    void saveSuperhero(Superhero superhero);
}
