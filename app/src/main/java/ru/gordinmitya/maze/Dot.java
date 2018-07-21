package ru.gordinmitya.maze;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.Objects;

public class Dot implements Drawable {
    protected Point point;
    protected Paint paint;
    private int size;

    public Dot(int size, Point point, Paint paint) {
        this.size = size;
        this.point = point;
        this.paint = paint;
    }

    public Point getPoint() {
        return point;
    }

    public void goTo(int x, int y) {
        point.x = x;
        point.y = y;
    }

    public int getX() {
        return point.x;
    }

    public int getY() {
        return point.y;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dot)) return false;
        Dot dot = (Dot) o;
        return size == dot.size &&
                Objects.equals(point, dot.point);
    }

    @Override
    public int hashCode() {

        return Objects.hash(size, point, paint);
    }
}
