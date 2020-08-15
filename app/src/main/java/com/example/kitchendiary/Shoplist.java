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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import info.androidhive.sqlite.model.DatabaseHelper;
import info.androidhive.sqlite.model.Ing_db;
import info.androidhive.sqlite.model.Ing_in_shoplist;
import info.androidhive.sqlite.model.Shoplist_db;

public class Shoplist extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    ListView listView;
    ArrayList<Shoplist_db> shoplist_dbsl;
    ArrayList<Ing_in_shoplist> ingInShoplists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Objects.requireNonNull(this.getSupportActionBar()).hide();
        } catch (NullPointerException ignored) {
        }
        setContentView(R.layout.activity_shoplist);

        databaseHelper = DatabaseHelper.getInstance(this);
        shoplist_dbsl = (ArrayList<Shoplist_db>) databaseHelper.getAllShoplists();
        ingInShoplists = (ArrayList<Ing_in_shoplist>) databaseHelper.getAllIng_in_shoplist();

        missing_ing_notification();
        add_shoplist();
        show_shoplists();
    }

    void add_shoplist() {
        final Button button = findViewById(R.id.shoplist_add_btn);
        button.setOnClickListener(v -> {
            startActivity(new Intent(Shoplist.this, Shoplist_add_page.class));
        });
    }

    public void show_shoplists() {
        listView = findViewById(R.id.shoplist_list);
        List_help_adapter help_adapter = new List_help_adapter(this, R.layout.shoplist_row, shoplist_dbsl);
        listView.setAdapter(help_adapter);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent();
            intent.putExtra("id", i);
            intent.setClass(Shoplist.this, Shoplist_page.class);
            startActivity(intent);
        });
    }

    void missing_ing_notification() {
        ArrayList<Ing_db> ing_dbs = (ArrayList<Ing_db>) databaseHelper.getAllIngredients();
        StringBuilder info = new StringBuilder();
        for (Ing_db in : ing_dbs) {
            if (in.getMade() > 5 && in.getIn_stock() < 50) {
                info.append("pozostało niewiele :").append(in.getName()).append("  rozważ kupno !\n");
            }
        }
        if (info.length() != 0) {
            Toast.makeText(this, info, Toast.LENGTH_LONG).show();
        }
    }

    class List_help_adapter extends ArrayAdapter<Shoplist_db> {
        Context context;
        int LayoutResId;
        List<Shoplist_db> list;

        List_help_adapter(@NonNull Context context, int resource, @NonNull List<Shoplist_db> objects) {
            super(context, resource, objects);
            this.LayoutResId = resource;
            this.context = context;
            this.list = objects;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                convertView = inflater.inflate(LayoutResId, parent, false);
            }

            Shoplist_db shoplist_db = list.get(position);
            ImageView edit = convertView.findViewById(R.id.shoplist_row_edit);
            ImageView remove = convertView.findViewById(R.id.shoplist_row_del);
            TextView name = convertView.findViewById(R.id.shoplist_row_name);
            TextView date = convertView.findViewById(R.id.shoplist_row_date);

            name.setText(shoplist_db.getName());
            date.setText(shoplist_db.getMod_date() + "");

            edit.setOnClickListener(V -> {
                Intent intent = new Intent();
                intent.putExtra("id", position);
                intent.putExtra("db_id", shoplist_db.getId());
                intent.setClass(Shoplist.this, Shoplist_edit_page.class);
                startActivity(intent);
            });
            remove.setOnClickListener(v -> {
                int pom_id = shoplist_db.getId();
                for (Ing_in_shoplist in : ingInShoplists) {
                    if (in.getShoplist_id() == pom_id)
                        databaseHelper.deleteIng_in_shoplist(in.getId());
                }
                databaseHelper.deleteShoplist(pom_id);

                finish();
                startActivity(getIntent());
            });
            name.setOnClickListener(v -> {
                Intent intent = new Intent();
                intent.putExtra("id", position);
                intent.putExtra("db_id", shoplist_db.getId());
                intent.setClass(Shoplist.this, Shoplist_page.class);
                startActivity(intent);
            });
            date.setOnClickListener(v -> {
                Intent intent = new Intent();
                intent.putExtra("id", position);
                intent.putExtra("db_id", shoplist_db.getId());
                intent.setClass(Shoplist.this, Shoplist_page.class);
                startActivity(intent);
            });

            return convertView;
        }
    }
}
