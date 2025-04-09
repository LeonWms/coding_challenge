package com.example.deesee.service.impl;

import com.example.deesee.domain.Identity;
import com.example.deesee.domain.Superhero;
import com.example.deesee.domain.Superpower;
import com.example.deesee.repository.SuperheroRepository;
import com.example.deesee.service.SuperheroService;
import com.example.deesee.service.encryption.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SuperheroServiceImpl implements SuperheroService {

    @Autowired
    private SuperheroRepository superheroRepository;

    @Autowired
    private EncryptionService encryptionService;


    @Override
    public List<Superhero> getAllSuperheroes() {
        return superheroRepository.getAll();
    }

    @Override
    public List<Superhero> getAllSuperheroesWithEncryptedIdentities(int key) {
        List<Superhero> superheroes = superheroRepository.getAll();

        return encryptAll(superheroes, key);
    }

    @Override
    public List<Superhero> getSuperheroesWithSuperpowers(List<Superpower> superpowers) {
        List<Superhero> superheroes = superheroRepository.findBySuperpowers(superpowers);
        return superheroes;
    }

    @Override
    public List<Superhero> getSuperheroesWithSuperpowersAndEncryptedIdentities(List<Superpower> superpowers, int key) {
        List<Superhero> superheroesBySuperpower = superheroRepository.findBySuperpowers(superpowers);

        return encryptAll(superheroesBySuperpower, key);
    }

    @Override
    public void saveSuperheroes(List<Superhero> superheroes) {
        superheroRepository.saveAll(superheroes);
    }

    @Override
    public void saveSuperhero(Superhero superhero) {
        superheroRepository.save(superhero);
    }

    private List<Superhero> encryptAll(List<Superhero> superheroes, int key) {
        List<Superhero> encryptedIdentities = new ArrayList<>();

        for (Superhero s : superheroes) {
            Identity originalIdentity = s.getIdentity();
            String encryptedFirstName = encryptionService.encrypt(originalIdentity.getFirstName(), key);
            String encryptedLastName = encryptionService.encrypt(originalIdentity.getLastName(), key);

            Superhero encryptedHero = new Superhero(
                    s.getName(),
                    new Identity(encryptedFirstName,
                            encryptedLastName),
                    s.getBirthday(),
                    s.getSuperpowers());

            encryptedIdentities.add(encryptedHero);
        }
        return encryptedIdentities;
    }
}
