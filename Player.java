import java.util.*;

/**
 * Player class - represents the human player.
 * Demonstrates INHERITANCE (extends Combatant).
 */
public class Player extends Combatant {
    private int coins;
    private boolean hasTiebreaker;
    private boolean predictionForNextRound;
    private Moves lastOpponentMove;
    
    public Player(String name) {
        super(name, 5);  // Start with 5 hearts
        this.coins = 0;
        this.hasTiebreaker = false;
        this.predictionForNextRound = false;
        this.lastOpponentMove = null;
    }
    
    // POLYMORPHISM: Implementing abstract method from parent
    @Override
    public Moves selectMove(Scanner scanner) {
        System.out.println("\nChoose your move:");
        System.out.println("1. Rock");
        System.out.println("2. Paper");
        System.out.println("3. Scissors");
        System.out.println("4. Lizard");
        System.out.println("5. Spock");
        System.out.print("> ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        Moves playerMove;
        switch (choice) {
            case 1:
                playerMove = Moves.ROCK;
                break;
            case 2:
                playerMove = Moves.PAPER;
                break;
            case 3:
                playerMove = Moves.SCISSORS;
                break;
            case 4:
                playerMove = Moves.LIZARD;
                break;
            case 5:
                playerMove = Moves.SPOCK;
                break;
            default:
                System.out.println("Invalid choice! Defaulting to Rock.");
                playerMove = Moves.ROCK;
        }
        
        return playerMove;
    }
    
    // Coin management
    public int getCoins() {
        return coins;
    }
    
    public void addCoins(int amount) {
        coins += amount;
        System.out.println("💰 +" + amount + " coins! (Total: " + coins + ")");
    }
    
    public boolean spendCoins(int amount) {
        if (coins >= amount) {
            coins -= amount;
            System.out.println("💰 -" + amount + " coins (Remaining: " + coins + ")");
            return true;
        }
        return false;
    }
    
    // Tiebreaker perk
    public boolean hasTiebreaker() {
        return hasTiebreaker;
    }
    
    public void addTiebreaker() {
        hasTiebreaker = true;
    }
    
    public void useTiebreaker() {
        hasTiebreaker = false;
    }
    
    // Prediction perk (for next round only)
    public boolean hasPredictionForNextRound() {
        return predictionForNextRound;
    }
    
    public void activatePredictionForNextRound() {
        predictionForNextRound = true;
    }
    
    public void clearPredictionForNextRound() {
        predictionForNextRound = false;
    }
    
    // Last opponent move tracking
    public void setLastOpponentMove(Moves move) {
        this.lastOpponentMove = move;
    }
    
    public Moves getLastOpponentMove() {
        return lastOpponentMove;
    }
    
    public void showStatus() {
        System.out.println("\n┌─────────────────────────────────┐");
        System.out.println("│ " + getName() + "'s Status");
        System.out.println("├─────────────────────────────────┤");
        System.out.println("│ ❤️  Hearts: " + getHearts() + "/" + getMaxHearts());
        System.out.println("│ 💰 Coins: " + coins);
        if (hasTiebreaker) {
            System.out.println("│ ⚡ Tiebreaker: ACTIVE");
        }
        if (predictionForNextRound) {
            System.out.println("│ 🔮 Prediction: NEXT ROUND");
        }
        System.out.println("└─────────────────────────────────┘");
    }
}