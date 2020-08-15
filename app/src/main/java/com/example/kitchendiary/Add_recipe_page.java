package com.example.kitchendiary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import info.androidhive.sqlite.model.DatabaseHelper;
import info.androidhive.sqlite.model.Ing_db;
import info.androidhive.sqlite.model.Ing_in_dish;
import info.androidhive.sqlite.model.Rcp_db;

import static com.example.kitchendiary.MainActivity.ing_to_add_in_dish;

public class Add_recipe_page extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    ListView listView;
    ArrayList<Ing_db> ingredients;
    Bitmap img;
    boolean name_set, desc_set, occasion_set;
    int vegan, kcal, protein, fat, carbs;
    String name, description, occasion;
    Switch s_vegan;
    EditText dish_name, how_to_cook, occasion_edt;
    ImageView rcp_image;
    public static final int PICK_IMAGE = 1;
    Uri image_uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe_page);

        databaseHelper = DatabaseHelper.getInstance(this);

        s_vegan = findViewById(R.id.rcp_add_page_vegan);
        dish_name = findViewById(R.id.rcp_add_page_name);
        how_to_cook = findViewById(R.id.rcp_add_page_desc);
        rcp_image = findViewById(R.id.rcp_add_page_image);
        occasion_edt = findViewById(R.id.rcp_add_page_occasion);

        ingredients = (ArrayList<Ing_db>) databaseHelper.getAllIngredients();
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        set_ing_list();
        add_ing_to_list();
        add();

    }

    void add() {
        final Button add_recipe = findViewById(R.id.rcp_add_page_submit_btn);
        image_get();
        add_recipe.setOnClickListener(v -> {
            name_get();
            desc_get();
            vegan_get();
            occasion_get();
            kcal = kcal_get();
            protein = prot_get();
            carbs = carbs_get();
            fat = fat_get();
            if (ing_to_add_in_dish.size() > 0 && name_set && desc_set && occasion_set) {
                databaseHelper.createRecipe(new Rcp_db(databaseHelper.get_rcp_size(), kcal, carbs, fat, protein, name, description, getBitmapAsByteArray(img), vegan, 0, occasion));
                for (int i = 0; i < ing_to_add_in_dish.size(); i++) {
                    databaseHelper.createIng_in_dish(new Ing_in_dish(databaseHelper.get_ing_in_rcp_size() + i, databaseHelper.get_rcp_size() - 1, ing_to_add_in_dish.get(i).getIng_id(), ing_to_add_in_dish.get(i).getAmount()));
                }
                ing_to_add_in_dish.clear();
                img.recycle();
                startActivity(new Intent(Add_recipe_page.this, Recipes.class));
            } else {
                Toast.makeText(this, "wprowadź wszystkie dane", Toast.LENGTH_LONG).show();
            }
        });
    }

    void add_ing_to_list() {
        final Button button = findViewById(R.id.rcp_add_page_add_ing_btn);
        button.setOnClickListener(V -> {
            Intent intent = new Intent();
            intent.putExtra("activity", 0);
            intent.setClass(Add_recipe_page.this, Select_Ingredients.class);
            startActivity(intent);
        });

    }

    public void del_ing_in_dish_to_add(View v) {
        ConstraintLayout ParentRow = (ConstraintLayout) v.getParent();
        ing_to_add_in_dish.remove(ParentRow.getId() + 1);
        finish();
        startActivity(getIntent());
    }

    void vegan_get() {
        boolean isVegan = s_vegan.isChecked();
        if (isVegan) {
            vegan = 1;
        } else {
            vegan = 0;
        }
    }

    void name_get() {
        name = String.valueOf(dish_name.getText());
        if (name != "") name_set = true;
    }

    void occasion_get() {
        occasion = String.valueOf(occasion_edt.getText());
        if (occasion != "") occasion_set = true;
    }

    void desc_get() {
        description = String.valueOf(how_to_cook.getText());
        if (description != "") desc_set = true;
    }

    int kcal_get() {
        int k = 0;
        for (Ing_in_dish in : ing_to_add_in_dish) {
            k += ingredients.get(in.getIng_id()).getIng_calories();
        }
        return k;
    }

    int prot_get() {
        int p = 0;
        for (Ing_in_dish in : ing_to_add_in_dish) {
            p += ingredients.get(in.getIng_id()).getIng_proteins();
        }
        return p;
    }

    int carbs_get() {
        int c = 0;
        for (Ing_in_dish in : ing_to_add_in_dish) {
            c += ingredients.get(in.getIng_id()).getIng_carbs();
        }
        return c;
    }

    int fat_get() {
        int f = 0;
        for (Ing_in_dish in : ing_to_add_in_dish) {
            f += ingredients.get(in.getIng_id()).getIng_fat();
        }
        return f;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            image_uri = data.getData();
            try {
                img = MediaStore.Images.Media.getBitmap(getContentResolver(), image_uri);
                rcp_image.setImageBitmap(img);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void image_get() {
        rcp_image.setOnClickListener(v -> {
            Intent gallery = new Intent();
            gallery.setType("image/jpeg");
            gallery.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(gallery, "Select Picture"), PICK_IMAGE);
        });
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, outputStream);
        return outputStream.toByteArray();
    }

    void set_ing_list() {
        listView = findViewById(R.id.rcp_add_page_ing_list);
        List_help_adapter help_adapter = new List_help_adapter(this, R.layout.dish_add_ing_row, ing_to_add_in_dish, ingredients);
        listView.setAdapter(help_adapter);
    }

    class List_help_adapter extends ArrayAdapter<Ing_in_dish> {
        Context context;
        int LayoutResId;
        ArrayList<Ing_in_dish> list;
        ArrayList<Ing_db> ingredients;
        ArrayList<Rcp_db> rcp_list;

        public List_help_adapter(@NonNull Context context, int resource, @NonNull ArrayList<Ing_in_dish> objects, ArrayList<Ing_db> ingredients) {
            super(context, resource, objects);
            this.LayoutResId = resource;
            this.context = context;
            this.list = objects;
            this.ingredients = ingredients;
            this.rcp_list = (ArrayList<Rcp_db>) databaseHelper.getAllRecipes();
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                convertView = inflater.inflate(LayoutResId, parent, false);
            }
            TextView name = convertView.findViewById(R.id.add_dish_row_name);
            TextView amount = convertView.findViewById(R.id.add_dish_ing_row_amount);
            ImageView del_btn = findViewById(R.id.add_dish_row_del);
            Ing_in_dish ingInDish = list.get(position);

            name.setText(ingredients.get(ingInDish.getIng_id()).getName());
            amount.setText(ingInDish.getAmount() + "");

            return convertView;
        }
    }
}
