package com.example.deesee.restapi;

import com.example.deesee.domain.Superhero;
import com.example.deesee.domain.SuperheroDTO;
import com.example.deesee.domain.Superpower;
import com.example.deesee.service.SuperheroService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/superheroes")
public class SuperheroController {

    private final SuperheroService superheroService;
    private final int encryptionKey;

    public SuperheroController(SuperheroService superheroService,
                               @Value("${encryption.key}") int encryptionKey) {
        this.superheroService = superheroService;
        this.encryptionKey = encryptionKey;
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getSuperheroes(
            @RequestParam(required = false) List<String> superpowers,
            @RequestParam(required = false, defaultValue = "false") boolean encrypted) {

        List<Superhero> superheroes;

        if (superpowers != null && !superpowers.isEmpty()) {
            superheroes = encrypted
                    ? superheroService.getSuperheroesWithSuperpowersAndEncryptedIdentities(stringToEnum(superpowers), encryptionKey)
                    : superheroService.getSuperheroesWithSuperpowers(stringToEnum(superpowers));
        } else {
            superheroes = encrypted
                    ? superheroService.getAllSuperheroesWithEncryptedIdentities(encryptionKey)
                    : superheroService.getAllSuperheroes();
        }

        return ResponseEntity.ok(mapSuperheroesToResponse(superheroes));
    }


    @PostMapping
    public ResponseEntity<Map<String, Object>> createSuperhero(@RequestBody SuperheroDTO superheroDTO) {
        Superhero superhero = superheroDTO.toSuperhero();
        superheroService.saveSuperhero(superhero);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapSuperheroToResponse(superhero));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<Map<String, Object>>> createSuperheroes(@RequestBody List<SuperheroDTO> superheroesDTO) {
        List<Superhero> superheroes = superheroesDTO.stream()
                .map(SuperheroDTO::toSuperhero)
                .collect(Collectors.toList());
        superheroService.saveSuperheroes(superheroes);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapSuperheroesToResponse(superheroes));
    }

    private List<Map<String, Object>> mapSuperheroesToResponse(List<Superhero> superheroes) {
        return superheroes.stream()
                .map(this::mapSuperheroToResponse)
                .collect(Collectors.toList());
    }

    private Map<String, Object> mapSuperheroToResponse(Superhero superhero) {
        Map<String, Object> response = new HashMap<>();
        response.put("name", superhero.getName());
        response.put("identity", superhero.getIdentity());
        response.put("birthday", superhero.getBirthday());
        response.put("superpowers", superhero.getSuperpowers());
        return response;
    }

    private List<Superpower> stringToEnum(List<String> stringSuperpowers) {
        return stringSuperpowers.stream()
                .map(String::toUpperCase)
                .map(s -> {
                    try {
                        return Superpower.valueOf(s);
                    } catch (IllegalArgumentException e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
