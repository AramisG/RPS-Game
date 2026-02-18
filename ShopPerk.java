public class ShopPerk {
    public enum PerkType { HEART_BOOST, TIEBREAKER, PREDICTION }

    private String name;
    private String description;
    private int price;
    private PerkType type;

    public ShopPerk(String name, String description, int price, PerkType type) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.type = type;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getPrice() { return price; }
    public PerkType getType() { return type; }

    public static ShopPerk heartBoost() {
        return new ShopPerk("Heart Boost", "Max hearts +1 (permanent)", 5, PerkType.HEART_BOOST);
    }

    public static ShopPerk tiebreaker() {
        return new ShopPerk("Tiebreaker", "Win next tie (one-time)", 3, PerkType.TIEBREAKER);
    }

    public static ShopPerk prediction() {
        return new ShopPerk("Prediction", "Guess opponent move for 2 damage (next round)", 3, PerkType.PREDICTION);
    }
}
