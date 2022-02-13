package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HuntedListTest {

    private HuntedList testList;

    private Pokemon treecko = new Pokemon(252, "treecko", new ArrayList<String>(Arrays.asList("Starter")), 100, true);
    private Pokemon grovyle = new Pokemon(253, "grovyle", new ArrayList<String>(Arrays.asList("Evolve")), 100, false);
    private Pokemon sceptile = new Pokemon(254, "sceptile", new ArrayList<String>(Arrays.asList("Evolve")), 100, false);

    @BeforeEach
    void runBefore() {
        testList = new HuntedList();
    }

    @Test
    void testConstructor() {
        assertEquals(0, testList.getHuntedList().size());
    }

    @Test
    void testAddPokemon() {
        testList.addPokemon(treecko);
        assertEquals(1, testList.getHuntedList().size());
        assertEquals(treecko, testList.getHuntedList().get(0));
        testList.addPokemon(grovyle);
        assertEquals(2, testList.getHuntedList().size());
        assertEquals(grovyle, testList.getHuntedList().get(1));
    }

    @Test
    void testRemovePokemon() {
        testList.addPokemon(treecko);
        testList.addPokemon(grovyle);
        assertEquals(2, testList.getHuntedList().size());
        testList.removePokemon(treecko);
        assertEquals(1, testList.getHuntedList().size());
        assertEquals(grovyle, testList.getHuntedList().get(0));

        testList.removePokemon(treecko);
        assertEquals(1, testList.getHuntedList().size());
        assertEquals(grovyle, testList.getHuntedList().get(0));
    }

    @Test
    void testGetNamesOfAllPokemon() {
        assertEquals("", testList.getNamesOfAllPokemon());
        testList.addPokemon(treecko);
        assertEquals("treecko", testList.getNamesOfAllPokemon());
        testList.addPokemon(grovyle);
        testList.addPokemon(sceptile);
        assertEquals("treecko, grovyle, sceptile", testList.getNamesOfAllPokemon());
    }
}
