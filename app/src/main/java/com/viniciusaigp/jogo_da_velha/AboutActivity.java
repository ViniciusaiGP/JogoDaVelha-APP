package com.viniciusaigp.jogo_da_velha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.Objects;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);//Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);//Ativar o botão
        getSupportActionBar().setTitle("Configurações/sobre");//Titulo para ser exibido
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(this, SettingsActivity.class));
            finishAffinity();
        }
        return true;
    }
}