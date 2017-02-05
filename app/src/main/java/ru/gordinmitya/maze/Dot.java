package ru.gordinmitya.maze;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Dot implements Drawable {
    private int size;
    protected Point point;
    protected Paint paint;

    public Dot(int size, Point point, Paint paint) {
        this.size = size;
        this.point = point;
        this.paint = paint;
    }

    public Point getPoint() {
        return point;
    }

    @Override
    public void draw(Canvas canvas, Rect rect) {
        float cellSize = (float) (rect.right - rect.left) / size;
        canvas.drawRect(
                rect.left + point.x * cellSize,
                rect.top + point.y * cellSize,
                rect.left + point.x * cellSize + cellSize,
                rect.top + point.y * cellSize + cellSize,
                paint);
    }


    @Override
    public void saveToStream(OutputStreamWriter osw) {

        try {
            osw.write(point.x);
            osw.write(point.y);
            osw.write(size);
            osw.flush();
        } catch (Exception e) {
            Log.e("Maze", "Ошибка сохранения Dot в поток");
        }
    }

    @Override
    public void loadFromStream(InputStreamReader isw) {
        try {
            point.x = isw.read();
            point.y = isw.read();

            size = isw.read();
        } catch (IOException e) {
            // сохраняем в логи и передаём ошибку далее
            Log.e("Maze", "Ошибка чтения Dot из потока");
            new IOException("Ошибка чтения Dot из потока: " + e.getMessage());
        }
    }


}
