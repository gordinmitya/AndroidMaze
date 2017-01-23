package ru.gordinmitya.maze;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Maze implements Drawable {
    private Paint paint;

    public Maze() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.GREEN);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(100,100,75, paint);
    }
}
