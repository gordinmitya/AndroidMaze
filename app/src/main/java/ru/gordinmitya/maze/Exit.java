package ru.gordinmitya.maze;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.v4.content.ContextCompat;

public class Exit extends Dot {
    public Exit(Context context, Point point, int size) {
        super(size, point, getPaint(context));
    }
    private static Paint getPaint(Context context) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(ContextCompat.getColor(context, R.color.gm_exit));
        return paint;
    }
}
