package com.techlabs.besttaylorswiftwallpaperspro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WallpaperActivity extends AppCompatActivity {

    int[] icons =
            {R.drawable.its0, R.drawable.its1, R.drawable.its2, R.drawable.its3,
                    R.drawable.its4, R.drawable.its5, R.drawable.its6,
                    R.drawable.its7, R.drawable.its8, R.drawable.its9,
                    R.drawable.its10, R.drawable.its11, R.drawable.its12,
                    R.drawable.its13, R.drawable.its14, R.drawable.its15,
                    R.drawable.its16, R.drawable.its17, R.drawable.its18,
                    R.drawable.its19, R.drawable.its20, R.drawable.its21,
                    R.drawable.its22, R.drawable.its23, R.drawable.its24,
                    R.drawable.its25, R.drawable.its26, R.drawable.its27,
                    R.drawable.its28, R.drawable.its29, R.drawable.its30,
                    R.drawable.its31, R.drawable.its32, R.drawable.its33,
                    R.drawable.its34, R.drawable.its35, R.drawable.its36,
                    R.drawable.its37, R.drawable.its38, R.drawable.its39,
                    R.drawable.its40, R.drawable.its41, R.drawable.its42,
                    R.drawable.its43, R.drawable.its44, R.drawable.its45,
                    R.drawable.its46, R.drawable.its47, R.drawable.its48,
                    R.drawable.its49, R.drawable.its50, R.drawable.its51,
                    R.drawable.its52, R.drawable.its53, R.drawable.its54,
                    R.drawable.its55, R.drawable.its56, R.drawable.its57,
                    R.drawable.its58, R.drawable.its59, R.drawable.its60,
                    R.drawable.its61
            };

    String[] imageTitle = new String[62];

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_wallpaper);

        for(int i=0; i<62; i++)
        {
            imageTitle[i] = "Wallpaper Type: "+i;
        }

        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < 62; i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("listview_title", imageTitle[i]);
            hm.put("listview_image", Integer.toString(icons[i]));
            aList.add(hm);
        }

        String[] from = {"listview_image", "listview_title"};
        int[] to = {R.id.listview_image, R.id.listview_item_title};

        SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.custom_list_view, from, to);
        final ListView androidListView = (ListView) findViewById(R.id.myList);
        androidListView.setAdapter(simpleAdapter);

        androidListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int id =i;
                Toast.makeText(getBaseContext(), "Click CROP option after cropping to set wallpaper.", Toast.LENGTH_LONG).show();
                Intent i2 = new Intent(getApplicationContext(), WallpaperSelectionActivity.class);
                i2.putExtra("image_id", ""+id);
                startActivity(i2);
            }
        });


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
}
