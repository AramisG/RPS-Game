import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║  ROCK PAPER SCISSORS GAUNTLET         ║");
        System.out.println("║  Lizard Spock Edition!                ║");
        System.out.println("║  🎮 Defeat 5 Trainers to Win! 🎮      ║");
        System.out.println("╚════════════════════════════════════════╝\n");
        
        System.out.println("Game Rules:");
        System.out.println("• 5 hearts each - first to 0 loses");
        System.out.println("• Win round = opponent loses 1 heart, you get 2 coins");
        System.out.println("• Lose round = you lose 1 heart, you get 1 coin");
        System.out.println("• LOSE A ROUND = Shop appears! (comeback mechanic)");
        System.out.println("• 5 moves: Rock, Paper, Scissors, Lizard, Spock\n");
        
        System.out.print("What is your name?\n> ");
        String name = scanner.nextLine().trim();

        Player player = new Player(name);

        System.out.println("\nWelcome, " + player.getName() + "!");
        System.out.println("When you lose a round, you can spend coins on perks!");
        
        System.out.print("\nPress ENTER to begin...");
        scanner.nextLine();

        // Level 0: Tutorial - Always Rock (no coins until final blow)
        Trainer level0 = new Trainer(
                "Rocky the Beginner",
                Arrays.asList(Strat.ALWAYS_ROCK)
        );

        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║         LEVEL 0: TUTORIAL              ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println("Rocky always throws ROCK!");
        System.out.println("(Coins only awarded on the final blow)");
        System.out.print("\nPress ENTER to start...");
        scanner.nextLine();

        if (!Battle.battle(player, level0, scanner, true)) {
            gameOver(scanner);
            return;
        }
        
        System.out.println("\n✓ Tutorial Complete!");
        System.out.print("Press ENTER to continue...");
        scanner.nextLine();

        // Level 1: Random moves
        Trainer level1 = new Trainer(
                "Randy the Unpredictable",
                Arrays.asList(Strat.RANDOM)
        );

        if (!battleLevel(player, level1, scanner, 1, "Randy throws anything!")) {
            gameOver(scanner);
            return;
        }

        // Level 2: Mix of strategies
        Trainer level2 = new Trainer(
                "Roxanne the Strategist",
                Arrays.asList(Strat.ALWAYS_ROCK, Strat.RANDOM, Strat.COUNTER_ROCK)
        );

        if (!battleLevel(player, level2, scanner, 2, "Roxanne loves Rock moves!")) {
            gameOver(scanner);
            return;
        }

        // Level 3: More complex
        Trainer level3 = new Trainer(
                "Chaos Master Chen",
                Arrays.asList(Strat.RANDOM, Strat.RANDOM, Strat.COUNTER_PAPER)
        );

        if (!battleLevel(player, level3, scanner, 3, "Chen is unpredictable!")) {
            gameOver(scanner);
            return;
        }

        // Level 4: Final Boss
        Trainer level4 = new Trainer(
                "The Grand Champion",
                Arrays.asList(Strat.RANDOM)
        );

        if (!battleLevel(player, level4, scanner, 4, "The final challenge!")) {
            gameOver(scanner);
            return;
        }

        // Victory!
        victory(player);
        scanner.close();
    }

    private static boolean battleLevel(Player player, Trainer trainer, Scanner scanner, int level, String hint) {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║         LEVEL " + level + "                         ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println(hint);
        player.showStatus();
        System.out.print("\nPress ENTER to start battle...");
        scanner.nextLine();
        
        boolean won = Battle.battle(player, trainer, scanner, false);
        
        if (won) {
            System.out.println("\n✓ Level " + level + " Complete!");
            System.out.print("Press ENTER to continue...");
            scanner.nextLine();
        }
        
        return won;
    }

    private static void gameOver(Scanner scanner) {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║           💀 GAME OVER 💀              ║");
        System.out.println("║  Better luck next time, challenger!   ║");
        System.out.println("╚════════════════════════════════════════╝");
        scanner.close();
    }

    private static void victory(Player player) {
        System.out.println("\n\n");
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║                                        ║");
        System.out.println("║        🏆 VICTORY! 🏆                  ║");
        System.out.println("║                                        ║");
        System.out.println("║  Congratulations, " + String.format("%-20s", player.getName()) + " ║");
        System.out.println("║                                        ║");
        System.out.println("║  You conquered the gauntlet!          ║");
        System.out.println("║  All 5 trainers defeated!             ║");
        System.out.println("║                                        ║");
        System.out.println("║  Final Stats:                         ║");
        System.out.println("║  💰 Coins: " + String.format("%-27d", player.getCoins()) + " ║");
        System.out.println("║  ❤️  Max Hearts: " + String.format("%-21d", player.getMaxHearts()) + " ║");
        System.out.println("║                                        ║");
        System.out.println("╚════════════════════════════════════════╝");
    }
}