package ca.uwaterloo.drinkmasterapi.feature.mqtt.model;

// not used for mqtt
public class PourItem {

    private int drinkId;
    private double amount;

    public PourItem(int id, float amount) {
        this.drinkId = id;
        this.amount = amount;
    }

    public int getDrinkId() {
        return drinkId;
    }

    public void setDrinkId(int drinkId) {
        this.drinkId = drinkId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + drinkId +
                ", amount=" + amount +
                '}';
    }
}
