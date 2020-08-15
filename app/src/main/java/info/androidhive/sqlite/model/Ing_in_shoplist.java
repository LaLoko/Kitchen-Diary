package info.androidhive.sqlite.model;

public class Ing_in_shoplist {
    private int id, shoplist_id, ing_id, amount;

    Ing_in_shoplist() {
    }

    public Ing_in_shoplist(int id, int shoplist_id, int ing_id, int amount) {
        this.id = id;
        this.ing_id = ing_id;
        this.shoplist_id = shoplist_id;
        this.amount = amount;
    }

    public int getIng_id() {
        return ing_id;
    }

    public int getShoplist_id() {
        return shoplist_id;
    }

    public void setIng_id(int ing_id) {
        this.ing_id = ing_id;
    }

    public void setShoplist_id(int shoplist_id) {
        this.shoplist_id = shoplist_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
