package com.arman.traplus.Table_manner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.arman.traplus.R;


public class TableMannerClassfication extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tm_classification);

    }
        public void BeforeEating (View view){
            Intent intent = new Intent(TableMannerClassfication.this, BeforeEating.class);
            startActivity(intent);
        }

        public void WhileEating (View view){
            Intent i = new Intent(TableMannerClassfication.this, WhileEating.class);
            startActivity(i);
        }
        public void Drinking (View view){
            Intent i = new Intent(TableMannerClassfication.this, Drinking.class);
            startActivity(i);
        }
        public void AfterMeal (View view){
            Intent i = new Intent(TableMannerClassfication.this, AfterMeal.class);
            startActivity(i);
        }
        public void Others (View view){
            Intent i = new Intent(TableMannerClassfication.this, Others.class);
            startActivity(i);
        }
    }
