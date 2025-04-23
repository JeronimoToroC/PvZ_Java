package game;

import game.control.EntityType;
import game.control.InputHandler;
import game.control.Cell;
import game.entities.PeaShooter;
import game.entities.Zombie;
import game.entities.ZombieSpawner;

import java.util.List;
import java.util.ArrayList;

public class Game {

    private Board board;
    private ZombieSpawner spawner;

    private final int filas = 5;
    private final int columnas = 10;

    private final List<Zombie> zombiesActivos = new ArrayList<>();
    private final List<PeaShooter> shootersActivos = new ArrayList<>();

    public void start() {
        System.out.println("🌱 Iniciando el juego...");

        board = new Board(filas, columnas);

        // Plantas iniciales y shooters en columna 0
        for (int i = 0; i < filas; i++) {
            board.placeEntity(i, 0, EntityType.PLANT);
            PeaShooter shooter = new PeaShooter(board, i);
            shooter.start();
            registrarPeaShooter(shooter);
        }

        // Iniciar GameLoop
        GameLoop loop = new GameLoop(board);
        Thread loopThread = new Thread(loop);
        loopThread.start();

        // Iniciar ZombieSpawner
        spawner = new ZombieSpawner(this, board, 3000); // cada 3 segundos
        spawner.start();

        // Iniciar InputHandler
        InputHandler handler = new InputHandler(this, loop);
        Thread inputThread = new Thread(handler);
        inputThread.start();
    }

    public Board getBoard() {
        return board;
    }

    public void stopSpawner() {
        if (spawner != null) {
            spawner.terminate();
        }
    }

    public synchronized void registrarZombie(Zombie zombie) {
        zombiesActivos.add(zombie);
    }

    public synchronized void registrarPeaShooter(PeaShooter shooter) {
        shootersActivos.add(shooter);
    }

    public void detenerZombis() {
        for (Zombie z : zombiesActivos) {
            z.terminate();
        }
    }

    public void detenerPeaShooters() {
        for (PeaShooter p : shootersActivos) {
            p.terminate();
        }
    }
}
