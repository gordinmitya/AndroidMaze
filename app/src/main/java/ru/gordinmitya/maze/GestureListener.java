package ru.gordinmitya.maze;

import android.view.GestureDetector;
import android.view.MotionEvent;

import static ru.gordinmitya.maze.MovementDirection.DOWN;
import static ru.gordinmitya.maze.MovementDirection.LEFT;
import static ru.gordinmitya.maze.MovementDirection.RIGHT;
import static ru.gordinmitya.maze.MovementDirection.UP;

public class GestureListener extends GestureDetector.SimpleOnGestureListener {
    private InputListener listener;

    public GestureListener(InputListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        int diffX, diffY;
        diffX = Math.round(e2.getX() - e1.getX());
        diffY = Math.round(e2.getY() - e1.getY());
        if (Math.abs(diffX) > Math.abs(diffY)) {
            if (diffX > 0) {
                listener.onMove(RIGHT);
            } else {
                listener.onMove(LEFT);
            }
        } else {
            if (diffX > 0) {
                listener.onMove(UP);
            } else {
                listener.onMove(DOWN);
            }
        }
        return true;
    }
}
