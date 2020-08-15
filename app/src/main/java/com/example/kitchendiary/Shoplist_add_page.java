package com.example.kitchendiary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import info.androidhive.sqlite.model.DatabaseHelper;
import info.androidhive.sqlite.model.Ing_db;
import info.androidhive.sqlite.model.Ing_in_shoplist;
import info.androidhive.sqlite.model.Shoplist_db;

import static com.example.kitchendiary.MainActivity.ing_to_add_in_shoplist;

public class Shoplist_add_page extends Activity {

    ListView listView;
    DatabaseHelper databaseHelper;
    ArrayList<Ing_in_shoplist> in_shoplists;
    String name, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoplist_add_page);

        databaseHelper = DatabaseHelper.getInstance(this);
        in_shoplists = new ArrayList<>();

        set_ing_list();
        add_ing_to_list();
        add_btn_click();
    }

    void add_ing_to_list() {
        Button button = findViewById(R.id.shoplist_add_ing_btn);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(Shoplist_add_page.this, Select_Ingredients.class);
            intent.putExtra("activity", 3);
            startActivity(intent);
        });
    }

    void add_btn_click() {
        final Button button = findViewById(R.id.shoplist_add_save_btn);
        button.setOnClickListener(v -> {
            name = get_name();
            date = get_date();

            if (!name.isEmpty() && ing_to_add_in_shoplist.size() > 0) {
                int shoplist_id = databaseHelper.get_shoplists_size() + 1;
                int ing_in_shoplist_id = databaseHelper.get_ing_in_shoplist_size() + 1;
                for (Ing_in_shoplist ing : ing_to_add_in_shoplist) {
                    databaseHelper.createIng_in_shoplist(new Ing_in_shoplist(ing_in_shoplist_id, shoplist_id, ing.getIng_id(), ing.getAmount()));
                    ing_in_shoplist_id++;
                }
                databaseHelper.createShoplist(new Shoplist_db(shoplist_id, name, date));
                startActivity(new Intent(Shoplist_add_page.this, Shoplist.class));
                ing_to_add_in_shoplist.clear();
            } else {
                Toast.makeText(this, "wprowadź wszystkie dane", Toast.LENGTH_LONG).show();
            }
        });
    }

    String get_name() {
        final EditText name = findViewById(R.id.shoplist_add_name);
        return name.getText() + "";
    }

    String get_date() {
        return new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()) + "";
    }

    void set_ing_list() {
        listView = findViewById(R.id.shoplist_add_list);
        List_help_adapter help_adapter = new List_help_adapter(this, R.layout.add_shoplist_ing_row, ing_to_add_in_shoplist, databaseHelper);
        listView.setAdapter(help_adapter);
    }

    public void del_ing_from_shoplist(View v) {
        ConstraintLayout ParentRow = (ConstraintLayout) v.getParent();
        in_shoplists.remove(ParentRow.getId() + 1);
        databaseHelper.deleteIng_in_shoplist(ParentRow.getId() + 1);
        finish();
        startActivity(getIntent());
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

        class DataHolder {
            TextView name;
            TextView amount;
            ImageView del_btn;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            List_help_adapter.DataHolder dataHolder;
            if (convertView == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                convertView = inflater.inflate(LayoutResId, parent, false);
                dataHolder = new List_help_adapter.DataHolder();
                dataHolder.name = convertView.findViewById(R.id.add_shoplist_ing_row_name);
                dataHolder.amount = convertView.findViewById(R.id.add_shoplist_ing_row_row_amount);

                convertView.setTag(dataHolder);
            } else {
                dataHolder = (List_help_adapter.DataHolder) convertView.getTag();
            }
            Ing_in_shoplist ing_in_shoplist = list.get(position);

            dataHolder.name.setText(ingredients.get(ing_in_shoplist.getIng_id()).getName());
            dataHolder.amount.setText(ing_in_shoplist.getAmount() + "");
            dataHolder.del_btn = findViewById(R.id.add_shoplist_ing_row_delete);

            return convertView;
        }
    }

}
