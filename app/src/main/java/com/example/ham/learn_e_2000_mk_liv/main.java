package com.example.ham.learn_e_2000_mk_liv;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class main extends AppCompatActivity implements TextToSpeech.OnInitListener {
    private String[] mTestArray, words_in_arabic;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private Date date = new Date();
    private TextView textView2, The_right_word, The_wrong_word, textView, the_word_of_today, test;
    private SharedPreferences preference_shared;
    SharedPreferences text_shared;
    private SharedPreferences text_shared_id, text_shared_in_arabic;
    private SharedPreferences.Editor edit;
    private sql sqlite = new sql(this);
    private TextToSpeech textToSpeech;
    private EditText edittext;
    private ImageView next, sound, next2;
    View v;
    private Button button;
    private long time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_a);
        next = (ImageView) findViewById(R.id.next);
        next2 = (ImageView) findViewById(R.id.next2);
        sound = (ImageView) findViewById(R.id.sound);
        textView = (TextView) findViewById(R.id.text_view);
        textView2 = (TextView) findViewById(R.id.text_view2);
        test = (TextView) findViewById(R.id.test);
        The_right_word = (TextView) findViewById(R.id.The_right_word);
        The_wrong_word = (TextView) findViewById(R.id.The_wrong_word);
        button = (Button) findViewById(R.id.button);
        mTestArray = getResources().getStringArray(R.array.array_mk);
        words_in_arabic = getResources().getStringArray(R.array.words_in_arabic);
        textToSpeech = new TextToSpeech(this, this);
        edittext = (EditText) findViewById(R.id.edit_Text);
        the_word_of_today = (TextView) findViewById(R.id.the_word_of_today);
        //=====================================================
        preference_shared = this.getSharedPreferences("PREFERENCE", MODE_PRIVATE);
        text_shared = this.getSharedPreferences("TEXT", MODE_PRIVATE);
        text_shared_in_arabic = this.getSharedPreferences("TEXT2", MODE_PRIVATE);
        text_shared_id = this.getSharedPreferences("ID", MODE_PRIVATE);
        if (preference_shared.getBoolean("isFirstRun", true)) {
            textView.setText(mTestArray[(0) % (mTestArray.length)]);
            text_shared.edit().putString("Text", textView.getText().toString()).apply();
            // النص لأول مرة يأخذ العنصر من القائمة
            textView2.setText(words_in_arabic[(0) % (words_in_arabic.length)]);
            text_shared_in_arabic.edit().putString("Text2", textView2.getText().toString()).apply();
            ArrayList arrayList = sqlite.get_wordss();
            if (arrayList.isEmpty()) {
                text_shared_id.edit().putString("id", "1").apply();
            }
            Save_Date();
            String Full_word = text_shared.getString("Text", "") + "    " + text_shared_in_arabic.getString("Text2", "");
            sqlite.insert_words(Full_word);
        } else {
            if (!Objects.equals(preference_shared.getString("Date", ""), dateFormat.format(date))) {
                String text_id = text_shared_id.getString("id", "");
                int id = Integer.parseInt(text_id);
                textView.setText(mTestArray[id]);
                textView2.setText(words_in_arabic[id]);
                text_shared.edit().putString("Text", textView.getText().toString()).apply();
                text_shared_in_arabic.edit().putString("Text2", textView2.getText().toString()).apply();
                id++;
                String text_id_2 = String.valueOf(id);
                text_shared_id.edit().putString("id", text_id_2).apply();
                String Full_word = text_shared.getString("Text", "") + "      " + text_shared_in_arabic.getString("Text2", "");
                sqlite.insert_words(Full_word);
                Save_Date();
                Notification_();
            } else {
                // الأوامر التي يتم تنفيذها في حال كان اليوم نفسه
                textView.setText(text_shared.getString("Text", ""));
                textView2.setText(text_shared_in_arabic.getString("Text2", ""));
                // يتم وضع النص الذي تم حفظه وهو الذي تم اختياره عشوائياً ليتم عرضه طوال اليوم
            }
        }
        preference_shared.edit().putBoolean("isFirstRun", false).apply(); // يتم تعيين قيمة تشغيل التطبيق لاول مرة .. إي الغائه
    }

    private void Save_Date() {
        preference_shared = this.getSharedPreferences("PREFERENCE", MODE_PRIVATE);
        edit = preference_shared.edit();
        edit.clear();
        edit.putString("Date", dateFormat.format(date));
        edit.apply();
    }

    @Override
    public void onInit(int i) {
        if (i != TextToSpeech.ERROR) {
            textToSpeech.setLanguage(Locale.UK);
        }
    }

    public void sound(View view) {
        textToSpeech.speak(text_shared.getString("Text", ""), TextToSpeech.QUEUE_FLUSH, null);
    }

    public void next2(View view) {
        String the_word = edittext.getText().toString().trim();
        if (the_word.equals(text_shared.getString("Text", ""))) {
            Toast.makeText(main.this, "أحسنت", Toast.LENGTH_LONG).show();
            sound.setVisibility(View.VISIBLE);
            textView.setVisibility(v.VISIBLE);
            textView2.setVisibility(v.VISIBLE);
            The_wrong_word.setVisibility(v.GONE);
            next.setVisibility(v.VISIBLE);
            edittext.setVisibility(v.GONE);
            next2.setVisibility(v.GONE);
            the_word_of_today.setVisibility(v.VISIBLE);
            test.setVisibility(v.GONE);
            edittext.setText("");

        } else if (the_word.isEmpty()) {
            Toast.makeText(main.this, "أكتب الكلمه", Toast.LENGTH_LONG).show();
        } else {
            edittext.setVisibility(v.GONE);
            next2.setVisibility(v.GONE);
            button.setVisibility(v.VISIBLE);
            The_wrong_word.setVisibility(v.VISIBLE);
            The_right_word.setVisibility(v.VISIBLE);
            The_right_word.setText(text_shared.getString("Text", "") + "   ");
            The_wrong_word.setText(edittext.getText().toString() + "   ");

        }
    }

    public void next(View view) {
        sound.setVisibility(v.GONE);
        textView.setVisibility(v.GONE);
        textView2.setVisibility(v.GONE);
        next.setVisibility(v.GONE);
        edittext.setVisibility(v.VISIBLE);
        next2.setVisibility(v.VISIBLE);
        test.setVisibility(v.VISIBLE);
        the_word_of_today.setVisibility(v.GONE);
    }

    private void Notification_() {
        java.util.Calendar C = java.util.Calendar.getInstance();
        C.set(java.util.Calendar.HOUR_OF_DAY, 12);
        C.set(java.util.Calendar.MINUTE, 30);
        C.set(java.util.Calendar.SECOND, 0);
        Intent alarm = new Intent(getApplicationContext(), receiver.class);
        AlarmManager alarm_M = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarm_M.set(AlarmManager.RTC_WAKEUP, C.getTimeInMillis(), PendingIntent.getBroadcast(getApplicationContext(), 0, alarm, PendingIntent.FLAG_UPDATE_CURRENT));
    }

    public void button(View view) {
        edittext.setVisibility(v.VISIBLE);
        next2.setVisibility(v.VISIBLE);
        The_wrong_word.setVisibility(v.INVISIBLE);
        The_right_word.setVisibility(v.INVISIBLE);
        button.setVisibility(v.GONE);
    }

    @Override
    public void onBackPressed() {
        if (time + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            Toast.makeText(main.this, "أضغط مرة اخرى للخروج", Toast.LENGTH_LONG).show();
        }
        time = System.currentTimeMillis();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_of_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.email) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setData(Uri.parse("mailto:"));
            intent.setType("message/rfc822");
            intent.putExtra(Intent.EXTRA_EMAIL, "mkcompany445@gmail.com");
            intent.putExtra(Intent.EXTRA_SUBJECT, "اقتراح");
            intent.putExtra(Intent.EXTRA_TEXT, "عزيزي المستخدم يمكنك ارسال اي اقتراح عبر البريد الالكتروني الخاص بنا mkcompany445@gmail.com ");
            startActivity(Intent.createChooser(intent, "اتصل بنا"));
        } else if (id == R.id.memo) {
            Intent intent = new Intent(main.this, memo.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.list) {
            Intent intent = new Intent(main.this, list_of_words.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
