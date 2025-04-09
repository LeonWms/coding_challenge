package com.example.deesee.repository;

import com.example.deesee.domain.Superhero;
import com.example.deesee.domain.Superpower;

import java.util.List;

public interface SuperheroRepository {

    List<Superhero> getAll();
    void saveAll(List<Superhero>superheroes);
    void save(Superhero superhero);
    List<Superhero> findBySuperpowers(List<Superpower> superpowers);
}
