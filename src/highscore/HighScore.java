import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

 /**
 *  This class prints highscores from a textfile.
 *
 * @author  Marcus Bardvall
 * @version 2021.11.10
 */

public class HighScore {

    public static void printHighScores(String filename) {
        BufferedReader inputStream = null;
        //Create at list of players
        ArrayList<Player> players = new ArrayList<>();

        try {
            inputStream = new BufferedReader(new FileReader(filename));
            String line;
            String[] playerData;
            //modify to show what country a player is from, Changed the second variable in playerdata to be the players country in the textfile
            while ((line = inputStream.readLine()) != null) {
                playerData = line.split(",");
                //Adds every player in the file to the playerList
                players.add(new Player(playerData[0], playerData[1] ,Integer.parseInt(playerData[2])));
            }
            //Print out every players
            for (Player p : players) {
                System.out.println(p.toString());
            }
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    public static void main(String[] args) {
        printHighScores("scores.txt");
    }
}
