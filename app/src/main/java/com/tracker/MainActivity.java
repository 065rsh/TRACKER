package com.tracker;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_LOCATION = 1;
    public static TextView homeTemp1, homeTemp2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_actionbar);

        final View firstAnimalBg_V = findViewById(R.id.firstAnimalBg_V);
        View secondAnimalBg_V = findViewById(R.id.secondAnimalBg_V);
        View actionBarView = getSupportActionBar().getCustomView();
        ImageButton imageButton = actionBarView.findViewById(R.id.backButton);
        imageButton.setVisibility(View.GONE);
        homeTemp1 = findViewById(R.id.homeTemp1);
        homeTemp2 = findViewById(R.id.homeTemp2);

        final Handler handler = new Handler();
// Define the code block to be executed

        Runnable runnableCode = new Runnable() {
            @Override
            public void run() {
                fetchDataMain process = new fetchDataMain();
                process.execute();
                handler.postDelayed(this, 2000);
            }
        };
// Start the initial runnable task by posting through the handler
        handler.post(runnableCode);

        firstAnimalBg_V.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, firstAnimal.class));
            }
        });

        secondAnimalBg_V.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, secondAnimal.class));
            }
        });

    }
}
