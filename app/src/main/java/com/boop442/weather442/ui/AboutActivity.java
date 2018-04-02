package com.boop442.weather442.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.boop442.weather442.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.aboutTitleTextView) TextView mAboutTitleTextView;
    @BindView(R.id.githubTextView) TextView mGithubTextView;
    @BindView(R.id.linkedinTextView) TextView mLinkedinTextView;
    @BindView(R.id.emailTextView) TextView mEmailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ButterKnife.bind(this);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/I Am Awake.ttf");
        mAboutTitleTextView.setTypeface(typeface);

        mGithubTextView.setOnClickListener(this);
        mLinkedinTextView.setOnClickListener(this);
        mEmailTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == mGithubTextView) {
            Intent ghIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/telpuhova/weather-app"));
            startActivity(ghIntent);
        }
        if (view == mLinkedinTextView) {
            Intent liIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/natalia-telpukhova/"));
            startActivity(liIntent);
        }
        if (view == mEmailTextView) {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            intent.putExtra(Intent.CATEGORY_APP_EMAIL, "telpuhova@gmail.com");
            intent.putExtra(Intent.EXTRA_EMAIL, "telpuhova@gmail.com");
            intent.putExtra(Intent.EXTRA_SUBJECT, "weather442");
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }
}
