package com.example.deesee.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class SuperheroDTO {
    private String name;
    private Identity identity;
    private String birthday;
    private List<String> superpowers;

    public SuperheroDTO() {

    }

    public SuperheroDTO(String name, Identity identity, String birthday, List<String> superpowers) {
        this.name = name;
        this.identity = identity;
        this.birthday = birthday;
        this.superpowers = superpowers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Identity getIdentity() {
        return identity;
    }

    public void setIdentity(Identity identity) {
        this.identity = identity;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public List<String> getSuperpowers() {
        return superpowers;
    }

    public void setSuperpowers(List<String> superpowers) {
        this.superpowers = superpowers;
    }

    public Superhero toSuperhero() {
        List<Superpower> powers = superpowers.stream()
                .map(String::toUpperCase)
                .map(s -> {
                    try {
                        return Superpower.valueOf(s);
                    } catch (IllegalArgumentException e) {
                        return null;
                    }
                })
                .filter(power -> power != null)
                .collect(Collectors.toList());

        return new Superhero(
                name,
                identity,
                LocalDate.parse(birthday),
                powers
        );
    }
}
