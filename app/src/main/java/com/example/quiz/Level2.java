package com.example.quiz;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class Level2 extends AppCompatActivity {

    Dialog dialog;

    public int numLeft; // переменная для левой картинки + текст
    public int numRight; // переменная для правой картинки + текст
    Array array = new Array();
    Random random = new Random();
    public int count = 0; // счетчик правильных ответов

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.universal);

        //Создаем переменную text_levels
        TextView text_levels = findViewById(R.id.text_levels);
        text_levels.setText(R.string.level2);

        // скругление кртинок
        final ImageView img_left = (ImageView)findViewById(R.id.img_left);
        img_left.setClipToOutline(true);

        final ImageView img_right = (ImageView)findViewById(R.id.img_right);
        img_right.setClipToOutline(true);

        // Путь к левой TextView
        final TextView text_left = findViewById(R.id.text_left);
        // Путь к правой TextView
        final TextView text_right = findViewById(R.id.text_right);

        //Развернуть игру на весь экран
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        //Кнопка перехода на первый уровень
        TextView button_back = (TextView) findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Level2.this, GameLevels.class);
                    startActivity(intent);
                    finish();
                }catch (Exception ignored){}
            }
        });

        //Массив для прогресса игры
        final int[] progress = {
                R.id.point1, R.id.point2, R.id.point3, R.id.point4, R.id.point5,
                R.id.point6, R.id.point7, R.id.point8, R.id.point9, R.id.point10,
                R.id.point11, R.id.point12, R.id.point13, R.id.point14, R.id.point15,
                R.id.point16, R.id.point17, R.id.point18, R.id.point19, R.id.point20,
        };

        //Подключаем анимацию
        final Animation a = AnimationUtils.loadAnimation(Level2.this, R.anim.alpha);

        //Рандомим числа
        numLeft = random.nextInt(10); // генерация чисел от 1 до 10
        img_left.setImageResource(array.images2[numLeft]); // Достаем из массива картинку
        text_left.setText(array.texts2[numLeft]); // Достаем из массива текст

        numRight = random.nextInt(10); // генерация чисел от 1 до 10

        while (numLeft == numRight){
            numRight = random.nextInt(10) ;
        }

        img_right.setImageResource(array.images2[numRight]);
        text_right.setText(array.texts2[numRight]);

        //Обрабатываем нажатие на левую картинку

        img_left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                // условие касания картинки
                if (motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                    // если коснулся картинки
                    img_right.setEnabled(false); // блокируем правую картинку
                    if (numLeft > numRight){
                        img_left.setImageResource(R.drawable.img_true);
                    } else{
                        img_left.setImageResource(R.drawable.img_false);
                    }
                }else if (motionEvent.getAction()==MotionEvent.ACTION_UP){
                // если отпустил палец
                    if (numLeft > numRight){
                        if (count < 20){
                            count += 1;
                        }
                        // Закрашываем прогресс серым цветом
                        for (int i = 0; i < 20; i++)
                        {
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points);
                        }
                        // Закрашываем прогресс зеленым цветом
                        for (int i = 0; i < count; i++)
                        {
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points_green);
                        }
                    }else{
                        if (count > 0)
                        {
                            if (count == 1)
                            {
                                count = 0;
                            } else
                            {
                                count -= 2;
                            }
                        }
                        // Закрашываем прогресс серым цветом
                        for (int i = 0; i < 19; i++)
                        {
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points);
                        }
                        // Закрашываем прогресс зеленым цветом
                        for (int i = 0; i < count; i++)
                        {
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points_green);
                        }
                    }
                    if (count == 20)
                    {
                        //Выход из уровня

                    }
                    else
                    {
                        numLeft = random.nextInt(10); // генерация чисел от 1 до 10
                        img_left.setImageResource(array.images2[numLeft]); // Достаем из массива картинку
                        img_left.startAnimation(a);
                        text_left.setText(array.texts2[numLeft]); // Достаем из массива текст

                        numRight = random.nextInt(10); // генерация чисел от 1 до 10

                        while (numLeft == numRight)
                        {
                            numRight = random.nextInt(10) ;
                        }

                        img_right.setImageResource(array.images2[numRight]);
                        img_right.startAnimation(a);
                        text_right.setText(array.texts2[numRight]);
                        img_right.setEnabled(true); // включаем обратно правую картинку
                    }
                }

                return true;
            }
        });

        //Обрабатываем нажатие на правую картинку
        img_right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                // условие касания картинки
                if (motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                    // если коснулся картинки
                    img_left.setEnabled(false); // блокируем левую картинку
                    if (numLeft < numRight){
                        img_right.setImageResource(R.drawable.img_true);
                    } else{
                        img_right.setImageResource(R.drawable.img_false);
                    }
                }else if (motionEvent.getAction()==MotionEvent.ACTION_UP){
                    // если отпустил палец
                    if (numLeft < numRight){
                        if (count < 20){
                            count += 1;
                        }
                        // Закрашываем прогресс серым цветом
                        for (int i = 0; i < 20; i++)
                        {
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points);
                        }
                        // Закрашываем прогресс зеленым цветом
                        for (int i = 0; i < count; i++)
                        {
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points_green);
                        }
                    }else{
                        if (count > 0)
                        {
                            if (count == 1)
                            {
                                count = 0;
                            } else
                            {
                                count -= 2;
                            }
                        }
                        // Закрашываем прогресс серым цветом
                        for (int i = 0; i < 19; i++)
                        {
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points);
                        }
                        // Закрашываем прогресс зеленым цветом
                        for (int i = 0; i < count; i++)
                        {
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points_green);
                        }
                    }
                    if (count == 20)
                    {
                        //Выход из уровня

                    }
                    else
                    {
                        numLeft = random.nextInt(10); // генерация чисел от 1 до 10
                        img_left.setImageResource(array.images2[numLeft]); // Достаем из массива картинку
                        img_left.startAnimation(a);
                        text_left.setText(array.texts2[numLeft]); // Достаем из массива текст

                        numRight = random.nextInt(10); // генерация чисел от 1 до 10

                        while (numLeft == numRight){
                            numRight = random.nextInt(10) ;
                        }

                        img_right.setImageResource(array.images2[numRight]);
                        img_right.startAnimation(a);
                        text_right.setText(array.texts2[numRight]);
                        img_left.setEnabled(true); // включаем обратно левую картинку
                    }
                }

                return true;
            }
        });
    }
    // Системная кнопка "Назад"
    @Override
    public void onBackPressed() {
        try {
            Intent intent = new Intent(Level2.this, GameLevels.class);
            startActivity(intent);
            finish();
        }catch (Exception ignored){}

    }
}
