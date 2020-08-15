package com.example.kitchendiary;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import info.androidhive.sqlite.model.DatabaseHelper;
import info.androidhive.sqlite.model.Ing_db;

public class Ingredient_page extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    List<Ing_db> ing_dbList;
    String amount_to_add;
    int id, db_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_page);

        databaseHelper = DatabaseHelper.getInstance(this);
        ing_dbList = databaseHelper.getAllIngredients();

        try {
            Objects.requireNonNull(this.getSupportActionBar()).hide();
        } catch (NullPointerException ignored) {
        }

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
        setEverything();
        add_to_stock();
        del_ing();
    }

    void setEverything() {
        TextView tv = findViewById(R.id.ig_page_name);
        ImageView iv = findViewById(R.id.ig_page_avatar);
        iv.setImageBitmap(arr_to_bit(ing_dbList.get(id).getImage()));
        tv.setText(ing_dbList.get(id).getName());
        tv = findViewById(R.id.ig_page_buydate);
        tv.setText(ing_dbList.get(id).getBuy_date());
        tv = findViewById(R.id.ig_page_eatabletime);
        tv.setText(ing_dbList.get(id).getEatable_time() + "");
        tv = findViewById(R.id.ig_page_kcal);
        tv.setText(ing_dbList.get(id).getIng_calories() + "");
        tv = findViewById(R.id.ig_page_carbs);
        tv.setText(ing_dbList.get(id).getIng_carbs() + "");
        tv = findViewById(R.id.ig_page_prot);
        tv.setText(ing_dbList.get(id).getIng_proteins() + "");
        tv = findViewById(R.id.ig_page_fat);
        tv.setText(ing_dbList.get(id).getIng_fat() + "");
        tv = findViewById(R.id.ig_page_in_stock);
        tv.setText(ing_dbList.get(id).getIn_stock() + "");
        tv = findViewById(R.id.ig_page_vegan);
        if (ing_dbList.get(id).isVegan() == 1) {
            tv.setText("wegańskie");
            tv.setTextColor(Color.GREEN);
        } else {
            tv.setText("niewegańskie");
            tv.setTextColor(Color.RED);
        }
    }

    Bitmap arr_to_bit(byte[] b) {
        return BitmapFactory.decodeByteArray(b, 0, b.length);
    }

    void add_to_stock() {
        final Button b_add = findViewById(R.id.ig_page_btn);
        final EditText to_add = findViewById(R.id.ig_page_add);
        b_add.setOnClickListener(view -> {
            amount_to_add = to_add.getText().toString();
            if (amount_to_add.matches("\\d+")) {
                int is_updated;
                String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                is_updated = databaseHelper.updateING(db_id, Integer.parseInt(amount_to_add), currentDate, ing_dbList.get(id).getMade());
                if (is_updated == 1) {
                    Toast.makeText(this, "zaktualizowano dane", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "nie udało się zaktualizować danych", Toast.LENGTH_LONG).show();
                }
                startActivity(new Intent(Ingredient_page.this, Ingredients.class));
                //finish();
                //startActivity(getIntent());
            } else {
                Toast.makeText(this, "nieprawidłowa wartość!", Toast.LENGTH_LONG).show();
            }
        });
    }

    void del_ing() {
        final Button btn = findViewById(R.id.del_ing);
        btn.setOnClickListener(v -> {
            databaseHelper.deleteIngredient(db_id);
            ing_dbList.remove(id);
            startActivity(new Intent(Ingredient_page.this, Ingredients.class));
        });
    }
}
