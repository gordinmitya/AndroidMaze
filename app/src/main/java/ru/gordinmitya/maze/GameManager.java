package ru.gordinmitya.maze;


import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class GameManager extends GestureDetector.SimpleOnGestureListener {
    private List<Drawable> drawables = new ArrayList<>();
    private View view;
    private Player player;

    public GameManager() {
        player = new Player();

        drawables.add(player);
        drawables.add(new Maze());
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        int diffX = 0, diffY = 0;
        diffX = Math.round(e2.getX() - e1.getX());
        diffY = Math.round(e2.getY() - e1.getY());
        player.move(diffX, diffY);
        view.invalidate();
        return super.onFling(e1, e2, velocityX, velocityY);
    }

    public void draw(Canvas canvas) {
        for (Drawable drawableItem:
             drawables) {
            drawableItem.draw(canvas);
        }
    }

    public void setView(View view) {
        this.view = view;
    }
}
