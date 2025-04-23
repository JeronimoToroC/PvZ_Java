package game.entities;

import game.Board;
import game.control.Cell;
import game.control.EntityType;

public class PeaShooter extends Thread {

    private final Board board;
    private final int row;
    private final int col = 0; // Planta siempre en columna 0
    private volatile boolean running = true;

    public PeaShooter(Board board, int row) {
        this.board = board;
        this.row = row;
    }

    public void terminate() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            boolean zombieDetected = false;

            for (int c = col + 1; c < board.getCols(); c++) {
                Cell cell = board.getCell(row, c);
                cell.lock();
                try {
                    if (cell.isZombie()) {
                        zombieDetected = true;
                        break;
                    }
                } finally {
                    cell.unlock();
                }
            }

            if (zombieDetected) {
                // Disparar proyectil desde la columna 1 (no 0)
                if (col + 1 < board.getCols()) {
                    Projectile p = new Projectile(board, row, col + 1);
                    p.start();
                }
            }

            try {
                Thread.sleep(2000); // Dispara cada 2 segundos
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
