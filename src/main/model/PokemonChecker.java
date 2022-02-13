package model;

import java.util.ArrayList;
import java.util.Arrays;

// Represents a Pokémon authenticator to determine whether Pokémon are in the system and can pull information about them
public class PokemonChecker {

    private ArrayList<String> evolve = new ArrayList<String>(Arrays.asList("Evolve"));
    private ArrayList<String> route101 = new ArrayList<String>(Arrays.asList("Route 101"));
    private ArrayList<String> route120 = new ArrayList<String>(Arrays.asList("Route 120"));
    private ArrayList<String> petalburgWoods = new ArrayList<String>(Arrays.asList("Petalburg Woods"));
    private ArrayList<String> nowhere = new ArrayList<String>(Arrays.asList("Nowhere"));

    private Pokemon treecko = new Pokemon(252, "treecko", new ArrayList<String>(Arrays.asList("Starter")), 100, true);
    private Pokemon grovyle = new Pokemon(253, "grovyle", new ArrayList<String>(Arrays.asList("Evolve")), 100, false);
    private Pokemon sceptile = new Pokemon(254, "sceptile", evolve, 100, false);
    private Pokemon torchic = new Pokemon(255, "torchic", new ArrayList<String>(Arrays.asList("Starter")), 100, true);
    private Pokemon combusken = new Pokemon(256, "combusken", evolve, 100, false);
    private Pokemon blaziken = new Pokemon(257, "blaziken", new ArrayList<String>(Arrays.asList("Evolve")), 100, false);
    private Pokemon mudkip = new Pokemon(258, "mudkip", new ArrayList<String>(Arrays.asList("Starter")), 100, true);
    private Pokemon marshtomp = new Pokemon(259, "marshtomp", evolve, 100, false);
    private Pokemon swampert = new Pokemon(260, "swampert", new ArrayList<String>(Arrays.asList("Evolve")), 100, false);
    private Pokemon poochyena = new Pokemon(261, "poochyena", route101, 45, true);
    private Pokemon mightyena = new Pokemon(262, "mightyena", route120, 30, false);
    private Pokemon zigzagoon = new Pokemon(263, "zigzagoon", route101, 10, true);
    private Pokemon linoone = new Pokemon(264, "linoone", new ArrayList<String>(Arrays.asList("Route 118")), 10, false);
    private Pokemon wurmple = new Pokemon(265, "wurmple", new ArrayList<String>(Arrays.asList("Route 101")), 45, true);
    private Pokemon silcoon = new Pokemon(266, "silcoon", petalburgWoods, 10, false);

    private Pokemon dummy = new Pokemon(999, "Invalid Pokemon", nowhere, 0, false);

    private ArrayList<Pokemon> allPokemon;

    // EFFECTS: Adds all Pokémon in the system to an ArrayList for easy access.
    public PokemonChecker() {
        allPokemon = new ArrayList<Pokemon>(Arrays.asList(treecko, grovyle, sceptile, torchic, combusken, blaziken));
        allPokemon.addAll(Arrays.asList(mudkip, marshtomp, swampert, poochyena, mightyena, zigzagoon, linoone));
        allPokemon.addAll(Arrays.asList(wurmple, silcoon));
    }

    // EFFECTS: given a string, returns true if the string matches the name of a Pokémon in the system. False otherwise
    public Boolean checkValidPokemon(String pokemon) {
        if (pokemon.equals(checkPokemonName(pokemon))) {
            return true;
        }
        return false;
    }

    // EFFECTS: given a String, if the string matches the name of a Pokémon in the system, returns the Pokémon's name.
    //          returns "not valid Pokémon name" otherwise.
    public String checkPokemonName(String pokemon) {
        for (Pokemon i : allPokemon) {
            if (i.getName().equals(pokemon)) {
                return i.getName();
            }
        }
        return "not valid pokemon name";
    }


    // EFFECTS: given a string, if the string matches the name of a Pokémon in the system, returns the Pokémon.
    //          Otherwise returns a dummy Pokémon.
    public Pokemon getPokemonByName(String pokemon) {
        if (checkValidPokemon(pokemon)) {
            for (Pokemon i : allPokemon) {
                if (i.getName().equals(pokemon)) {
                    return i;
                }
            }
        }
        return dummy;
    }

    public ArrayList<Pokemon> getPokemonChecker() {
        return this.allPokemon;
    }

    // MODIFIES: this
    // EFFECTS: clears the list of all pokemon.
    public void clearAllPokemon() {
        this.allPokemon = new ArrayList<Pokemon>();
    }

}
