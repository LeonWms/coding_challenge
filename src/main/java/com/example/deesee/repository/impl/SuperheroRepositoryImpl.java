package com.example.deesee.repository.impl;


import com.example.deesee.domain.Superhero;
import com.example.deesee.domain.Superpower;
import com.example.deesee.repository.SuperheroRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class SuperheroRepositoryImpl implements SuperheroRepository {

    private final List<Superhero> superheroes = new ArrayList<>();

    @Override
    public List<Superhero> getAll() {
        return superheroes;
    }

    @Override
    public void saveAll(List<Superhero> superheroes) {
        this.superheroes.addAll(superheroes);
    }

    @Override
    public void save(Superhero superhero) {
        this.superheroes.add(superhero);
    }

    @Override
    public List<Superhero> findBySuperpowers(List<Superpower> superpowers) {
        return getAll().stream().
                filter(hero -> hero.getSuperpowers().containsAll(superpowers))
                .collect(Collectors.toList());
    }
}
