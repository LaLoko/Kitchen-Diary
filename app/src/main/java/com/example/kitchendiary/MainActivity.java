package com.example.kitchendiary;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import info.androidhive.sqlite.model.DatabaseHelper;
import info.androidhive.sqlite.model.Ing_db;
import info.androidhive.sqlite.model.Ing_in_dish;
import info.androidhive.sqlite.model.Ing_in_shoplist;
import info.androidhive.sqlite.model.Rcp_db;

public class MainActivity extends AppCompatActivity {
    int ing_id = 0;
    int rcp_id = 0;
    int ing_n_rcp_id = 0;
    public static ArrayList<Ing_in_dish> ing_to_add_in_dish;
    public static ArrayList<Ing_in_shoplist> ing_to_add_in_shoplist;
    public static boolean rcp_filter_vegan = false, rcp_filter_avabile = false;
    public static boolean ing_filter_avabile = false;


    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = DatabaseHelper.getInstance(this);
        ing_to_add_in_shoplist = new ArrayList<>();
        ing_to_add_in_dish = new ArrayList<>();

        ing_id = databaseHelper.get_ing_size();
        rcp_id = databaseHelper.get_rcp_size();
        ing_n_rcp_id = databaseHelper.get_ing_in_rcp_size();

        db_create();

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

        databaseHelper.closeDB();
    }

    public void go_to_rec(View v) {
        startActivity(new Intent(MainActivity.this, Recipes.class));
    }

    public void go_to_ingr(View v) {
        startActivity(new Intent(MainActivity.this, Ingredients.class));
    }

    public void go_to_shpls(View v) {
        startActivity(new Intent(MainActivity.this, Shoplist.class));
    }

    void db_create() {
        if (databaseHelper.get_ing_size() == 0) add_ingrendients_to_database();
        if (databaseHelper.get_rcp_size() == 0) add_recipes_to_database();
    }

    void add_ingrendients_to_database() {
        byte[] egg = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.egg));
        byte[] onion = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.onion));
        byte[] white_rice = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.white_rice));
        byte[] cheese = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.cheese));
        byte[] minced_beef = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.mince_beef));
        byte[] butter = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.butter));
        byte[] black_pepper = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.pepper));
        byte[] salt = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.salt));
        byte[] tomato = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.tomato));
        byte[] carrot = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.carrot));
        byte[] spagetti = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.spagetti));
        byte[] cabbage = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.cabbage));
        byte[] soy_sauce = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.soy_sauce));
        byte[] ginger = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.ginger));
        byte[] green_onion = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.green_onion));
        byte[] rapeseed_oil = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.rapeseed_oil));
        byte[] mozzarella = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.mozzarella));
        byte[] olive_oil = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.olive_oil));
        byte[] basile = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.basile));
        byte[] bread = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.bread));
        byte[] tortilla = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.tortilla));
        byte[] chorizo = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.chorizo));
        byte[] chilli = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.chilli));
        byte[] cream = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.cream));
        byte[] bacon = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.bacon));
        byte[] parmesan = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.parmesan));
        byte[] potato = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.potato));
        byte[] parsley = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.parsley));
        byte[] beetroot = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.beetroot));
        byte[] paprika = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.paprika));
        byte[] garlic = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.garlic));
        byte[] rosemary = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.rosemary));
        byte[] water = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.water));
        byte[] flour = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.flour));
        byte[] sugar = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.sugar));
        byte[] yeast = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.yeast));
        byte[] oregano = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.oregano));
        byte[] tomatocnt = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.tomatocnt));
        byte[] lasagne_pasta = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.lasagne_pasta));
        byte[] milk = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.milk));
        byte[] chicken = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.chicken));
        byte[] vinegar = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.vinegar));
        byte[] sesame = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.sesame));
        byte[] smh_rolls = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.smh_rolls));
        byte[] chickenqrt = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.chickenqrt));
        byte[] herbesdeprovence = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.herbesdeprovence));
        byte[] majonese = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.majonese));
        byte[] spinach = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.spinach));
        byte[] pepproni = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.pepperoni));
        byte[] kurkuma = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.kurkuma));
        byte[] kminek = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.kminek));
        byte[] bulion = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.bulion));

        databaseHelper.createIngredients(new Ing_db(getIng_id_add(), 14 + "", 155, 1, 11, 13, "jajko", egg, 0, 0));//0
        databaseHelper.createIngredients(new Ing_db(getIng_id_add(), 14 + "", 39, 9, 0, 1, "cebula", onion, 1, 0));//1
        databaseHelper.createIngredients(new Ing_db(getIng_id_add(), "...", 357, 80, 1, 8, "ryż biały", white_rice, 1, 0));//2
        databaseHelper.createIngredients(new Ing_db(getIng_id_add(), 14 + "", 402, 1, 33, 25, "ser", cheese, 0, 0));//3
        databaseHelper.createIngredients(new Ing_db(getIng_id_add(), 4 + "", 254, 0, 20, 17, "mięso mielone wołowe", minced_beef, 0, 0));//4
        databaseHelper.createIngredients(new Ing_db(getIng_id_add(), 7 + "", 18, 4, 0, 1, "pomidor", tomato, 1, 0));//5
        databaseHelper.createIngredients(new Ing_db(getIng_id_add(), 30 + "", 717, 0, 81, 1, "masło", butter, 0, 0));//6
        databaseHelper.createIngredients(new Ing_db(getIng_id_add(), "...", 255, 65, 4, 11, "pieprz czarny", black_pepper, 1, 0));//7
        databaseHelper.createIngredients(new Ing_db(getIng_id_add(), "...", 0, 0, 0, 0, "sól", salt, 1, 0));//8
        databaseHelper.createIngredients(new Ing_db(getIng_id_add(), "16", 41, 10, 0, 1, "marchew", carrot, 1, 0));//9
        databaseHelper.createIngredients(new Ing_db(getIng_id_add(), "...", 158, 31, 1, 6, "makaron spagetti", spagetti, 1, 0));//10
        databaseHelper.createIngredients(new Ing_db(getIng_id_add(), "60", 25, 6, 0, 1, "kapusta głowiasta", cabbage, 1, 0));//11
        databaseHelper.createIngredients(new Ing_db(getIng_id_add(), "...", 53, 5, 0, 8, "sos sojowy ciemny", soy_sauce, 1, 0));//12
        databaseHelper.createIngredients(new Ing_db(getIng_id_add(), "90", 80, 18, 1, 2, "imbir", ginger, 1, 0));//13
        databaseHelper.createIngredients(new Ing_db(getIng_id_add(), "7", 30, 4, 1, 3, "szczypior", green_onion, 1, 0));//14
        databaseHelper.createIngredients(new Ing_db(getIng_id_add(), "...", 884, 0, 100, 0, "olej rzepakowy", rapeseed_oil, 1, 0));//15
        databaseHelper.createIngredients(new Ing_db(getIng_id_add(), "14", 280, 3, 17, 21, "ser mozzarella", mozzarella, 1, 0));//16
        databaseHelper.createIngredients(new Ing_db(getIng_id_add(), "...", 884, 0, 100, 0, "oliwa z oliwek", olive_oil, 1, 0));//17
        databaseHelper.createIngredients(new Ing_db(getIng_id_add(), "10", 22, 3, 1, 3, "bazylia", basile, 1, 0));//18
        databaseHelper.createIngredients(new Ing_db(getIng_id_add(), "7", 264, 49, 3, 9, "chleb", bread, 1, 0));//19
        databaseHelper.createIngredients(new Ing_db(getIng_id_add(), "7", 237, 50, 1, 7, "tortilla", tortilla, 1, 0));//20
        databaseHelper.createIngredients(new Ing_db(getIng_id_add(), "14", 455, 2, 38, 24, "chorizo", chorizo, 0, 0));//21
        databaseHelper.createIngredients(new Ing_db(getIng_id_add(), "20", 40, 10, 0, 2, "papryka chilli", chilli, 1, 0));//22
        databaseHelper.createIngredients(new Ing_db(getIng_id_add(), "20", 195, 4, 19, 3, "śmietanka", cream, 0, 0));//23
        databaseHelper.createIngredients(new Ing_db(getIng_id_add(), "21", 540, 1, 42, 37, "boczek", bacon, 0, 0));//24
        databaseHelper.createIngredients(new Ing_db(getIng_id_add(), "30", 431, 4, 29, 38, "parmezan", parmesan, 0, 0));//25
        databaseHelper.createIngredients(new Ing_db(getIng_id_add(), "200", 86, 20, 0, 2, "ziemniak", potato, 1, 0));//26
        databaseHelper.createIngredients(new Ing_db(getIng_id_add(), "60", 36, 6, 1, 3, "pietruszka", parsley, 1, 0));//27
        databaseHelper.createIngredients(new Ing_db(getIng_id_add(), "60", 43, 10, 0, 2, "burak", beetroot, 1, 0));//28
        databaseHelper.createIngredients(new Ing_db(getIng_id_add(), "18", 40, 9, 0, 2, "papryka", paprika, 1, 0));//29
        databaseHelper.createIngredients(new Ing_db(getIng_id_add(), "100", 152, 29, 1, 6, "czosnek", garlic, 1, 0));//30
        databaseHelper.createIngredients(new Ing_db(getIng_id_add(), "...", 131, 21, 6, 3, "rozmaryn", rosemary, 1, 0));//31
        databaseHelper.createIngredients(new Ing_db(getIng_id_add(), "...", 0, 0, 0, 0, "woda", water, 1, 0));//32
        databaseHelper.createIngredients(new Ing_db(getIng_id_add(), "...", 364, 76, 1, 10, "mąka", flour, 1, 0));//33
        databaseHelper.createIngredients(new Ing_db(getIng_id_add(), "...", 386, 100, 0, 0, "cukier", sugar, 1, 0));//34
        databaseHelper.createIngredients(new Ing_db(getIng_id_add(), "...", 325, 41, 8, 40, "drożdże", yeast, 1, 0));//35
        databaseHelper.createIngredients(new Ing_db(getIng_id_add(), "...", 306, 64, 10, 11, "oregano", oregano, 1, 0));//36
        databaseHelper.createIngredients(new Ing_db(getIng_id_add(), "200", 82, 19, 1, 4, "koncentrat pomidorowy", tomatocnt, 1, 0));//37
        databaseHelper.createIngredients(new Ing_db(getIng_id_add(), "...", 158, 31, 1, 6, "makaron lasagne", lasagne_pasta, 1, 0));//38
        databaseHelper.createIngredients(new Ing_db(getIng_id_add(), "21", 42, 5, 1, 3, "mleko", milk, 0, 0));//39
        databaseHelper.createIngredients(new Ing_db(getIng_id_add(), "14", 164, 0, 4, 31, "pierś z kurczaka", chicken, 0, 0));//40
        databaseHelper.createIngredients(new Ing_db(getIng_id_add(), "100", 21, 1, 0, 0, "ocet jabłkowy", vinegar, 1, 0));//41
        databaseHelper.createIngredients(new Ing_db(getIng_id_add(), "...", 572, 23, 50, 18, "sezam", sesame, 1, 0));//42
        databaseHelper.createIngredients(new Ing_db(getIng_id_add(), "...", 395, 72, 5, 13, "bułka tarta", smh_rolls, 1, 0));//43
        databaseHelper.createIngredients(new Ing_db(getIng_id_add(), "14", 170, 0, 12, 17, "ćwiartka z kurczaka", chickenqrt, 0, 0));//44
        databaseHelper.createIngredients(new Ing_db(getIng_id_add(), "...", 0, 0, 0, 0, "zioła prowansalskie", herbesdeprovence, 1, 0));//45
        databaseHelper.createIngredients(new Ing_db(getIng_id_add(), "100", 680, 1, 75, 1, "majonez", majonese, 0, 0));//46
        databaseHelper.createIngredients(new Ing_db(getIng_id_add(), "21", 22, 1, 0, 3, "szpinak", spinach, 1, 0));//47
        databaseHelper.createIngredients(new Ing_db(getIng_id_add(), "30", 493, 0, 44, 23, "pepperoni", pepproni, 0, 0));//48
        databaseHelper.createIngredients(new Ing_db(getIng_id_add(), "...", 354, 65, 10, 8, "kurkuma", kurkuma, 1, 0));//49
        databaseHelper.createIngredients(new Ing_db(getIng_id_add(), "...", 375, 44, 22, 18, "kmin rzymski", kminek, 1, 0));//50
        databaseHelper.createIngredients(new Ing_db(getIng_id_add(), "7", 12, 1, 0, 2, "bulion", bulion, 1, 0));//51
    }

    void add_recipes_to_database() {
        ArrayList<Ing_db> ing_dbs = (ArrayList<Ing_db>) databaseHelper.getAllIngredients();
        //buritto
        byte[] img = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.buritto));
        String how_to_cook = "mięso mielone doprawić przyprawami, następnie podsmażyć na oleju aż zmieni kolor, potem dodać pokrojoną w kostkę cebulę, paprykę oraz pomidory," +
                "w międzyczasie ugotować ryż, mięso z warzywami smażymy do momentu zagęszczenia się dania, następnie dodajemy guacamole oraz starkowany ser i mieszamy," +
                "następnie dodajemy ryż oraz mieszamy do uzyskania względnie jednolitego wyglądu";
        Rcp_db rcp = new Rcp_db(getRcp_id_add(), 0, 0, 0, 0, "buritto", how_to_cook, img, 0, 0, "obiad");

        ArrayList<Ing_in_dish> ingInDish = new ArrayList<>();

        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 0, 1, 50));
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 0, 2, 200));
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 0, 3, 50));
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 0, 4, 300));
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 0, 5, 100));
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 0, 22, 100));
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 0, 15, 10));
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 0, 8, 3));
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 0, 7, 5));

        rcp.setRcp_calories(calc_kcal(ingInDish, ing_dbs));
        rcp.setRcp_carbs(calc_carbs(ingInDish, ing_dbs));
        rcp.setRcp_proteins(calc_prot(ingInDish, ing_dbs));
        rcp.setRcp_fat(calc_fat(ingInDish, ing_dbs));

        databaseHelper.createRecipe(rcp);
        for (Ing_in_dish in : ingInDish) databaseHelper.createIng_in_dish(in);
        //szakszuka
        img = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.szakszuka));
        how_to_cook = "rozpuścić masło na patelni, dodać posiekane drobno pomidory, smażyć aż staną się gęste, następnie wbić na wierzch jajka i przyprawić solą i pieprzem," +
                "gotować całość pod przykrywką aż jajka się zetną";
        rcp = new Rcp_db(getRcp_id_add(), 0, 0, 0, 0, "szakszuka", how_to_cook, img, 0, 0, "kolacja/śniadanie");
        ingInDish.clear();
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 1, 5, 300));
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 1, 0, 200));
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 1, 7, 3));
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 1, 8, 3));
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 1, 6, 50));

        rcp.setRcp_calories(calc_kcal(ingInDish, ing_dbs));
        rcp.setRcp_carbs(calc_carbs(ingInDish, ing_dbs));
        rcp.setRcp_proteins(calc_prot(ingInDish, ing_dbs));
        rcp.setRcp_fat(calc_fat(ingInDish, ing_dbs));

        databaseHelper.createRecipe(rcp);
        for (Ing_in_dish in : ingInDish) databaseHelper.createIng_in_dish(in);
        //yakisoba
        img = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.yakisoba));
        how_to_cook = "ugotować makaron, na rozgrzaną patelnie wlać olej, zacząć smażyć posiekaną drobno kapustę i marchewkę, dodać sosu sojowego oraz ztarkowany imbir (może być suszony)"
                + " w trakcie smażenia dodać szczypior i również podsmażyć , w momencie dostatecznego podsmażenia warzyw dodajemy makaron i smażymy do momentu uzyskania preferowanego smaku"
                + "(w wersjii niewegańskiej możemy dodać smażonego boczku)";
        rcp = new Rcp_db(getRcp_id_add(), 0, 0, 0, 0, "yakisoba", how_to_cook, img, 1, 0, "obiad");
        ingInDish.clear();
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 2, 9, 100));
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 2, 10, 350));
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 2, 11, 150));
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 2, 12, 20));
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 2, 13, 5));
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 2, 14, 30));
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 2, 15, 10));

        rcp.setRcp_calories(calc_kcal(ingInDish, ing_dbs));
        rcp.setRcp_carbs(calc_carbs(ingInDish, ing_dbs));
        rcp.setRcp_proteins(calc_prot(ingInDish, ing_dbs));
        rcp.setRcp_fat(calc_fat(ingInDish, ing_dbs));

        databaseHelper.createRecipe(rcp);
        for (Ing_in_dish in : ingInDish) databaseHelper.createIng_in_dish(in);
        //włoski garnek
        img = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.tomato_pasta));
        how_to_cook = "ugotować makaron wedle preferencji, na rozgrzaną patelnię wlać oliwę oraz dodać posiekane w kostkę pomidory, smażyć aż zaczną być gęste,"
                + "dodać makaron oraz posiekaną mozarellę, smażyć mieszając aż do uzyskania jednolitej masy, na koniec dodać świeżą bazylię";
        rcp = new Rcp_db(getRcp_id_add(), 0, 0, 0, 0, "włoski garnek", how_to_cook, img, 1, 0, "obiad");
        ingInDish.clear();
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 3, 10, 300));
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 3, 5, 200));
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 3, 17, 15));
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 3, 18, 20));
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 3, 16, 60));

        rcp.setRcp_calories(calc_kcal(ingInDish, ing_dbs));
        rcp.setRcp_carbs(calc_carbs(ingInDish, ing_dbs));
        rcp.setRcp_proteins(calc_prot(ingInDish, ing_dbs));
        rcp.setRcp_fat(calc_fat(ingInDish, ing_dbs));

        databaseHelper.createRecipe(rcp);
        for (Ing_in_dish in : ingInDish) databaseHelper.createIng_in_dish(in);
        //jajkochlebek
        img = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.jajkochlebek));
        how_to_cook = "kromkę chleba dokładnie obtoczyć w roztrzepanym jajku z dwoch stron, na rozgrzanej patelni roztopić masło następnie obustronnnie podsmażyć kromkę chleba";
        rcp = new Rcp_db(getRcp_id_add(), 0, 0, 0, 0, "jajkochlebek (tosty francuskie)", how_to_cook, img, 0, 0, "kolacja/śniadanie");
        ingInDish.clear();
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 4, 19, 25));
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 4, 0, 50));
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 4, 6, 10));

        rcp.setRcp_calories(calc_kcal(ingInDish, ing_dbs));
        rcp.setRcp_carbs(calc_carbs(ingInDish, ing_dbs));
        rcp.setRcp_proteins(calc_prot(ingInDish, ing_dbs));
        rcp.setRcp_fat(calc_fat(ingInDish, ing_dbs));

        databaseHelper.createRecipe(rcp);
        for (Ing_in_dish in : ingInDish) databaseHelper.createIng_in_dish(in);
        //quesadilla
        img = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.quesadilla));
        how_to_cook = "na rozgrzanej SZEROKIEJ patelni roztopić masło, podsmażyć posiekane chorizo, wylać dwa roztrzepane jajka i posolić, dodać potarkowany ser oraz posiekaną paprykę chilli," +
                "jak jajka się zetną całość przykryć tortillą i docisnąć, złożyć całość na pół tortillą do zewnątrz i podsmażyć z obu stron";
        rcp = new Rcp_db(getRcp_id_add(), 0, 0, 0, 0, "quesadilla", how_to_cook, img, 0, 0, "przekąska");
        ingInDish.clear();
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 5, 20, 50));
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 5, 21, 20));
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 5, 0, 100));
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 5, 3, 100));
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 5, 6, 20));
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 5, 8, 5));
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 5, 22, 20));

        rcp.setRcp_calories(calc_kcal(ingInDish, ing_dbs));
        rcp.setRcp_carbs(calc_carbs(ingInDish, ing_dbs));
        rcp.setRcp_proteins(calc_prot(ingInDish, ing_dbs));
        rcp.setRcp_fat(calc_fat(ingInDish, ing_dbs));

        databaseHelper.createRecipe(rcp);
        for (Ing_in_dish in : ingInDish) databaseHelper.createIng_in_dish(in);
        //Carbonara
        img = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.carbonara));
        how_to_cook = "Makaron ugotować w osolonej wodzie, według przepisu na opakowaniu. Odcedzić." +
                "Boczek pokroić w drobną kostkę. Podsmażyć na patelni na niskiej mocy palnika, aż się lekko zarumieni.Śmietankę, jajka, ser, szczyptę soli i dość dużą ilość" +
                " pieprzu przełożyć do miski (najlepiej wysokiej i wąskiej) i zmiksować blenderem.Ugotowany makaron dodać do gorącego boczku. Przesmażyć, mieszając przez ok. 1 minutę." +
                " Patelnię zdjąć z palnika, dodać masę jajeczną i wymieszać. Można dodać też posiekaną natkę pietruszki. " +
                "Mieszać przez chwilę, aż wszystkie składniki dobrze się połączą, a sos lekko zgęstnieje. (Lepiej nie mieszać na palniku, żeby nie zrobiła się jajecznica).";
        rcp = new Rcp_db(getRcp_id_add(), 0, 0, 0, 0, "Carbonara", how_to_cook, img, 0, 0, "obiad");
        ingInDish.clear();
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 6, 10, 200));
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 6, 0, 130));
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 6, 23, 100));//śmietanka
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 6, 24, 100));//boczek
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 6, 25, 30));//parmezan
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 6, 8, 5));//sól
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 6, 7, 5));//pieprz

        rcp.setRcp_calories(calc_kcal(ingInDish, ing_dbs));
        rcp.setRcp_carbs(calc_carbs(ingInDish, ing_dbs));
        rcp.setRcp_proteins(calc_prot(ingInDish, ing_dbs));
        rcp.setRcp_fat(calc_fat(ingInDish, ing_dbs));

        databaseHelper.createRecipe(rcp);
        for (Ing_in_dish in : ingInDish) databaseHelper.createIng_in_dish(in);
        //pieczone warzywa
        img = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.beaked_wegetables));
        how_to_cook = "Ziemniaki, marchewki, pietruszki i buraki obrać, umyć i osuszyć. Ziemniaki pokroić na ćwiartki. Buraki, jeśli są małe pokroić na ćwiartki." +
                " (Większe na pół i każdą połowę na 3- 4 części).Marchewki i pietruszki przekroić wzdłuż na 4 części (na ćwiartki)," +
                " a następnie każdą część w poprzek na pół (żeby nie były długie).Paprykę pokroić wzdłuż na ćwiartki, usunąć pestki, białe błonki i odciąć ogonek." +
                " Umyć i osuszyć.Cebulę obrać i pokroić na ćwiartki.Ząbki czosnku obrać.Wszystkie warzywa przełożyć do dużej miski. Dodać oliwę i przyprawy." +
                " (Z rozmarynu oberwać igielki). Potrząsając miską i zataczając koliste ruchy, obtoczyć warzywa w oliwie z przyprawami." +
                "Warzywa wyłożyć na dużą blachę z piekarnika wyłożoną papierem do pieczenia tak, aby na siebie nie nachodziły." +
                " Piec w nagrzanym piekarniku ok. 30 minut w temperaturze 190°C z termoobiegiem.";
        rcp = new Rcp_db(getRcp_id_add(), 0, 0, 0, 0, "pieczone warzywa", how_to_cook, img, 1, 0, "obiad");
        ingInDish.clear();
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 7, 26, 500));//zmieniaki
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 7, 9, 120));//marchewki
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 7, 27, 140));//pietruszki
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 7, 28, 200));//buraki
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 7, 29, 240));//papryka
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 7, 1, 120));//cebula
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 7, 30, 5));//czosnek
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 7, 31, 3));//rozmaryn
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 7, 17, 6));//oliwa
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 7, 7, 4));//sól
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 7, 8, 4));//pieprz


        rcp.setRcp_calories(calc_kcal(ingInDish, ing_dbs));
        rcp.setRcp_carbs(calc_carbs(ingInDish, ing_dbs));
        rcp.setRcp_proteins(calc_prot(ingInDish, ing_dbs));
        rcp.setRcp_fat(calc_fat(ingInDish, ing_dbs));

        databaseHelper.createRecipe(rcp);
        for (Ing_in_dish in : ingInDish) databaseHelper.createIng_in_dish(in);
        //pizza (Margarita)
        img = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.pizza));
        how_to_cook = "Drożdże wymieszać w szklance z ciepłą wodą, do miski wrzucić mąkę sół cukier oraz wlać drożdże wymieszane z wodą, wszystko razem wymieszać" +
                "następnie dodać oliwy i ugniatać około 15 min. Ciasto umieścić z powrotem w misce i wysmarować je oraz miskę oliwą" +
                ", następnie miskę z ciastem odstawić w ciepłe miejsce na całą dobę." +
                "Następnego dnia ciasto rozwałkować dopasowując do blachy, wysmarować koncentratem pomidorowym wymieszanym z bazylią oraz oregano (w proporcjach 1:4)" +
                ". Następnie na wierzch umieścić posiekaną mozzarellę. Piec w piekraniku w 250 stopniach około 10 min";
        rcp = new Rcp_db(getRcp_id_add(), 0, 0, 0, 0, "pizza (margarita)", how_to_cook, img, 1, 0, "obiad");
        ingInDish.clear();
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 8, 33, 250));//mąka
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 8, 32, 150));//woda
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 8, 8, 3));//sól
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 8, 17, 10));//oliwa
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 8, 16, 70));//mozzarella
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 8, 34, 3));//cukier
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 8, 36, 4));//oragano
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 8, 18, 1));//bazylia
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 8, 37, 40));//koncentrat pomidorowy
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 8, 35, 25));//drożdże

        rcp.setRcp_calories(calc_kcal(ingInDish, ing_dbs));
        rcp.setRcp_carbs(calc_carbs(ingInDish, ing_dbs));
        rcp.setRcp_proteins(calc_prot(ingInDish, ing_dbs));
        rcp.setRcp_fat(calc_fat(ingInDish, ing_dbs));

        databaseHelper.createRecipe(rcp);
        for (Ing_in_dish in : ingInDish) databaseHelper.createIng_in_dish(in);
        //Lasagne
        img = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.lasagne));
        how_to_cook = "Sos beszamelowy: Na patelni rozpuszczamy 4 łyżki masła, zdejmujemy z ognia i dosypujemy 4 łyżki mąki." +
                " Mieszamy aż powstanie gładka masa. Następnie dolewamy szklankę mleka, z powrotem wstawiamy na palnik i podgrzewamy do uzyskania " +
                "jednolitej gęstej masy - ciągle mieszając.\n" +
                "Lazania: Na patelni podsmażamy mięso, dodajemy posiekane w kosteczkę cebule i czosnek. " +
                "Gdy mięso będzie już rozdrobnione i podsmażone dodajemy posiekane w kostkę pomidory i koncentrat pomidorowy, przyprawiamy do smaku solą," +
                " pieprzem, oregano i bazylią i chwilę dusimy. W osolonej wodzie z dodatkiem oliwy z oliwek podgotowujemy makaron ok 2-3 min, odcedzamy." +
                " Naczynie żaroodporne smarujemy oliwą i układamy pierwszą warstwę makaronu. Makaron pokrywamy cienką warstwą beszamelu a na beszamelu " +
                "układamy mięso. Przykrywamy makaronem i powtarzamy czynność. Ostatnia warstwa powinna się składać z beszamelu i mięsa. " +
                "Całość posypujemy startym serem. Pieczemy w piekarniku rozgrzanym do 180 stopni ok 20-30 minut. " +
                "Potem wyłączamy piekarnik i lazanię pozostawiamy w nim jeszcze na ok. 20 min.";
        rcp = new Rcp_db(getRcp_id_add(), 0, 0, 0, 0, "Lasagne", how_to_cook, img, 0, 0, "obiad");
        ingInDish.clear();
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 9, 4, 500));//mięso
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 9, 5, 400));//pomidory
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 9, 37, 200));//koncentrat
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 9, 1, 150));//cebula
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 9, 30, 40));//czosnek
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 9, 38, 500));//makaron
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 9, 3, 300));//ser
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 9, 8, 3));//sól
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 9, 7, 3));//pieprz
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 9, 18, 2));//bazylia
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 9, 36, 4));//oregano
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 9, 17, 8));//oliwa
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 9, 39, 250));//mleko
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 9, 33, 150));//mąka
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 9, 6, 140));//masło

        rcp.setRcp_calories(calc_kcal(ingInDish, ing_dbs));
        rcp.setRcp_carbs(calc_carbs(ingInDish, ing_dbs));
        rcp.setRcp_proteins(calc_prot(ingInDish, ing_dbs));
        rcp.setRcp_fat(calc_fat(ingInDish, ing_dbs));

        databaseHelper.createRecipe(rcp);
        for (Ing_in_dish in : ingInDish) databaseHelper.createIng_in_dish(in);
        //podpłomyk
        img = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.podplomyk));
        how_to_cook = "Mieszamy mąkę z wodą oraz solą do uzyskania jednolitej masy, następnie ugniatamy oraz rozwałkowujemy ciasto aby miało grubość około pół cm." +
                "Przygotowujemy składniki w zależności od preferencji, następnie umieszczamy ciasto na suchej rozgrzanej patelni i pieczemy około 5 min." +
                "Po umływie czasu przewracamy ciasto na drugą stronę, smarujemy koncentratem i umieszczamy na wierzchu wszystkie składniki, pieczemy pod przykrywką" +
                "około 6 min";
        rcp = new Rcp_db(getRcp_id_add(), 0, 0, 0, 0, "Podpłomyk", how_to_cook, img, 1, 0, "kolacja/śniadanie");
        ingInDish.clear();
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 10, 33, 150));//mąka
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 10, 8, 3));//sól
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 10, 32, 40));//woda
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 10, 37, 15));//koncentrat
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 10, 3, 20));//ser

        rcp.setRcp_calories(calc_kcal(ingInDish, ing_dbs));
        rcp.setRcp_carbs(calc_carbs(ingInDish, ing_dbs));
        rcp.setRcp_proteins(calc_prot(ingInDish, ing_dbs));
        rcp.setRcp_fat(calc_fat(ingInDish, ing_dbs));

        databaseHelper.createRecipe(rcp);
        for (Ing_in_dish in : ingInDish) databaseHelper.createIng_in_dish(in);
        //kurczak teriyaki
        img = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.teriyaki));
        how_to_cook = "Wymieszać sos sojowy z octem, oraz imbirem (w proszku), następnie dodać pokrojoną na kawałki pierś z kurczaka. Wymieszać wszystko w misce, przykryć folią i odstawić na około godzinę do lodówki " +
                "po upłynięciu godziny podgrzać olej na patelni, następnie wyjęte z marynaty kawałki kurczaka podsmażyć, dodać cukru i poczekać aż się skarmelizuje. Następnie dodać sezamu i wszystko razem dokłądnie " +
                "wymieszać. Najlepiej podawać z bialym ryżem.(lub onigiri)";
        rcp = new Rcp_db(getRcp_id_add(), 0, 0, 0, 0, "kurczak teriyaki", how_to_cook, img, 0, 0, "obiad");
        ingInDish.clear();
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 11, 40, 500));//pierś z kurczaka
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 11, 41, 6));//ocet
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 11, 12, 40));//sos sojowy
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 11, 13, 5));//imbir
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 11, 34, 15));//cukier
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 11, 15, 8));//olej
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 11, 42, 8));//sezam

        rcp.setRcp_calories(calc_kcal(ingInDish, ing_dbs));
        rcp.setRcp_carbs(calc_carbs(ingInDish, ing_dbs));
        rcp.setRcp_proteins(calc_prot(ingInDish, ing_dbs));
        rcp.setRcp_fat(calc_fat(ingInDish, ing_dbs));

        databaseHelper.createRecipe(rcp);
        for (Ing_in_dish in : ingInDish) databaseHelper.createIng_in_dish(in);
        //krążki cebulowe
        img = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.onion_rings));
        how_to_cook = "Pokrój cebulę w plastry o grubości 0,5 cm i porozdzielaj je na krążki. Wymieszaj w misce mąkę z solą, pieprzem i bułką tartą (można również dodac chilli w proszku), obtocz w takiej " +
                "panierce krążki cebuli. W drugim naczyniu wymieszaj mleko z jajkiem. Następnie maczaj krążki raz w misce z mlekiem ,a potem w panierce. Czynność powtórz dwa razy. " +
                "Na koniec podsmaż krążki na głębokim oleju.";
        rcp = new Rcp_db(getRcp_id_add(), 0, 0, 0, 0, "krążki cebulowe", how_to_cook, img, 0, 0, "przekąska");
        ingInDish.clear();
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 12, 1, 300));//cebula
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 12, 33, 200));//mąka
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 12, 39, 250));//mleko
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 12, 8, 5));//sól
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 12, 7, 5));//pieprz
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 12, 15, 500));//olej
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 12, 0, 100));//jajko
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 12, 43, 100));//bułka tarta

        rcp.setRcp_calories(calc_kcal(ingInDish, ing_dbs));
        rcp.setRcp_carbs(calc_carbs(ingInDish, ing_dbs));
        rcp.setRcp_proteins(calc_prot(ingInDish, ing_dbs));
        rcp.setRcp_fat(calc_fat(ingInDish, ing_dbs));

        databaseHelper.createRecipe(rcp);
        for (Ing_in_dish in : ingInDish) databaseHelper.createIng_in_dish(in);
        //podudzia z kurczaka duszone z kapustą
        img = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.chicken_cabbage));
        how_to_cook = "Przyprawiamy ćwiartki z kurczaka ,następnie smażymy je do lekkiego zarumienienia. " +
                "Na tą samą patelnię wrzucamy pokrojoną cebulę i czosnek, jak się lekko podsmaży dodajemy posiekane pomidory i przyprawiamy solą , pieprzem oraz ziołami prowansalskimi." +
                " Kapustę kroimy w kostkę, wykładamy nią dół garnka , następnie wrzucamy ćwiartki i zalewamy sosem. Gotujemy aż do zmięknięcia kapusty.";
        rcp = new Rcp_db(getRcp_id_add(), 0, 0, 0, 0, "podudzia z kurczaka duszone z kapustą", how_to_cook, img, 0, 0, "obiad");
        ingInDish.clear();
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 13, 44, 1000));//ćwiartki z kkurczaka
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 13, 11, 400));//kapusta
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 13, 5, 300));//pomidory
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 13, 8, 5));//sól
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 13, 7, 8));//pieprz
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 13, 45, 6));//zioła prowansalskie
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 13, 0, 150));//cebula
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 13, 30, 30));//czosnek
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 13, 15, 8));//olej

        rcp.setRcp_calories(calc_kcal(ingInDish, ing_dbs));
        rcp.setRcp_carbs(calc_carbs(ingInDish, ing_dbs));
        rcp.setRcp_proteins(calc_prot(ingInDish, ing_dbs));
        rcp.setRcp_fat(calc_fat(ingInDish, ing_dbs));

        databaseHelper.createRecipe(rcp);
        for (Ing_in_dish in : ingInDish) databaseHelper.createIng_in_dish(in);
        //piecozne piersi z kurczaka
        img = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.beaked_chicken));
        how_to_cook = "Piersi z kurczaka kroimy na grubsze plastry (tak jak na schabowe), przyprawiamy solą i pieprzem." +
                " Na blasze wykładamy folię srebrną, a na niej jedno przy drugim krążki pokrojonej cebuli. Na to układamy piersi z kurczaka, a na wierzch kładziemy plastry sera." +
                " Wszystko smarujemy z góry majonezem. Wstawiamy do piekarnika rozgrzanego do 180 stopni na około 20 minut i gotowe.";
        rcp = new Rcp_db(getRcp_id_add(), 0, 0, 0, 0, "piersi z kurczaka pieczone z cebulą i serem", how_to_cook, img, 0, 0, "obiad");
        ingInDish.clear();
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 14, 40, 300));//piersi z kurczaka
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 14, 11, 100));//ser
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 14, 46, 60));//majonez
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 14, 8, 5));//sól
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 14, 7, 8));//pieprz
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 14, 0, 150));//cebula

        rcp.setRcp_calories(calc_kcal(ingInDish, ing_dbs));
        rcp.setRcp_carbs(calc_carbs(ingInDish, ing_dbs));
        rcp.setRcp_proteins(calc_prot(ingInDish, ing_dbs));
        rcp.setRcp_fat(calc_fat(ingInDish, ing_dbs));

        databaseHelper.createRecipe(rcp);
        for (Ing_in_dish in : ingInDish) databaseHelper.createIng_in_dish(in);
        //smażony szpinak z makaronem
        img = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.spinach_pasta));
        how_to_cook = "Do osolonej wody dodajemy łyżkę oliwy i wrzucamy makaron. W międzyczasie rozgrzewamy dwie łyżki oliwy na patelni, wrzucamy szpinak. " +
                "Następnie dodajemy posiekany drobno czosnek i podsmażamy wszystko razem.Po kilku minutach smażenia dodajemy makaron i potarkowany parmezan. Wszystko razem podsmażamy jeszcze 1-2 min.";
        rcp = new Rcp_db(getRcp_id_add(), 0, 0, 0, 0, "szpinak z makaronem", how_to_cook, img, 1, 0, "obiad");
        ingInDish.clear();
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 15, 47, 200));//szpinak
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 15, 25, 16));//parmezan
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 15, 30, 12));//czosnek
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 15, 8, 5));//sól
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 15, 17, 16));//oliwa
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 15, 10, 300));//makaron

        rcp.setRcp_calories(calc_kcal(ingInDish, ing_dbs));
        rcp.setRcp_carbs(calc_carbs(ingInDish, ing_dbs));
        rcp.setRcp_proteins(calc_prot(ingInDish, ing_dbs));
        rcp.setRcp_fat(calc_fat(ingInDish, ing_dbs));

        databaseHelper.createRecipe(rcp);
        for (Ing_in_dish in : ingInDish) databaseHelper.createIng_in_dish(in);
        //leczo
        img = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.leczo));
        how_to_cook = "Paprykę pokroić w średniej wielkości kawałki. Cebulę oczyścić z łupin, pokroić na kawałki podobnej wielkości jak papryka.Kiełbasę pokroić w plasterki lub półplasterki, średniej grubości." +
                " Na patelni rozgrzać 1 łyżkę oleju, wrzucić kiełbasę i obsmażyć do zrumienienia (chyba, że kiełbasa jest tłusta, wtedy nie używać dodatkowego tłuszczu)." +
                "W garnku rozgrzać 2-3 łyżki oleju, na małym ogniu, wrzucić pokrojoną cebulę oraz przyprawy, zamieszać, przykryć i na małym ogniu dusić kilka minut." +
                " Następnie do cebuli dodać pokrojoną paprykę i chilli, obsmażoną kiełbasę i pomidory, dokładnie zamieszać, przykryć i dusić na małym ogniu ok. 15 - 20 min. " +
                "Od czasu do czasu zamieszać.Leczo jest gotowe kiedy papryka jest miękka. (podawać na gorąco z pieczywem)";
        rcp = new Rcp_db(getRcp_id_add(), 0, 0, 0, 0, "leczo na ostro", how_to_cook, img, 0, 0, "obiad");
        ingInDish.clear();
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 16, 29, 250));//papryka
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 16, 0, 200));//cebula
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 16, 22, 100));//chilli
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 16, 8, 5));//sól
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 16, 7, 8));//pieprz
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 16, 15, 8));//olej
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 16, 5, 400));//pomidory
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 16, 21, 400));//chorizo

        rcp.setRcp_calories(calc_kcal(ingInDish, ing_dbs));
        rcp.setRcp_carbs(calc_carbs(ingInDish, ing_dbs));
        rcp.setRcp_proteins(calc_prot(ingInDish, ing_dbs));
        rcp.setRcp_fat(calc_fat(ingInDish, ing_dbs));

        databaseHelper.createRecipe(rcp);
        for (Ing_in_dish in : ingInDish) databaseHelper.createIng_in_dish(in);
        //racuchy
        img = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.racuchy));
        how_to_cook = "Drożdże + 3 łyżki mąki + 2 łyżeczki cukru + mleko, wymieszać dokładnie w dużej misce, przykryć ściereczką, odstawić do wyrośnięcia na 10 min. " +
                "Do wyrośniętego zaczynu wsypać pozostałą mąkę, cukier i jajko całość wymieszać. Następnie wlać rozpuszczone masło i ponownie wymieszać." +
                " Przykryć ściereczką i odstawić w ciepłe miejsce na ok. 20 min. Na patelni rozgrzać olej. Łyżką zamoczoną w oleju nabieramy ciasto i kładziemy na olej (ciasto nie powinno być " +
                "lejące a raczej ciągnące), zmniejszamy ogień pod patelnią i przykrywamy, smażmy 2-3 min a następnie przekładamy na drugą stronę, smażymy kolejne 2-3 min. Usmażone racuchy odkładamy na chwilę," +
                " na ręcznik papierowy.Podajemy ciepłe, posypane cukrem pudrem lub z ulubioną konfiturą.";
        rcp = new Rcp_db(getRcp_id_add(), 0, 0, 0, 0, "racuchy", how_to_cook, img, 0, 0, "przekąska");
        ingInDish.clear();
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 17, 33, 250));//mąka
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 17, 35, 20));//drożdże
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 17, 0, 80));//jajko
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 17, 34, 70));//cukier
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 17, 39, 120));//mleko
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 17, 6, 20));//masło

        rcp.setRcp_calories(calc_kcal(ingInDish, ing_dbs));
        rcp.setRcp_carbs(calc_carbs(ingInDish, ing_dbs));
        rcp.setRcp_proteins(calc_prot(ingInDish, ing_dbs));
        rcp.setRcp_fat(calc_fat(ingInDish, ing_dbs));

        databaseHelper.createRecipe(rcp);
        for (Ing_in_dish in : ingInDish) databaseHelper.createIng_in_dish(in);
        //calzone
        img = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.calzone));
        how_to_cook = "Drożdże wymieszać w szklance z ciepłą wodą, do miski wrzucić mąkę sół cukier oraz wlać drożdże wymieszane z wodą, wszystko razem wymieszać " +
                "następnie dodać oliwy i ugniatać około 15 min. Ciasto umieścić z powrotem w misce i wysmarować je oraz miskę oliwą" +
                ", następnie miskę z ciastem odstawić w ciepłe miejsce na całą dobę." +
                "Następnego dnia ciasto rozwałkować posypując obustonnie mąką, wysmarować koncentratem pomidorowym wymieszanym z bazylią oraz oregano (w proporcjach 1:4)" +
                ". Następnie na wierzch umieścić posiekaną mozzarellę, peperoni oraz wasze ulubione składniki. Trzymając za jeden z brzegów ostrożnie złożyć pizzę na pół (farszem do środka)." +
                "W następnej kolejności ścisnąć brzegi razem formując coś na kształ pieroga. W centralnej części wykonać 3 nacięcia nożem, posmarować z wierzchu oliwą oraz posypać solą." +
                " Piec w piekraniku w 250 stopniach około 10 min";
        rcp = new Rcp_db(getRcp_id_add(), 0, 0, 0, 0, "Calzone", how_to_cook, img, 1, 0, "obiad");
        ingInDish.clear();
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 18, 33, 250));//mąka
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 18, 32, 150));//woda
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 18, 8, 3));//sól
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 18, 17, 10));//oliwa
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 18, 16, 70));//mozzarella
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 18, 34, 3));//cukier
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 18, 36, 4));//oragano
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 18, 18, 1));//bazylia
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 18, 37, 40));//koncentrat pomidorowy
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 18, 35, 25));//drożdże
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 18, 48, 50));//peperoni

        rcp.setRcp_calories(calc_kcal(ingInDish, ing_dbs));
        rcp.setRcp_carbs(calc_carbs(ingInDish, ing_dbs));
        rcp.setRcp_proteins(calc_prot(ingInDish, ing_dbs));
        rcp.setRcp_fat(calc_fat(ingInDish, ing_dbs));

        databaseHelper.createRecipe(rcp);
        for (Ing_in_dish in : ingInDish) databaseHelper.createIng_in_dish(in);
        //jambalaya
        img = getBitmapAsByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.jambalaya));
        how_to_cook = "Pokrój chorizo, pierś z kurczaka, paprykę, cebulę w piórka oraz pomidora. Na rozgrzany tłuszcz dodaj paprykę i cebulę, smaż 5 min. " +
                "Dodaj chorizo i kurczaka doprawionego solą i pieprzem. Podsmażaj 3-4 min. Następnie dodaj garść surowego ryżu oraz przyprawy, a także pomidora. " +
                "Całość wymieszaj i dodaj bulion – gotuj pod przykryciem 15–20 min. od czasu do czasu mieszając. Po ugotowaniu dopraw solą i pieprzem oraz posyp natką pietruszki, " +
                "dodaj łyżkę koncentratu pomidorowego.";
        rcp = new Rcp_db(getRcp_id_add(), 0, 0, 0, 0, "Jambalaya", how_to_cook, img, 1, 0, "obiad");
        ingInDish.clear();
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 19, 29, 120));//papryka
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 19, 0, 50));//cebula
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 19, 5, 60));//pomidor
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 19, 8, 3));//sól
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 19, 7, 3));//pieprz
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 19, 2, 70));//ryż
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 19, 49, 4));//kurkuma
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 19, 50, 5));//kminek
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 19, 51, 500));//bulion
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 19, 27, 10));//pietruszka
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 19, 37, 30));//koncentrat
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 19, 40, 300));//pierś z kurczaka
        ingInDish.add(new Ing_in_dish(getIng_n_rcp_id_add(), 19, 21, 200));//chorizo

        rcp.setRcp_calories(calc_kcal(ingInDish, ing_dbs));
        rcp.setRcp_carbs(calc_carbs(ingInDish, ing_dbs));
        rcp.setRcp_proteins(calc_prot(ingInDish, ing_dbs));
        rcp.setRcp_fat(calc_fat(ingInDish, ing_dbs));

        databaseHelper.createRecipe(rcp);
        for (Ing_in_dish in : ingInDish) databaseHelper.createIng_in_dish(in);
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
        return outputStream.toByteArray();
    }

    int calc_kcal(ArrayList<Ing_in_dish> in, ArrayList<Ing_db> ingredient) {
        int pom = 0;
        for (Ing_in_dish amt : in) {
            pom += ingredient.get(amt.getIng_id()).getIng_calories() * (amt.getAmount() / 100);
        }
        return pom;
    }

    int calc_prot(ArrayList<Ing_in_dish> in, ArrayList<Ing_db> ingredient) {
        int pom = 0;
        for (Ing_in_dish amt : in) {
            pom += ingredient.get(amt.getIng_id()).getIng_proteins() * (amt.getAmount() / 100);
        }
        return pom;
    }

    int calc_fat(ArrayList<Ing_in_dish> in, ArrayList<Ing_db> ingredient) {
        int pom = 0;
        for (Ing_in_dish amt : in) {
            pom += ingredient.get(amt.getIng_id()).getIng_fat() * (amt.getAmount() / 100);
        }
        return pom;
    }

    int calc_carbs(ArrayList<Ing_in_dish> in, ArrayList<Ing_db> ingredient) {
        int pom = 0;
        for (Ing_in_dish amt : in) {
            pom += ingredient.get(amt.getIng_id()).getIng_carbs() * (amt.getAmount() / 100);
        }
        return pom;
    }

    int getRcp_id_add() {
        rcp_id++;
        return rcp_id - 1;
    }

    int getIng_id_add() {
        ing_id++;
        return ing_id - 1;
    }

    public int getIng_n_rcp_id_add() {
        ing_n_rcp_id++;
        return ing_n_rcp_id - 1;
    }
}
