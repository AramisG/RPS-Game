import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Trainer extends Combatant {
    private List<Strat> strategies;
    private Random rand;

    public Trainer(String name, List<Strat> strategies) {
        super(name, 5);
        this.strategies = strategies;
        this.rand = new Random();
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
}
