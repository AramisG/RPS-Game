/**
 * Simple perk data class for the shop system
 */
public class ShopPerk {
    private String name;
    private String description;
    private int price;
    private PerkType type;
    
    public enum PerkType {
        HEART_BOOST,
        TIEBREAKER,
        PREDICTION
    }
    
    public ShopPerk(String name, String description, int price, PerkType type) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.type = type;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public int getPrice() {
        return price;
    }
    
    public PerkType getType() {
        return type;
    }
    
    // Static factory methods for creating perks
    public static ShopPerk createHeartBoost() {
        return new ShopPerk(
            "Heart Boost",
            "Permanently increase max hearts by 1",
            5,
            PerkType.HEART_BOOST
        );
    }
    
    public static ShopPerk createTiebreaker() {
        return new ShopPerk(
            "Tiebreaker",
            "Win the next tied round (one-time use)",
            3,
            PerkType.TIEBREAKER
        );
    }
    
    public static ShopPerk createPrediction() {
        return new ShopPerk(
            "Prediction",
            "Guess opponent's next move for 2x damage (next round only!)",
            3,
            PerkType.PREDICTION
        );
    }
}