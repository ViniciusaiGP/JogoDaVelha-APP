package com.viniciusaigp.jogo_da_velha;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import java.util.Arrays;
import java.util.Objects;

import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO;
import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES;

public class TemaActivity extends AppCompatActivity {
    private final int[] salvarTema = new int[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tema);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);//Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);//Ativar o botão
        getSupportActionBar().setTitle("Configurações/temas");//Titulo para ser exibido

        RadioButton temaClaro = findViewById(R.id.temaClaro);
        RadioButton temaEscuro = findViewById(R.id.temaEscuro);
        RadioButton temaSistema = findViewById(R.id.temaSistema);

        //temaClaro.setEnabled(false);
        //temaEscuro.setEnabled(false);


        for (RadioButton radioButton : Arrays.asList(temaClaro, temaEscuro, temaSistema)) {
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    rbTema(view);
                }
            });
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(this, SettingsActivity.class));
            finishAffinity();
        }
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    private void rbTema(View view) {

        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.temaEscuro:
                if (checked) {
                    AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES);
                }
                break;

            case R.id.temaClaro:
                if (checked) {
                    AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO);
                }
                break;

            case R.id.temaSistema:
                if (checked) {
                    AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM);
                }
                break;
        }
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void initializeTema() {
        
    }
}