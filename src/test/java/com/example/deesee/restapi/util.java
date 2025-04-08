package com.example.deesee.restapi;

import com.example.deesee.domain.Identity;
import com.example.deesee.domain.Superhero;
import com.example.deesee.domain.SuperheroDTO;
import com.example.deesee.domain.Superpower;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class util {

    static Superhero createSuperhero(){
        Superhero superman = new Superhero(
                "superman",
                new Identity("clark", "kent"),
                LocalDate.of(1977, 4, 18),
                Arrays.asList(Superpower.FLIGHT, Superpower.STRENGTH, Superpower.INVULNERABILITY)
        );
        return superman;
    }

    static Superhero createSuperheroEncrypted(){
        Superhero superman = new Superhero(
                "superman",
                new Identity("enctm", "mgpv"),
                LocalDate.of(1977, 4, 18),
                Arrays.asList(Superpower.FLIGHT, Superpower.STRENGTH, Superpower.INVULNERABILITY)
        );
        return superman;
    }

    static List<Superhero> createSuperheroes(){
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

        List<Superhero> superheroes = Arrays.asList(batman, superman);
        return superheroes;
    }

    static List<Superhero> createSuperheroesEncrypted(){
        Superhero encryptedSuperman = new Superhero(
                "superman",
                new Identity("enctm", "mgpv"),
                LocalDate.of(1977, 4, 18),
                Arrays.asList(Superpower.FLIGHT, Superpower.STRENGTH, Superpower.INVULNERABILITY)
        );

        Superhero encryptedBatman = new Superhero(
                "batman",
                new Identity("dtweg", "yc[og"),
                LocalDate.of(1915, 4, 17),
                Arrays.asList(Superpower.STRENGTH)
        );

        List<Superhero> encryptedSuperheroes = Arrays.asList(encryptedBatman, encryptedSuperman);
        return encryptedSuperheroes;
    }

    static SuperheroDTO createSuperheroDTO(){
        SuperheroDTO superman = new SuperheroDTO(
                "superman",
                new Identity("clark", "kent"),
                "1977-04-18",
                List.of("flight", "strength", "invulnerability")
        );
        return superman;
    }

    static List<SuperheroDTO> createSuperheroDTOs(){
        List<SuperheroDTO> superheroDTOs = List.of(
                new SuperheroDTO(
                        "batman",
                        new Identity("bruce", "wayne"),
                        "1972-02-19",
                        List.of("strength")
                ),
                new SuperheroDTO(
                        "superman",
                        new Identity("clark", "kent"),
                        "1977-04-18",
                        List.of("flight", "strength", "invulnerability")
                )
        );
        return superheroDTOs;
    }

}
