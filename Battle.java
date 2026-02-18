import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Battle {

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

    public static boolean battle(Player player, Combatant opponent, Scanner scanner, boolean isTutorial) {
        System.out.println("\nBATTLE START");
        System.out.println(player.getName() + " VS " + opponent.getName());
        
        // Show trainer special abilities
        if (opponent instanceof Trainer) {
            Trainer t = (Trainer) opponent;
            if (opponent.getMaxHearts() > 5) {
                System.out.println("[" + opponent.getName() + " has " + opponent.getMaxHearts() + " hearts!]");
            }
            if (t.shouldPredictThisRound(1)) {
                System.out.println("[" + opponent.getName() + " can predict your moves!]");
            }
            if (t.hasTiebreaker()) {
                System.out.println("[" + opponent.getName() + " has a tiebreaker!]");
            }
        }

        player.resetHearts();
        opponent.resetHearts();

        int round = 1;

        while (player.isAlive() && opponent.isAlive()) {
            System.out.println("\n--- Round " + round + " ---");
            System.out.println(player.getName() + " hearts: " + player.getHearts());
            System.out.println(opponent.getName() + " hearts: " + opponent.getHearts());
            System.out.println("Coins: " + player.getCoins());

            // PLAYER Prediction perk
            Moves playerPredicted = null;
            boolean playerPredictionActive = player.hasPredictionForNextRound();
            if (playerPredictionActive) {
                System.out.println("\nYour Prediction is active! Guess their move:");
                System.out.println("1) Rock  2) Paper  3) Scissors  4) Lizard  5) Spock");
                int guess = readIntInRange(scanner, 1, 5);
                playerPredicted = numberToMove(guess);
                player.clearPredictionForNextRound();
            }

            // TRAINER Prediction (if they have it)
            Moves trainerPredicted = null;
            boolean trainerPredictionActive = false;
            if (opponent instanceof Trainer) {
                Trainer t = (Trainer) opponent;
                if (t.shouldPredictThisRound(round)) {
                    trainerPredicted = t.predictPlayerMove();
                    trainerPredictionActive = true;
                    System.out.println("\n[" + opponent.getName() + " is predicting your move...]");
                }
            }

            // Get moves
            Moves playerMove = player.selectMove(scanner);
            Moves opponentMove = opponent.selectMove(scanner);

            System.out.println(player.getName() + " chose: " + playerMove);
            System.out.println(opponent.getName() + " chose: " + opponentMove);

            // Check player prediction
            boolean playerPredictionCorrect = playerPredictionActive && (playerPredicted == opponentMove);
            if (playerPredictionActive) {
                System.out.println(playerPredictionCorrect ? "Your prediction CORRECT!" : "Your prediction wrong.");
            }

            // Check trainer prediction
            boolean trainerPredictionCorrect = trainerPredictionActive && (trainerPredicted == playerMove);
            if (trainerPredictionActive) {
                System.out.println(trainerPredictionCorrect ? 
                    "[" + opponent.getName() + " predicted correctly!]" : 
                    "[" + opponent.getName() + "'s prediction was wrong.]");
            }

            int result = compare(playerMove, opponentMove);

            if (result == 1) {
                // Player wins
                int damage = playerPredictionCorrect ? 2 : 1;
                opponent.takeDamage(damage);
                System.out.println("You win the round! " + opponent.getName() + " takes " + damage + " damage.");

                if (!isTutorial || !opponent.isAlive()) {
                    player.addCoins(2);
                }

            } else if (result == -1) {
                // Opponent wins
                int damage = trainerPredictionCorrect ? 2 : 1;
                player.takeDamage(damage);
                System.out.println("You lose the round! You take " + damage + " damage.");

                if (!isTutorial || !opponent.isAlive()) {
                    player.addCoins(1);
                }

                // Shop appears when you lose (if still alive)
                if (player.isAlive() && !isTutorial) {
                    inBattleShop(player, scanner);
                }

            } else {
                // Tie
                boolean playerHasTiebreaker = player.hasTiebreaker();
                boolean trainerHasTiebreaker = false;
                if (opponent instanceof Trainer) {
                    trainerHasTiebreaker = ((Trainer) opponent).hasTiebreaker();
                }
                
                if (playerHasTiebreaker && trainerHasTiebreaker) {
                    System.out.println("Tie! Both have tiebreakers - they cancel out!");
                    player.useTiebreaker();
                    ((Trainer) opponent).useTiebreaker();
                } else if (playerHasTiebreaker) {
                    System.out.println("Tie! Your tiebreaker activates.");
                    opponent.takeDamage(1);
                    player.useTiebreaker();
                    if (!isTutorial || !opponent.isAlive()) {
                        player.addCoins(2);
                    }
                } else if (trainerHasTiebreaker) {
                    System.out.println("Tie! " + opponent.getName() + "'s tiebreaker activates.");
                    player.takeDamage(1);
                    ((Trainer) opponent).useTiebreaker();
                    if (!isTutorial || !opponent.isAlive()) {
                        player.addCoins(1);
                    }
                    if (player.isAlive() && !isTutorial) {
                        inBattleShop(player, scanner);
                    }
                } else {
                    System.out.println("Tie. No damage.");
                }
            }

            round++;
            if (player.isAlive() && opponent.isAlive()) {
                System.out.println("Press ENTER to continue...");
                scanner.nextLine();
            }
        }

        if (player.isAlive()) {
            System.out.println("\nVICTORY!");
            return true;
        } else {
            System.out.println("\nDEFEAT");
            return false;
        }
    }

    private static void inBattleShop(Player player, Scanner scanner) {
        System.out.println("\n--- COMEBACK SHOP ---");
        System.out.println("Your coins: " + player.getCoins());

        List<ShopPerk> allPerks = new ArrayList<>();
        allPerks.add(ShopPerk.heartBoost());
        allPerks.add(ShopPerk.tiebreaker());
        allPerks.add(ShopPerk.prediction());

        Collections.shuffle(allPerks);

        // show up to 3
        int offerCount = Math.min(3, allPerks.size());
        for (int i = 0; i < offerCount; i++) {
            ShopPerk p = allPerks.get(i);
            System.out.println((i + 1) + ") " + p.getName() + " (" + p.getPrice() + " coins) - " + p.getDescription());
        }
        System.out.println("4) Skip");

        int choice = readIntInRange(scanner, 1, 4);
        if (choice == 4) return;

        ShopPerk chosen = allPerks.get(choice - 1);

        if (!player.spendCoins(chosen.getPrice())) {
            System.out.println("Not enough coins.");
            return;
        }

        // apply perk
        switch (chosen.getType()) {
            case HEART_BOOST:
                player.increaseMaxHearts(1);
                System.out.println("Bought Heart Boost! Max hearts is now " + player.getMaxHearts());
                break;
            case TIEBREAKER:
                player.giveTiebreaker();
                System.out.println("Bought Tiebreaker! You will win the next tie.");
                break;
            case PREDICTION:
                player.activatePredictionForNextRound();
                System.out.println("Bought Prediction! Active next round.");
                break;
        }
    }

    private static Moves numberToMove(int n) {
        switch (n) {
            case 1: return Moves.ROCK;
            case 2: return Moves.PAPER;
            case 3: return Moves.SCISSORS;
            case 4: return Moves.LIZARD;
            default: return Moves.SPOCK;
        }
    }

    private static int readIntInRange(Scanner scanner, int min, int max) {
        while (true) {
            System.out.print("> ");

            if (!scanner.hasNextInt()) {
                System.out.println("Please enter a number.");
                scanner.nextLine();
                continue;
            }

            int value = scanner.nextInt();
            scanner.nextLine();

            if (value < min || value > max) {
                System.out.println("Enter a number from " + min + " to " + max + ".");
                continue;
            }

            return value;
        }
    }
}