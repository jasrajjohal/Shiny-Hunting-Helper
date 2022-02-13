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

public class JsonWriterTest extends JsonTest {

    private Pokemon treecko = new Pokemon(252, "treecko", new ArrayList<String>(Arrays.asList("Starter")), 100, true);
    private Pokemon grovyle = new Pokemon(253, "grovyle", new ArrayList<String>(Arrays.asList("Evolve")), 100, false);
    private Pokemon sceptile = new Pokemon(254, "sceptile", new ArrayList<String>(Arrays.asList("Evolve")), 100, false);

    @Test
    void testWriterInvalidFile() {
        try {
            PlanToHuntList pth = new PlanToHuntList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }


    @Test
    void testWriterEmptyPlanToHuntList() {
        try {
            PlanToHuntList pth = new PlanToHuntList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyPlanToHuntList.json");
            writer.open();
            writer.writePTList(pth);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyPlanToHuntList.json");
            pth = reader.readPTList();
            assertEquals(0, pth.getPlanToHunt().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterEmptyCurrentlyHuntingList() {
        try {
            CurrentlyHuntingList ch = new CurrentlyHuntingList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyCurrentlyHuntingList.json");
            writer.open();
            writer.writeCHList(ch);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyCurrentlyHuntingList.json");
            ch = reader.readCHList();
            assertEquals(0, ch.getCurrentlyHunting().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterEmptyHuntedList() {
        try {
            HuntedList h = new HuntedList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyHuntedList.json");
            writer.open();
            writer.writeHList(h);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyHuntedList.json");
            h = reader.readHList();
            assertEquals(0, h.getHuntedList().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralPlanToHuntList() {
        try {
            PlanToHuntList pth = new PlanToHuntList();
            pth.addPokemon(treecko);
            pth.addPokemon(grovyle);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralPlanToHuntList.json");
            writer.open();
            writer.writePTList(pth);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralPlanToHuntList.json");
            pth = reader.readPTList();
            assertEquals(2, pth.getPlanToHunt().size());
            List<Pokemon> pokemonList = pth.getPlanToHunt();
            assertEquals(2, pokemonList.size());
            checkPokemon(pokemonList.get(0).getName());
            checkPokemon(pokemonList.get(1).getName());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralCurrentlyHuntingList() {
        try {
            CurrentlyHuntingList ch = new CurrentlyHuntingList();
            ch.addPokemon(treecko);
            ch.addPokemon(grovyle);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralCurrentlyHuntingList.json");
            writer.open();
            writer.writeCHList(ch);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralCurrentlyHuntingList.json");
            ch = reader.readCHList();
            assertEquals(2, ch.getCurrentlyHunting().size());
            List<Pokemon> pokemonList = ch.getCurrentlyHunting();
            assertEquals(2, pokemonList.size());
            checkPokemon(pokemonList.get(0).getName());
            checkPokemon(pokemonList.get(1).getName());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralHuntedList() {
        try {
            HuntedList h = new HuntedList();
            h.addPokemon(treecko);
            h.addPokemon(grovyle);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralHuntedList.json");
            writer.open();
            writer.writeHList(h);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralHuntedList.json");
            h = reader.readHList();
            assertEquals(2, h.getHuntedList().size());
            List<Pokemon> pokemonList = h.getHuntedList();
            assertEquals(2, pokemonList.size());
            checkPokemon(pokemonList.get(0).getName());
            checkPokemon(pokemonList.get(1).getName());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
