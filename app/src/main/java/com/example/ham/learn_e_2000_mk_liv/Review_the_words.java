package com.example.ham.learn_e_2000_mk_liv;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

public class Review_the_words extends AppCompatActivity implements TextToSpeech.OnInitListener {
    private TextView textView;
    private ImageView sound2;
    private TextToSpeech textToSpeech;
    private String word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_the_words);
        textView = (TextView) findViewById(R.id.T);
        sound2 = (ImageView) findViewById(R.id.sound_2);
        Intent intent = getIntent();
        word = intent.getExtras().getString("word");
        textView.setText(word);
        textToSpeech = new TextToSpeech(this, this);
    }

    public void sound_2(View view) {
        textToSpeech.speak(word, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void onInit(int i) {
        if (i != TextToSpeech.ERROR) {
            textToSpeech.setLanguage(Locale.UK);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list_of_words_2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.back_list_of_words_2) {
            Intent intent = new Intent(getApplicationContext(), list_of_words.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Review_the_words.this, list_of_words.class);
        startActivity(intent);
        finish();
    }
}
