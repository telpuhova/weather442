package com.boop442.weather442.ui;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.boop442.weather442.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutActivity extends AppCompatActivity {
    @BindView(R.id.aboutTitleTextView) TextView mAboutTitleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ButterKnife.bind(this);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/I Am Awake.ttf");
        mAboutTitleTextView.setTypeface(typeface);
    }
}
