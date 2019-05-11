package com.example.hans_pc.sobatpmi.Menu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.hans_pc.sobatpmi.MainActivity;
import com.example.hans_pc.sobatpmi.R;

public class SettingsActivity extends AppCompatActivity {

    private static final String SET_NAME = "settings";
    private static final String SET_DARK_THEME = "set_dark_theme";
    private static final String SET_FONT_LARGE = "set_font_large" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences preferences = getSharedPreferences(SET_NAME, MODE_PRIVATE);

        final boolean theme = preferences.getBoolean(SET_DARK_THEME, false);
        boolean font = preferences.getBoolean(SET_FONT_LARGE, false);

        if (theme && font) {
            setTheme(R.style.AppTheme_Dark_FontLarge);
        } else if (theme) {
            setTheme(R.style.AppTheme_Dark_FontNormal);
        } else if (font) {
            setTheme(R.style.AppTheme_FontLarge);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final Switch switchFont = findViewById(R.id.fontSizeSetting);
        switchFont.setChecked(font);
        switchFont.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = getSharedPreferences(SET_NAME, MODE_PRIVATE).edit();
                editor.putBoolean(SET_FONT_LARGE, isChecked);
                editor.apply();
                recreate();
            }
        });

        @SuppressLint("CutPasteId") final Switch switchNightMode = findViewById(R.id.nightModeSetting);
        switchNightMode.setChecked(theme);

        Button btnApply = findViewById(R.id.button_accept);
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(switchNightMode.isChecked()){
                    toggleTheme(1);
                }
                else if (!switchNightMode.isChecked()){
                    toggleTheme(0);
                }
            }
        });
    }

    private void toggleTheme(int mode) {
        switch (mode) {
            case 1:
                SharedPreferences.Editor editor = getSharedPreferences(SET_NAME, MODE_PRIVATE).edit();
                editor.putBoolean(SET_DARK_THEME, true);
                editor.apply();
                recreate();
                startActivity(new Intent(SettingsActivity.this, MainActivity.class));
                finish();
                break;
            case 0:
                editor = getSharedPreferences(SET_NAME, MODE_PRIVATE).edit();
                editor.putBoolean(SET_DARK_THEME, false);
                editor.apply();
                startActivity(new Intent (SettingsActivity.this, MainActivity.class));
                finish();
                break;
        }

    }
}
