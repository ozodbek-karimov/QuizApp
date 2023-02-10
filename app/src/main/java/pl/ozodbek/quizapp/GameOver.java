package pl.ozodbek.quizapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GameOver extends AppCompatActivity {

    TextView trueAnswer, bestAnswer;
    SharedPreferences scoreReader;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        //Intent-dan kelgan malumotlarni resevedScore-ga saqlandi..
        int receivedScore = getIntent().getExtras().getInt("natija");

        trueAnswer = findViewById(R.id.trueAnswer);
        bestAnswer = findViewById(R.id.bestAnswer);

        //Quiz davomida toplangan ball trueAnswer-ga saqlandi..
        trueAnswer.setText("" + receivedScore);

        //SharedPreferenses obyektiga pref nomi orqali 0 boshlang'ich shaklda saqlandi.
        // chunki eng yuqori ballni topish uchun..
        scoreReader = getSharedPreferences("pref", 0);

        //scoreReader-dagi malumotni receivedScoreReader-ga int formatida berildi..
        int receiveScoreReader = scoreReader.getInt("ScoreReaderDATA", 0);

        // sharedPreferenses-dagi malumotni oqib olish uchun Editor-dan foydalanildi ( BufferedReader0ga o'xshash)
        SharedPreferences.Editor editor = scoreReader.edit();

        //Agarda quizdan kelgan ball Reader-dagi balldan katta bolsa ,kelgan ball ekranga uzatildi
        if (receivedScore > receiveScoreReader) {

            receiveScoreReader = receivedScore;

            editor.putInt("ScoreReaderDATA", receiveScoreReader);

            //commit() malumotlani saqlab qoladi...( flush()- ga o'xshash)
            editor.commit();
        }

        //ekranga uzatilish jarayoni..
        bestAnswer.setText("" + receiveScoreReader);
    }


    // Qayta o'yin boshlanish qismi..
    public void restart(View view) {

        //restart bosilsa Intent bilan StartGame oynasiga o'tkazildi..
        Intent intent = new Intent(GameOver.this, StartGame.class);
        startActivity(intent);
        finish();
    }

    // Quizdan chiqish..
    public void exit(View view) {

        // dasturdan finish() metodi orqali chiqib ketiladi..
        finish();
    }
}