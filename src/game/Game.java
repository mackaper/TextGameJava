import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

 /**
 *  This class is the main class of the "Auntie-dote" application.
 *  "Auntie-dote" is a simple game where you play as an antidote moving 
 *  through a humans body in search for deadly venom.
 *
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 *
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 *
 * @author  Marcus Bardvall
 * @version 2021.11.10
 */

public class Game
{
    private Parser parser;
    private Room currentRoom;
    int moves = 12; //the "timer" that limits the amout of moves that the player is able to make before the aunt dies of the venom

    /**
     * Create the game and initialise its internal map.
     */
    public Game()
    {
        //Creates rooms through a text file
        createRoomsFromFile("rooms.txt");
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    public void createRoomsFromFile(String filename)
    {
        BufferedReader inputStream = null;
        //Hashmap to store all the rooms
        HashMap<String, Room> roomsWorld = new HashMap<String, Room>();

        try {
            inputStream = new BufferedReader(new FileReader(filename));
            String line;

            String[] roomData;
            while ((line = inputStream.readLine()) != null) {
                roomData = line.split(",");
                //check if the line starts with "Room" to create an instance  
                if(roomData[0].equals("Room"))
                {
                    roomsWorld.put(roomData[1], new Room(roomData[2], roomData[3]));

                }
                //Check if the line starts with "Exit" to call the setExit method to the given room
                else if(roomData[0].equals("Exit"))
                {
                    roomsWorld.get(roomData[1]).setExit(roomData[2], roomsWorld.get(roomData[3]));
                }

            }         
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
        
        currentRoom = roomsWorld.get("leftArm"); // start game with the antidote
    }
    
    
    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play()
    {
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to Auntie-dote!");
        System.out.println("A young boy's aunt has been bitten by a snake and seeks help");
        System.out.println("You, the antidote, must enter in her left arm and seek out the poison and destroy it");
        System.out.println("Type 'help' if you need help.");
        System.out.println("to check for venom, type 'venomCheck'");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, this could either be a win or loss, false otherwise.
     */
    private boolean processCommand(Command command)
    {
        boolean wantToQuit = false;
        boolean winCheck = false;
        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            moves--;
            goRoom(command);
            System.out.println("actions left: " + moves);
        }
        //checks if the body part has any venom in it
        else if (commandWord.equals("venomCheck")) {
            moves--;
            wantToQuit = checkStatus(command);
            winCheck = wantToQuit;
            System.out.println("actions left: " + moves);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }

        if (moves==0 && winCheck == false)
        {
            System.out.println("out of actions! YOU LOSE");
            wantToQuit=true;
        }      
        
        // else command not recognised.
        return wantToQuit;
    }
    //Method to check if there is any venom in the bodypart
    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the check finds poisoned bodypart.
     */
    public boolean checkStatus(Command command)
    {
        if(command.hasSecondWord()) {
            System.out.println("Only write 'venomCheck' please");
            return false;
        }
        else
        {
            if(currentRoom.checkHealth().equals("Poisoned"))
            {
                System.out.println("Venom found and will be destroyed immediately");
                System.out.println("You saved the aunt's life! YOU WIN!!!");
                return true;
            }
            else
            {
                System.out.println("no sign of venom in this part of the body");
                return false;
            }
        }
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the
     * command words.
     */
    private void printHelp()
    {
        System.out.println("You are the antidote, you need to find the venom");
        System.out.println("otherwise the host dies");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /**
     * Try to in to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("You can't go there!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    /**
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command)
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }
}
