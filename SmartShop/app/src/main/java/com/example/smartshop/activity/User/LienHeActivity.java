package com.example.smartshop.activity.User;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.DrawableCompat;

import com.example.smartshop.R;

public class LienHeActivity extends AppCompatActivity {

    Toolbar toolbarlienhe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lien_he);
        toolbarlienhe = findViewById(R.id.toolbarlienhe);
        ActionToolbar();
    }

    private void ActionToolbar(){
        if (toolbarlienhe != null) {
            setSupportActionBar(toolbarlienhe);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                Drawable navIcon = toolbarlienhe.getNavigationIcon();
                if (navIcon != null) {
                    navIcon = DrawableCompat.wrap(navIcon);
                    DrawableCompat.setTint(navIcon, Color.WHITE);
                    toolbarlienhe.setNavigationIcon(navIcon);
                }
            }
            toolbarlienhe.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } else {
            Log.e("ToolbarError", "Toolbar is null");
        }
    }
}