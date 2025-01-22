# Game Documentation

### Theme
The idea of this game called "Auntie-dote" is that a young boys aunt recently has been bitten by a snake and gets an antidote against it. In this game, YOU play as the antidote traveling through the aunts bodyparts in search of the venom to terminate it.

### How to win/lose
To Lose:
You lose when the "timer" is out, that means the player has used to many "moves" or "actions" before the venom is found. In this version you have 12 actions total. The Idea is that if you are to slow as the antidote, then the venom will be able to kill the host(the aunt).

To win:
Go through each body part and use the "venomCheck" command to check if there is venom, when you get rid of all the venom from a specific bodypart, you win. In my case, I have put the venom in the right leg to make this "win guide" a bit easier, however this feature should be randomized for maximum fun.


### Extensions
I have implemented the first option, the "timer" that tracks the actions/commands a player gives. I think this feature
was well suited for this game since you should not have all the time in the world to stop deadly venom. 

This is added in the Game.java file as and integer at 12 called "moves". When you use the command "go" or "venomCheck" the integer goes down one number(move--). I also added an if check to see if moves has reached zero and should therefore lose.

