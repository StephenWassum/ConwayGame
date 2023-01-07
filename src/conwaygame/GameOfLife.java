package conwaygame;
import java.util.ArrayList;
/**
 * Main Methods used by the Driver to run the conway game.
 */
public class GameOfLife {

    private static final boolean ALIVE = true;
    private static final boolean  DEAD = false;

    private boolean[][] grid;    
    private int totalAliveCells; 

    /**
    * Default Constructor
    */
    public GameOfLife() {
        grid = new boolean[5][5];
        totalAliveCells = 5;
        grid[1][1] = ALIVE;
        grid[1][3] = ALIVE;
        grid[2][2] = ALIVE;
        grid[3][2] = ALIVE;
        grid[3][3] = ALIVE;
    }

    /**
    * Constructor which creates a grid of alive and dead cells to be used in following methods.
    */
    public GameOfLife (String file) {
        StdIn.setFile(file);
        int r = StdIn.readInt();
        int c = StdIn.readInt();

        grid = new boolean [r][c];
        for (int index = 0; index < r; index++) {
            for (int index2 = 0; index2 < c; index2++) {
                    boolean tf = StdIn.readBoolean();
                    if (tf == ALIVE){
                       grid[index][index2] = ALIVE;
                    }
                    else{
                    grid[index][index2] = DEAD;
                    }
                }
        }
    }

    /**
     * Returns grid
     */
    public boolean[][] getGrid () {
        return grid;
    }
    
    /**
     * Returns totalAliveCells
     */
    public int getTotalAliveCells () {
        return totalAliveCells;
    }

    /**
     * Returns the status of the cell at (row,col): ALIVE or DEAD
     */
    public boolean getCellState (int row, int col) {
        if (grid[row][col] == ALIVE) {
            return true; } 
        else {
            return false;
        }
    }

    /**
     * Returns true if there are any cells Alive
     */
    public boolean isAlive () {
        for (int index = 0; index < grid.length; index++) {
            for (int index2 = 0; index2 < grid[index].length; index2++) {
                if (grid[index][index2] == ALIVE){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Determines the number of alive cells around a selected cell. Every cell has 8 neighbors,
     * cell neighbors wrap around array edges.
     */
    public int numOfAliveNeighbors (int row, int col) {
        int Neighbors = 0;

        for (int index = row-1; index < row+2; index++) {
            int mod = ((index) + grid.length) % grid.length;
            for (int index2 = col-1; index2 < col+2; index2++) {
                if (index == row && index2 == col){
                    continue;
                }
                int mod2 = ((index2) + grid[0].length) % grid[0].length;
                if (grid[mod][mod2] == true){
                    Neighbors += 1;
                }
            }
        }

        return Neighbors;
    }

    /**
     * Creates a new grid with the next generation of the current grid using 
     * the rules for Conway's Game of Life.
     */
    public boolean[][] computeNewGrid () {
        int r = grid.length;
        int c = grid[0].length;
        boolean [][] NewGrid = new boolean[r][c];

        for (int index = 0; index < grid.length; index++) {
            for (int index2 = 0; index2 < grid[0].length; index2++) {
                if (grid[index][index2] == ALIVE && numOfAliveNeighbors(index, index2) <= 1){
                    NewGrid[index][index2] = DEAD; }
                else if (grid[index][index2] == DEAD && numOfAliveNeighbors(index, index2) == 3){
                    NewGrid[index][index2] = ALIVE; }
                else if (grid[index][index2] == ALIVE && numOfAliveNeighbors(index, index2) >= 4){
                    NewGrid[index][index2] = DEAD; }
                else {
                    NewGrid[index][index2] = grid[index][index2];}
            }}
        return NewGrid;
    }

    /**
     * Updates totalAliveCells instance variable
     */
    public void nextGeneration () {
        grid = computeNewGrid();
    }

    /**
     * Updates the current grid with the grid computed after multiple (n) generations. 
     */
    public void nextGeneration (int n) {
        for (int index = 0; index < n; index++) {
            grid = computeNewGrid();
        }
    }

    /**
     * Determines the number of separate cell communities 
     */
    public int numOfCommunities() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        WeightedQuickUnionUF uf = new WeightedQuickUnionUF(grid.length, grid[0].length);
        int communities = 0;

        for (int index = 0; index < grid.length; index++) {
            for (int index2 = 0; index2 < grid[0].length; index2++) {
                if (grid[index][index2]==ALIVE){
                    if (numOfAliveNeighbors(index, index2) == 0) {
                        communities += 1;
                    }

                    else if (numOfAliveNeighbors(index, index2) > 0){

                        if (grid[((index+1) + grid.length) % grid.length][index2] == ALIVE){
                            uf.union(index, index2, ((index+1) + grid.length) % grid.length, index2);
                        }
                        

                        if (grid[((index+1) + grid.length) % grid.length][((index2+1) + grid[0].length) % grid[0].length] == ALIVE){
                            uf.union(index, index2, ((index+1) + grid.length) % grid.length, ((index2+1) + grid[0].length) % grid[0].length);
                        }

                        if (grid[((index+1) + grid.length) % grid.length][((index2 - 1) + grid[0].length) % grid[0].length] == ALIVE){
                            uf.union(index, index2, ((index+1) + grid.length) % grid.length, ((index2 - 1) + grid[0].length) % grid[0].length);
                        }

                        if (grid[index][((index2 - 1) + grid[0].length) % grid[0].length] == ALIVE){
                            uf.union(index, index2, index, ((index2 - 1) + grid[0].length) % grid[0].length);
                        }

                        if (grid[index][((index2 + 1) + grid[0].length) % grid[0].length] == ALIVE){
                            uf.union(index, index2, index, ((index2 + 1) + grid[0].length) % grid[0].length);
                        }

                        if (grid[((index-1) + grid.length) % grid.length][index2] == ALIVE){
                            uf.union(index, index2, ((index-1) + grid.length) % grid.length, index2);}
                        

                        if (grid[((index-1) + grid.length) % grid.length][((index2+1) + grid[0].length) % grid[0].length] == ALIVE){
                            uf.union(index, index2, ((index-1) + grid.length) % grid.length, ((index2+1) + grid[0].length) % grid[0].length);
                        }

                        if (grid[((index-1) + grid.length) % grid.length][((index2 - 1) + grid[0].length) % grid[0].length] == ALIVE){
                            uf.union(index, index2, ((index-1) + grid.length) % grid.length, ((index2 - 1) + grid[0].length) % grid[0].length);
                        }  
                        
                        
                    }          
                }
            } 
        }


        
        for (int index = 0; index < grid.length; index++) {
            for (int index2 = 0; index2 < grid[0].length; index2++) { 
                if (getCellState(index,index2)){
                    if (list.contains(uf.find(index, index2))){
                        continue;
                    }
                    else{
                        list.add(uf.find(index, index2));
                    }
                    communities +=1;
                } 
            }
        }

        return communities;
    }                 
}

    