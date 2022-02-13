package persistence;

import model.CurrentlyHuntingList;
import model.HuntedList;
import model.PlanToHuntList;
import model.Pokemon;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {

    private Pokemon treecko = new Pokemon(252, "treecko", new ArrayList<String>(Arrays.asList("Starter")), 100, true);
    private Pokemon grovyle = new Pokemon(253, "grovyle", new ArrayList<String>(Arrays.asList("Evolve")), 100, false);
    private Pokemon sceptile = new Pokemon(254, "sceptile", new ArrayList<String>(Arrays.asList("Evolve")), 100, false);

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            PlanToHuntList pth = reader.readPTList();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyPlanToHuntList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyPlanToHuntList.json");
        try {
            PlanToHuntList pth = reader.readPTList();
            assertEquals(0, pth.getPlanToHunt().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderEmptyCurrentlyHuntingList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyCurrentlyHuntingList.json");
        try {
            CurrentlyHuntingList ch = reader.readCHList();
            assertEquals(0, ch.getCurrentlyHunting().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderEmptyHuntedList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyHuntedList.json");
        try {
            HuntedList h = reader.readHList();
            assertEquals(0, h.getHuntedList().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralPlanToHuntList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralPlanToHuntList.json");
        try {
            PlanToHuntList pth = reader.readPTList();
            List<Pokemon> pokemonList = pth.getPlanToHunt();
            assertEquals(2, pokemonList.size());
            checkPokemon(pokemonList.get(0).getName());
            checkPokemon(pokemonList.get(1).getName());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralCurrentlyHuntingList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralCurrentlyHuntingList.json");
        try {
            CurrentlyHuntingList ch = reader.readCHList();
            List<Pokemon> pokemonList = ch.getCurrentlyHunting();
            assertEquals(2, pokemonList.size());
            checkPokemon(pokemonList.get(0).getName());
            checkPokemon(pokemonList.get(1).getName());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralHuntedList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralHuntedList.json");
        try {
            HuntedList h = reader.readHList();
            List<Pokemon> pokemonList = h.getHuntedList();
            assertEquals(2, pokemonList.size());
            checkPokemon(pokemonList.get(0).getName());
            checkPokemon(pokemonList.get(1).getName());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
