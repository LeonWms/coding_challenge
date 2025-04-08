package com.example.deesee.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class Superhero {

    private final String name;
    private final Identity identity;
    private final LocalDate birthday;
    private final List<Superpower> superpowers;

    public Superhero(String name, Identity identity,
                     LocalDate birthday, List<Superpower> superpowers) {
        this.name = name;
        this.identity = identity;
        this.birthday = birthday;
        this.superpowers = superpowers;
    }

    public String getName() {
        return name;
    }


    public Identity getIdentity() {
        return identity;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public List<Superpower> getSuperpowers() {
        return superpowers;
    }
}
