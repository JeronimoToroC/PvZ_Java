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
            clearConsole(); // opcional

            System.out.println("🕒 Turno " + turn++);
            board.printBoard();

            try {
                Thread.sleep(1000); // Espera 1 segundo
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("⛔ Game loop interrupted.");
            }
        }
    }

    // Opcional: Limpia la consola para "animación"
    private void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
