import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Trainer extends Combatant {
    private List<Strat> strategies;
    private Random rand;
    
    // Special mechanics
    private boolean alwaysHasPrediction;
    private boolean berserkerMode;
    private int berserkerThreshold;
    private boolean hasTiebreaker;
    private List<Integer> predictionRounds; // for boss - specific rounds to predict

    // Constructor for basic trainers
    public Trainer(String name, List<Strat> strategies) {
        this(name, 5, strategies, false, false, 0, false, null);
    }
    
    // Constructor with all options
    public Trainer(String name, int hearts, List<Strat> strategies, 
                   boolean alwaysHasPrediction, boolean berserkerMode, 
                   int berserkerThreshold, boolean hasTiebreaker, 
                   List<Integer> predictionRounds) {
        super(name, hearts);
        this.strategies = strategies;
        this.rand = new Random();
        this.alwaysHasPrediction = alwaysHasPrediction;
        this.berserkerMode = berserkerMode;
        this.berserkerThreshold = berserkerThreshold;
        this.hasTiebreaker = hasTiebreaker;
        this.predictionRounds = predictionRounds;
    }

    @Override
    public Moves selectMove(Scanner scanner) {
        if (strategies == null || strategies.isEmpty()) {
            return Moves.ROCK;
        }

        Strat chosen = strategies.get(rand.nextInt(strategies.size()));

        switch (chosen) {
            case ALWAYS_ROCK: return Moves.ROCK;
            case ALWAYS_PAPER: return Moves.PAPER;
            case ALWAYS_SCISSORS: return Moves.SCISSORS;

            case RANDOM:
                Moves[] all = Moves.values();
                return all[rand.nextInt(all.length)];

            case COUNTER_ROCK:
                return (rand.nextInt(100) < 70) ? Moves.PAPER : randomMove();

            case COUNTER_PAPER:
                return (rand.nextInt(100) < 70) ? Moves.SCISSORS : randomMove();

            case COUNTER_SCISSORS:
                return (rand.nextInt(100) < 70) ? Moves.ROCK : randomMove();

            default:
                return Moves.ROCK;
        }
    }

    private Moves randomMove() {
        Moves[] all = Moves.values();
        return all[rand.nextInt(all.length)];
    }
    
    // Check if trainer should predict this round
    public boolean shouldPredictThisRound(int roundNumber) {
        // Always predict if that's their mechanic
        if (alwaysHasPrediction) {
            return true;
        }
        
        // Berserker mode - predict when low on health
        if (berserkerMode && getHearts() <= berserkerThreshold) {
            return true;
        }
        
        // Boss - predict on specific rounds
        if (predictionRounds != null && predictionRounds.contains(roundNumber)) {
            return true;
        }
        
        return false;
    }
    
    // Trainer predicts player's move
    public Moves predictPlayerMove() {
        // Simple AI: just pick a random move to predict
        // Could be made smarter by tracking player history
        Moves[] all = Moves.values();
        return all[rand.nextInt(all.length)];
    }
    
    public boolean hasTiebreaker() {
        return hasTiebreaker;
    }
    
    public void useTiebreaker() {
        hasTiebreaker = false;
    }
}