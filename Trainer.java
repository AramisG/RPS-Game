import java.util.*;

/**
 * Trainer class - represents AI opponents.
 * Demonstrates INHERITANCE (extends Combatant).
 */
public class Trainer extends Combatant {
    private List<Strat> strategyPool;
    private Random rand;

    public Trainer(String name, List<Strat> strategyPool) {
        super(name, 5);  // Start with 5 hearts
        this.strategyPool = new ArrayList<>(strategyPool);
        this.rand = new Random();
    }

    // POLYMORPHISM: Implementing abstract method from parent
    @Override
    public Moves selectMove(Scanner scanner) {
        if (strategyPool.isEmpty()) {
            return Moves.ROCK; // fallback
        }

        Strat chosen = strategyPool.get(rand.nextInt(strategyPool.size()));

        switch (chosen) {
            case ALWAYS_ROCK:
                return Moves.ROCK;
            case ALWAYS_PAPER:
                return Moves.PAPER;
            case ALWAYS_SCISSORS:
                return Moves.SCISSORS;
            case RANDOM:
                Moves[] allMoves = Moves.values();
                return allMoves[rand.nextInt(allMoves.length)];
            case COUNTER_ROCK:
                // 70% paper, 30% random
                if (rand.nextInt(100) < 70) {
                    return Moves.PAPER;
                } else {
                    Moves[] moves = Moves.values();
                    return moves[rand.nextInt(moves.length)];
                }
            case COUNTER_PAPER:
                // 70% scissors, 30% random
                if (rand.nextInt(100) < 70) {
                    return Moves.SCISSORS;
                } else {
                    Moves[] moves = Moves.values();
                    return moves[rand.nextInt(moves.length)];
                }
            case COUNTER_SCISSORS:
                // 70% rock, 30% random
                if (rand.nextInt(100) < 70) {
                    return Moves.ROCK;
                } else {
                    Moves[] moves = Moves.values();
                    return moves[rand.nextInt(moves.length)];
                }
            default:
                return Moves.ROCK;
        }
    }
}