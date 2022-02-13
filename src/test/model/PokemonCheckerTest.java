package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class PokemonCheckerTest {

    private PokemonChecker testChecker;

    @BeforeEach
    void runBefore() {
        testChecker = new PokemonChecker();
    }

    @Test
    void testConstructor() {
        assertEquals(15, testChecker.getPokemonChecker().size());
    }

    @Test
    void testCheckValidPokemon() {
        assertTrue(testChecker.checkValidPokemon("treecko"));
        assertFalse(testChecker.checkValidPokemon("treeko"));
    }

    @Test
    void testCheckPokemonName() {
        assertEquals("sceptile", testChecker.checkPokemonName("sceptile"));
        assertEquals("not valid pokemon name", testChecker.checkPokemonName("scptle"));
    }

    @Test
    void testGetPokemonByName() {
        assertEquals("sceptile", testChecker.getPokemonByName("sceptile").getName());
        assertEquals("Invalid Pokemon", testChecker.getPokemonByName("scptle").getName());
    }

    @Test
    void testGetPokemonByNameEmpty() {
        PokemonChecker empty = new PokemonChecker();
        empty.clearAllPokemon();

        assertEquals("Invalid Pokemon", empty.getPokemonByName("sceptile").getName());
    }
}
