package game;

import game.control.ConsoleLogger;

public class GameLoop implements Runnable {

    private final Board board;
    private volatile boolean running = true;

    public GameLoop(Board board) {
        this.board = board;
    }

    public void stopLoop() {
        running = false;
    }

    @Override
    public void run() {
        int turn = 0;
        while (running) {
            // Limpieza desactivada para no borrar mensajes del logger
            // clearConsole();

            System.out.println("🕒 Turno " + turn++);
            board.printBoard();
            ConsoleLogger.getInstance().printLog(); // Mostrar mensajes del juego

            try {
                Thread.sleep(1000); // Espera 1 segundo
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                ConsoleLogger.getInstance().log("⛔ Game loop interrupted.");
            }
        }
    }

    // Esta función ya no se usa, pero la dejamos por si la necesitas luego
    private void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
