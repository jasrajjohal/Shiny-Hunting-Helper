package persistence;

import model.CurrentlyHuntingList;
import model.HuntedList;
import model.PlanToHuntList;
import model.Pokemon;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// NOTE: Some code may be adapted from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
// Represents a writer that writes JSON representation of pokemon lists to file
public class JsonWriter {

    private static final int TAB = 4;
    private String destination;
    private PrintWriter writer;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of plan to hunt list to file
    public void writePTList(PlanToHuntList pth) {
        JSONObject json = pth.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of currently hunting list to file
    public void writeCHList(CurrentlyHuntingList ch) {
        JSONObject json = ch.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of hunted list to file
    public void writeHList(HuntedList h) {
        JSONObject json = h.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }

}
