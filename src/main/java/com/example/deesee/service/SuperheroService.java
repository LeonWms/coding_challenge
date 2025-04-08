package com.example.deesee.service;

import com.example.deesee.domain.Superhero;
import com.example.deesee.domain.Superpower;

import java.util.List;

public interface SuperheroService {

    public List<Superhero> getAllSuperheroes();
    public List<Superhero> getAllSuperheroesWithEncryptedIdentities(int key);
    public List<Superhero> getSuperheroesWithSuperpowers(List<Superpower> superpowers);
    public List<Superhero> getSuperheroesWithSuperpowersAndEncryptedIdentities(List<Superpower> superpowers, int key);
    public void saveSuperheroes(List<Superhero> superheroes);
    public void saveSuperhero(Superhero superhero);
}
