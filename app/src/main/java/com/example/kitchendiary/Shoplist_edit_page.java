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
import android.widget.EditText;
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

import static com.example.kitchendiary.MainActivity.ing_to_add_in_shoplist;

public class Shoplist_edit_page extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    int id, db_id;
    String shoplist_name;
    EditText name;
    TextView date;
    ArrayList<Shoplist_db> shoplists;
    ArrayList<Ing_in_shoplist> ingInShoplists;
    public ArrayList<Ing_in_shoplist> ing_to_delate;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoplist_edit_page);
        try {
            Objects.requireNonNull(this.getSupportActionBar()).hide();
        } catch (NullPointerException ignored) {
        }
        databaseHelper = DatabaseHelper.getInstance(this);

        shoplists = (ArrayList<Shoplist_db>) databaseHelper.getAllShoplists();
        ingInShoplists = (ArrayList<Ing_in_shoplist>) databaseHelper.getAllIng_in_shoplist();
        ing_to_delate = new ArrayList<>();

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
        conect_lists();
        set_all();
        add_ing_to_shoplist();
        save_changes();
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

    void conect_lists() {
        if (ing_to_add_in_shoplist.size() > 0) {
            ingInShoplists.addAll(ing_to_add_in_shoplist);
        }
    }

    void save_changes() {
        Button button = findViewById(R.id.shoplist_edit_submit_btn);
        button.setOnClickListener(V -> {
            shoplist_name = String.valueOf(name.getText());
            int ing_in_shoplist_id = databaseHelper.get_ing_in_shoplist_size() + 1;
            databaseHelper.updateShoplist(new Shoplist_db(db_id, shoplist_name, get_date()));
            for (Ing_in_shoplist in : ing_to_add_in_shoplist) {
                databaseHelper.createIng_in_shoplist(new Ing_in_shoplist(ing_in_shoplist_id, in.getShoplist_id(), in.getIng_id(), in.getAmount()));
                ing_in_shoplist_id++;
            }
            for (Ing_in_shoplist in : ing_to_delate)
                databaseHelper.deleteIng_in_shoplist(in.getId());
            ing_to_add_in_shoplist.clear();
            startActivity(new Intent(Shoplist_edit_page.this, Shoplist.class));
        });
    }

    void set_all() {
        name = findViewById(R.id.shoplist_edit_name);
        name.setText(shoplists.get(id).getName());
        date = findViewById(R.id.shoplist_edit_date);
        date.setText(shoplists.get(id).getMod_date());
        set_ing_list();
    }

    void add_ing_to_shoplist() {
        final Button button = findViewById(R.id.shoplist_edit_add_btn);
        button.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("activity", 1);
            intent.putExtra("shoplist_id", db_id);
            intent.setClass(Shoplist_edit_page.this, Select_Ingredients.class);
            startActivity(intent);
        });
    }

    void set_ing_list() {
        listView = findViewById(R.id.shoplist_edit_list);
        List_help_adapter help_adapter = new List_help_adapter(this, R.layout.edit_shoplist_ing_row, ingInShoplists, databaseHelper);
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
                TextView name = convertView.findViewById(R.id.edit_shoplist_ing_row_name);
                TextView amount = convertView.findViewById(R.id.edit_shoplist_ing_row_row_amount);
                ImageView del_btn = convertView.findViewById(R.id.edit_shoplist_ing_row_delete);

                name.setText(ingredients.get(ing_in_shoplist.getIng_id()).getName());
                amount.setText(ing_in_shoplist.getAmount() + "");


                del_btn.setOnClickListener(v -> {
                    list.remove(position);
                    ing_to_delate.add(ing_in_shoplist);
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
