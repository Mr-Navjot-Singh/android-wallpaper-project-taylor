package com.techlabs.besttaylorswiftwallpaperspro;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.WallpaperManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class WallpaperSelectionActivity extends AppCompatActivity {

    WallpaperManager wallpaperManager;
    Uri uri, myImageUri;
    int height, width;
    private InterstitialAd mInterstitialAd;
    private AdView mAdView;

    int[] icons =
            {R.drawable.ts0, R.drawable.ts1, R.drawable.ts2, R.drawable.ts3,
                    R.drawable.ts4, R.drawable.ts5, R.drawable.ts6,
                    R.drawable.ts7, R.drawable.ts8, R.drawable.ts9,
                    R.drawable.ts10, R.drawable.ts11, R.drawable.ts12,
                    R.drawable.ts13, R.drawable.ts14, R.drawable.ts15,
                    R.drawable.ts16, R.drawable.ts17, R.drawable.ts18,
                    R.drawable.ts19, R.drawable.ts20, R.drawable.ts21,
                    R.drawable.ts22, R.drawable.ts23, R.drawable.ts24,
                    R.drawable.ts25, R.drawable.ts26, R.drawable.ts27,
                    R.drawable.ts28, R.drawable.ts29, R.drawable.ts30,
                    R.drawable.ts31, R.drawable.ts32, R.drawable.ts33,
                    R.drawable.ts34, R.drawable.ts35, R.drawable.ts36,
                    R.drawable.ts37, R.drawable.ts38, R.drawable.ts39,
                    R.drawable.ts40, R.drawable.ts41, R.drawable.ts42,
                    R.drawable.ts43, R.drawable.ts44, R.drawable.ts45,
                    R.drawable.ts46, R.drawable.ts47, R.drawable.ts48,
                    R.drawable.ts49, R.drawable.ts50, R.drawable.ts51,
                    R.drawable.ts52, R.drawable.ts53, R.drawable.ts54,
                    R.drawable.ts55, R.drawable.ts56, R.drawable.ts57,
                    R.drawable.ts58, R.drawable.ts59, R.drawable.ts60,
                    R.drawable.ts61};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_wallpaper_selection);

        findViewById(R.id.about).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), WallpaperActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                startActivity(i);
            }
        });

        wallpaperManager = WallpaperManager.getInstance(this);
        Intent i = getIntent();
        String id = i.getStringExtra("image_id");

        int id2 = Integer.parseInt(id);

        for(int a = 0; a<62; a++)
        {
            if(id2 == a){
                uri = Uri.parse("android.resource://com.techlabs.besttaylorswiftwallpaperspro/drawable/"+icons[a]);
            }
        }

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        height = metrics.heightPixels;
        width = metrics.widthPixels;
        CropImage.activity(uri).setAspectRatio(width,height).start(this);

        // Code for Interstital AD

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-4566109119621113/2895380634");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        // Code for add
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if(resultCode == RESULT_OK){
                myImageUri = result.getUri();

                InputStream inputStream = null;
                try {
                    inputStream = getContentResolver().openInputStream(myImageUri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    wallpaperManager.setWallpaperOffsetSteps(1, 1);
                    wallpaperManager.suggestDesiredDimensions(width, height);

                    wallpaperManager.setStream(inputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Toast.makeText(this, "Wallpaper set successfully", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, WallpaperActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                    startActivity(intent);
                }



            }
            else if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception e = result.getError();
                Toast.makeText(this, "Possible error is :"+e, Toast.LENGTH_LONG).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
