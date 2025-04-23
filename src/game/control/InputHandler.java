package game.control;

import java.util.Scanner;

import game.Board;
import game.Game;
import game.GameLoop;
import game.control.EntityType;
import game.entities.PeaShooter;

public class InputHandler implements Runnable {

    private final Game game;
    private final GameLoop loop;
    private final Scanner scanner;

    public InputHandler(Game game, GameLoop loop) {
        this.game = game;
        this.loop = loop;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void run() {
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("exit")) {
                System.out.println("👋 Cerrando juego...");
                loop.stopLoop();
                game.stopSpawner();
                game.detenerZombis();
                game.detenerPeaShooters();
                break;
            } else if (input.equals("info")) {
                game.getBoard().printBoard();
            } else if (input.startsWith("plant")) {
                try {
                    int row = Integer.parseInt(input.split(" ")[1]);
                    if (row >= 0 && row < game.getBoard().getRows()) {
                        game.getBoard().placeEntity(row, 0, EntityType.PLANT);
                        PeaShooter shooter = new PeaShooter(game.getBoard(), row);
                        shooter.start();
                        game.registrarPeaShooter(shooter);
                        System.out.println("🌱 Planta colocada en fila " + row);
                    } else {
                        System.out.println("❌ Fila fuera de rango.");
                    }
                } catch (Exception e) {
                    System.out.println("⚠️ Uso: plant <fila>");
                }
            } else {
                System.out.println("❓ Comando no reconocido. Usa: plant <fila>, info, exit");
            }
        }

        scanner.close();
    }
}
