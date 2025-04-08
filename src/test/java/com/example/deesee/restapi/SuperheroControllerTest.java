package com.example.deesee.restapi;

import com.example.deesee.domain.Identity;
import com.example.deesee.domain.Superhero;
import com.example.deesee.domain.SuperheroDTO;
import com.example.deesee.domain.Superpower;
import com.example.deesee.service.SuperheroService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.deesee.restapi.util.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(SuperheroController.class)
@TestPropertySource(properties = "encryption.key=2")
public class SuperheroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SuperheroService superheroService;

    @Autowired
    private ObjectMapper objectMapper;

    @Captor
    private ArgumentCaptor<List<Superhero>> superheroCaptorList;

    @Captor
    private ArgumentCaptor<Superhero> superheroCaptor;

    private final int encryptionKey = 2;


    @Test
    void testGetAllSuperheroes() throws Exception {
        List<Superhero> superheroes = createSuperheroes();
        when(superheroService.getAllSuperheroes()).thenReturn(superheroes);

        mockMvc.perform(get("/api/superheroes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("batman")))
                .andExpect(jsonPath("$[0].identity.firstName", is("bruce")))
                .andExpect(jsonPath("$[0].identity.lastName", is("wayne")))
                .andExpect(jsonPath("$[0].superpowers", hasSize(1)))
                .andExpect(jsonPath("$[1].name", is("superman")))
                .andExpect(jsonPath("$[1].identity.firstName", is("clark")))
                .andExpect(jsonPath("$[1].identity.lastName", is("kent")))
                .andExpect(jsonPath("$[1].superpowers", hasSize(3)));

        verify(superheroService, times(1)).getAllSuperheroes();
    }

    @Test
    void getAllSuperheroesWithEncryptedIdentities() throws Exception {
        List<Superhero> encryptedSuperheroes = createSuperheroesEncrypted();
        when(superheroService.getAllSuperheroesWithEncryptedIdentities(encryptionKey)).thenReturn(encryptedSuperheroes);

        mockMvc.perform(get("/api/superheroes")
                        .param("encrypted", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("batman")))
                .andExpect(jsonPath("$[0].identity.firstName", is("dtweg")))
                .andExpect(jsonPath("$[0].identity.lastName", is("yc[og")))
                .andExpect(jsonPath("$[0].superpowers", hasSize(1)))
                .andExpect(jsonPath("$[1].name", is("superman")))
                .andExpect(jsonPath("$[1].identity.firstName", is("enctm")))
                .andExpect(jsonPath("$[1].identity.lastName", is("mgpv")))
                .andExpect(jsonPath("$[1].superpowers", hasSize(3)));

        verify(superheroService, times(1)).getAllSuperheroesWithEncryptedIdentities(encryptionKey);
    }

    @Test
    void testGetSuperheroesWithSuperpowers() throws Exception {
        Superhero superman = createSuperhero();
        when(superheroService.getSuperheroesWithSuperpowers(List.of(Superpower.FLIGHT))).thenReturn(List.of(superman));

        mockMvc.perform(get("/api/superheroes")
                        .param("superpowers", "FLIGHT"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("superman")))
                .andExpect(jsonPath("$[0].identity.firstName", is("clark")))
                .andExpect(jsonPath("$[0].identity.lastName", is("kent")))
                .andExpect(jsonPath("$[0].superpowers", hasSize(3)))
                .andExpect(jsonPath("$[0].superpowers", hasItem("FLIGHT")));

        verify(superheroService, times(1)).getSuperheroesWithSuperpowers(List.of(Superpower.FLIGHT));

    }

    @Test
    void testGetSuperheroesWithSuperpowersAndEncryptedIdentities() throws Exception {
        Superhero superman = createSuperheroEncrypted();

        when(superheroService.getSuperheroesWithSuperpowersAndEncryptedIdentities(List.of(Superpower.FLIGHT),encryptionKey))
                .thenReturn(List.of(superman));

        mockMvc.perform(get("/api/superheroes")
                        .param("superpowers", "FLIGHT")
                        .param("encrypted", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("superman")))
                .andExpect(jsonPath("$[0].identity.firstName", is("enctm")))
                .andExpect(jsonPath("$[0].identity.lastName", is("mgpv")))
                .andExpect(jsonPath("$[0].superpowers", hasSize(3)))
                .andExpect(jsonPath("$[0].superpowers", hasItem("FLIGHT")));

        verify(superheroService, times(1)).
                getSuperheroesWithSuperpowersAndEncryptedIdentities(List.of(Superpower.FLIGHT), encryptionKey);

    }

    @Test
    void testCreateSuperhero() throws Exception{
        SuperheroDTO superman = createSuperheroDTO();

        mockMvc.perform(post("/api/superheroes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(superman)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("superman")))
                .andExpect(jsonPath("$.identity.firstName", is("clark")))
                .andExpect(jsonPath("$.identity.lastName", is("kent")))
                .andExpect(jsonPath("$.superpowers", hasSize(3)));


        verify(superheroService).saveSuperhero(superheroCaptor.capture());
        Superhero captured = superheroCaptor.getValue();
        assertEquals("superman", captured.getName());
        assertEquals("clark", captured.getIdentity().getFirstName());
        assertEquals("kent", captured.getIdentity().getLastName());
        assertEquals(3, captured.getSuperpowers().size());
    }

    @Test
    void testCreateSuperheroes() throws Exception {
        List<SuperheroDTO> superheroDTOs = createSuperheroDTOs();

        mockMvc.perform(post("/api/superheroes/batch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(superheroDTOs)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("batman")))
                .andExpect(jsonPath("$[0].identity.firstName", is("bruce")))
                .andExpect(jsonPath("$[0].identity.lastName", is("wayne")))
                .andExpect(jsonPath("$[0].superpowers", hasSize(1)))
                .andExpect(jsonPath("$[1].name", is("superman")))
                .andExpect(jsonPath("$[1].identity.firstName", is("clark")))
                .andExpect(jsonPath("$[1].identity.lastName", is("kent")))
                .andExpect(jsonPath("$[1].superpowers", hasSize(3)));

        verify(superheroService).saveSuperheroes(superheroCaptorList.capture());

        List<Superhero> capturedHeroes = superheroCaptorList.getValue();
        assertEquals(2, capturedHeroes.size());

        Superhero batman = capturedHeroes.get(0);
        assertEquals("batman", batman.getName());
        assertEquals("bruce", batman.getIdentity().getFirstName());
        assertEquals("wayne", batman.getIdentity().getLastName());
        assertEquals(1, batman.getSuperpowers().size());

        Superhero superman = capturedHeroes.get(1);
        assertEquals("superman", superman.getName());
        assertEquals("clark", superman.getIdentity().getFirstName());
        assertEquals("kent", superman.getIdentity().getLastName());
        assertEquals(3, superman.getSuperpowers().size());
    }



}
