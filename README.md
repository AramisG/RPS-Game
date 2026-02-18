# Rock Paper Scissors Lizard Spock Gauntlet

## Files Required (8 total):
1. Battle.java
2. Combatant.java
3. Main.java
4. Moves.java
5. Player.java
6. ShopPerk.java (note the capital P!)
7. Strat.java
8. Trainer.java

## How to Compile and Run:

### Method 1 (Recommended):
```bash
javac *.java
java Main
```

### Method 2 (If Method 1 fails):
```bash
javac Moves.java
javac Strat.java
javac Combatant.java
javac ShopPerk.java
javac Player.java
javac Trainer.java
javac Battle.java
javac Main.java
java Main
```

## Troubleshooting:

### "class ShopPerk is public, should be declared in a file named ShopPerk.java"
- Make sure the file is named **ShopPerk.java** with capital S and capital P
- Delete any files named "Shopperk.java" or similar
- On Windows, do: `del *.class` to clean up before recompiling

### "file does not contain class Trainer"
- Make sure all 8 .java files are in the same directory
- Try deleting all .class files and recompile from scratch
- Make sure no old versions of files exist

### General Tips:
- Put all 8 .java files in a fresh, empty folder
- Delete any .class files before compiling
- Make sure you're in the correct directory when running javac/java

## Game Rules:

### Combat:
- 5 hearts each - first to 0 loses
- Win a round = opponent loses 1 heart, you get 2 coins
- Lose a round = you lose 1 heart, you get 1 coin
- Tie = nothing happens (unless you have Tiebreaker perk)

### Moves (Rock-Paper-Scissors-Lizard-Spock):
- Rock beats Scissors & Lizard
- Paper beats Rock & Spock
- Scissors beats Paper & Lizard
- Lizard beats Paper & Spock
- Spock beats Rock & Scissors

### Perks (bought with coins after each battle):
1. **Heart Boost (5 coins)** - Permanently gain +1 max heart
2. **Tiebreaker (3 coins)** - Win the next tied round (one-time use)
3. **Prediction (3 coins)** - Active for next battle only
   - Before each round, guess opponent's move
   - Correct guess = 2 damage instead of 1
   - With 5 moves, it's a 1/5 (20%) chance!

### Tutorial Level 0:
- Rocky always throws Rock
- No coins awarded until the final blow
- Teaches you the combat system

## Strategy Tips:
- Save coins for Heart Boost early - more HP helps survivability
- Prediction is a gamble but powerful if used wisely
- Tiebreaker is cheap and can save you in clutch moments
- With 5 moves instead of 3, prediction is much riskier!

Enjoy the gauntlet! 🎮
