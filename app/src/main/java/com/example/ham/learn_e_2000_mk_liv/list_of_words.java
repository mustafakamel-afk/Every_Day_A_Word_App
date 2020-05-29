package com.example.ham.learn_e_2000_mk_liv;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class list_of_words extends AppCompatActivity {
    private ListView listView;
    private sql sqlite = new sql(this);
    private ArrayList<String> list_Draft = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_words);

        listView = (ListView) findViewById(R.id.list_view);
        final ArrayList<String> arrayList = sqlite.get_wordss();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this, R.layout.row_item, R.id.text_of_list, arrayList);
        listView.setAdapter(arrayAdapter);
        for (int i = 0; i < arrayList.size(); i++) {
            list_Draft.add(arrayList.get(i));
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String word = list_Draft.get(i);
                Intent intent = new Intent(list_of_words.this, Review_the_words.class);
                intent.putExtra("word", word);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_of_word, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.back_list_of_words) {
            Intent intent = new Intent(getApplicationContext(), main.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(list_of_words.this, main.class);
        startActivity(intent);
        finish();
    }

}

