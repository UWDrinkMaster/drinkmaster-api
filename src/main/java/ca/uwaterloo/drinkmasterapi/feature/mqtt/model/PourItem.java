package ca.uwaterloo.drinkmasterapi.feature.mqtt.model;

// not used for mqtt
public class PourItem {

    private int id;
    private float amount;

    public PourItem(int id, float amount) {
        this.id = id;
        this.amount = amount;
    }

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
        return "{" +
                "id=" + id +
                ", amount=" + amount +
                '}';
    }
}
