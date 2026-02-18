import java.util.*;

/**
 * Abstract base class for all fighters in the game.
 * Demonstrates INHERITANCE and ABSTRACTION.
 */
public abstract class Combatant {
    private String name;
    private int hearts;
    private int maxHearts;
    
    // Constructor
    public Combatant(String name, int maxHearts) {
        this.name = name;
        this.maxHearts = maxHearts;
        this.hearts = maxHearts;
    }
    
    // ENCAPSULATION: Getters and setters for controlled access
    public String getName() {
        return name;
    }
    
    public int getHearts() {
        return hearts;
    }
    
    public int getMaxHearts() {
        return maxHearts;
    }
    
    public void takeDamage(int damage) {
        hearts = Math.max(0, hearts - damage);
    }
    
    public void heal(int amount) {
        hearts = Math.min(maxHearts, hearts + amount);
    }
    
    public void increaseMaxHearts(int amount) {
        maxHearts += amount;
        hearts += amount; // Also heal when max increases
    }
    
    public void resetHearts() {
        hearts = maxHearts;
    }
    
    public boolean isAlive() {
        return hearts > 0;
    }
    
    // ABSTRACTION: Force subclasses to implement their own move selection
    // POLYMORPHISM: Each subclass will have different behavior
    public abstract Moves selectMove(Scanner scanner);
    
    // Optional: toString for easy printing
    @Override
    public String toString() {
        return name + " (" + hearts + "/" + maxHearts + " ❤️)";
    }
}