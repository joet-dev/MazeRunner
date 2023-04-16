# Maze Runner :video_game:
*`Created 2021`*

# Description
Maze Runner is a simple 2D grid-based game where you must navigate your way through a randomly generated world to collect as many gold coins as possible and make it to the exit. Apples are scattered over the game world to regenerate your character’s stamina. Watch out for traps. If you stand in one you will be forced to pay a gold coin. 

To win the game: Reach the exit. Your score will be the number of coins you have collected.
Beware: Make sure your stamina doesn’t drop to zero or you will lose the game! If you step on a trap without any gold coins you will lose the game!

Below is a basic UML diagram of the game. 

![Maze Game UML](https://user-images.githubusercontent.com/69287038/232345221-b16f30d9-1e8b-4f34-a5a1-9f3f32fbee60.png)

**Game Features**

Users can set the difficulty (0-10) of the game. If the value input is invalid (letters or numbers outside of the range), the game will automatically use the default value of 5. To prevent misunderstandings and issues, the difficulty cannot be changed while in game. Therefore, I have implemented a quit button if they want to change the difficulty. The game has full save-state and load-state functionality using java.io serializable. 
