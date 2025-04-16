import control.EntityType;
import control.Cell;

public class Game {

    private Board board;

    private final int filas = 5;
    private final int columnas = 10;

    public void start() {
    System.out.println("🌱 Iniciando el juego...");

    board = new Board(filas, columnas);

    // Plantas en columna 0
    for (int i = 0; i < filas; i++) {
        board.placeEntity(i, 0, control.EntityType.PLANT);
    }

    // Zombis en columna final
    for (int i = 0; i < filas; i++) {
        board.placeEntity(i, columnas - 1, control.EntityType.ZOMBIE);
    }

    // Lanzar GameLoop en un hilo
    GameLoop loop = new GameLoop(board);
    Thread loopThread = new Thread(loop);
    loopThread.start();
}


    public Board getBoard() {
        return board;
    }
}
