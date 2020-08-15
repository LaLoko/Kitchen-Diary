package com.example.kitchendiary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import info.androidhive.sqlite.model.DatabaseHelper;
import info.androidhive.sqlite.model.Ing_db;
import info.androidhive.sqlite.model.Ing_in_shoplist;
import info.androidhive.sqlite.model.Shoplist_db;

public class Shoplist_page extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    int id, db_id;
    TextView name;
    TextView date;
    ArrayList<Shoplist_db> shoplists;
    ArrayList<Ing_in_shoplist> ingInShoplists;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoplist_page);
        try {
            Objects.requireNonNull(this.getSupportActionBar()).hide();
        } catch (NullPointerException ignored) {
        }
        databaseHelper = DatabaseHelper.getInstance(this);

        shoplists = (ArrayList<Shoplist_db>) databaseHelper.getAllShoplists();
        ingInShoplists = (ArrayList<Ing_in_shoplist>) databaseHelper.getAllIng_in_shoplist();

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                id = extras.getInt("id");
                db_id = extras.getInt("db_id");
            }
        } else {
            id = (int) savedInstanceState.getSerializable("id");
            db_id = (int) savedInstanceState.getSerializable("db_id");
        }
        filter_list();
        set_all();
        ret_btn();
    }

    void ret_btn() {
        Button btn = findViewById(R.id.shoplist_page_btn);
        btn.setOnClickListener(v -> {
            startActivity(new Intent(Shoplist_page.this, Shoplist.class));
        });
    }

    void set_all() {
        name = findViewById(R.id.shoplist_page_name);
        name.setText(shoplists.get(id).getName());
        date = findViewById(R.id.shoplist_page_date);
        date.setText(shoplists.get(id).getMod_date());
        set_ing_list();
    }

    void filter_list() {
        ArrayList<Ing_in_shoplist> pom = new ArrayList<>();
        for (Ing_in_shoplist in : ingInShoplists) {
            if (in.getShoplist_id() == db_id) {
                pom.add(in);
            }
        }
        ingInShoplists = pom;
    }

    void set_ing_list() {
        listView = findViewById(R.id.shoplist_page_list);
        List_help_adapter help_adapter = new List_help_adapter(this, R.layout.add_shoplist_ing_row, ingInShoplists, databaseHelper);
        listView.setAdapter(help_adapter);
    }

    class List_help_adapter extends ArrayAdapter<Ing_in_shoplist> {
        Context context;
        int LayoutResId;
        ArrayList<Ing_in_shoplist> list;
        ArrayList<Ing_db> ingredients;
        DatabaseHelper databaseHelper;

        List_help_adapter(@NonNull Context context, int resource, @NonNull ArrayList<Ing_in_shoplist> objects, DatabaseHelper databaseHelper) {
            super(context, resource, objects);
            this.LayoutResId = resource;
            this.context = context;
            this.list = objects;
            this.databaseHelper = databaseHelper;
            this.ingredients = (ArrayList<Ing_db>) databaseHelper.getAllIngredients();
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                convertView = inflater.inflate(LayoutResId, parent, false);
            }
            if (list.size() > 0) {
                Ing_in_shoplist ing_in_shoplist = list.get(position);
                TextView name = convertView.findViewById(R.id.add_shoplist_ing_row_name);
                TextView amount = convertView.findViewById(R.id.add_shoplist_ing_row_row_amount);
                ImageView del_btn = convertView.findViewById(R.id.add_shoplist_ing_row_delete);

                name.setText(ingredients.get(ing_in_shoplist.getIng_id()).getName());
                amount.setText(ing_in_shoplist.getAmount() + "");


                del_btn.setOnClickListener(v -> {
                    list.remove(position);
                    int pom_id = ing_in_shoplist.getId();
                    databaseHelper.deleteIng_in_shoplist(pom_id);
                    databaseHelper.updateShoplist(new Shoplist_db(id, String.valueOf(name.getText()), get_date()));
                    notifyDataSetChanged();
                });
            }
            return convertView;
        }
    }

    String get_date() {
        return new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()) + "";
    }
}
