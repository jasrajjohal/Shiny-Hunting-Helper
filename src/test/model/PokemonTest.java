package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PokemonTest {
    private Pokemon testPokemon;

    @BeforeEach
    void runBefore() {
        testPokemon = new Pokemon(1, "test pokemon", new ArrayList<String>(Arrays.asList("Starter")), 50, true);
    }

    @Test
    void testConstructor() {
        assertEquals(1, testPokemon.getDexNum());
        assertEquals("test pokemon", testPokemon.getName());
        assertEquals(1, testPokemon.getLocations().size());
        assertTrue(testPokemon.getLocations().contains("Starter"));
        assertEquals(50, testPokemon.getEncounterRate());
        assertTrue(testPokemon.getIsBreedable());
    }
}