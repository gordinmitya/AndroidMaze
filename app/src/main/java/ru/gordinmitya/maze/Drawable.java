package ru.gordinmitya.maze;


import android.graphics.Canvas;
import android.graphics.Rect;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

interface Drawable {
    // отрисовка
    void draw(Canvas canvas, Rect rect);

    // сохранение состояния в поток
    void saveToStream(OutputStreamWriter sw);

    // загрузка состояния из потока
    void loadFromStream(InputStreamReader sr);

}
