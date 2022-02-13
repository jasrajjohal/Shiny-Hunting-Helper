package ui;

import model.*;
import model.Event;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.List;

// Shiny Hunter Application
// Some code may be adapted from:
// https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java
// https://youtu.be/Kmgo00avvEw
public class ShinyHunterApp extends JPanel implements ActionListener, ListSelectionListener {

    private JFrame frame; // main frame
    private JFrame pokemonFrame; // pokemon list frame

    private JButton addButton;
    private JButton removeButton;
    List<JButton> pokemonButtons = new ArrayList<JButton>(); // list of all pokemon as buttons

    private JList list; // the 3 different hunting lists
    private JList pokemonList; // the list of pokemon inside the hunting lists

    private DefaultListModel pokemonModel;

    private JMenuItem saveItem;
    private JMenuItem loadItem;
    private JMenuItem exitItem;

    private int currentSelectedList; // 0: Plant To Hunt, 1: Currently Hunting, 2: Hunted

    private static final String JSON_STORE = "./data/";
    private static final String JSON_STORE_PTH = "./data/planToHuntList.json";
    private static final String JSON_STORE_CH = "./data/currentlyHuntingList.json";
    private static final String JSON_STORE_H = "./data/huntedList.json";

    private Scanner input;
    private String chosenPokemon;
    private PokemonChecker checker = new PokemonChecker();
    private Boolean running = true;
    private PlanToHuntList planToHuntList;
    private CurrentlyHuntingList currentlyHuntingList;
    private HuntedList huntedList;
    private JsonWriter jsonWriterPTH;
    private JsonWriter jsonWriterCH;
    private JsonWriter jsonWriterH;
    private JsonReader jsonReaderPTH;
    private JsonReader jsonReaderCH;
    private JsonReader jsonReaderH;

    // EFFECTS: Creates a Scanner to obtain input, creates blank lists and then runs the Shiny Hunter Application.
    public ShinyHunterApp() {

        mainFrameSetup();
        menuSetup();
        listSetup();
        pokemonListSetup();
        mainButtonsSetup();
        pokemonFrameSetup();
        menuBarSetup();

        frame.revalidate();

        input = new Scanner(System.in);
        planToHuntList = new PlanToHuntList();
        currentlyHuntingList = new CurrentlyHuntingList();
        huntedList = new HuntedList();
        jsonWriterPTH = new JsonWriter(JSON_STORE_PTH);
        jsonWriterCH = new JsonWriter(JSON_STORE_CH);
        jsonWriterH = new JsonWriter(JSON_STORE_H);
        jsonReaderPTH = new JsonReader(JSON_STORE_PTH);
        jsonReaderCH = new JsonReader(JSON_STORE_CH);
        jsonReaderH = new JsonReader(JSON_STORE_H);

        // runShinyHunterApp();
    }

    // MODIFIES: this
    // EFFECTS: Calls associated methods depending on what button is pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            pokemonFrame.setVisible(true); // makes the pokemon frame visible
        } else if (e.getSource() == removeButton && !pokemonList.isSelectionEmpty()) {
            try {
                removePokemon();
            } catch (ConcurrentModificationException exception) {
                pokemonListRefresher();
            }
        } else if (e.getSource() == saveItem) {
            saveLists();
        } else if (e.getSource() == loadItem) {
            loadLists();
            pokemonListRefresher();
        } else if (e.getSource() == exitItem) {
            for (Event event : EventLog.getInstance()) {
                System.out.println(event);
            }
            System.out.println("Thank you for using Shiny Hunting Helper!");
            System.exit(0);
        } else if (e.getActionCommand().equals("add pokemon")) {
            String pokemonName = getPokemonButtonText(e);
            addPokemon(pokemonName);
        }
    }

    //This method is required by ListSelectionListener.
    // MODIFIES: this
    // EFFECTS: clears and reloads the list of pokemon whenever the selected hunting list changes
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (list.getSelectedIndex() == 0 || list.getSelectedIndex() == 1 || list.getSelectedIndex() == 2) {
                pokemonModel.clear();
                //Selection, enable the add button.
                addButton.setEnabled(true);
                currentSelectedList = list.getSelectedIndex();

                loadPokemonListEntries();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: adds pokemon to the pokemon list depending on which hunting list is currently selected
    private void loadPokemonListEntries() {
        if (currentSelectedList == 0) {
            for (Pokemon p : planToHuntList.getPlanToHunt()) {
                if (!pokemonModel.contains(p.getName())) {
                    pokemonModel.addElement(p.getName());
                }
            }
        } else if (currentSelectedList == 1) {
            for (Pokemon p : currentlyHuntingList.getCurrentlyHunting()) {
                if (!pokemonModel.contains(p.getName())) {
                    pokemonModel.addElement(p.getName());
                }
            }
        } else if (currentSelectedList == 2) {
            for (Pokemon p : huntedList.getHuntedList()) {
                if (!pokemonModel.contains(p.getName())) {
                    pokemonModel.addElement(p.getName());
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: reloads all the pokemon in the corresponding hunting list and ensures the pokemon list is updated.
    private void pokemonListRefresher() {
        if (currentSelectedList == 0) {
            pokemonModel.clear();
            for (Pokemon p : planToHuntList.getPlanToHunt()) {
                if (!pokemonModel.contains(p.getName())) {
                    pokemonModel.addElement(p.getName());
                }
            }
        } else if (currentSelectedList == 1) {
            pokemonModel.clear();
            for (Pokemon p : currentlyHuntingList.getCurrentlyHunting()) {
                if (!pokemonModel.contains(p.getName())) {
                    pokemonModel.addElement(p.getName());
                }
            }
        } else if (currentSelectedList == 2) {
            pokemonModel.clear();
            for (Pokemon p : huntedList.getHuntedList()) {
                if (!pokemonModel.contains(p.getName())) {
                    pokemonModel.addElement(p.getName());
                }
            }
        }
    }

    // MODIFIES: PlanToWatchList, CurrentlyHuntingList, HuntedList
    // EFFECTS: adds the pokemon to the corresponding list then updates the pokemon list and hides the pokemon frame
    private void addPokemon(String pokemonName) {
        if (currentSelectedList == 0) {
            planToHuntList.addPokemon(checker.getPokemonByName(pokemonName));
        } else if (currentSelectedList == 1) {
            currentlyHuntingList.addPokemon(checker.getPokemonByName(pokemonName));
        } else if (currentSelectedList == 2) {
            huntedList.addPokemon(checker.getPokemonByName(pokemonName));
        }
        pokemonListRefresher();
        pokemonFrame.setVisible(false);
    }

    // EFFECTS: returns the name of the pokemon from the pokemon button that was pressed. Or null if the action event
    //          passed is not a pokemon button.
    private String getPokemonButtonText(ActionEvent e) {
        for (JButton pokemonButton : pokemonButtons) {
            if (e.getSource() == pokemonButton) {
                return pokemonButton.getText();
            }
        }
        return null;
    }

    // MODIFIES: PlanToWatchList, CurrentlyHuntingList, HuntedList
    // EFFECTS: removes the selected pokemon from the associated list and then refreshes the pokemon list
    private void removePokemon() {
        if (currentSelectedList == 0) {
            planToHuntList.removePokemon(checker.getPokemonByName(pokemonList.getSelectedValue().toString()));
        } else if (currentSelectedList == 1) {
            currentlyHuntingList.removePokemon(checker.getPokemonByName(pokemonList.getSelectedValue().toString()));
        } else if (currentSelectedList == 2) {
            huntedList.removePokemon(checker.getPokemonByName(pokemonList.getSelectedValue().toString()));
        }
        pokemonListRefresher();
    }

    // MODIFIES: this
    // EFFECTS: Creates a JMenuBar with a file menu. Creates the various menu items for the file menu and sets their
    //          keybinds. Adds the menubar to the main frame.
    private void menuBarSetup() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        saveItem = new JMenuItem("Save");
        loadItem = new JMenuItem("Load");
        exitItem = new JMenuItem("Exit");

        saveItem.addActionListener(this);
        loadItem.addActionListener(this);
        exitItem.addActionListener(this);

        fileMenu.setMnemonic(KeyEvent.VK_F); // Alt + f for file
        saveItem.setMnemonic(KeyEvent.VK_S); // s for save
        loadItem.setMnemonic(KeyEvent.VK_L); // l for load
        exitItem.setMnemonic(KeyEvent.VK_E); // e for exit

        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);
    }

    // MODIFIES: this
    // EFFECTS: Creates the main JFrame for the application.
    private void mainFrameSetup() {
        frame = new JFrame(); // creates a frame
        frame.setTitle("Menu"); // sets the title of the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exits out of the application when closed
        frame.setLayout(new BorderLayout());
        frame.setSize(1024, 512); // sets the size of the frame
        frame.setVisible(true); // makes the frame visible

        ImageIcon iconImage = new ImageIcon("./images/star icon.png"); // creates an ImageIcon
        frame.setIconImage(iconImage.getImage()); // sets the icon of the frame
        frame.getContentPane().setBackground(new Color(123, 50, 250)); // sets the colour of the background

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                for (Event event : EventLog.getInstance()) {
                    System.out.println(event);
                }
                System.out.println("Thank you for using Shiny Hunting Helper!");
                System.exit(0);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: creates a new JFrame for the popup after clicking the "Add" button. Creates JButtons for all available
    //          pokemon and adds them to the pokemon JFrame.
    private void pokemonFrameSetup() {
        pokemonFrame = new JFrame();
        pokemonFrame.setLayout(new FlowLayout());
        pokemonFrame.setTitle("Available Pokemon"); // sets the title of the frame
        pokemonFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); // hides the JFrame when closed
        pokemonFrame.setSize(576, 576); // sets the size of the frame

        ArrayList<String> paths = new ArrayList<String>(Arrays.asList("./images/treecko.png", "./images/grovyle.png"));
        paths.addAll(Arrays.asList("./images/sceptile.png", "./images/torchic.png", "./images/combusken.png"));
        paths.addAll(Arrays.asList("./images/blaziken.png", "./images/mudkip.png", "./images/marshtomp.png"));
        paths.addAll(Arrays.asList("./images/swampert.png", "./images/poochyena.png", "./images/mightyena.png"));
        paths.addAll(Arrays.asList("./images/zigzagoon.png", "./images/linoone.png", "./images/wurmple.png"));
        paths.addAll(Arrays.asList("./images/silcoon.png"));

        for (int i = 0; i < 15; i++) {
            ImageIcon image = new ImageIcon(paths.get(i));
            JButton pokemonButton = new JButton(image);
            pokemonButton.setActionCommand("add pokemon");
            pokemonButton.setText(checker.getPokemonChecker().get(i).getName());
            pokemonButton.setSize(128, 32);
            pokemonButton.addActionListener(this);
            pokemonButton.setFont(new Font("Helvetica", Font.BOLD, 14));
            pokemonButton.setFocusable(false);
            pokemonButtons.add(pokemonButton);
            pokemonFrame.add(pokemonButton);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a JPanel for the main button options with formatting. Creates the main JButtons with formatting.
    //          Adds the buttons to the JPanel and adds the JPanel to the main JFrame.
    private void mainButtonsSetup() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.orange);

        addButton = new JButton();
        addButton.setText("Add");
        addButton.setBounds(0, 384, 64, 32);
        addButton.addActionListener(this);
        addButton.setFont(new Font("Helvetica", Font.BOLD, 14));
        addButton.setFocusable(false);
        removeButton = new JButton();
        removeButton.setText("Remove");
        removeButton.setBounds(384, 384, 96, 32);
        removeButton.addActionListener(this);
        removeButton.setFont(new Font("Helvetica", Font.BOLD, 14));
        removeButton.setFocusable(false);

        buttonPanel.setPreferredSize(new Dimension(1024, 64));

        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);
    }

    // MODIFIES: this
    // EFFECTS: Creates a JList for the list of pokemon in each hunting list with formatting. Puts the JList in a
    //          JScrollPane. Adds the JScrollPane to the main frame.
    private void pokemonListSetup() {
        pokemonModel = new DefaultListModel();
        pokemonList = new JList(pokemonModel);
        pokemonList.setVisibleRowCount(5);
        pokemonList.setBackground(Color.gray);
        JScrollPane pokemonScrollPane = new JScrollPane(pokemonList);
        pokemonList.setPreferredSize(new Dimension(764, 384));

        frame.add(pokemonScrollPane);
        frame.add(pokemonScrollPane, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: Creates a JList for the various hunting lists with formatting. Puts the JList in a JScrollPane.
    //          Adds the JScrollPane to the main frame.
    private void listSetup() {
        DefaultListModel listModel = new DefaultListModel();
        listModel.addElement("Plan To Hunt List");
        listModel.addElement("Currently Hunting List");
        listModel.addElement("Previously Hunted List");
        list = new JList(listModel);
        list.setPreferredSize(new Dimension(256, 384));
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
        list.setBackground(Color.pink);
        JScrollPane listScrollPane = new JScrollPane(list);

        frame.add(listScrollPane, BorderLayout.WEST);
    }

    // MODIFIES: this
    // EFFECTS: creates the menu JLabel with formatting. Creates menu JPanel with formatting. Adds the menu JLabel to
    //          the menu JPanel and adds the menu JPanel to the main JFrame.
    private void menuSetup() {
        JLabel menuLabel = new JLabel();
        menuLabel.setText("Please Select What You Would Like To Do");
        menuLabel.setHorizontalAlignment(SwingConstants.CENTER);
        menuLabel.setVerticalAlignment(SwingConstants.TOP);
        menuLabel.setFont(new Font("Helvetica", Font.BOLD, 24));

        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(Color.blue);
        menuPanel.setPreferredSize(new Dimension(1024, 64));
        menuPanel.add(menuLabel);
        frame.add(menuPanel, BorderLayout.NORTH);
    }

    // EFFECTS: Processes the Application and handles user input
    private void runShinyHunterApp() {

        while (running) {
            start();
            menu();
        }
        System.out.println("Thank you for using Shiny Hunting Helper!");
    }

    // EFFECTS: Asks user to enter a Pokémon name until a valid one is entered.
    private void start() {
        Boolean isValidPokemon = false;

        while (!isValidPokemon) {
            System.out.println("Please type the name of the Pokemon you wish to hunt.");
            chosenPokemon = input.next().toLowerCase();
            if (checker.checkValidPokemon(chosenPokemon)) {
                isValidPokemon = true;
            } else {
                System.out.println("Oops, the pokemon name you have entered is incorrect.");
            }
        }
    }

    // EFFECTS: Asks user what they would like to do with the application and calls the associated methods accordingly.
    private void menu() {
        System.out.println("What would you like to do with " + chosenPokemon);
        System.out.println("Options include: \n 1. Add to a list \n 2. Remove from a list \n 3. Check state of a list");
        System.out.println(" 4. Go Back \n 5. Quit App \n 6. Save lists \n 7. Load lists");
        System.out.println("Enter the number of the option you would like to select.");
        int optionSelect = input.nextInt();

        if (optionSelect == 1) {
            addToList();
        } else if (optionSelect == 2) {
            removeFromList();
        } else if (optionSelect == 3) {
            checkStateOfList();
        } else if (optionSelect == 5) {
            running = false;
        } else if (optionSelect == 6) {
            saveLists();
        } else if (optionSelect == 7) {
            loadLists();
        } else {
            System.out.println("Let's go back!");
        }
    }

    // MODIFIES: PlanToWatchList, CurrentlyHuntingList, HuntedList
    // EFFECTS: adds the chosen Pokémon to any of the 3 lists. If the Pokémon is being added to the plan to hunt list,
    //          it is removed from the currently hunting list if it is in there. The inverse is true if the Pokémon is
    //          being added to the currently hunting List.
    //          If the Pokémon is added to the hunted list, it is removed from the other 2 lists.
    private void addToList() {
        System.out.println("Select which list you wish to add to by entering the associated number.");
        System.out.println(" 1. Plan to Hunt List \n 2. Currently Hunting List \n 3. Hunted List");
        int listSelector = input.nextInt();

        if (listSelector == 1) {
            planToHuntList.addPokemon(checker.getPokemonByName(chosenPokemon));
            currentlyHuntingList.removePokemon(checker.getPokemonByName(chosenPokemon));
            System.out.println(chosenPokemon + " was successfully added!");

        } else if (listSelector == 2) {
            currentlyHuntingList.addPokemon(checker.getPokemonByName(chosenPokemon));
            planToHuntList.removePokemon(checker.getPokemonByName(chosenPokemon));
            System.out.println(chosenPokemon + " was successfully added!");

        } else if (listSelector == 3) {
            huntedList.addPokemon(checker.getPokemonByName(chosenPokemon));
            planToHuntList.removePokemon(checker.getPokemonByName(chosenPokemon));
            currentlyHuntingList.removePokemon(checker.getPokemonByName(chosenPokemon));
            System.out.println(chosenPokemon + " was successfully added!");

        } else {
            System.out.println("Uh Oh " + listSelector + " is an invalid response! Let's retry that!");
        }
    }

    // MODIFIES: PlanToWatchList, CurrentlyHuntingList, HuntedList
    // EFFECTS: Removes the chosen Pokémon from any of the 3 lists.
    private void removeFromList() {
        System.out.println("Select which list you wish to remove from by entering the associated number.");
        System.out.println(" 1. Plan to Hunt List \n 2. Currently Hunting List \n 3. Hunted List");
        int listSelector = input.nextInt();

        if (listSelector == 1) {
            planToHuntList.removePokemon(checker.getPokemonByName(chosenPokemon));
            System.out.println(chosenPokemon + " was successfully removed!");
        } else if (listSelector == 2) {
            currentlyHuntingList.removePokemon(checker.getPokemonByName(chosenPokemon));
            System.out.println(chosenPokemon + " was successfully removed!");
        } else if (listSelector == 3) {
            huntedList.removePokemon(checker.getPokemonByName(chosenPokemon));
            System.out.println(chosenPokemon + " was successfully removed!");
        } else {
            System.out.println("Uh Oh " + listSelector + " is an invalid response! Let's retry that!");
        }
    }

    // EFFECTS: lists the names of Pokémon in any of the 3 lists.
    private void checkStateOfList() {
        System.out.println("Select which list you wish to see by entering the associated number.");
        System.out.println(" 1. Plan to Hunt List \n 2. Currently Hunting List \n 3. Hunted List");
        int listSelector = input.nextInt();

        if (listSelector == 1) {
            System.out.println(" Your Plan to Hunt list is: \n" + planToHuntList.getNamesOfAllPokemon());
            System.out.println("");
        } else if (listSelector == 2) {
            System.out.println(" Your Currently Hunting list is: \n" + currentlyHuntingList.getNamesOfAllPokemon());
            System.out.println("");
        } else if (listSelector == 3) {
            System.out.println(" Your Hunted list is: \n" + huntedList.getNamesOfAllPokemon());
            System.out.println("");
        } else {
            System.out.println("Uh Oh " + listSelector + " is an invalid response! Let's retry that!");
        }
    }

    // NOTE: Some code may be adapted from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // EFFECTS: saves the plan to hunt, currently hunting, and hunted lists to file
    private void saveLists() {
        try {
            jsonWriterPTH.open();
            jsonWriterPTH.writePTList(planToHuntList);
            jsonWriterPTH.close();

            jsonWriterCH.open();
            jsonWriterCH.writeCHList(currentlyHuntingList);
            jsonWriterCH.close();

            jsonWriterH.open();
            jsonWriterH.writeHList(huntedList);
            jsonWriterH.close();

            System.out.println("All lists have been saved!");

        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // NOTE: Some code may be adapted from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // MODIFIES: this
    // EFFECTS: loads plan to hunt, currently hunting, and hunted lists from file
    private void loadLists() {
        try {
            planToHuntList = jsonReaderPTH.readPTList();
            currentlyHuntingList = jsonReaderCH.readCHList();
            huntedList = jsonReaderH.readHList();

            System.out.println("Loaded all lists!");
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

}
