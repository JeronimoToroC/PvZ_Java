import control.Cell;
import control.EntityType;

/**
 * Represents the game board (matrix of cells).
 */
public class Board {

    private final int rows;
    private final int cols;
    private final Cell[][] grid;

    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        grid = new Cell[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = new Cell();
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    /**
     * Returns the cell at the specified position.
     */
    public Cell getCell(int row, int col) {
        return grid[row][col];
    }

    /**
     * Places an entity in the specified position (thread-safe).
     */
    public void placeEntity(int row, int col, EntityType type) {
        Cell cell = getCell(row, col);
        cell.lock();
        try {
            cell.setEntity(type);
        } finally {
            cell.unlock();
        }
    }

    /**
     * Clears the cell (sets it to EMPTY).
     */
    public void clearCell(int row, int col) {
        Cell cell = getCell(row, col);
        cell.lock();
        try {
            cell.setEntity(EntityType.EMPTY);
        } finally {
            cell.unlock();
        }
    }

    /**
     * Prints the current state of the board to the console.
     */
    public void printBoard() {
        System.out.println("\n=== Game Board ===");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Cell cell = grid[i][j];
                cell.lock();
                try {
                    System.out.print(cell.toString() + " ");
                } finally {
                    cell.unlock();
                }
            }
            System.out.println(); // newline after each row
        }
    }
}
