package com.example.kitchendiary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import info.androidhive.sqlite.model.DatabaseHelper;
import info.androidhive.sqlite.model.Ing_db;
import info.androidhive.sqlite.model.Ing_in_dish;
import info.androidhive.sqlite.model.Ing_in_shoplist;
import info.androidhive.sqlite.model.Rcp_db;
import info.androidhive.sqlite.model.Shoplist_db;

public class Recipe_page extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    int id, db_id;
    ArrayList<Rcp_db> rcp_list;
    ArrayList<Ing_in_dish> in_dish;
    ArrayList<Ing_db> ingredients;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_page);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                id = extras.getInt("id");
                db_id = extras.getInt("db_id");
            }
        } else {
            id = (Integer) savedInstanceState.getSerializable("id");
            db_id = (Integer) savedInstanceState.getSerializable("db_id");
        }
        databaseHelper = DatabaseHelper.getInstance(this);

        rcp_list = (ArrayList<Rcp_db>) databaseHelper.getAllRecipes();
        ingredients = (ArrayList<Ing_db>) databaseHelper.getAllIngredients();
        in_dish = (ArrayList<Ing_in_dish>) databaseHelper.getAllIngredients_in_recipes();
        cook();
        set_everything();
        add_missing_ing_to_shoplist();
    }

    void add_missing_ing_to_shoplist() {
        final Button button = findViewById(R.id.rcp_page_add_to_shoplist);
        button.setOnClickListener(v -> {
            int shoplist_id = databaseHelper.get_shoplists_size() + 1;
            int ing_in_shoplist_id = databaseHelper.get_ing_in_shoplist_size() + 1;
            int amout_to_buy;

            databaseHelper.createShoplist(new Shoplist_db(shoplist_id, "Lista zakupów " + shoplist_id, new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date())));
            for (Ing_in_dish in : in_dish) {
                if (in.getRcp_id() == id) {
                    amout_to_buy = (int) (in.getAmount() - ingredients.get(in.getIng_id()).getIn_stock());
                    if (amout_to_buy > 0) {
                        databaseHelper.createIng_in_shoplist(new Ing_in_shoplist(ing_in_shoplist_id, shoplist_id, in.getIng_id(), amout_to_buy));
                        ing_in_shoplist_id++;
                    }
                }
            }
            startActivity(new Intent(Recipe_page.this, Shoplist.class));
        });
    }

    void cook() {
        final Button btn = findViewById(R.id.rcp_page_cook);
        btn.setOnClickListener(v -> {
            boolean can_cook = true;
            for (Ing_in_dish in : in_dish) {
                if (in.getRcp_id() == id) {
                    if (in.getAmount() - ingredients.get(in.getIng_id()).getIn_stock() > 0) {
                        can_cook = false;
                        break;
                    }
                }
            }
            if (can_cook) {
                ArrayList<Ing_in_dish> used = new ArrayList<>();
                for (Ing_in_dish in : in_dish) {
                    if (id == in.getRcp_id()) {
                        used.add(in);
                    }
                }
                int suc;
                for (Ing_in_dish in : used) {
                    databaseHelper.updateING(in.getIng_id() + 1, (int) (ingredients.get(in.getIng_id()).getIn_stock() - in.getAmount()),
                            ingredients.get(in.getIng_id()).getBuy_date(), ingredients.get(in.getIng_id()).getMade() + 1);
                }
                suc = databaseHelper.updateRCP(db_id, rcp_list.get(id).getMade() + 1);
                if (suc == 1) {
                    Toast.makeText(this, "Ugotowano", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Recipe_page.this, Recipes.class));
                } else {
                    Toast.makeText(this, "Nie udało się ugotować dania ):", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "nie posiadasz wystarczającej ilości składników aby przygotować to danie!", Toast.LENGTH_LONG).show();
            }
        });
    }

    void set_everything() {
        ImageView iv = findViewById(R.id.rcp_page_img);
        iv.setImageBitmap(arr_to_bit(rcp_list.get(id).getRcp_image()));

        TextView tv = findViewById(R.id.rcp_page_name);
        tv.setText(rcp_list.get(id).getDish_name());

        tv = findViewById(R.id.rcp_page_kcal);
        tv.setText(rcp_list.get(id).getRcp_calories() + "");

        tv = findViewById(R.id.rcp_page_carbs);
        tv.setText(rcp_list.get(id).getRcp_carbs() + "");

        tv = findViewById(R.id.rcp_page_fat);
        tv.setText(rcp_list.get(id).getRcp_fat() + "");

        tv = findViewById(R.id.rcp_page_prot);
        tv.setText(rcp_list.get(id).getRcp_proteins() + "");

        tv = findViewById(R.id.rcp_page_how_to);
        tv.setMovementMethod(new ScrollingMovementMethod());
        tv.setText(rcp_list.get(id).getHow_to_cook());

        tv = findViewById(R.id.rcp_page_occasion);
        tv.setText(rcp_list.get(id).getOccasion() + "");

        if (rcp_list.get(id).isVegan() == 1) {
            tv = findViewById(R.id.rcp_page_vegan);
            tv.setTextColor(Color.GREEN);
            tv.setText("wegańskie");
        } else {
            tv = findViewById(R.id.rcp_page_vegan);
            tv.setTextColor(Color.RED);
            tv.setText("niewegańskie");
        }
        set_ing_list();
    }

    void set_ing_list() {
        listView = findViewById(R.id.rcp_page_ing_in_dish);
        ArrayList<Ing_in_dish> used = new ArrayList<>();
        for (Ing_in_dish in : in_dish) {
            if (id == in.getRcp_id()) {
                used.add(in);
            }
        }
        List_help_adapter help_adapter = new List_help_adapter(this, R.layout.ing_in_dish_row, used, ingredients);
        listView.setAdapter(help_adapter);
    }

    Bitmap arr_to_bit(byte[] b) {
        return BitmapFactory.decodeByteArray(b, 0, b.length);
    }
}

class List_help_adapter extends ArrayAdapter<Ing_in_dish> {
    private Context context;
    private int LayoutResId;
    private List<Ing_in_dish> list;
    private List<Ing_db> ingredients;

    List_help_adapter(@NonNull Context context, int resource, @NonNull List<Ing_in_dish> objects, List<Ing_db> ingredients) {
        super(context, resource, objects);
        this.LayoutResId = resource;
        this.context = context;
        this.list = objects;
        this.ingredients = ingredients;
    }

    static class DataHolder {
        TextView name;
        TextView in_stock;
        TextView amount;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        List_help_adapter.DataHolder dataHolder;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(LayoutResId, parent, false);
            dataHolder = new DataHolder();
            dataHolder.name = convertView.findViewById(R.id.in_dish_row_name);
            dataHolder.in_stock = convertView.findViewById(R.id.in_dish_row_in_stock);
            dataHolder.amount = convertView.findViewById(R.id.in_dish_row_amount);

            convertView.setTag(dataHolder);
        } else {
            dataHolder = (List_help_adapter.DataHolder) convertView.getTag();
        }
        Ing_in_dish ingInDish = list.get(position);

        dataHolder.name.setText(ingredients.get(ingInDish.getIng_id()).getName());
        dataHolder.amount.setText(ingInDish.getAmount() + " g");

        if (ingInDish.getAmount() <= ingredients.get(ingInDish.getIng_id()).getIn_stock()) {
            dataHolder.in_stock.setTextColor(Color.GREEN);
            dataHolder.in_stock.setText(ingredients.get(ingInDish.getIng_id()).getIn_stock() + "");
        } else {
            dataHolder.in_stock.setTextColor(Color.RED);
            dataHolder.in_stock.setText(ingredients.get(ingInDish.getIng_id()).getIn_stock() + "");
        }

        return convertView;
    }
}

