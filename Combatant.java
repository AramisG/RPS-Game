import java.util.Scanner;

public abstract class Combatant {
    private String name;
    private int hearts;
    private int maxHearts;

    public Combatant(String name, int maxHearts) {
        this.name = name;
        this.maxHearts = maxHearts;
        this.hearts = maxHearts;
    }

    public String getName() { return name; }
    public int getHearts() { return hearts; }
    public int getMaxHearts() { return maxHearts; }

    public void takeDamage(int damage) {
        hearts -= damage;
        if (hearts < 0) hearts = 0;
    }

    public void increaseMaxHearts(int amount) {
        maxHearts += amount;
        hearts += amount;
    }

    public void resetHearts() {
        hearts = maxHearts;
    }

    public boolean isAlive() {
        return hearts > 0;
    }

    public abstract Moves selectMove(Scanner scanner);
}