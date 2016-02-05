package com.gildder.invenbras.gestionactivos.views;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Outline;
import android.os.Bundle;
import android.view.View;

import com.gildder.invenbras.gestionactivos.R;

public class MainActivity extends Activity {
    Outline outline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v){
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }
}
