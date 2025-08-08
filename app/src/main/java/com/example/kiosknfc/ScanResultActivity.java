package com.example.kiosknfc;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.ortiz.touchview.TouchImageView;

public class ScanResultActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hide background image
        ImageView backgroundImage = findViewById(R.id.backgroundImage);
        backgroundImage.setVisibility(View.GONE);

        // Show scan result image
        TouchImageView scanResultImage = findViewById(R.id.scanResultImage);
        scanResultImage.setImageResource(R.drawable.scan_result);
        scanResultImage.setVisibility(View.VISIBLE);

        // Click to go back
        scanResultImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
