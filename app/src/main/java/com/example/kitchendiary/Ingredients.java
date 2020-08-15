package com.example.kitchendiary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import info.androidhive.sqlite.model.DatabaseHelper;
import info.androidhive.sqlite.model.Ing_db;

import static com.example.kitchendiary.MainActivity.ing_filter_avabile;

public class Ingredients extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    List<Ing_db> ing_dbList;
    ListView listView;
    int sort = 0;
    Switch s = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHelper = DatabaseHelper.getInstance(this);
        ing_dbList = databaseHelper.getAllIngredients();

        try {
            Objects.requireNonNull(this.getSupportActionBar()).hide();
        } catch (NullPointerException ignored) {
        }
        setContentView(R.layout.activity_ingredients);
        sort();

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                sort = extras.getInt("sort");
                ing_dbList = sort_Ing(sort, ing_dbList);
            }
        } else {
            sort = (int) savedInstanceState.getSerializable("sort");
            ing_dbList = sort_Ing(sort, ing_dbList);
        }
        s = findViewById(R.id.available_ing);
        if (ing_filter_avabile) {
            s.setChecked(true);
        }
        check_date();
        see_available();
        add_ing();
        show_ing(ing_filter_avabile);
    }

    void check_date() {
        StringBuilder info = new StringBuilder();
        long passed_days;
        String buy_date;
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        for (Ing_db in : ing_dbList) {
            if (in.getBuy_date() != null && !currentDate.equals(in.getBuy_date())) {
                if (!in.getEatable_time().equals("...")) {
                    buy_date = in.getBuy_date();

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    formatter = formatter.withLocale(Locale.GERMAN);
                    passed_days = ChronoUnit.DAYS.between(LocalDate.parse(buy_date, formatter), LocalDate.parse(currentDate, formatter));
                    if (Integer.parseInt(in.getEatable_time()) - passed_days < 5) {
                        info.append(in.getName()).append(" pozostało : ").append(Integer.parseInt(in.getEatable_time()) - passed_days).append("dni przydatności do spożycia !\n");
                    }
                }
            }
        }
        if (info.length() != 0) {
            Toast.makeText(this, info, Toast.LENGTH_LONG).show();
        }
    }

    void add_ing() {
        final Button bt = findViewById(R.id.ing_add);
        bt.setOnClickListener(v -> startActivity(new Intent(Ingredients.this, Add_Ingredient_page.class)));
    }

    public void sort() {
        final Button b_sort = findViewById(R.id.ing_sort);
        b_sort.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.putExtra("activity", 1);
            intent.setClass(Ingredients.this, Pop.class);
            startActivity(intent);
        });
    }

    List<Ing_db> sort_Ing(int type_of_sort, List<Ing_db> ing_dbs) {
        List<Ing_db> new_list = new ArrayList<>();
        String pom_name;
        int kcal;
        int index;
        switch (type_of_sort) {
            case R.id.sort_az: //A-Z
                while (ing_dbs.size() > 0) {
                    pom_name = ing_dbs.get(0).getName();
                    index = 0;
                    for (int i = 1; i < ing_dbs.size(); i++) {
                        if (ing_dbs.get(i).getName().compareTo(pom_name) < 0) {
                            pom_name = ing_dbs.get(i).getName();
                            index = i;
                        }
                    }
                    new_list.add(ing_dbs.get(index));
                    ing_dbs.remove(index);
                }
                break;
            case R.id.sort_za: //Z-A
                while (ing_dbs.size() > 0) {
                    pom_name = ing_dbs.get(0).getName();
                    index = 0;
                    for (int i = 1; i < ing_dbs.size(); i++) {
                        if (ing_dbs.get(i).getName().compareTo(pom_name) > 0) {
                            pom_name = ing_dbs.get(i).getName();
                            index = i;
                        }
                    }
                    new_list.add(ing_dbs.get(index));
                    ing_dbs.remove(index);
                }
                break;
            case R.id.sort_kcalup: //kcal asc
                while (ing_dbs.size() > 0) {
                    kcal = ing_dbs.get(0).getIng_calories();
                    index = 0;
                    for (int i = 1; i < ing_dbs.size(); i++) {
                        if (ing_dbs.get(i).getIng_calories() < kcal) {
                            kcal = ing_dbs.get(i).getIng_calories();
                            index = i;
                        }
                    }
                    new_list.add(ing_dbs.get(index));
                    ing_dbs.remove(index);
                }
                break;
            case R.id.sort_kcaldown: //kcal dsc
                while (ing_dbs.size() > 0) {
                    kcal = ing_dbs.get(0).getIng_calories();
                    index = 0;
                    for (int i = 1; i < ing_dbs.size(); i++) {
                        if (ing_dbs.get(i).getIng_calories() > kcal) {
                            kcal = ing_dbs.get(i).getIng_calories();
                            index = i;
                        }
                    }
                    new_list.add(ing_dbs.get(index));
                    ing_dbs.remove(index);
                }
                break;
        }
        return new_list;
    }

    void see_available() {
        s.setOnCheckedChangeListener((compoundButton, b) -> {
            ing_filter_avabile = b;
            show_ing(ing_filter_avabile);
        });
    }

    public void show_ing(boolean filter) {
        listView = findViewById(R.id.ing_list);
        ArrayList<Ing_db> filtred_list = new ArrayList<>();
        if (filter) {
            for (Ing_db in : ing_dbList) {
                if (in.getIn_stock() > 0) {
                    filtred_list.add(in);
                }
            }
        } else {
            filtred_list = (ArrayList<Ing_db>) ing_dbList;
        }
        List_help_adapter help_adapter = new List_help_adapter(this, R.layout.ing_row, filtred_list);
        listView.setAdapter(help_adapter);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent();
            int id = 0, db_id = 0;
            ArrayList<Ing_db> pom_ing = (ArrayList<Ing_db>) databaseHelper.getAllIngredients();
            for (int j = 0; j < pom_ing.size(); j++) {
                if (pom_ing.get(j).getName().equals(ing_dbList.get(i).getName())) {
                    id = j;
                    db_id = pom_ing.get(j).geting_id();
                }
            }
            intent.putExtra("id", id);
            intent.putExtra("db_id", db_id);
            intent.setClass(Ingredients.this, Ingredient_page.class);
            startActivity(intent);
        });
    }

    static class List_help_adapter extends ArrayAdapter<Ing_db> {
        Context context;
        int LayoutResId;
        List<Ing_db> list;
        boolean filter;

        List_help_adapter(@NonNull Context context, int resource, @NonNull List<Ing_db> objects) {
            super(context, resource, objects);
            this.LayoutResId = resource;
            this.context = context;
            this.list = objects;
        }

        static class DataHolder {
            ImageView imageView;
            TextView name;
            TextView amount;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            DataHolder dataHolder;
            if (convertView == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                convertView = inflater.inflate(LayoutResId, parent, false);
                dataHolder = new DataHolder();
                dataHolder.imageView = convertView.findViewById(R.id.ing_img);
                dataHolder.name = convertView.findViewById(R.id.ing_name);
                dataHolder.amount = convertView.findViewById(R.id.ing_amount);

                convertView.setTag(dataHolder);
            } else {
                dataHolder = (DataHolder) convertView.getTag();
            }
            Ing_db ing_db = list.get(position);

            dataHolder.name.setText(ing_db.getName());
            dataHolder.amount.setText(ing_db.getIn_stock() + "");
            dataHolder.imageView.setImageBitmap(BitmapFactory.decodeByteArray(ing_db.getImage(), 0, ing_db.getImage().length));

            return convertView;
        }
    }

}
