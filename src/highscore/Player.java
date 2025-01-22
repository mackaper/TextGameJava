 /**
 *  This class is used to create a player with name, country and their score, this class uses methods to print these fields.
 *
 * @author  Marcus Bardvall
 * @version 2021.11.10
 */

public class Player {
    //Fields
    String name;
    String country;
    int score;

    public Player(String name, String country, int score) {
        this.name = name;
        this.country = country;
        this.score = score;
    }
    //Formating to a string for printOut convenience
    public String toString() {
        return "Player " + name + " from " + country + " scored " + score + " points";
    }
}
