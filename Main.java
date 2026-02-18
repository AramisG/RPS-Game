import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("ROCK PAPER SCISSORS GAUNTLET");
        System.out.println("Lizard Spock Edition");
        System.out.println("Defeat 5 Trainers to Win!\n");
        
        System.out.println("Game Rules:");
        System.out.println("- 5 hearts each, first to 0 loses");
        System.out.println("- Win round = opponent loses 1 heart, you get 2 coins");
        System.out.println("- Lose round = you lose 1 heart, you get 1 coin");
        System.out.println("- Lose a round = Shop appears (comeback mechanic)");
        System.out.println("- 5 moves: Rock, Paper, Scissors, Lizard, Spock\n");
        
        System.out.print("What is your name?\n> ");
        String name = scanner.nextLine().trim();

        Player player = new Player(name);

        System.out.println("\nWelcome, " + player.getName() + "!");
        System.out.println("Face increasingly difficult trainers with special abilities!");
        
        System.out.print("\nPress ENTER to begin...");
        scanner.nextLine();

        // Level 0: Tutorial - Rocky (always rock, basic)
        Trainer level0 = new Trainer("Rocky the Beginner", Arrays.asList(Strat.ALWAYS_ROCK));

        System.out.println("\n--- LEVEL 0: TUTORIAL ---");
        System.out.println("Rocky always throws ROCK!");
        System.out.println("(Coins only awarded on the final blow)");
        System.out.print("\nPress ENTER to start...");
        scanner.nextLine();

        if (!Battle.battle(player, level0, scanner, true)) {
            gameOver(scanner);
            return;
        }
        
        System.out.println("\nTutorial Complete!");
        System.out.print("Press ENTER to continue...");
        scanner.nextLine();

        // Level 1: Randy (random, no special abilities)
        Trainer level1 = new Trainer("Randy the Unpredictable", Arrays.asList(Strat.RANDOM));

        if (!battleLevel(player, level1, scanner, 1, "Randy throws random moves.")) {
            gameOver(scanner);
            return;
        }

        // Level 2: The Psychic (always has prediction)
        Trainer level2 = new Trainer(
            "The Psychic",
            5,  // normal hearts
            Arrays.asList(Strat.RANDOM),
            true,  // alwaysHasPrediction = true
            false, // berserkerMode
            0,     // berserkerThreshold
            false, // hasTiebreaker
            null   // predictionRounds
        );

        if (!battleLevel(player, level2, scanner, 2, "The Psychic can predict your moves every round!")) {
            gameOver(scanner);
            return;
        }

        // Level 3: The Tank (7 hearts, no other special abilities)
        Trainer level3 = new Trainer(
            "The Tank",
            7,  // extra hearts!
            Arrays.asList(Strat.RANDOM),
            false, // alwaysHasPrediction
            false, // berserkerMode
            0,     // berserkerThreshold
            false, // hasTiebreaker
            null   // predictionRounds
        );

        if (!battleLevel(player, level3, scanner, 3, "The Tank has 7 hearts - this will be a long fight!")) {
            gameOver(scanner);
            return;
        }

        // Level 4: The Berserker (prediction when low on health)
        Trainer level4 = new Trainer(
            "The Berserker",
            5,  // normal hearts
            Arrays.asList(Strat.RANDOM, Strat.COUNTER_ROCK, Strat.COUNTER_PAPER),
            false, // alwaysHasPrediction
            true,  // berserkerMode = true
            2,     // berserkerThreshold = activates at 2 hearts
            false, // hasTiebreaker
            null   // predictionRounds
        );

        if (!battleLevel(player, level4, scanner, 4, "The Berserker gets prediction when low on health!")) {
            gameOver(scanner);
            return;
        }

        // Level 5: The Grand Champion (final boss - 8 hearts, prediction on rounds 3/6/9, has tiebreaker)
        List<Integer> bossRounds = Arrays.asList(3, 6, 9);
        Trainer level5 = new Trainer(
            "The Grand Champion",
            8,  // lots of hearts!
            Arrays.asList(Strat.RANDOM),
            false, // alwaysHasPrediction
            false, // berserkerMode
            0,     // berserkerThreshold
            true,  // hasTiebreaker = true
            bossRounds  // predicts on rounds 3, 6, 9
        );

        if (!battleLevel(player, level5, scanner, 5, "THE FINAL BOSS! 8 hearts, tiebreaker, and predicts on rounds 3, 6, and 9!")) {
            gameOver(scanner);
            return;
        }

        // Victory
        victory(player);
        scanner.close();
    }

    private static boolean battleLevel(Player player, Trainer trainer, Scanner scanner, int level, String hint) {
        System.out.println("\n--- LEVEL " + level + " ---");
        System.out.println(hint);
        player.showStatus();
        System.out.print("\nPress ENTER to start battle...");
        scanner.nextLine();
        
        boolean won = Battle.battle(player, trainer, scanner, false);
        
        if (won) {
            System.out.println("\nLevel " + level + " Complete!");
            System.out.print("Press ENTER to continue...");
            scanner.nextLine();
        }
        
        return won;
    }

    private static void gameOver(Scanner scanner) {
        System.out.println("\n--- GAME OVER ---");
        System.out.println("Better luck next time!");
        scanner.close();
    }

    private static void victory(Player player) {
        System.out.println("\n--- VICTORY ---");
        System.out.println("Congratulations, " + player.getName() + "!");
        System.out.println("You conquered the gauntlet!");
        System.out.println("All 5 trainers defeated!");
        System.out.println("\nFinal Stats:");
        System.out.println("Coins: " + player.getCoins());
        System.out.println("Max Hearts: " + player.getMaxHearts());
    }
}