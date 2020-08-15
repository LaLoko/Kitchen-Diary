package info.androidhive.sqlite.model;

public class Ing_in_dish {
    private int id, Rcp_id, Ing_id;
    float amount;

    public Ing_in_dish() {
    }

    public Ing_in_dish(int id, int Rcp_id, int Ing_id, float amount) {
        this.id = id;
        this.Rcp_id = Rcp_id;
        this.Ing_id = Ing_id;
        this.amount = amount;
    }

    public float getAmount() {
        return amount;
    }

    public int getId() {
        return id;
    }

    public int getIng_id() {
        return Ing_id;
    }

    public int getRcp_id() {
        return Rcp_id;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIng_id(int ing_id) {
        Ing_id = ing_id;
    }

    public void setRcp_id(int rcp_id) {
        Rcp_id = rcp_id;
    }
}
