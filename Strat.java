public enum Strat {
    ALWAYS_ROCK,
    ALWAYS_PAPER,
    ALWAYS_SCISSORS,
    RANDOM,
    COUNTER_ROCK,    // favors paper to counter rock
    COUNTER_PAPER,   // favors scissors to counter paper
    COUNTER_SCISSORS // favors rock to counter scissors
}