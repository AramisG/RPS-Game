import java.util.*;

/**
 * Battle class - handles combat between Combatants.
 * Rock-Paper-Scissors-Lizard-Spock rules:
 * - Rock beats Scissors & Lizard
 * - Paper beats Rock & Spock
 * - Scissors beats Paper & Lizard
 * - Lizard beats Paper & Spock
 * - Spock beats Rock & Scissors
 */
public class Battle {

    /**
     * Compare two moves and determine the winner
     * @return 1 if player wins, -1 if opponent wins, 0 if tie
     */
    public static int compare(Moves player, Moves opponent) {
        if (player == opponent) return 0;

        switch (player) {
            case ROCK:
                return (opponent == Moves.SCISSORS || opponent == Moves.LIZARD) ? 1 : -1;
            case PAPER:
                return (opponent == Moves.ROCK || opponent == Moves.SPOCK) ? 1 : -1;
            case SCISSORS:
                return (opponent == Moves.PAPER || opponent == Moves.LIZARD) ? 1 : -1;
            case LIZARD:
                return (opponent == Moves.PAPER || opponent == Moves.SPOCK) ? 1 : -1;
            case SPOCK:
                return (opponent == Moves.ROCK || opponent == Moves.SCISSORS) ? 1 : -1;
            default:
                return 0;
        }
    }

    /**
     * Main battle method - fight until someone reaches 0 hearts
     */
    public static boolean battle(Player player, Combatant opponent, Scanner scanner, boolean isTutorial) {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║         BATTLE START!                  ║");
        System.out.println("║  " + String.format("%-37s", player.getName() + " VS " + opponent.getName()) + "║");
        System.out.println("╚════════════════════════════════════════╝");

        player.resetHearts();
        opponent.resetHearts();

        int roundNumber = 0;
        Moves predictedMove = null;
        boolean predictionActive = false;

        while (player.isAlive() && opponent.isAlive()) {
            roundNumber++;
            System.out.println("\n" + "=".repeat(40));
            System.out.println("ROUND " + roundNumber);
            System.out.println("=".repeat(40));
            System.out.println(player.getName() + ": " + getHeartDisplay(player.getHearts()));
            System.out.println(opponent.getName() + ": " + getHeartDisplay(opponent.getHearts()));
            System.out.println("💰 Coins: " + player.getCoins());

            // Check if player bought prediction for THIS round
            predictedMove = null;
            predictionActive = player.hasPredictionForNextRound();
            
            if (predictionActive) {
                System.out.println("\n🔮 PREDICTION is active for this round!");
                System.out.println("What will they throw?");
                System.out.println("1. Rock");
                System.out.println("2. Paper");
                System.out.println("3. Scissors");
                System.out.println("4. Lizard");
                System.out.println("5. Spock");
                System.out.print("> ");
                
                int predChoice = scanner.nextInt();
                scanner.nextLine();
                
                switch (predChoice) {
                    case 1: predictedMove = Moves.ROCK; break;
                    case 2: predictedMove = Moves.PAPER; break;
                    case 3: predictedMove = Moves.SCISSORS; break;
                    case 4: predictedMove = Moves.LIZARD; break;
                    case 5: predictedMove = Moves.SPOCK; break;
                    default: predictedMove = Moves.ROCK;
                }
                System.out.println("🔮 Prediction locked in: " + predictedMove);
                
                // Clear prediction after use
                player.clearPredictionForNextRound();
            }

            // Get moves
            Moves playerMove = player.selectMove(scanner);
            Moves opponentMove = opponent.selectMove(scanner);

            System.out.println("\n" + player.getName() + " chose: " + playerMove);
            System.out.println(opponent.getName() + " chose: " + opponentMove);

            // Check prediction
            boolean predictionCorrect = false;
            if (predictionActive && predictedMove == opponentMove) {
                System.out.println("\n✨ PREDICTION CORRECT! ✨");
                predictionCorrect = true;
            } else if (predictionActive) {
                System.out.println("\n❌ Prediction was wrong...");
            }

            // Determine winner
            int result = compare(playerMove, opponentMove);

            if (result == 1) {
                // Player wins
                int damage = predictionCorrect ? 2 : 1;
                opponent.takeDamage(damage);
                System.out.println("\n✓ You WIN the round! " + opponent.getName() + " takes " + damage + " damage!");
                
                // Award coins (not in tutorial until final blow)
                if (!isTutorial || !opponent.isAlive()) {
                    player.addCoins(2);
                }
                
                // NO SHOP when you win!
                
            } else if (result == -1) {
                // Opponent wins - PLAYER LOSES
                player.takeDamage(1);
                System.out.println("\n✗ You LOSE the round! You take 1 damage!");
                
                // Award coins (not in tutorial until final blow)
                if (!isTutorial || !opponent.isAlive()) {
                    player.addCoins(1);
                }
                
                // SHOP APPEARS when you lose! (but not if battle is over)
                if (player.isAlive() && (!isTutorial || !opponent.isAlive())) {
                    inBattleShop(player, scanner);
                }
                
            } else {
                // Tie
                if (player.hasTiebreaker()) {
                    System.out.println("\n⚡ TIE - But your TIEBREAKER activates!");
                    opponent.takeDamage(1);
                    System.out.println(opponent.getName() + " takes 1 damage!");
                    player.useTiebreaker();
                    System.out.println("(Tiebreaker consumed)");
                    
                    if (!isTutorial || !opponent.isAlive()) {
                        player.addCoins(2);
                    }
                } else {
                    System.out.println("\n➖ TIE! No damage dealt.");
                }
                // NO SHOP on ties
            }

            if (player.isAlive() && opponent.isAlive()) {
                System.out.print("\nPress ENTER to continue...");
                scanner.nextLine();
            }
        }

        System.out.println("\n╔════════════════════════════════════════╗");
        if (player.isAlive()) {
            System.out.println("║         🏆 VICTORY! 🏆                 ║");
            System.out.println("╚════════════════════════════════════════╝");
            return true;
        } else {
            System.out.println("║         💀 DEFEAT 💀                   ║");
            System.out.println("╚════════════════════════════════════════╝");
            return false;
        }
    }

    /**
     * In-battle shop that appears when player loses a round
     */
    private static void inBattleShop(Player player, Scanner scanner) {
        System.out.println("\n┌────────────────────────────────────────┐");
        System.out.println("│      💰 COMEBACK SHOP! 💰              │");
        System.out.println("└────────────────────────────────────────┘");
        System.out.println("💰 Your coins: " + player.getCoins());
        System.out.println("\nQuick! Buy a perk to turn things around:");
        
        // Create all possible perks
        List<ShopPerk> allPerks = Arrays.asList(
            ShopPerk.createHeartBoost(),
            ShopPerk.createTiebreaker(),
            ShopPerk.createPrediction()
        );
        
        // Shuffle and show 3 random perks
        List<ShopPerk> shuffled = new ArrayList<>(allPerks);
        Collections.shuffle(shuffled);
        List<ShopPerk> offered = shuffled.subList(0, Math.min(3, shuffled.size()));
        
        for (int i = 0; i < offered.size(); i++) {
            ShopPerk perk = offered.get(i);
            System.out.println("\n" + (i + 1) + ". " + perk.getName() + " - " + perk.getPrice() + " coins");
            System.out.println("   " + perk.getDescription());
        }
        
        System.out.println("\n4. Skip (save your coins)");
        
        System.out.print("\nYour choice (1-4): ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        if (choice >= 1 && choice <= 3) {
            ShopPerk chosen = offered.get(choice - 1);
            
            if (player.getCoins() >= chosen.getPrice()) {
                player.spendCoins(chosen.getPrice());
                
                // Apply the perk
                switch (chosen.getType()) {
                    case HEART_BOOST:
                        player.increaseMaxHearts(1);
                        System.out.println("\n✨ " + chosen.getName() + " purchased!");
                        System.out.println("   Max hearts: " + player.getMaxHearts() + " | Current: " + player.getHearts());
                        break;
                    case TIEBREAKER:
                        player.addTiebreaker();
                        System.out.println("\n✨ " + chosen.getName() + " purchased!");
                        System.out.println("   You'll win the next tie!");
                        break;
                    case PREDICTION:
                        player.activatePredictionForNextRound();
                        System.out.println("\n✨ " + chosen.getName() + " purchased!");
                        System.out.println("   Active for the NEXT ROUND!");
                        break;
                }
            } else {
                System.out.println("\n❌ Not enough coins! (Need " + chosen.getPrice() + ", have " + player.getCoins() + ")");
            }
        } else {
            System.out.println("\n💰 Coins saved!");
        }
    }

    private static String getHeartDisplay(int hearts) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hearts; i++) {
            sb.append("❤️ ");
        }
        return sb.toString();
    }
}