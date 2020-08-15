package com.example.kitchendiary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.RadioGroup;

public class Pop extends Activity {
    int act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_sort);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .4), (int) (height * .4));
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                act = extras.getInt("activity");
            }
        } else {
            act = (int) savedInstanceState.getSerializable("activity");
        }
        selected();
    }

    void selected() {
        final RadioGroup radioGroup = findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener((radioGroup1, i) -> {
            Intent intent = new Intent();
            intent.putExtra("sort", i);
            intent.setClass(Pop.this, Ingredients.class);
            startActivity(intent);
        });
    }
}
