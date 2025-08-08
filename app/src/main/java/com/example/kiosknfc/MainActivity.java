package com.example.kiosknfc;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends Activity {
    private NfcAdapter nfcAdapter;
    private static final String PASSWORD = "#teamlidl";
    private DevicePolicyManager dpm;
    private ComponentName adminComponent;

    private RelativeLayout loginOverlay;
    private EditText passwordInput;
    private Button loginButton;
    private ImageView backgroundImage;
    private boolean loggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // Initialize UI elements
        loginOverlay = findViewById(R.id.loginOverlay);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        backgroundImage = findViewById(R.id.backgroundImage);

        // Set black background for login
        RelativeLayout mainLayout = findViewById(R.id.mainLayout);
        mainLayout.setBackgroundColor(android.graphics.Color.BLACK);
        backgroundImage.setVisibility(View.GONE);

        // Initialize NFC
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            finish();
        }

        // Initialize device policy manager
        dpm = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
        adminComponent = new ComponentName(this, MyDeviceAdminReceiver.class);

        // Start kiosk mode if device owner
        if (dpm.isDeviceOwnerApp(getPackageName())) {
            dpm.setLockTaskPackages(adminComponent, new String[]{getPackageName()});
            startLockTask();
        }

        // Login button listener
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = passwordInput.getText().toString();
                if (PASSWORD.equals(input)) {
                    loggedIn = true;
                    loginOverlay.setVisibility(View.GONE);
                    mainLayout.setBackgroundColor(android.graphics.Color.TRANSPARENT);
                    backgroundImage.setImageResource(R.drawable.hintergrund);
                    backgroundImage.setVisibility(View.VISIBLE);
                    Toast.makeText(MainActivity.this, "Login erfolgreich!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Falsches Passwort!", Toast.LENGTH_SHORT).show();
                    passwordInput.setText("");
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (dpm != null && dpm.isDeviceOwnerApp(getPackageName())) {
            startLockTask();
        }
        if (loggedIn && nfcAdapter != null) {
            Intent intent = new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            nfcAdapter.enableForegroundDispatch(this,
                    PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE),
                    null, null);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(this);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (loggedIn) {
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            if (tag != null) {
                Intent imageIntent = new Intent(this, ScanResultActivity.class);
                startActivity(imageIntent);
            }
        }
    }
}
