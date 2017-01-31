package ru.gordinmitya.maze;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

public class Player extends Dot {

    public Player(Point start, int size) {
        super(size, start, getPaint());
    }

    private static Paint getPaint() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        return paint;
    }

    public void goTo(int x, int y) {
        point.x = x;
        point.y = y;
        Log.i("Player", String.format("%d %d", point.x, point.y));
    }

    public int getX() {
        return point.x;
    }

    public int getY() {
        return point.y;
    }
}
