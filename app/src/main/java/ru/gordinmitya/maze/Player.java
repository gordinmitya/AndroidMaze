package ru.gordinmitya.maze;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.v4.content.ContextCompat;

public class Player extends Dot {

    public Player(Context context, Point start, int size) {
        super(size, start, getPaint(context));
    }

    private static Paint getPaint(Context context) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(ContextCompat.getColor(context, R.color.gm_player));
        return paint;
    }
}
