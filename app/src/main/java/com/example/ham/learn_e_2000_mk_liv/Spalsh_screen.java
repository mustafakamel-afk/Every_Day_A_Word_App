package com.example.ham.learn_e_2000_mk_liv;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Spalsh_screen extends AppCompatActivity {
    sql sqlite = new sql(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spalsh_screen);
        Thread T = new Thread() {
            public void run() {
                try {
                    sleep(2500);
                    Intent I = new Intent(getApplicationContext(), main.class);
                    startActivity(I);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        };
        T.start();
    }
}
