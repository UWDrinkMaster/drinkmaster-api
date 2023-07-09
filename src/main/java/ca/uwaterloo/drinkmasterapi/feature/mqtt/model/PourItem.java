package ca.uwaterloo.drinkmasterapi.feature.mqtt.model;


public class PourItem {

    private int id;
    private float amount;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "PourItem{" +
                "id=" + id +
                ", amount=" + amount +
                '}';
    }
}
