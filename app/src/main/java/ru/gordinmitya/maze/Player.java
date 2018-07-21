package ru.gordinmitya.maze;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

public class Player extends Dot {

    public Player(Point start, int size) {
        super(size, start, getPaint());
    }

    private static Paint getPaint() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        return paint;
    }
}
