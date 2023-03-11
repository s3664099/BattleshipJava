# Battleship - Java

This is a game that I developed from one of the text books that I had from university, namely *The Design and 
Analysis Algorithms*. It was a shame that I never got a chance to practice more of those questions, but I'm doing now,
even though I'm still stuck in brute force algorithms (which is why Battleships is one of the games that they
suggested). Anyway, this is the Java version of the game (and I do plan on writing a C++ version of it as
well).

## Executing the Game

To install the game you have to first compile it:

javac *.java

Once it has compiled you run it as follows:

java main

## Playing the Game

You start off by selection 0,1, or 2 players. 0 players will have the computer play against itself, one player you play
against the computer, and two players will do the obvious (though it is on a single computer, therefore you will need to
look away when the other player is playing).

The first thing is you set your ships. The ships need to be at least one space apart (for some reason ships can't be
placed on the bottom line). You select whether they are across or down, and then select the co-ordinates. If the ship
can't be placed there, then you are given a list of co-ordinates where the ship can be placed.

Once the ships have been placed, the game begins. You select co-ordinates where you are going to fire, and the opponent
does the same. You cannot shot the same place twice (as is the case with the board game). You are notified of when you
hit a ship, and the ship you hit. If you sink the ship you are notified, and if you are the first to sink all of the
ships then you win.

## Issues
It looks like you can't place a ship on the bottom line (number 9) of the grid.

## Future Additions
I'm considering adding JFrames to hold the game, but that will be sometime down the track. I'll be leaving my version
in Eclipse so that I can simply work from there.

## Algorithms

There are two algorithms that are of note in the game, which I will outline below. The first is where the computer
places the ships, and the second is where the computer makes the shot.

**Place Ships**

So, there are two lists that are a part of this algorithm: Potential Places and Used Spots. Potential places holds a list of
co-ordinates that are potential places a ship can be placed, which used spots hold a list of co-ordinates that have been used.
Whenever a ship is placed, the co-ordinates, and the surrounding co-ordinates, are placed into Used Spots.

The algorithm selects whether the ships with be horizontal or vertical, and then takes the length of the ship and goes through
the board and compares the co-ordinates that the ship will take up if that spot is used, and see if it is already being used. If
it is, then that co-ordinate will not be added as a potential spot. If there are no potential spots, then the alrgorithm will look
for potential spots for the other angle.

Once a list of potential spots has been created, the computer will take a random co-ordinate and place the ship there.

**Select Shot**

The two options are either going from one side of the board to the other firing shots (which is not a particularly efficient way
of winning the game) or randoming selection spots to hit (which is somewhat better, but needs a but more thought to be able to
win the game). So, noting that randomly selecting squares offers a better chance of winning, I worked the algorithm for that.
(Actually, the idea of going from one end of the board to the other simiply never crossed my mind, which is a good thing).

So, a list of all the co-ordinates is generated, and the computer picks a random one. If it is a miss, the next player has a shot, and the
co-ordinate is removed from the list. However, if a hit is registered, this will narrow down the opportunities to the co-ordinates
immediately adjoining the co-ordinate. Note, the player then gets to shoot again, so the player will select one of those four co-orinates.
If it is a miss, then that co-ordinate is removed, but if it is a hit, the two co-ordinates that aren't of the same angle will be removed.
The computer will continue heading in that direction until a miss is recorded, or the ship is sunk.

I used this method because this seems to be the most logical way that a human player would play. Unless the human player is really familiar
with the opponent's playing style, Battleships really comes down to a guessing game, since you do not know where the other player's ships
are located. As such, randomly selecting spots until a hit is recorded, in the end, is how a human would play the game.

