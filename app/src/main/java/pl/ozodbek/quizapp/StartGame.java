package pl.ozodbek.quizapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

@SuppressWarnings("ALL")
public class StartGame extends AppCompatActivity {

    TextView timer;
    TextView mainImage;
    HashMap<String, Integer> map = new HashMap<>();
    ArrayList<String> list = new ArrayList<>();
    int index;
    Button btn1, btn2, btn3, btn4;
    TextView starScore;
    int score;
    CountDownTimer countDownTimer;
    long millisUntilFinished;
    CardView anim1,anim2,anim3,anim4;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);
        timer = findViewById(R.id.timer);
        mainImage = findViewById(R.id.mainImage);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        anim1 = findViewById(R.id.anim1);
        anim2 = findViewById(R.id.anim2);
        anim3 = findViewById(R.id.anim3);
        anim4 = findViewById(R.id.anim4);
        starScore = findViewById(R.id.starScore);

        index = 0;

        //listga javoblar keladi..
        list.add("Abu Bakir Siddiq, Abdurrahmon ibn Avf");
        list.add("Sad ibn Muoz");
        list.add("Abu Salama ibn Abdulasad");
        list.add("Payg'ambarimizning oila azolariga");
        list.add("Qur'on va Sunnat");
        list.add("Hanzala");
        list.add("Kabira gunoh qilib, Sag'ira gunohda davom etayotgan");
        list.add("Alloh rizosi uchun qarz berish");
        list.add("Alloh yo'lida qilingan,solih amal");
        list.add("Qubo");
        list.add("Islom dini hukmlarini,dalili bilan o'rganiladigan ilm");
        list.add("Tavof");
        list.add("Arofotdagi Rahmat tog'i");
        list.add("Umar ibn Hattob");
        list.add("27 barobar");
        list.add("Ju'di tog'i");
        list.add("Mus'ab ibn Umayr");


        //mapda: key uchun listdagi nomlar keladi, value uchun drawable-dan text keladi..
        map.put(list.get(0), R.string.AbuBakirSiqqdiq);
        map.put(list.get(1), R.string.SadIbnMuoz);
        map.put(list.get(2), R.string.AbuSalama);
        map.put(list.get(3), R.string.AhliBayt);
        map.put(list.get(4), R.string.QuronSunnat);
        map.put(list.get(5), R.string.Hanzala);
        map.put(list.get(6), R.string.KabiraSagira);
        map.put(list.get(7), R.string.Qarzihasana);
        map.put(list.get(8), R.string.amalisolih);
        map.put(list.get(9), R.string.qubo);
        map.put(list.get(10), R.string.fiqh);
        map.put(list.get(11), R.string.tavof);
        map.put(list.get(12), R.string.arofot);
        map.put(list.get(13), R.string.Umar);
        map.put(list.get(14), R.string.jamoat);
        map.put(list.get(15), R.string.judi);
        map.put(list.get(16), R.string.elchi);


        //shuffle metodi listdagi malumotlarni tasodifiy o'zgartiradi..
        Collections.shuffle(list);

        // Javob tanlanguncha 30 soniya sanaladi..
        millisUntilFinished = 30000;

        // Yulduzchani (javob) sanaydi..
        score = 0;

        // O'yinni boshlash uchun startGame() metodini chaqirildi..
        startGame();
    }

    // Quiz-ni boshlash uchun method..
    private void startGame() {
        // Javob tanlanguncha 30 soniya sanaladi..
        millisUntilFinished = 30000;

        // ekrandagi vaqtni sanashi uchun setText qilindi..
        // 30000-ni 1000-ga bo'linadi va 30 soniya sanaydi..
        timer.setText("" + (millisUntilFinished / 1000) + "s");

        // To'g'ri javobni ekranga setText qilindi..
        starScore.setText(score + " / " + list.size());

        // Savollani chiqarish uchun generateQuestion() metodi chaqirildi..
        generateQuestions(index);


        countDownTimer = new CountDownTimer(millisUntilFinished, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText("" + (millisUntilFinished / 1000) + "s");
            }

            @Override
            public void onFinish() {
                index++;

                //Savol tanlangan yoki tanlanmaganini tekshirish..
                if (index > list.size() - 1) {

                    // Agar tanlangan bo'lsa Asosiy rasm va  buttondagi malumotlar ko'rinmaydi..
                    mainImage.setVisibility(View.GONE);
                    btn1.setVisibility(View.GONE);
                    btn2.setVisibility(View.GONE);
                    btn3.setVisibility(View.GONE);
                    btn4.setVisibility(View.GONE);

                    // Agar Savol javob tugasa GameOver oynasiga o'tiladi..
                    Intent intent = new Intent(StartGame.this, GameOver.class);
                    intent.putExtra("natija", score);
                    startActivity(intent);

                    // StartGame oynasini tugatish..
                    finish();
                } else {

                    //Agar javob tanlanmasa User-ni hohshiga qarab keyingi oynaga ,
                    //hech nimani tanlamay o'tadi. Vaqt to'xtatiladi. startGame() qayta ochiladi.
                    countDownTimer = null;
                    startGame();
                }
            }
        }.start(); // vaqt sanashni avtomatik boshlash uchun start() metodi chaqirildi..
    }

    // Javoblarni alamashtirish uchun metod..
    private void generateQuestions(int index) {

        ArrayList<String> cloneList = (ArrayList<String>) list.clone();

        // To'g'ri javob (correctAnswer)-ga joylandi..
        String correctAnswer = list.get(index);

        // Qolgan 3 ta notogri javobni chiqarish uchun, togri javobni ochrish -
        // Arraylistdan ochirib,notogrilarini chalkashtiramiz (shuffle() bilan)
        cloneList.remove(correctAnswer);
        Collections.shuffle(cloneList);

        // Qaytarilmaydigan notogri javoblarni yangi Arraylistda saqlaymiz, bu listda -
        // togri javobi ham saqlaymiz
        ArrayList<String> newList = new ArrayList<>();
        newList.add(cloneList.get(0));
        newList.add(cloneList.get(1));
        newList.add(cloneList.get(2));
        newList.add(correctAnswer);

        // Togri javob qoshildi, endi uni notogrilari bilan aralashtiramiz..
        Collections.shuffle(newList);

        // Aralashgan javoblarni buttonlarga joylaymiz.. (setText)
        btn1.setText(newList.get(0));
        btn2.setText(newList.get(1));
        btn3.setText(newList.get(2));
        btn4.setText(newList.get(3));


        // Map-dagi valuega joylangan rasmlarni ham mapdan chaqiramiz, va hozrgi -
        // turgan rasm bilan alamshtiramiz..
        mainImage.setText(map.get(list.get(index)));
        anim1.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_left));
        anim2.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_left2));
        anim3.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_left3));
        anim4.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_left4));
    }

    // Keyingi savolga o'tganda ishlaydigan metod..
    public void nextQuestion(View view) {

        // Button bosilganini bilish uchun rang almashadi..
        btn1.setBackgroundColor(Color.parseColor("#2196f3"));
        btn2.setBackgroundColor(Color.parseColor("#2196f3"));
        btn3.setBackgroundColor(Color.parseColor("#2196f3"));
        btn4.setBackgroundColor(Color.parseColor("#2196f3"));

        // Rang o'zgargandan song, qolganlari bosilmaydi, o'zgarmaydi..
        btn1.setEnabled(true);
        btn2.setEnabled(true);
        btn3.setEnabled(true);
        btn4.setEnabled(true);

        // Javob bosilishi bilan timer toxtaydi..
        countDownTimer.cancel();
        index++;


        //Savol tanlangan yoki tanlanmaganini tekshirish..
        if (index > list.size() - 1) {

            mainImage.setVisibility(View.GONE);
            btn1.setVisibility(View.GONE);
            btn2.setVisibility(View.GONE);
            btn3.setVisibility(View.GONE);
            btn4.setVisibility(View.GONE);


            // Yulduzchaga to'plagan malumotni bilan o'yinni yakunlaydi. GameOver oynaga o'tad..
            Intent intent = new Intent(StartGame.this, GameOver.class);
            intent.putExtra("natija", score);
            startActivity(intent);
            finish();
        } else {

            //Agar user jvob tanlamagan bo'lsa keyingi savolga o'tadi, va timer ham boshqatdan sanaydi..
            countDownTimer = null;
            startGame();
        }
    }

    // Javob tanlanib bo'lgandan song ishlaydigan metod..
    public void answerSelected(View view) {

        //Javob tanlansa qolgan buttonlar bosilmay qoladi
        btn1.setEnabled(false);
        btn2.setEnabled(false);
        btn3.setEnabled(false);
        btn4.setEnabled(false);


        // timer joyida toxtaydi..
        countDownTimer.cancel();

        //Tanlangan savob answerda saqlanadi.
        String answer = ((Button) view).getText().toString().trim();

        // togri javob esa list-dan index orqali chaqirib, correctAnswerga saqlanadi..
        String correctAnswer = list.get(index);

        // Javob haqida animatsiya paydo boladi..
        view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.answer_anim));


        //Agar javob to'gri bolsa yulduzcha bittaga oshadi, yashilda togri deb yozuv paydo boladi.
        if (answer.equals(correctAnswer)) {
            //Agar javob to'gri bolsa yulduzcha bittaga oshadi..
            score++;
            starScore.setText(score + " / " + list.size());
            view.setBackgroundColor(Color.parseColor("#21FA0C"));

        } else {
            // Agar javob noto'gri bolsa qizilda notogri deb yozuv paydo boladi.
            view.setBackgroundColor(Color.parseColor("#FA0B0B"));


        }
    }
}