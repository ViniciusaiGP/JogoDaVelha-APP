package com.viniciusaigp.jogo_da_velha;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {

    private Button temasStart;
    private Button sobre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);//Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);//Ativar o botão
        getSupportActionBar().setTitle("Configurações");//Titulo para ser exibido

        temasStart = findViewById(R.id.btnTemaStart);
        sobre = findViewById(R.id.btnSobre);

        temasStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, TemaActivity.class);
                startActivity(intent);
            }
        });

        sobre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(this, MainActivity.class));
            finishAffinity();
        }
        return true;
    }
}