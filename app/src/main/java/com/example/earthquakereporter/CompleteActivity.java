package com.example.earthquakereporter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toolbar;

public class CompleteActivity extends AppCompatActivity {
    private TextView title;
    private TextView strength;
    private TextView no_of_people;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar =getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_complete);
        title=findViewById(R.id.title);
        strength=findViewById(R.id.perceived_magnitude);
        no_of_people=findViewById(R.id.number_of_people);
        Bundle bundle = getIntent().getExtras();
        String mag = bundle.getString("magnitude");
        String sTitle=bundle.getString("title");
        String felt=bundle.getString("felt");
        if(!TextUtils.isEmpty(mag)){
            strength.setText(mag);
        }
        if(!TextUtils.isEmpty(sTitle)){
            title.setText(sTitle);
        }
        if(!TextUtils.isEmpty(felt)){
            no_of_people.setText(felt+" "+"People felt that");
        }
        GradientDrawable magnitudeCircle = (GradientDrawable) strength.getBackground();
        int magnitudeColor = bundle.getInt("color");
        magnitudeCircle.setColor(magnitudeColor);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}