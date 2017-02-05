package ru.gordinmitya.maze;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    private MazeView view;
    private GestureDetector gestureDetector;
    private final String MAZE_KEY = "Maze";
    private GameManager gameManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            // поток через который будем считывать данные
            InputStream is = null;
            try {
                try {
                    // получаем из сохранения массив байтов c именем MAZE_KEY и преобразуем его в поток.
                    // идея использования потока была в том, чтоб в случае реализации возможности сохранения
                    // в файл, просто заменить поток ByteArrayInputStream на FileInputStream
                    // без переписывания кода
                    is = new ByteArrayInputStream(savedInstanceState.getByteArray(MAZE_KEY));
                    // конструктор принимающий поток данных
                    gameManager = new GameManager(is);
                }
                // блок обязательного выполнения
                finally {
                    // если поток существует, то закрываем его
                    if (is != null)
                        is.close();
                }
            } catch (Exception e) {
                Log.e("Maze", "Ошибка чтения состояния из потока : " + e.getMessage());
                // пытаемся сделать вид, что всё хорошо, вызвав другой конструктор
                // конструктор по умолчанию без параметров

            }

        }
        // если сохранённых данных нет, то
        else
            // конструктор по умолчанию без параметров
            gameManager = new GameManager();

        view = new MazeView(this, gameManager);
        setContentView(view);

        gestureDetector = new GestureDetector(this, gameManager);
    }


    // сохранение временного состояния приложения для последующего восстановления
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        OutputStream os = null;
        try {
            try {
                // создаём поток в памяти из байтов
                os = new ByteArrayOutputStream();
                // вызываем метод сохраняющий состояние игры
                gameManager.saveToStream(os);
                // превращаем поток с сохраненными данными в строку, а строку в массив байтов
                // массив байтов сохраняем с именем MAZE_KEY
                outState.putByteArray(MAZE_KEY, os.toString().getBytes());
            }
            // обязательный блок, освобождающий ресурсы
            finally {
                if (os != null) os.close();
            }
        } catch (IOException e) {
            Log.e("Maze", "Ошибка записи состояния в потоке : " + e.getMessage());
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }
}
