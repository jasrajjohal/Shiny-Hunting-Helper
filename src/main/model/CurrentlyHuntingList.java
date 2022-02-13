package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents a list of all the Pokémon that are currently being hunted.
public class CurrentlyHuntingList implements Writable {

    private ArrayList<Pokemon> currentlyHunting;

    // EFFECTS: Creates an arraylist that holds Pokemon and sets currentlyHunting to it.
    public CurrentlyHuntingList() {
        currentlyHunting = new ArrayList<Pokemon>();
    }

    // MODIFIES: this
    // EFFECTS: adds the given Pokémon to the list.
    public void addPokemon(Pokemon pokemon) {
        this.currentlyHunting.add(pokemon);
        EventLog.getInstance().logEvent(new Event("Added a pokemon to the currently hunting list."));
    }

    // MODIFIES: this
    // EFFECTS: if the Pokémon is in the list, it is removed.
    public void removePokemon(Pokemon pokemon) {
        for (Pokemon p : this.currentlyHunting) {
            if (pokemon.getName().equals(p.getName())) {
                currentlyHunting.remove(p);
                EventLog.getInstance().logEvent(new Event("Removed a pokemon to the currently hunting list."));
            }
        }
    }

    // EFFECTS: returns the list of Pokémon that one is currently hunting.
    public ArrayList<Pokemon> getCurrentlyHunting() {
        return this.currentlyHunting;
    }

    // EFFECTS: returns a string of all the Pokémon in the list separated by commas.
    public String getNamesOfAllPokemon() {
        String names = "";
        int count = 0;
        for (Pokemon i : currentlyHunting) {
            names += i.getName();
            count += 1;

            if (count < currentlyHunting.size()) {
                names += ", ";
            }
        }
        return names;
    }

    // NOTE: Some code may be adapted from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // EFFECTS: Enters the Pokemon in this currently hunting list to the json object and returns it.
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", "Currently Hunting");
        json.put("Pokemon", pokemonToJson());
        EventLog.getInstance().logEvent(new Event("saved currently hunting list."));
        return json;
    }

    // NOTE: Some code may be adapted from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // EFFECTS: returns Pokemon in this Currently Hunting list as a JSON array
    private JSONArray pokemonToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Pokemon p : currentlyHunting) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;
    }

    public void loadLog() {
        EventLog.getInstance().logEvent(new Event("Loaded currently hunting list."));
    }
}
