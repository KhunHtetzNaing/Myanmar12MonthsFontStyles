package com.htetznaing.myanmarfontstyles;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.io.File;

public class FuckActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView imageView;
    Button install,change;
    String font;
    int image;
    LOL pff;
    Typeface mm;
    TextView textView;

    AdRequest adRequest;
    AdView Banner;
    InterstitialAd iAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuck);

        if (ContextCompat.checkSelfPermission(FuckActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        pff = new LOL();

        mm = Typeface.createFromAsset(getAssets(),"kason.ttf");
        textView = (TextView) findViewById(R.id.textView);
        textView.setTypeface(mm);

        font = getIntent().getStringExtra("name");
        image = getIntent().getIntExtra("image",R.drawable.icon);

        imageView = (ImageView) findViewById(R.id.iv);
        install = (Button) findViewById(R.id.install);
        change = (Button) findViewById(R.id.change);

        imageView.setImageResource(image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAD();
            }
        });

        install.setOnClickListener(this);
        change.setOnClickListener(this);

        adRequest = new AdRequest.Builder().build();
        Banner = (AdView) findViewById(R.id.adView);
        Banner.loadAd(adRequest);

        iAd = new InterstitialAd(this);
        iAd.setAdUnitId("ca-app-pub-4173348573252986/1750277861");
        iAd.loadAd(adRequest);
        iAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                loadAD();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                loadAD();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
                loadAD();
            }
        });
    }

    @Override
    public void onClick(View view) {
        showAD();
        switch (view.getId()){
            case R.id.install:
                Install();
                break;
            case R.id.change:
                Change();
                break;
        }
    }

    public void Install(){
        if (ContextCompat.checkSelfPermission(FuckActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        File file = new File("/sdcard/"+font);
        if (file.exists()){

        }else{
            pff.Assets2SD(FuckActivity.this,"icon.png","/sdcard/","htetz.zip");
            if (file.exists()){
                pff.ABUnzip("/sdcard/htetz.zip","/sdcard/samFuck");
            }
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File("/sdcard/samFuck/"+font)),"application/vnd.android.package-archive");
        startActivity(intent);
    }

    public void Change(){
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            ComponentName componentName = new ComponentName("com.android.settings", "com.android.settings.flipfont.FontListProgressActivity");
            intent.setComponent(componentName);
            startActivity(intent);
        }catch (Exception e){
            startActivityForResult(new Intent(Settings.ACTION_DISPLAY_SETTINGS),0);
        }
    }

    public void sendEmail(View view) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto","khunhtetznaing@gmail.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Myanmar Font Styles[FlipFont]");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Enter your feedback here!.");
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }


    public void loadAD(){
        if (!iAd.isLoaded()){
            iAd.loadAd(adRequest);
        }
    }

    public void showAD(){
        if (iAd.isLoaded()){
            iAd.show();
        }else{
            iAd.loadAd(adRequest);
        }
    }
}
