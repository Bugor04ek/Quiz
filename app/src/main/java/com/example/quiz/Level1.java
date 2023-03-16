package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
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

import org.w3c.dom.Text;

import java.util.Random;

public class Level1 extends AppCompatActivity {

    Dialog dialog;
    Dialog dialogEnd;

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
        text_levels.setText(R.string.level1);

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

        // Вызов диалогового окна в начале игры
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //скрываем заголовок
        dialog.setContentView(R.layout.previewdialog); // путь к макету диалогового окна
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); //прозрачный фон диалогового окна
        dialog.setCancelable(false);

        // Кнопка, закрывающая диалоговое окно

        TextView btnclose = (TextView) dialog.findViewById(R.id.btclose);
        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Level1.this, GameLevels.class);
                    startActivity(intent);
                    finish();
                }catch (Exception ignored){}
                dialog.dismiss();
            }

        });

        TextView btncontinue = (TextView) dialog.findViewById(R.id.btncontinue);
        btncontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }

        });

        dialog.show();

        //----------------------------------------------------------

        // Вызов диалогового окна в конце игры
        dialogEnd = new Dialog(this);
        dialogEnd.requestWindowFeature(Window.FEATURE_NO_TITLE); //скрываем заголовок
        dialogEnd.setContentView(R.layout.dialogend); // путь к макету диалогового окна
        dialogEnd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); //прозрачный фон диалогового окна
        dialogEnd.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialogEnd.setCancelable(false);

        // Кнопка, закрывающая диалоговое окно

        TextView btnclose2 = (TextView) dialogEnd.findViewById(R.id.btclose);
        btnclose2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Level1.this, GameLevels.class);
                    startActivity(intent);
                    finish();
                }catch (Exception ignored){}
                dialogEnd.dismiss();
            }

        });

        TextView btncontinue2 = (TextView) dialogEnd.findViewById(R.id.btncontinue);
        btncontinue2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                try
                {
                    Intent intent = new Intent(Level1.this, Level2.class);
                    startActivity(intent);
                    finish();
                }catch (Exception e)
                {

                }
                dialogEnd.dismiss();
            }

        });

        //-----------------------------------------------------------

        //Кнопка перехода на первый уровень
        TextView button_back = (TextView) findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Level1.this, GameLevels.class);
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
        final Animation a = AnimationUtils.loadAnimation(Level1.this, R.anim.alpha);

        //Рандомим числа
        numLeft = random.nextInt(10); // генерация чисел от 1 до 10
        img_left.setImageResource(array.images1[numLeft]); // Достаем из массива картинку
        text_left.setText(array.texts1[numLeft]); // Достаем из массива текст

        numRight = random.nextInt(10); // генерация чисел от 1 до 10

        while (numLeft == numRight){
            numRight = random.nextInt(10) ;
        }

        img_right.setImageResource(array.images1[numRight]);
        text_right.setText(array.texts1[numRight]);

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
                        dialogEnd.show();
                    }
                    else
                    {
                        numLeft = random.nextInt(10); // генерация чисел от 1 до 10
                        img_left.setImageResource(array.images1[numLeft]); // Достаем из массива картинку
                        img_left.startAnimation(a);
                        text_left.setText(array.texts1[numLeft]); // Достаем из массива текст

                        numRight = random.nextInt(10); // генерация чисел от 1 до 10

                        while (numLeft == numRight)
                        {
                            numRight = random.nextInt(10) ;
                        }

                        img_right.setImageResource(array.images1[numRight]);
                        img_right.startAnimation(a);
                        text_right.setText(array.texts1[numRight]);
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
                        dialogEnd.show();
                    }
                    else
                    {
                        numLeft = random.nextInt(10); // генерация чисел от 1 до 10
                        img_left.setImageResource(array.images1[numLeft]); // Достаем из массива картинку
                        img_left.startAnimation(a);
                        text_left.setText(array.texts1[numLeft]); // Достаем из массива текст

                        numRight = random.nextInt(10); // генерация чисел от 1 до 10

                        while (numLeft == numRight){
                            numRight = random.nextInt(10) ;
                        }

                        img_right.setImageResource(array.images1[numRight]);
                        img_right.startAnimation(a);
                        text_right.setText(array.texts1[numRight]);
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
            Intent intent = new Intent(Level1.this, GameLevels.class);
            startActivity(intent);
            finish();
        }catch (Exception ignored){}

    }
}
