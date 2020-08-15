package com.example.kitchendiary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import info.androidhive.sqlite.model.DatabaseHelper;
import info.androidhive.sqlite.model.Ing_db;
import info.androidhive.sqlite.model.Ing_in_dish;
import info.androidhive.sqlite.model.Rcp_db;

import static com.example.kitchendiary.MainActivity.rcp_filter_avabile;
import static com.example.kitchendiary.MainActivity.rcp_filter_vegan;

public class Recipes extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    List<Rcp_db> rcp_dbList;
    ListView listView;
    int sort = R.id.sort_rcp_az;
    Switch sa = null;
    Switch sv = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Objects.requireNonNull(this.getSupportActionBar()).hide();
        } catch (NullPointerException ignored) {
        }
        setContentView(R.layout.activity_recipes);
        databaseHelper = DatabaseHelper.getInstance(this);

        rcp_dbList = databaseHelper.getAllRecipes();

        sort();
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                sort = extras.getInt("sort");
                rcp_dbList = sort_Rcp(sort, rcp_dbList);
            }
        } else {
            sort = (int) savedInstanceState.getSerializable("sort");
            rcp_dbList = sort_Rcp(sort, rcp_dbList);
        }
        sa = findViewById(R.id.available_rcp);
        sv = findViewById(R.id.vegan_rcp);
        if (rcp_filter_avabile) {
            sa.setChecked(true);
        }
        if (rcp_filter_vegan) {
            sv.setChecked(true);
        }

        search();
        to_add_page();
        avb_switch();
        veg_switch();
        show_rcp(rcp_filter_avabile, rcp_filter_vegan);
    }

    void search() {
        final Button btn = findViewById(R.id.rcp_search_btn);
        final TextView text = findViewById(R.id.rcp_search_text);
        btn.setOnClickListener(v -> {
            String tags = String.valueOf(text.getText());
            if (!tags.equals("")) {
                String[] tgs;
                tgs = tags.split(",");
                for (String tg : tgs) {
                    tg.replaceAll("\\s", "");
                }
                ArrayList<Rcp_db> out = new ArrayList<>();
                for (Rcp_db rcp : rcp_dbList) {
                    String[] dsc = rcp.getHow_to_cook().split(" ");
                    int correct = 0;
                    for (String e : tgs) {
                        for (String g : dsc) {
                            if (e.equals(g)) {
                                correct++;
                            }
                        }
                        if (correct == 0) break;
                    }
                    if (correct == tgs.length) {
                        out.add(rcp);
                    }
                }
                if (out.size() > 0) {
                    rcp_dbList = out;
                    show_rcp(rcp_filter_avabile, rcp_filter_vegan);
                } else {
                    Toast.makeText(this, "brak przepisów z podanymi tagami", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "wpierw wprowadź tekst !", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void sort() {
        final Button b_sort = findViewById(R.id.rcp_sort);
        b_sort.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setClass(Recipes.this, PopRcp.class);
            startActivity(intent);
        });
    }

    List<Rcp_db> sort_Rcp(int type_of_sort, List<Rcp_db> rcp_dbs) {
        List<Rcp_db> new_list = new ArrayList<>();
        String pom_name, occasion;
        int kcal;
        int made;
        int index;
        switch (type_of_sort) {
            case R.id.sort_rcp_az: //A-Z
                while (rcp_dbs.size() > 0) {
                    pom_name = rcp_dbs.get(0).getDish_name();
                    index = 0;
                    for (int i = 1; i < rcp_dbs.size(); i++) {
                        if (rcp_dbs.get(i).getDish_name().compareTo(pom_name) < 0) {
                            pom_name = rcp_dbs.get(i).getDish_name();
                            index = i;
                        }
                    }
                    new_list.add(rcp_dbs.get(index));
                    rcp_dbs.remove(index);
                }
                break;
            case R.id.sort_rcp_za: //Z-A
                while (rcp_dbs.size() > 0) {
                    pom_name = rcp_dbs.get(0).getDish_name();
                    index = 0;
                    for (int i = 1; i < rcp_dbs.size(); i++) {
                        if (rcp_dbs.get(i).getDish_name().compareTo(pom_name) > 0) {
                            pom_name = rcp_dbs.get(i).getDish_name();
                            index = i;
                        }
                    }
                    new_list.add(rcp_dbs.get(index));
                    rcp_dbs.remove(index);
                }
                break;
            case R.id.sort_rcp_kcalup: //kcal asc
                while (rcp_dbs.size() > 0) {
                    kcal = rcp_dbs.get(0).getRcp_calories();
                    index = 0;
                    for (int i = 1; i < rcp_dbs.size(); i++) {
                        if (rcp_dbs.get(i).getRcp_calories() < kcal) {
                            kcal = rcp_dbs.get(i).getRcp_calories();
                            index = i;
                        }
                    }
                    new_list.add(rcp_dbs.get(index));
                    rcp_dbs.remove(index);
                }
                break;
            case R.id.sort_rcp_kcaldown: //kcal dsc
                while (rcp_dbs.size() > 0) {
                    kcal = rcp_dbs.get(0).getRcp_calories();
                    index = 0;
                    for (int i = 1; i < rcp_dbs.size(); i++) {
                        if (rcp_dbs.get(i).getRcp_calories() > kcal) {
                            kcal = rcp_dbs.get(i).getRcp_calories();
                            index = i;
                        }
                    }
                    new_list.add(rcp_dbs.get(index));
                    rcp_dbs.remove(index);
                }
                break;
            case R.id.sort_rcp_made_up: //made asc
                while (rcp_dbs.size() > 0) {
                    made = rcp_dbs.get(0).getMade();
                    index = 0;
                    for (int i = 1; i < rcp_dbs.size(); i++) {
                        if (rcp_dbs.get(i).getMade() > made) {
                            made = rcp_dbs.get(i).getMade();
                            index = i;
                        }
                    }
                    new_list.add(rcp_dbs.get(index));
                    rcp_dbs.remove(index);
                }
                break;
            case R.id.sort_rcp_made_down: //made dsc
                while (rcp_dbs.size() > 0) {
                    made = rcp_dbs.get(0).getMade();
                    index = 0;
                    for (int i = 1; i < rcp_dbs.size(); i++) {
                        if (rcp_dbs.get(i).getMade() < made) {
                            made = rcp_dbs.get(i).getMade();
                            index = i;
                        }
                    }
                    new_list.add(rcp_dbs.get(index));
                    rcp_dbs.remove(index);
                }
                break;
            case R.id.sort_rcp_occasion: //occasion
                while (rcp_dbs.size() > 0) {
                    occasion = rcp_dbs.get(0).getOccasion();
                    index = 0;
                    for (int i = 1; i < rcp_dbs.size(); i++) {
                        if (rcp_dbs.get(i).getOccasion().compareTo(occasion) < 0) {
                            occasion = rcp_dbs.get(i).getOccasion();
                            index = i;
                        }
                    }
                    new_list.add(rcp_dbs.get(index));
                    rcp_dbs.remove(index);
                }
                break;
        }
        return new_list;
    }

    public void to_add_page() {
        final Button button = findViewById(R.id.rcp_add);
        button.setOnClickListener(view -> startActivity(new Intent(Recipes.this, Add_recipe_page.class)));
    }

    public void show_rcp(boolean filter_avb, boolean filter_veg) {
        listView = findViewById(R.id.rcp_list);
        ArrayList<Rcp_db> filtred_list = new ArrayList<>();
        if (filter_avb && filter_veg) {
            for (Rcp_db in : rcp_dbList) {
                if (in.isVegan() == 1 && ready_for_cook(in)) {
                    filtred_list.add(in);
                }
            }
        } else if (!filter_veg && filter_avb) {
            for (Rcp_db in : rcp_dbList) {
                if (ready_for_cook(in)) {
                    filtred_list.add(in);
                }
            }
        } else if (filter_veg) {
            for (Rcp_db in : rcp_dbList) {
                if (in.isVegan() == 1) {
                    filtred_list.add(in);
                }
            }
        } else {
            filtred_list = (ArrayList<Rcp_db>) rcp_dbList;
        }
        List_help_adapter help_adapter = new List_help_adapter(this, R.layout.rcp_row, filtred_list);
        listView.setAdapter(help_adapter);
        ArrayList<Rcp_db> finalFiltred_list = filtred_list;
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            int db_id = 0;
            Intent intent = new Intent();
            int id = 0;
            ArrayList<Rcp_db> pom_rcp = (ArrayList<Rcp_db>) databaseHelper.getAllRecipes();
            for (int j = 0; j < pom_rcp.size(); j++) {
                if (pom_rcp.get(j).getDish_name().equals(finalFiltred_list.get(i).getDish_name())) {
                    id = j;
                    db_id = pom_rcp.get(j).getId_PR_KEY();
                }
            }
            intent.putExtra("id", id);
            intent.putExtra("db_id", db_id);
            intent.setClass(Recipes.this, Recipe_page.class);
            startActivity(intent);
        });
    }

    void avb_switch() {
        sa.setOnCheckedChangeListener((compoundButton, b) -> {
            rcp_filter_avabile = b;
            show_rcp(rcp_filter_avabile, rcp_filter_vegan);
        });
    }

    void veg_switch() {
        sv.setOnCheckedChangeListener((compoundButton, b) -> {
            rcp_filter_vegan = b;
            show_rcp(rcp_filter_avabile, rcp_filter_vegan);
        });
    }

    boolean ready_for_cook(Rcp_db recipie) {
        boolean flag = true;
        ArrayList<Ing_in_dish> ing_in_dishes = (ArrayList<Ing_in_dish>) databaseHelper.getAllIngredients_in_recipes();
        ArrayList<Ing_in_dish> inDish = new ArrayList<>();
        for (Ing_in_dish in : ing_in_dishes) {
            if (in.getRcp_id() == recipie.getId_PR_KEY()) inDish.add(in);
        }
        ArrayList<Ing_db> ingredients = (ArrayList<Ing_db>) databaseHelper.getAllIngredients();
        for (Ing_in_dish in : inDish) {
            if (!(ingredients.get(in.getIng_id()).getIn_stock() >= in.getAmount())) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    class List_help_adapter extends ArrayAdapter<Rcp_db> {
        Context context;
        int LayoutResId;
        List<Rcp_db> list;

        List_help_adapter(@NonNull Context context, int resource, @NonNull List<Rcp_db> objects) {
            super(context, resource, objects);
            this.LayoutResId = resource;
            this.context = context;
            this.list = objects;
        }

        class DataHolder {
            ImageView imageView;
            TextView name;
            TextView avabile;
            TextView vegan;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            Recipes.List_help_adapter.DataHolder dataHolder;
            if (convertView == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                convertView = inflater.inflate(LayoutResId, parent, false);
                dataHolder = new Recipes.List_help_adapter.DataHolder();
                dataHolder.imageView = convertView.findViewById(R.id.rcp_row_img);
                dataHolder.name = convertView.findViewById(R.id.rcp_row_name);
                dataHolder.avabile = convertView.findViewById(R.id.rcp_row_avb);
                dataHolder.vegan = convertView.findViewById(R.id.rcp_row_vegan);

                convertView.setTag(dataHolder);
            } else {
                dataHolder = (Recipes.List_help_adapter.DataHolder) convertView.getTag();
            }
            Rcp_db rcp_db = list.get(position);
            dataHolder.name.setText(rcp_db.getDish_name());
            if (rcp_db.isVegan() == 1) {
                dataHolder.vegan.setText("wegańskie");
                dataHolder.vegan.setTextColor(Color.GREEN);
            } else {
                dataHolder.vegan.setText("niewegańskie");
                dataHolder.vegan.setTextColor(Color.RED);
            }
            if (ready_for_cook(rcp_db)) {
                dataHolder.avabile.setTextColor(Color.GREEN);
                dataHolder.avabile.setText("dostępne");
            } else {
                dataHolder.avabile.setTextColor(Color.RED);
                dataHolder.avabile.setText("niedostępne");
            }
            dataHolder.imageView.setImageBitmap(BitmapFactory.decodeByteArray(rcp_db.getRcp_image(), 0, rcp_db.getRcp_image().length));

            return convertView;
        }
    }
}
