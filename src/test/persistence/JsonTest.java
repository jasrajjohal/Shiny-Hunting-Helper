package persistence;

import model.Pokemon;
import model.PokemonChecker;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonTest {

    PokemonChecker checker = new PokemonChecker();

    protected void checkPokemon(String pokemon) {
        assertTrue(checker.checkValidPokemon(pokemon));
    }
}
