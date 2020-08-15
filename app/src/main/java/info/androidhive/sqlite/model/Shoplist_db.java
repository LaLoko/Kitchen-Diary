package info.androidhive.sqlite.model;

public class Shoplist_db {
    private int shoplist_id;
    private String mod_date, shp_name;

    Shoplist_db() {
    }

    public Shoplist_db(int shoplist_id, String shp_name, String mod_date) {
        this.shoplist_id = shoplist_id;
        this.mod_date = mod_date;
        this.shp_name = shp_name;
    }

    public int getId() {
        return shoplist_id;
    }

    public void setId(int shoplist_id) {
        this.shoplist_id = shoplist_id;
    }

    public String getMod_date() {
        return mod_date;
    }

    public void setMod_date(String mod_date) {
        this.mod_date = mod_date;
    }

    public String getName() {
        return shp_name;
    }

    public void setName(String shp_name) {
        this.shp_name = shp_name;
    }
}
