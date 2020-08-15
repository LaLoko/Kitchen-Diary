package com.example.kitchendiary;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import info.androidhive.sqlite.model.DatabaseHelper;
import info.androidhive.sqlite.model.Ing_db;

public class Add_Ingredient_page extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    String name;
    int kcal, carbs, prot, fat, vegan;
    String eatable_time;
    Bitmap img;
    public static final int PICK_IMAGE = 1;
    Uri image_uri;
    ImageView avatar;
    EditText e_name, e_kcal, e_prot, e_carbs, e_fat, e_eatable_time;
    Switch s_vegan;
    Button add_btn;
    boolean g_name, g_kcal, g_carbs, g_prot, g_fat, g_eat, img_set = false;
    ArrayList<Ing_db> ing_dbs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient_page);

        databaseHelper = DatabaseHelper.getInstance(this);
        avatar = findViewById(R.id.ig_add_page_avatar);
        e_name = findViewById(R.id.ig_add_name);
        e_kcal = findViewById(R.id.ig_add_kcal);
        e_prot = findViewById(R.id.ig_add_prot);
        e_carbs = findViewById(R.id.ig_add_carbs);
        e_fat = findViewById(R.id.ig_add_fat);
        e_eatable_time = findViewById(R.id.ig_add_eatable_time);
        s_vegan = findViewById(R.id.ig_add_page_vegan);
        add_btn = findViewById(R.id.ig_add_btn);
        ing_dbs = (ArrayList<Ing_db>) databaseHelper.getAllIngredients();
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

        add();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            image_uri = data.getData();
            try {
                img = MediaStore.Images.Media.getBitmap(getContentResolver(), image_uri);
                avatar.setImageBitmap(img);
                img_set = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void add() {
        image_get();
        add_btn.setOnClickListener(v -> {
            name_get();
            kcal_get();
            prot_get();
            carbs_get();
            fat_get();
            eatable_time_get();
            vegan_get();

            if (if_all_set()) {
                boolean avb_name = true;
                for (Ing_db in : ing_dbs) {
                    if (in.getName().toLowerCase().equals(name.toLowerCase())) {
                        avb_name = false;
                        break;
                    }
                }
                if (avb_name) {
                    databaseHelper.createIngredients(new Ing_db(databaseHelper.get_ing_size(), eatable_time, kcal, carbs, fat, prot, name, getBitmapAsByteArray(img), vegan, 0));
                    startActivity(new Intent(Add_Ingredient_page.this, Ingredients.class));
                } else {
                    Toast.makeText(this, "składnik o podanej nazwie już istnieje!", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "wprowadź wszystkie dane!", Toast.LENGTH_LONG).show();
            }

        });

    }

    void image_get() {
        avatar.setOnClickListener(v -> {
            Intent gallery = new Intent();
            gallery.setType("image/jpeg");
            gallery.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(gallery, "Select Picture"), PICK_IMAGE);
        });
    }

    void name_get() {
        name = String.valueOf(e_name.getText());
    }

    void kcal_get() {
        kcal = Integer.parseInt(String.valueOf(e_kcal.getText()));
    }

    void prot_get() {
        prot = Integer.parseInt(String.valueOf(e_prot.getText()));
    }

    void carbs_get() {
        carbs = Integer.parseInt(String.valueOf(e_carbs.getText()));
    }

    void fat_get() {
        fat = Integer.parseInt(String.valueOf(e_fat.getText()));
    }

    void eatable_time_get() {
        eatable_time = String.valueOf(e_eatable_time.getText());
    }

    void vegan_get() {
        boolean isVegan = s_vegan.isChecked();
        if (isVegan) {
            vegan = 1;
        } else {
            vegan = 0;
        }
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, outputStream);
        return outputStream.toByteArray();
    }

    boolean if_all_set() {
        if (!name.isEmpty()) g_name = true;
        else e_name.setText("NIEPRAWIDŁOWA NAZWA");

        if (String.valueOf(kcal).matches("\\d+")) g_kcal = true;
        else e_kcal.setText("NIEPRAWIDŁOWA WARTOŚĆ");

        if (String.valueOf(prot).matches("\\d+")) g_prot = true;
        else e_prot.setText("NIEPRAWIDŁOWA WARTOŚĆ");

        if (String.valueOf(carbs).matches("\\d+")) g_carbs = true;
        else e_carbs.setText("NIEPRAWIDŁOWA WARTOŚĆ");

        if (String.valueOf(fat).matches("\\d+")) g_fat = true;
        else e_fat.setText("NIEPRAWIDŁOWA WARTOŚĆ");

        if (String.valueOf(eatable_time).matches("\\d+")) g_eat = true;
        else e_eatable_time.setText("NIEPRAWIDŁOWA WARTOŚĆ");


        return g_carbs && g_eat && g_fat && g_kcal && g_name && g_prot && img_set;
    }

}
