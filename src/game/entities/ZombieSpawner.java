package game.entities;

import java.util.Random;

import game.Board;
import game.Game;
import game.control.Cell;
import game.control.EntityType;
import game.control.ConsoleLogger;

public class ZombieSpawner extends Thread {

    private final Game game;
    private final Board board;
    private final int spawnIntervalMs;
    private volatile boolean running = true;
    private final Random random = new Random();

    public ZombieSpawner(Game game, Board board, int spawnIntervalMs) {
        this.game = game;
        this.board = board;
        this.spawnIntervalMs = spawnIntervalMs;
    }

    public void terminate() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            int row = random.nextInt(board.getRows());
            int col = board.getCols() - 1;
            Cell cell = board.getCell(row, col);

            cell.lock();
            try {
                if (cell.isEmpty()) {
                    cell.setEntity(EntityType.ZOMBIE);
                    Zombie zombie = new Zombie(board, row);
                    zombie.start();
                    game.registrarZombie(zombie); // registra el zombi activo
                    ConsoleLogger.getInstance().log("🧟 Zombi creado en fila " + row);
                }
            } finally {
                cell.unlock();
            }

            try {
                Thread.sleep(spawnIntervalMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                ConsoleLogger.getInstance().log("⚠️ ZombieSpawner interrumpido.");
                break;
            }
        }
    }
}
