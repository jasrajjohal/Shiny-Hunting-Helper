package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents a Pokémon and holds their pokedex number, name, locations, encounter rate, and whether they are breedable.
public class Pokemon implements Writable {

    private int dexNum;
    private String name;
    private ArrayList locations;
    private int encounterRate;
    private boolean isBreedable;

    // EFFECTS: sets the pokedex number, Pokémon name, available locations and encounter rate of a Pokémon.
    //          As well as whether it is breedable.
    public Pokemon(int dexNum, String name, ArrayList<String> locations, int encounterRate, boolean isBreedable) {
        this.dexNum = dexNum;
        this.name = name;
        this.locations = locations;
        this.encounterRate = encounterRate;
        this.isBreedable = isBreedable;
    }

    // EFFECTS: Returns the pokedex number of the pokemon
    public int getDexNum() {
        return this.dexNum;
    }

    // EFFECTS: Returns the name of the pokemon
    public String getName() {
        return this.name;
    }

    // EFFECTS: Returns the available locations of the pokemon
    public ArrayList getLocations() {
        return this.locations;
    }

    // EFFECTS: Returns the encounter rate of the pokemon
    public int getEncounterRate() {
        return this.encounterRate;
    }

    // EFFECTS: Returns whether the pokemon is breedable
    public Boolean getIsBreedable() {
        return this.isBreedable;
    }

    // NOTE: Some code may be adapted from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // EFFECTS: Enters the Pokemon's attributes json object and returns it.
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("dexNum", dexNum);
        json.put("name", name);
        json.put("locations", locations);
        json.put("encounter rate", encounterRate);
        json.put("is breedable", isBreedable);
        return json;
    }
}
