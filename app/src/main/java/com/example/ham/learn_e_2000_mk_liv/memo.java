package com.example.ham.learn_e_2000_mk_liv;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import android.content.SharedPreferences;

public class memo extends AppCompatActivity {
    private String memo;
    private EditText editText_memo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);
        editText_memo = (EditText) findViewById(R.id.editText_memo);
        SharedPreferences sharedPreferences = getSharedPreferences("PREPS", 0);
        memo = sharedPreferences.getString("memo", "");
        editText_memo.setText(memo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.back_memo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.back_memo) {
            Intent intent = new Intent(getApplicationContext(), main.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(memo.this, main.class);
        startActivity(intent);
        finish();
    }

    public void button_save(View view) {
        memo = editText_memo.getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences("PREPS", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("memo", memo);
        editor.commit();
        Toast.makeText(memo.this, "تم الحفظ", Toast.LENGTH_SHORT).show();
    }
}
