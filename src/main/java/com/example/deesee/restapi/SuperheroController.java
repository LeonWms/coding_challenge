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

    /**
     *
     * @param superpowers: Optional list of superpowers
     * @param encrypted: Optional boolean to return encrypted superheroes
     * @return: Response with a list of superhero as maps
     */
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

    /**
     *
     * @param superheroDTO: Data transfer object containing superhero information
     * @return: Response with the created superhero as a map
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createSuperhero(@RequestBody SuperheroDTO superheroDTO) {
        Superhero superhero = superheroDTO.toSuperhero();
        superheroService.saveSuperhero(superhero);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapSuperheroToResponse(superhero));
    }

    /**
     *
     * @param superheroesDTO: List of superhero information
     * @return: Response with a list of created superheroes as maps
     */
    @PostMapping("/batch")
    public ResponseEntity<List<Map<String, Object>>> createSuperheroes(@RequestBody List<SuperheroDTO> superheroesDTO) {
        List<Superhero> superheroes = superheroesDTO.stream()
                        .map(SuperheroDTO:: toSuperhero)
                        .collect(Collectors.toList());
        superheroService.saveSuperheroes(superheroes);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapSuperheroesToResponse(superheroes));
    }

    /**
     *
     * @param superheroes: List of superhero entities
     * @return: List of maps representing each superhero's details
     */
    private List<Map<String, Object>> mapSuperheroesToResponse(List<Superhero> superheroes) {
        return superheroes.stream()
                .map(this::mapSuperheroToResponse)
                .collect(Collectors.toList());
    }

    /**
     *
     * @param superhero: A single superhero entity
     * @return: A map containing superhero's details
     */
    private Map<String, Object> mapSuperheroToResponse(Superhero superhero) {
        Map<String, Object> response = new HashMap<>();
        response.put("name", superhero.getName());
        response.put("identity", superhero.getIdentity());
        response.put("birthday", superhero.getBirthday());
        response.put("superpowers", superhero.getSuperpowers());
        return response;
    }

    /**
     *
     * @param stringSuperpowers: List o superpowers as strings
     * @return: List of valid superpower as enums
     */
    private List<Superpower> stringToEnum(List<String> stringSuperpowers){
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
