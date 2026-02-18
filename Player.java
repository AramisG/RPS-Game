import java.util.Scanner;

public class Player extends Combatant {
    private int coins;
    private boolean hasTiebreaker;
    private boolean predictionNextRound;

    public Player(String name) {
        super(name, 5);
        coins = 0;
        hasTiebreaker = false;
        predictionNextRound = false;
    }

    @Override
    public Moves selectMove(Scanner scanner) {
        System.out.println("\nChoose your move:");
        System.out.println("1) Rock");
        System.out.println("2) Paper");
        System.out.println("3) Scissors");
        System.out.println("4) Lizard");
        System.out.println("5) Spock");

        int choice = readIntInRange(scanner, 1, 5);

        switch (choice) {
            case 1: return Moves.ROCK;
            case 2: return Moves.PAPER;
            case 3: return Moves.SCISSORS;
            case 4: return Moves.LIZARD;
            default: return Moves.SPOCK;
        }
    }

    // -------- Coins --------
    public int getCoins() { return coins; }

    public void addCoins(int amount) {
        coins += amount;
        System.out.println("+" + amount + " coins (Total: " + coins + ")");
    }

    public boolean spendCoins(int amount) {
        if (coins < amount) return false;
        coins -= amount;
        return true;
    }

    // -------- Perks --------
    public boolean hasTiebreaker() { return hasTiebreaker; }
    public void giveTiebreaker() { hasTiebreaker = true; }
    public void useTiebreaker() { hasTiebreaker = false; }

    public boolean hasPredictionForNextRound() { return predictionNextRound; }
    public void activatePredictionForNextRound() { predictionNextRound = true; }
    public void clearPredictionForNextRound() { predictionNextRound = false; }

    public void showStatus() {
        System.out.println("\n--- " + getName() + " ---");
        System.out.println("Hearts: " + getHearts() + "/" + getMaxHearts());
        System.out.println("Coins: " + coins);
        System.out.println("Tiebreaker: " + (hasTiebreaker ? "YES" : "NO"));
        System.out.println("Prediction next round: " + (predictionNextRound ? "YES" : "NO"));
    }

    // -------- Input Helper (prevents crashes) --------
    private int readIntInRange(Scanner scanner, int min, int max) {
        while (true) {
            System.out.print("> ");

            if (!scanner.hasNextInt()) {
                System.out.println("Please enter a number.");
                scanner.nextLine(); // throw away bad input
                continue;
            }

            int value = scanner.nextInt();
            scanner.nextLine(); // consume newline

            if (value < min || value > max) {
                System.out.println("Enter a number from " + min + " to " + max + ".");
                continue;
            }

            return value;
        }
    }
}
