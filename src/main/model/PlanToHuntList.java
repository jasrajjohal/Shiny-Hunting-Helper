package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents a list of all the Pokémon that are planned to be hunted.
public class PlanToHuntList implements Writable {

    private ArrayList<Pokemon> planToHunt;

    // EFFECTS: Creates an arraylist that holds Pokemon and sets planToHunt to it.
    public PlanToHuntList() {
        planToHunt = new ArrayList<Pokemon>();
    }

    // MODIFIES: this
    // EFFECTS: adds the given Pokémon to the list.
    public void addPokemon(Pokemon pokemon) {
        this.planToHunt.add(pokemon);
        EventLog.getInstance().logEvent(new Event("Added a pokemon to the plan to hunt list."));
    }

    // MODIFIES: this
    // EFFECTS: if the Pokémon is in the list, it is removed.
    public void removePokemon(Pokemon pokemon) {
        for (Pokemon p : this.planToHunt) {
            if (pokemon.getName().equals(p.getName())) {
                planToHunt.remove(p);
                EventLog.getInstance().logEvent(new Event("Removed a pokemon to the plan to hunt list."));
            }
        }
    }

    // EFFECTS: returns the list of Pokémon that one plans to hunt.
    public ArrayList<Pokemon> getPlanToHunt() {
        return this.planToHunt;
    }

    // EFFECTS: returns a string of all the Pokémon in the list separated by commas.
    public String getNamesOfAllPokemon() {
        String names = "";
        int count = 0;
        for (Pokemon i : planToHunt) {
            names += i.getName();
            count += 1;

            if (count < planToHunt.size()) {
                names += ", ";
            }
        }
        return names;
    }

    // NOTE: Some code may be adapted from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // EFFECTS: Enters the Pokemon in this plan to hunt list to the json object and returns it.
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", "Plan To Hunt List");
        json.put("Pokemon", pokemonToJson());
        EventLog.getInstance().logEvent(new Event("saved plan to hunt list"));
        return json;
    }

    // NOTE: Some code may be adapted from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // EFFECTS: returns Pokemon in this plan to hunt list as a JSON array
    private JSONArray pokemonToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Pokemon p : planToHunt) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;
    }

    public void loadLog() {
        EventLog.getInstance().logEvent(new Event("Loaded plan to hunt list."));
    }
}
