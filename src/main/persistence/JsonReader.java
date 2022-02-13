package persistence;

import model.CurrentlyHuntingList;
import model.HuntedList;
import model.PlanToHuntList;
import model.PokemonChecker;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// NOTE: Some code may be adapted from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {

    private String source;

    private PokemonChecker checker = new PokemonChecker();

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads plan to hunt list from file and returns it;
    // throws IOException if an error occurs reading data from file
    public PlanToHuntList readPTList() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePTList(jsonObject);
    }

    // EFFECTS: reads currently hunting list from file and returns it;
    // throws IOException if an error occurs reading data from file
    public CurrentlyHuntingList readCHList() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCHList(jsonObject);
    }

    // EFFECTS: reads hunting list from file and returns it;
    // throws IOException if an error occurs reading data from file
    public HuntedList readHList() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseHList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses plan to hunt list from JSON object and returns it
    private PlanToHuntList parsePTList(JSONObject jsonObject) {
        PlanToHuntList pth = new PlanToHuntList();
        addToPlanToHuntList(pth, jsonObject);
        pth.loadLog();
        return pth;
    }

    // EFFECTS: parses currently hunting list from JSON object and returns it
    private CurrentlyHuntingList parseCHList(JSONObject jsonObject) {
        CurrentlyHuntingList ch = new CurrentlyHuntingList();
        addToCurrentlyHuntingList(ch, jsonObject);
        ch.loadLog();
        return ch;
    }

    // EFFECTS: parses hunted list from JSON object and returns it
    private HuntedList parseHList(JSONObject jsonObject) {
        HuntedList h = new HuntedList();
        addToHuntedList(h, jsonObject);
        h.loadLog();
        return h;
    }

    // MODIFIES: pth
    // EFFECTS: parses pokemon from JSON object and adds them to plan to hunt list
    private void addToPlanToHuntList(PlanToHuntList pth, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Pokemon");
        for (Object json : jsonArray) {
            JSONObject nextPokemon = (JSONObject) json;
            addPokemonPTList(pth, nextPokemon);
        }
    }

    // MODIFIES: pth
    // EFFECTS: parses Pokemon from JSON object and adds it to plan to hunt list
    private void addPokemonPTList(PlanToHuntList pth, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        pth.addPokemon(checker.getPokemonByName(name));
    }

    // MODIFIES: ch
    // EFFECTS: parses Pokemon from JSON object and adds them to currently hunting list
    private void addToCurrentlyHuntingList(CurrentlyHuntingList ch, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Pokemon");
        for (Object json : jsonArray) {
            JSONObject nextPokemon = (JSONObject) json;
            addPokemonCHList(ch, nextPokemon);
        }
    }

    // MODIFIES: ch
    // EFFECTS: parses Pokemon from JSON object and adds it to Currently Hunting List
    private void addPokemonCHList(CurrentlyHuntingList ch, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        ch.addPokemon(checker.getPokemonByName(name));
    }

    // MODIFIES: h
    // EFFECTS: parses Pokemon from JSON object and adds them to Hunted List
    private void addToHuntedList(HuntedList h, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Pokemon");
        for (Object json : jsonArray) {
            JSONObject nextPokemon = (JSONObject) json;
            addPokemonHList(h, nextPokemon);
        }
    }

    // MODIFIES: h
    // EFFECTS: parses Pokemon from JSON object and adds it to Hunted List
    private void addPokemonHList(HuntedList h, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        h.addPokemon(checker.getPokemonByName(name));
    }
}
