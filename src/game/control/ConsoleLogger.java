package game.control;

import java.util.LinkedList;

public class ConsoleLogger {

    private static final int MAX_MESSAGES = 10;
    private final LinkedList<String> messages = new LinkedList<>();

    // Singleton instance
    private static final ConsoleLogger instance = new ConsoleLogger();

    private ConsoleLogger() {}

    public static ConsoleLogger getInstance() {
        return instance;
    }

    public synchronized void log(String message) {
        if (messages.size() >= MAX_MESSAGES) {
            messages.removeFirst();
        }
        messages.add(message);
    }

    public synchronized void printLog() {
        System.out.println("\n=== Mensajes ===");
        for (String msg : messages) {
            System.out.println(msg);
        }
    }

    public synchronized void clear() {
        messages.clear();
    }
}
