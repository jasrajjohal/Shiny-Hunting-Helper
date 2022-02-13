package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents a list of all the Pokémon that have been hunted.
public class HuntedList implements Writable {
    private ArrayList<Pokemon> hunted;

    // EFFECTS: Creates an arraylist that holds Pokemon and sets hunted to it.
    public HuntedList() {
        hunted = new ArrayList<Pokemon>();
    }

    // MODIFIES: this
    // EFFECTS: adds the given Pokémon to the list.
    public void addPokemon(Pokemon pokemon) {
        this.hunted.add(pokemon);
        EventLog.getInstance().logEvent(new Event("Added a pokemon to the hunted list."));
    }

    // MODIFIES: this
    // EFFECTS: if the Pokémon is in the list, it is removed.
    public void removePokemon(Pokemon pokemon) {
        for (Pokemon p : this.hunted) {
            if (pokemon.getName().equals(p.getName())) {
                hunted.remove(p);
                EventLog.getInstance().logEvent(new Event("Removed a pokemon to the hunted list."));
            }
        }
    }

    // EFFECTS: returns the list of Pokémon that one has hunted.
    public ArrayList<Pokemon> getHuntedList() {
        return this.hunted;
    }

    // EFFECTS: returns a string of all the Pokémon in the list separated by commas.
    public String getNamesOfAllPokemon() {
        String names = "";
        int count = 0;
        for (Pokemon i : hunted) {
            names += i.getName();
            count += 1;

            if (count < hunted.size()) {
                names += ", ";
            }
        }
        return names;
    }

    // NOTE: Some code may be adapted from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // EFFECTS: Enters the Pokemon in this hunted list to the json object and returns it.
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", "Hunted List");
        json.put("Pokemon", pokemonToJson());
        EventLog.getInstance().logEvent(new Event("saved hunted list."));
        return json;
    }

    // NOTE: Some code may be adapted from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // EFFECTS: returns Pokemon in this Hunted list as a JSON array
    private JSONArray pokemonToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Pokemon p : hunted) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;
    }

    public void loadLog() {
        EventLog.getInstance().logEvent(new Event("Loaded hunted list."));
    }
}

