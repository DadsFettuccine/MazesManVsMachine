import greenfoot.*; 
import java.util.ArrayList;

/**
 * The Player class handles: 
   * Player objects and the consequent initialisation
   * Keeping track of the time elapsed
   * The movement of the player
   * Checking if the player has completed a maze
 * 
 * By WS
 * Version: 22/05/2021
 */
public class Player extends Actor
{
    private int cellSize; // Stores the size of the cells in the world 
    private final int movementDelay = 5; // The movement delay in act cycles (5 act cycle gap between a previous input and registering a new input)
    public int timer = 0; // Keeps track of the number of act cycles that the player object has been in the world for
    private int count = 0; // Keeps track of the number of act cycles elapsed (Difference between 'timer' is that count gets reset to enable movement delay)
    private int currentCellRow; // Stores the row index of the mazeLayout array for the current cell
    private int currentCellCol; // Stores the column index of the mazeLayout array for the current cell
    public ArrayList<Integer> pathTakenRows = new ArrayList<>(); // Stores the row indexes of the mazeLayout array for the cells of the path the player has taken
    public ArrayList<Integer> pathTakenCols = new ArrayList<>(); // Stores the column indexes of the mazeLayout array for the cells of the path the player has taken
    private String previousInput; // Stores the previous registered movement input key

    /** Constructor for the initialisation of a player object **/
    public Player(int cellSize)
    {
        this.cellSize = cellSize; // Store the given cell size
        currentCellRow = 1; // Starting cell is at row index 1
        currentCellCol = 1; // Starting cell is at column index 1
        pathTakenRows.add(currentCellRow);
        pathTakenCols.add(currentCellCol);
        GreenfootImage playerImage = new GreenfootImage("player.png"); // Get and store the player image
        playerImage.scale(cellSize - cellSize/5, cellSize - cellSize/5); // Scale the player image to have a height and width = cellSize - 1/5 of the cell size (So that the player is smaller than the cells and is within the path)
        setImage(playerImage);
    }

    /** This method is called for every act cycle when the program is running which occurs when the run or act button is pressed in the environment **/
    public void act() 
    {
        timer++;
        movement();
    }    

    /** Handles the player movement by checking for inputs and updating the players position when it is a legal move **/
    private void movement()
    { 
        if (count >= movementDelay)
        {
            for (int i = 0; i < 2; i++) // The repetition is used to enable the 'slot in' effect when holding two keys down at once - On the first iteration the player can only move in a different direction to their previous direction moved, on the second iteration there is no such restriction 
            {
                if (Greenfoot.isKeyDown("W") && !Color.BLACK.equals(getWorld().getColorAt(getX(), getY() - cellSize)) && (previousInput != "W" || i == 1)) // If 'W' key is being pressed and there is no wall at on cell north of the current cell and (the previous input wasn't 'W' or this is the second iteration of the for loop)
                {
                    previousInput = "W";
                    currentCellRow--; // Up a cell
                    pathTakenRows.add(currentCellRow);
                    pathTakenCols.add(currentCellCol);
                    setLocation(getX(), getY() - cellSize); // Set location in world up a cell
                    count = 0;
                    i++; // Increments i a second time when the player moves so that the next iteration of the for loop is skipped if the player moved on the first iteration, ensuring that the player cannot skip the movement delay
                }
                else if (Greenfoot.isKeyDown("S") && !Color.BLACK.equals(getWorld().getColorAt(getX(), getY() + cellSize)) && (previousInput != "S" || i == 1)) // If 'S' key is being pressed and there is no wall at on cell south of the current cell and (the previous input wasn't 'S' or this is the second iteration of the for loop)
                {
                    previousInput = "S";
                    currentCellRow++; // Down a cell
                    pathTakenRows.add(currentCellRow);
                    pathTakenCols.add(currentCellCol);
                    setLocation(getX(), getY() + cellSize); // Set location in world down a cell
                    count = 0;
                    i++; // Increments i a second time when the player moves so that the next iteration of the for loop is skipped if the player moved on the first iteration, ensuring that the player cannot skip the movement delay
                }   
                else if (Greenfoot.isKeyDown("A") && !Color.BLACK.equals(getWorld().getColorAt(getX() - cellSize, getY())) && (previousInput != "A" || i == 1)) // If 'A' key is being pressed and there is no wall at on cell west of the current cell and (the previous input wasn't 'A' or this is the second iteration of the for loop)
                {
                    previousInput = "A";
                    currentCellCol--; // Left a cell
                    pathTakenRows.add(currentCellRow);
                    pathTakenCols.add(currentCellCol);
                    setLocation(getX() - cellSize, getY()); // Set location in world left a cell
                    count = 0;
                    i++; // Increments i a second time when the player moves so that the next iteration of the for loop is skipped if the player moved on the first iteration, ensuring that the player cannot skip the movement delay
                }

                else if (Greenfoot.isKeyDown("D") && !Color.BLACK.equals(getWorld().getColorAt(getX() + cellSize, getY())) && (previousInput != "D" || i == 1)) // If 'D' key is being pressed and there is no wall at on cell east of the current cell and (the previous input wasn't 'D' or this is the second iteration of the for loop)
                {
                    previousInput = "D";
                    currentCellCol++; // Right a cell
                    pathTakenRows.add(currentCellRow);
                    pathTakenCols.add(currentCellCol);
                    setLocation(getX() + cellSize, getY()); // Set location in world right a cell
                    count = 0;
                    i++; // Increments i a second time when the player moves so that the next iteration of the for loop is skipped if the player moved on the first iteration, ensuring that the player cannot skip the movement delay
                }  
            }
        }
        else
        {
            count++; // Having count++ in this else statement ensures that the count is not incremented directly after a move within the same act cycle (as count should be reset to 0 after a move) 
        }
    }

    /** Checks if the player has completed the maze **/
    public boolean checkCompleted()
    {
        if (Color.GREEN.equals(getWorld().getColorAt(getX(), getY()))) // If the player is on a green cell in the world, where a green cell represents the end cell
        {
            return true;
        }
        return false;
    }
}
