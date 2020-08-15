package com.example.kitchendiary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.sqlite.model.DatabaseHelper;
import info.androidhive.sqlite.model.Ing_db;
import info.androidhive.sqlite.model.Ing_in_dish;
import info.androidhive.sqlite.model.Ing_in_shoplist;

import static com.example.kitchendiary.MainActivity.ing_to_add_in_dish;
import static com.example.kitchendiary.MainActivity.ing_to_add_in_shoplist;


public class Select_Ingredients extends Activity {
    ArrayList<Ing_db> ingredients;
    DatabaseHelper databaseHelper;
    List_help_adapter help_adapter;
    ListView listView;
    EditText amt;
    TextView name;
    String ing_name;
    int amount = 0, ing_id;
    int activity, shoplist_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_ingredients);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                activity = extras.getInt("activity");
                shoplist_id = extras.getInt("shoplist_id");
            }
        } else {
            activity = ((Integer) savedInstanceState.getSerializable("activity"));
            shoplist_id = ((Integer) savedInstanceState.getSerializable("shoplist_id"));
        }

        databaseHelper = DatabaseHelper.getInstance(this);
        ingredients = (ArrayList<Ing_db>) databaseHelper.getAllIngredients();
        amt = findViewById(R.id.ing_select_amount);
        name = findViewById(R.id.ing_select_name);

        set_ing_list();
        confirm();
    }

    void setAmount() {
        String pom = String.valueOf(amt.getText());
        if (pom.matches("\\d+")) {
            amount = Integer.parseInt(String.valueOf(amt.getText()));
        } else {
            Toast.makeText(this, "wprowadzono nieprawidłową wartość", Toast.LENGTH_LONG).show();
        }
    }

    void confirm() {
        final Button button = findViewById(R.id.ing_select_btn);
        button.setOnClickListener(V -> {
            setAmount();
            if (amount != 0 && !ing_name.isEmpty()) {
                Intent intent = new Intent();
                if (activity == 0) {
                    ing_to_add_in_dish.add(new Ing_in_dish(databaseHelper.get_ing_in_rcp_size() + 1, databaseHelper.get_rcp_size() + 1, ing_id, amount));
                    intent.setClass(Select_Ingredients.this, Add_recipe_page.class);
                    startActivity(intent);
                } else if (activity == 1) {
                    ing_to_add_in_shoplist.add(new Ing_in_shoplist(databaseHelper.get_ing_in_shoplist_size() + 1, shoplist_id, ing_id, amount));
                    intent.setClass(Select_Ingredients.this, Shoplist_edit_page.class);
                    startActivity(intent);
                } else if (activity == 3) {
                    ing_to_add_in_shoplist.add(new Ing_in_shoplist(databaseHelper.get_ing_in_shoplist_size() + 1, databaseHelper.get_shoplists_size() + 1, ing_id, amount));
                    intent.setClass(Select_Ingredients.this, Shoplist_add_page.class);
                    startActivity(intent);
                }
            }
        });
    }

    void set_ing_list() {
        listView = findViewById(R.id.ing_select_list);
        help_adapter = new List_help_adapter(this, R.layout.ing_select_row, ingredients);
        listView.setAdapter(help_adapter);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            ing_name = ingredients.get(i).getName();
            ing_id = i;
            name.setText(ing_name);
        });
    }

    static class List_help_adapter extends ArrayAdapter<Ing_db> {
        Context context;
        int LayoutResId;
        List<Ing_db> ingredients;

        List_help_adapter(@NonNull Context context, int resource, @NonNull List<Ing_db> ingredients) {
            super(context, resource, ingredients);
            this.LayoutResId = resource;
            this.context = context;
            this.ingredients = ingredients;
        }


        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                convertView = inflater.inflate(LayoutResId, parent, false);
            }
            TextView name = convertView.findViewById(R.id.ing_select_row_name);
            Ing_db ing = ingredients.get(position);

            name.setText(ing.getName());

            return convertView;
        }
    }

}
