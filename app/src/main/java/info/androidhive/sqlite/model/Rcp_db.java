package info.androidhive.sqlite.model;

import java.util.ArrayList;

public class Rcp_db {
    private int id_PR_KEY, rcp_calories, rcp_carbs, rcp_proteins, rcp_fat, vegan, made;
    private String dish_name, how_to_cook,occasion;
    private byte[] rcp_image;
    private ArrayList<Ing_in_dish> ing_in_dishes = new ArrayList<>();

    public Rcp_db() {
    }

    public Rcp_db(int id_PR_KEY, int rcp_calories, int rcp_carbs, int rcp_fat, int rcp_proteins, String dish_name, String how_to_cook, byte[] rcp_image, int vegan,int made,String occasion) {
        this.id_PR_KEY = id_PR_KEY;
        this.rcp_calories = rcp_calories;
        this.rcp_carbs = rcp_carbs;
        this.rcp_fat = rcp_fat;
        this.rcp_proteins = rcp_proteins;
        this.how_to_cook = how_to_cook;
        this.dish_name = dish_name;
        this.rcp_image = rcp_image;
        this.vegan = vegan;
        this.made = made;
        this.occasion = occasion;
    }

    public void add_ing_to_rec(Ing_in_dish ing_in_dish) {
        ing_in_dishes.add(ing_in_dish);
    }

    public ArrayList<Ing_in_dish> get_ing_in_dish() {
        return ing_in_dishes;
    }

    public ArrayList<Integer> get_in_dish_ids() {
        ArrayList<Integer> out = new ArrayList<>();
        for (Ing_in_dish in : ing_in_dishes) {
            out.add(in.getId());
        }
        return out;
    }

    public void setDish_name(String dish_name) {
        this.dish_name = dish_name;
    }

    public void setId_PR_KEY(int id_PR_KEY) {
        this.id_PR_KEY = id_PR_KEY;
    }

    public void setRcp_calories(int rcp_calories) {
        this.rcp_calories = rcp_calories;
    }

    public void setRcp_carbs(int rcp_carbs) {
        this.rcp_carbs = rcp_carbs;
    }

    public void setRcp_fat(int rcp_fat) {
        this.rcp_fat = rcp_fat;
    }

    public void setRcp_image(byte[] rcp_image) {
        this.rcp_image = rcp_image;
    }

    public void setRcp_proteins(int rcp_proteins) {
        this.rcp_proteins = rcp_proteins;
    }

    public void setVegan(int vegan) {
        this.vegan = vegan;
    }

    public void setHow_to_cook(String how_to_cook) {
        this.how_to_cook = how_to_cook;
    }

    public int getId_PR_KEY() {
        return id_PR_KEY;
    }

    public int getRcp_calories() {
        return rcp_calories;
    }

    public int getRcp_carbs() {
        return rcp_carbs;
    }

    public int getRcp_fat() {
        return rcp_fat;
    }

    public int getRcp_proteins() {
        return rcp_proteins;
    }

    public String getDish_name() {
        return dish_name;
    }

    public byte[] getRcp_image() {
        return rcp_image;
    }

    public String getHow_to_cook() {
        return how_to_cook;
    }

    public int isVegan() {
        return vegan;
    }

    public void setMade(int made) {
        this.made = made;
    }

    public int getMade() {
        return made;
    }

    public String getOccasion() {
        return occasion;
    }

    public void setOccasion(String occasion) {
        this.occasion = occasion;
    }
}