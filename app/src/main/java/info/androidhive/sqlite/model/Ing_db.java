package info.androidhive.sqlite.model;

public class Ing_db {
    private int ing_id, in_stock, vegan;
    private int ing_calories, ing_proteins, ing_fat, ing_carbs, made;
    private String name, buy_date, eatable_time;
    private byte[] image;

    Ing_db() {
    }

    public Ing_db(int ing_id, String eatable_time, int ing_calories, int ing_carbs, int ing_fat, int ing_proteins, String name, byte[] image, int vegan, int made) {
        this.ing_id = ing_id;
        this.eatable_time = eatable_time;
        this.ing_calories = ing_calories;
        this.ing_carbs = ing_carbs;
        this.ing_fat = ing_fat;
        this.ing_proteins = ing_proteins;
        this.name = name;
        this.image = image;
        this.vegan = vegan;
        this.made = made;
    }

    public Ing_db(int ing_id, String eatable_time, int ing_calories, int ing_carbs, int ing_fat, int ing_proteins, String name, byte[] image, int vegan, int in_stock, String buy_date, int made) {
        this.ing_id = ing_id;
        this.eatable_time = eatable_time;
        this.ing_calories = ing_calories;
        this.ing_carbs = ing_carbs;
        this.ing_fat = ing_fat;
        this.ing_proteins = ing_proteins;
        this.name = name;
        this.image = image;
        this.vegan = vegan;
        this.in_stock = in_stock;
        this.buy_date = buy_date;
        this.made = made;
    }

    public void seting_id(int ing_id) {
        this.ing_id = ing_id;
    }

    public void setIn_stock(int in_stock) {
        this.in_stock = in_stock;
    }

    public void setEatable_time(String eatable_time) {
        this.eatable_time = eatable_time;
    }

    public void setIng_calories(int calories) {
        this.ing_calories = calories;
    }

    public void setIng_proteins(int proteins) {
        this.ing_proteins = proteins;
    }

    public void setIng_fat(int fat) {
        this.ing_fat = fat;
    }

    public void setIng_carbs(int carbs) {
        this.ing_carbs = carbs;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setBuy_date(String date) {
        this.buy_date = date;
    }

    public void setVegan(int vegan) {
        this.vegan = vegan;
    }

    public int geting_id() {
        return this.ing_id;
    }

    public int getIn_stock() {
        return this.in_stock;
    }

    public String getEatable_time() {
        return this.eatable_time;
    }

    public int getIng_calories() {
        return this.ing_calories;
    }

    public int getIng_proteins() {
        return this.ing_proteins;
    }

    public int getIng_fat() {
        return this.ing_fat;
    }

    public int getIng_carbs() {
        return this.ing_carbs;
    }

    public String getName() {
        return this.name;
    }

    public byte[] getImage() {
        return this.image;
    }

    public String getBuy_date() {
        return this.buy_date;

    }

    public int isVegan() {
        return vegan;
    }

    public int getMade() {
        return made;
    }

    public void setMade(int made) {
        this.made = made;
    }
}
