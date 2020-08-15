package info.androidhive.sqlite.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper sInstance;
    private static final String LOG = "DatabaseHelper";
    private static final int DATABASE_VERSION = 54;
    private static final String DATABASE_NAME = "KitchenDiaryDB.db";
    //table names
    private static final String TABLE_INGREDIENTS = "TABLE_INGREDIENTS";
    private static final String TABLE_RECIPES = "TABLE_RECIPES";
    private static final String TABLE_ING_IN_DISH = "TABLE_INGREDIENTS_IN_DISH";
    private static final String TABLE_SHOPLIST = "TABLE_SHOPLIST";
    private static final String TABLE_ING_IN_SHOPLIST = "TABLE_ING_IN_SHOPLIST";
    //both column name
    private static final String vegan = "vegan"; // int 1 vegan 0 not
    private static final String calories = "calories"; //int
    private static final String proteins = "proteins"; //int
    private static final String fat = "fat"; //int
    private static final String carbs = "carbs"; //int
    private static final String image = "image"; //blob
    //ingredients columns names
    private static final String id_ing = "id_ing"; //PRIMARY KEY
    private static final String in_stock = "in_stock"; //int amount in stock DEFAULT 0
    private static final String eatable_time = "eatable_time"; //int days
    private static final String name = "name"; //varchar
    private static final String buy_date = "buy_date"; //DEFAULT SYSDATE
    //recipes columns names
    private static final String id_rcp = "id_rcp"; //PRIMARY KEY
    private static final String ing_id_FR_KEY = "ing_id"; //FORGEIN KEY INT
    private static final String dish_name = "dish_name"; //varchar
    private static final String made = "made"; //integer numbers of made
    private static final String how_to_cook = "how_to_cook"; //cooking description
    private static final String occasion = "occasion"; //occasion to make dish
    //ingredients in dish column names
    private static final String id_ing_in_dish = "id_ing_in_dish"; //PRIMARY KEY
    private static final String ing_amount = "ing_amount"; //int amount of recipies need for dish
    //shoplist column names
    private static final String shoplist_id = "shoplist_id";
    private static final String mod_date = "mod_date"; // date of last modification or create
    private static final String shp_name = "shp_name";
    //ingrediedients in shoplist column names
    private static final String id_ing_in_shoplist = "id_ing_in_shoplist"; //FORGEIN KEY
    //ingredients database create statment
    private static final String CREATE_TABLE_INGREDIENTS = "CREATE TABLE " + TABLE_INGREDIENTS + "("
            + id_ing + " INTEGER PRIMARY KEY ,"
            + name + " TEXT,"
            + in_stock + " INTEGER DEFAULT 0,"
            + eatable_time + " INTEGER,"
            + buy_date + " TEXT,"
            + vegan + " INTEGER,"
            + calories + " INTEGER,"
            + proteins + " INTEGER,"
            + fat + " INTEGER,"
            + carbs + " INTEGER,"
            + made + " INTEGER DEFAULT 0,"
            + image + " BLOB" + ")";
    //recipes database create statment
    private static final String CREATE_TABLE_RECIPES = "CREATE TABLE " + TABLE_RECIPES + "("
            + id_rcp + " INTEGER PRIMARY KEY ,"
            + dish_name + " TEXT,"
            + vegan + " INTEGER,"
            + how_to_cook + " TEXT,"
            + calories + " INTEGER,"
            + proteins + " INTEGER,"
            + fat + " INTEGER,"
            + carbs + " INTEGER,"
            + made + " INTEGER DEFAULT 0,"
            + occasion + " TEXT,"
            + image + " BLOB" + ")";
    //ingrednients in dish create statment
    private static final String CREATE_TABLE_INGREDIENTS_IN_DISH = "CREATE TABLE " + TABLE_ING_IN_DISH + "("
            + id_ing_in_dish + " INTEGER PRIMARY KEY ,"
            + id_rcp + " INTEGER  ,"
            + id_ing + " INTEGER  ,"
            + ing_amount + " INTEGER  ,"
            + "FOREIGN KEY(" + id_rcp + ") REFERENCES " + TABLE_RECIPES + "( " + id_rcp + "),"
            + "FOREIGN KEY(" + id_ing + ") REFERENCES " + TABLE_INGREDIENTS + "(" + id_ing + "))";
    //shoplist create statment
    private static final String CREATE_TABLE_SHOPLIST = "CREATE TABLE " + TABLE_SHOPLIST + "("
            + shoplist_id + " INTEGER PRIMARY KEY ,"
            + mod_date + " TEXT,"
            + shp_name + " TEXT" + ")";
    //ingredients in shoplist create statment
    private static final String CREATE_TABLE_ING_IN_SHOPLIST = "CREATE TABLE " + TABLE_ING_IN_SHOPLIST + "("
            + id_ing_in_shoplist + " INTEGER PRIMARY KEY ,"
            + ing_amount + " INTEGER  ,"
            + shoplist_id + " INTEGER  ,"
            + id_ing + " INTEGER  ,"
            + "FOREIGN KEY(" + shoplist_id + ") REFERENCES " + TABLE_SHOPLIST + "(" + shoplist_id + "),"
            + "FOREIGN KEY(" + id_ing + ") REFERENCES " + TABLE_INGREDIENTS + "(" + id_ing + "))";

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_INGREDIENTS);
        sqLiteDatabase.execSQL(CREATE_TABLE_RECIPES);
        sqLiteDatabase.execSQL(CREATE_TABLE_INGREDIENTS_IN_DISH);
        sqLiteDatabase.execSQL(CREATE_TABLE_SHOPLIST);
        sqLiteDatabase.execSQL(CREATE_TABLE_ING_IN_SHOPLIST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i != i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENTS);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ING_IN_DISH);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOPLIST);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ING_IN_SHOPLIST);

            onCreate(sqLiteDatabase);
        }
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
    }

    public void createIngredients(Ing_db ing_db) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        ContentValues values = new ContentValues();

        values.put(eatable_time, ing_db.getEatable_time());
        values.put(calories, ing_db.getIng_calories());
        values.put(carbs, ing_db.getIng_carbs());
        values.put(fat, ing_db.getIng_fat());
        values.put(proteins, ing_db.getIng_proteins());
        values.put(name, ing_db.getName());
        values.put(image, ing_db.getImage());
        values.put(vegan, ing_db.isVegan());
        values.put(made, ing_db.getMade());

        db.endTransaction();
        db.insert(TABLE_INGREDIENTS, null, values);
    }

    public void createRecipe(Rcp_db rcp_db) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        ContentValues values = new ContentValues();

        values.put(id_rcp, rcp_db.getId_PR_KEY());
        values.put(calories, rcp_db.getRcp_calories());
        values.put(carbs, rcp_db.getRcp_carbs());
        values.put(fat, rcp_db.getRcp_fat());
        values.put(proteins, rcp_db.getRcp_proteins());
        values.put(dish_name, rcp_db.getDish_name());
        values.put(image, rcp_db.getRcp_image());
        values.put(vegan, rcp_db.isVegan());
        values.put(how_to_cook, rcp_db.getHow_to_cook());
        values.put(made, rcp_db.getMade());
        values.put(occasion, rcp_db.getOccasion());

        db.endTransaction();
        db.insert(TABLE_RECIPES, null, values);
    }

    public void createIng_in_dish(Ing_in_dish ing_in_dish) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        ContentValues values = new ContentValues();

        values.put(id_ing_in_dish, ing_in_dish.getId());
        values.put(id_rcp, ing_in_dish.getRcp_id());
        values.put(id_ing, ing_in_dish.getIng_id());
        values.put(ing_amount, ing_in_dish.getAmount());

        db.endTransaction();
        db.insert(TABLE_ING_IN_DISH, null, values);
    }

    public void createShoplist(Shoplist_db shoplist_db) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        ContentValues values = new ContentValues();

        values.put(shoplist_id, shoplist_db.getId());
        values.put(mod_date, shoplist_db.getMod_date());
        values.put(shp_name, shoplist_db.getName());

        db.endTransaction();
        db.insert(TABLE_SHOPLIST, null, values);
    }

    public void createIng_in_shoplist(Ing_in_shoplist ing_in_shoplist) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        ContentValues values = new ContentValues();

        values.put(id_ing_in_shoplist, ing_in_shoplist.getId());
        values.put(shoplist_id, ing_in_shoplist.getShoplist_id());
        values.put(id_ing, ing_in_shoplist.getIng_id());
        values.put(ing_amount, ing_in_shoplist.getAmount());

        db.endTransaction();
        db.insert(TABLE_ING_IN_SHOPLIST, null, values);
    }

    public List<Ing_db> getAllIngredients() {
        List<Ing_db> all_ing = new ArrayList<Ing_db>();
        String selectQuery = "SELECT * FROM " + TABLE_INGREDIENTS;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        try {
            if (c.moveToFirst()) {
                do {
                    Ing_db ing_db = new Ing_db(
                            c.getInt(c.getColumnIndex(id_ing)),
                            c.getString(c.getColumnIndex(eatable_time)),
                            c.getInt(c.getColumnIndex(calories)),
                            c.getInt(c.getColumnIndex(carbs)),
                            c.getInt(c.getColumnIndex(fat)),
                            c.getInt(c.getColumnIndex(proteins)),
                            c.getString(c.getColumnIndex(name)),
                            c.getBlob(c.getColumnIndex(image)),
                            c.getInt(c.getColumnIndex(vegan)),
                            c.getInt(c.getColumnIndex(in_stock)),
                            c.getString(c.getColumnIndex(buy_date)),
                            c.getInt(c.getColumnIndex(made)));
                    all_ing.add(ing_db);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            if (c != null && !c.isClosed()) {
                c.close();
            }
        }

        return all_ing;
    }

    public List<Rcp_db> getAllRecipes() {
        List<Rcp_db> all_rcp = new ArrayList<Rcp_db>();
        String selectQuery = "SELECT  * FROM " + TABLE_RECIPES;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Rcp_db rcp_db = new Rcp_db(
                        c.getInt(c.getColumnIndex(id_rcp)),
                        c.getInt(c.getColumnIndex(calories)),
                        c.getInt(c.getColumnIndex(carbs)),
                        c.getInt(c.getColumnIndex(fat)),
                        c.getInt(c.getColumnIndex(proteins)),
                        c.getString(c.getColumnIndex(dish_name)),
                        c.getString(c.getColumnIndex(how_to_cook)),
                        c.getBlob(c.getColumnIndex(image)),
                        c.getInt(c.getColumnIndex(vegan)),
                        c.getInt(c.getColumnIndex(made)),
                        c.getString(c.getColumnIndex(occasion)));

                all_rcp.add(rcp_db);
            } while (c.moveToNext());
        }

        c.close();
        return all_rcp;
    }

    public List<Ing_in_dish> getAllIngredients_in_recipes() {
        List<Ing_in_dish> all_rid = new ArrayList<Ing_in_dish>();
        String selectQuery = "SELECT * FROM " + TABLE_ING_IN_DISH;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Ing_in_dish ing_in_dish = new Ing_in_dish(
                        c.getInt(c.getColumnIndex(id_ing_in_dish)),
                        c.getInt(c.getColumnIndex(id_rcp)),
                        c.getInt(c.getColumnIndex(id_ing)),
                        c.getInt(c.getColumnIndex(ing_amount)));

                all_rid.add(ing_in_dish);
            } while (c.moveToNext());
        }
        return all_rid;
    }

    public List<Shoplist_db> getAllShoplists() {
        List<Shoplist_db> all_shoplists = new ArrayList<>();
        String selectQuerry = "SELECT * FROM " + TABLE_SHOPLIST;

        Log.e(LOG, selectQuerry);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuerry, null);

        if (c.moveToFirst()) {
            do {
                Shoplist_db shoplist_db = new Shoplist_db(
                        c.getInt(c.getColumnIndex(shoplist_id)),
                        c.getString(c.getColumnIndex(shp_name)),
                        c.getString(c.getColumnIndex(mod_date)));

                all_shoplists.add(shoplist_db);
            } while (c.moveToNext());
        }
        return all_shoplists;
    }

    public List<Ing_in_shoplist> getAllIng_in_shoplist() {
        List<Ing_in_shoplist> all_ing_in_shoplist = new ArrayList<>();
        String selectQuerry = "SELECT * FROM " + TABLE_ING_IN_SHOPLIST;

        Log.e(LOG, selectQuerry);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuerry, null);

        if (c.moveToFirst()) {
            do {
                Ing_in_shoplist ing_in_shoplist = new Ing_in_shoplist(
                        c.getInt(c.getColumnIndex(id_ing_in_shoplist)),
                        c.getInt(c.getColumnIndex(shoplist_id)),
                        c.getInt(c.getColumnIndex(id_ing)),
                        c.getInt(c.getColumnIndex(ing_amount)));

                all_ing_in_shoplist.add(ing_in_shoplist);
            } while (c.moveToNext());
        }
        return all_ing_in_shoplist;
    }

    public int updateING(int id, int amount_to_add, String date, int m) {
        int is_updated;
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        ContentValues values = new ContentValues();
        values.put(in_stock, amount_to_add);
        values.put(buy_date, date);
        values.put(made, m);

        is_updated = db.update(TABLE_INGREDIENTS, values, id_ing + " = ?", new String[]{Long.toString(id)});
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
        return is_updated;
    }

    public int updateRCP(int id, int md) {
        int is_updated;
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        ContentValues values = new ContentValues();
        //values.put(calories, rcp_db.getRcp_calories());
        //values.put(carbs, rcp_db.getRcp_carbs());
        //values.put(fat, rcp_db.getRcp_fat());
        //values.put(proteins, rcp_db.getRcp_proteins());
        //values.put(name, rcp_db.getDish_name());
        //values.put(how_to_cook,rcp_db.getHow_to_cook());
        //values.put(image, rcp_db.getRcp_image());
        //values.put(vegan, rcp_db.isVegan());
        values.put(made, md);
        //values.put(occasion, rcp_db.getOccasion());

        is_updated = db.update(TABLE_RECIPES, values, id_rcp + " = ?", new String[]{Long.toString(id)});
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
        return is_updated;
    }

    public int updateShoplist(Shoplist_db shoplist_db) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(shoplist_id, shoplist_db.getId());
        values.put(mod_date, shoplist_db.getMod_date());
        values.put(shp_name, shoplist_db.getName());

        return db.update(TABLE_SHOPLIST, values, shoplist_id + " = ?",
                new String[]{String.valueOf(shoplist_db.getId())});

    }

    public int updateIng_in_shoplist(Ing_in_shoplist ing_in_shoplist) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(id_ing_in_shoplist, ing_in_shoplist.getId());
        values.put(shoplist_id, ing_in_shoplist.getShoplist_id());
        values.put(id_ing, ing_in_shoplist.getIng_id());
        values.put(ing_amount, ing_in_shoplist.getAmount());

        return db.update(TABLE_ING_IN_SHOPLIST, values, id_ing_in_shoplist + " = ?",
                new String[]{String.valueOf(ing_in_shoplist.getId())});

    }

    public void deleteIngredient(long ing_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_INGREDIENTS, id_ing + " = ?", new String[]{String.valueOf(ing_id)});
    }

    public void deleteRecipie(long id, ArrayList<Integer> in_dish_ids) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RECIPES, id_rcp + " = ?", new String[]{String.valueOf(id)});
        for (int i : in_dish_ids) {
            deletrIng_in_dish(i);
        }
    }

    public void deletrIng_in_dish(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ING_IN_DISH, id + " = ?", new String[]{String.valueOf(id)});
    }

    public void deleteShoplist(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SHOPLIST, shoplist_id + " = ?", new String[]{String.valueOf(id)});
    }

    public void deleteIng_in_shoplist(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ING_IN_SHOPLIST, id_ing_in_shoplist + " = ?", new String[]{String.valueOf(id)});
    }

    public int getTableSize(String table_name) {
        SQLiteDatabase db = this.getReadableDatabase();
        int count = (int) DatabaseUtils.queryNumEntries(db, table_name);
        db.close();
        return count;
    }

    public int get_ing_size() {
        return getTableSize(TABLE_INGREDIENTS);
    }

    public int get_rcp_size() {
        return getTableSize(TABLE_RECIPES);
    }

    public int get_ing_in_rcp_size() {
        return getTableSize(TABLE_ING_IN_DISH);
    }

    public int get_shoplists_size() {
        return getTableSize(TABLE_SHOPLIST);
    }

    public int get_ing_in_shoplist_size() {
        return getTableSize(TABLE_ING_IN_SHOPLIST);
    }

    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}
