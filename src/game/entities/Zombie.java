package game.entities;

import game.Board;
import game.control.Cell;
import game.control.ConsoleLogger;
import game.control.EntityType;

public class Zombie extends Thread {

    private final Board board;
    private final int row;
    private int col;

    private volatile boolean running = true;

    public Zombie(Board board, int row) {
        this.board = board;
        this.row = row;
        this.col = board.getCols() - 1;
    }

    public void terminate() {
        running = false;
    }

    @Override
    public void run() {
        while (running && col > 0) {
            int nextCol = col - 1;

            Cell current = board.getCell(row, col);
            Cell next = board.getCell(row, nextCol);

            // Bloqueo ordenado para evitar deadlock
            if (row % 2 == 0) {
                current.lock();
                next.lock();
            } else {
                next.lock();
                current.lock();
            }

            try {
                EntityType target = next.getEntity();

                if (target == EntityType.PLANT) {
                    ConsoleLogger.getInstance().log("🧟 Zombi bloqueado por planta en fila " + row);
                    running = false;
                    continue;
                }

                if (target == EntityType.ZOMBIE) {
                    // Otro zombi adelante, no avanza
                    continue;
                }

                if (target == EntityType.PROJECTILE) {
                    // Impacto con proyectil: ambos mueren
                    next.setEntity(EntityType.EMPTY);    // borrar proyectil
                    current.setEntity(EntityType.EMPTY); // borrar zombi
                    ConsoleLogger.getInstance().log("💥 Zombi eliminado por proyectil en fila " + row + ", columna " + nextCol);
                    running = false;
                    break;
                }

                // ✅ Verificación adicional para evitar sobrescribir si algo apareció en el medio
                if (next.getEntity() == EntityType.EMPTY) {
                    current.setEntity(EntityType.EMPTY);
                    next.setEntity(EntityType.ZOMBIE);
                    col = nextCol;
                } else {
                    // Algo llegó primero (otro proyectil, planta, etc)
                    // No moverse esta vez
                }

            } finally {
                current.unlock();
                next.unlock();
            }

            try {
                Thread.sleep(1000); // Espera 1 segundo
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        if (col == 0) {
            ConsoleLogger.getInstance().log("💀 ¡Un zombi llegó a la casa! FILA " + row);
        }
    }
}
