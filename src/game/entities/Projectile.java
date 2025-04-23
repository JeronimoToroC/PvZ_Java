package game.entities;

import game.Board;
import game.control.Cell;
import game.control.ConsoleLogger;
import game.control.EntityType;

public class Projectile extends Thread {

    private final Board board;
    private final int row;
    private int col;

    private volatile boolean running = true;

    public Projectile(Board board, int row, int startCol) {
        this.board = board;
        this.row = row;
        this.col = startCol;
    }

    @Override
    public void run() {
        while (running && col < board.getCols() - 1) {
            int nextCol = col + 1;
            Cell current = board.getCell(row, col);
            Cell next = board.getCell(row, nextCol);

            // Lock ordenado y consistente
            if (row % 2 == 0) {
                current.lock();
                next.lock();
            } else {
                next.lock();
                current.lock();
            }

            try {
                EntityType target = next.getEntity();

                switch (target) {
                    case ZOMBIE:
                        // Impacto directo
                        next.setEntity(EntityType.EMPTY);
                        current.setEntity(EntityType.EMPTY);
                        ConsoleLogger.getInstance().log("💥 Proyectil impactó zombi en fila " + row + ", columna " + nextCol);
                        running = false;
                        break;

                    case EMPTY:
                        // Avanzar
                        current.setEntity(EntityType.EMPTY);
                        next.setEntity(EntityType.PROJECTILE);
                        col = nextCol;
                        break;

                    default:
                        // No avanzar si hay otro proyectil, planta u obstáculo
                        current.setEntity(EntityType.EMPTY);
                        running = false;
                        break;
                }

            } finally {
                current.unlock();
                next.unlock();
            }

            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        // Si llega al final, lo borra
        if (col == board.getCols() - 1) {
            Cell last = board.getCell(row, col);
            last.lock();
            try {
                if (last.getEntity() == EntityType.PROJECTILE) {
                    last.setEntity(EntityType.EMPTY);
                }
            } finally {
                last.unlock();
            }
        }
    }
}
