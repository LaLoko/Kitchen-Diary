package com.example.kitchendiary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.RadioGroup;

public class PopRcp extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_rcp);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .62), (int) (height * .6));
        selected();
    }

    void selected() {
        final RadioGroup radioGroup = findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener((radioGroup1, i) -> {
            Intent intent = new Intent();
            intent.putExtra("sort", i);
            intent.setClass(PopRcp.this, Recipes.class);
            startActivity(intent);
        });
    }
}
