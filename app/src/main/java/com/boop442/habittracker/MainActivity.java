package com.boop442.habittracker;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.locationsButton) Button mHabitsButton;
    @BindView(R.id.aboutButton) Button mAboutButton;
    @BindView(R.id.titleTextView) TextView mTitleTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/I Am Awake.ttf");
        Typeface typeface2 = Typeface.createFromAsset(getAssets(), "fonts/Halimun.ttf");
        mTitleTextView.setTypeface(typeface);

        mHabitsButton.setOnClickListener(this);
        mAboutButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mHabitsButton) {
            Intent intent = new Intent(MainActivity.this, LocationsActivity.class);
            startActivity(intent);
        }
        if (v == mAboutButton) {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        }
    }
}
