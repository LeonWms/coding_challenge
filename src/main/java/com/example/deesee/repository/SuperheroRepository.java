package com.example.deesee.repository;

import com.example.deesee.domain.Superhero;
import com.example.deesee.domain.Superpower;

import java.util.List;

public interface SuperheroRepository {

    public List<Superhero> getAll();
    public void saveAll(List<Superhero>superheroes);
    public void save(Superhero superhero);
    public List<Superhero> findBySuperpowers(List<Superpower> superpowers);
}
