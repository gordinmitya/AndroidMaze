package ru.gordinmitya.maze;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class GameManager extends GestureDetector.SimpleOnGestureListener {
    private List<Drawable> drawables = new ArrayList<>();
    private View view;
    private Exit exit;
    private Player player;
    private Maze maze;
    private Rect rect = new Rect();
    private int screenSize;


    // конструктор для нового лабиринта
    public GameManager() {
        create(5);
    }

    // конструктор для загрузки лабиринта из потока
    public GameManager(InputStream is) {
        loadFromStream(is);
    }

    private void create(int size) {
        drawables.clear();
        maze = new Maze(size);
        drawables.add(maze);
        exit = new Exit(maze.getEnd(), size);
        drawables.add(exit);
        player = new Player(maze.getStart(), size);
        drawables.add(player);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        int diffX, diffY;
        diffX = Math.round(e2.getX() - e1.getX());
        diffY = Math.round(e2.getY() - e1.getY());
        if (Math.abs(diffX) > Math.abs(diffY)) {
            diffX = diffX > 0 ? 1 : -1;
            diffY = 0;
        } else {
            diffX = 0;
            diffY = diffY > 0 ? 1 : -1;
        }
        int stepX = player.getX();
        int stepY = player.getY();

        while (maze.canPlayerGoTo(stepX + diffX, stepY + diffY)) {
            stepX += diffX;
            stepY += diffY;
            if (diffX != 0) {
                if (maze.canPlayerGoTo(stepX, stepY + 1)
                        || maze.canPlayerGoTo(stepX, stepY - 1)) {
                    break;
                }
            }
            if (diffY != 0) {
                if (maze.canPlayerGoTo(stepX + 1, stepY)
                        || maze.canPlayerGoTo(stepX - 1, stepY)) {
                    break;
                }
            }
        }
        player.goTo(stepX, stepY);

        if (exit.getPoint().equals(player.getPoint())) {
            create(maze.getSize() + 5);
        }

        view.invalidate();
        return super.onFling(e1, e2, velocityX, velocityY);
    }

    public void draw(Canvas canvas) {
        for (Drawable drawableItem :
                drawables) {
            drawableItem.draw(canvas, rect);
        }
    }

    public void setView(View view) {
        this.view = view;
    }

    public void setScreenSize(int width, int height) {
        screenSize = Math.min(width, height);
        rect.set(
                (width - screenSize) / 2,
                (height - screenSize) / 2,
                (width + screenSize) / 2,
                (height + screenSize) / 2
        );
    }

    public void saveToStream(OutputStream os) {
        OutputStreamWriter osw = null;
        try {
            try {
                // Вспомогательный класс для записи в  поток данных
                osw = new OutputStreamWriter(os);
                for (Drawable item : drawables) {
                    item.saveToStream(osw);
                }
            } finally {
                if (osw != null) osw.close();
            }

        } catch (Exception e) {
            Log.e("Maze", "Ошибка записи  в поток");
        }

    }


    public void loadFromStream(InputStream is) {
        InputStreamReader isw = null;
        try {
            try {
                // Вспомогательный класс для чтения потока данных
                isw = new InputStreamReader(is);
                drawables.clear();
                //конструктор с загрузкой лабиринта из потока
                maze = new Maze(isw);
                drawables.add(maze);
                int size = maze.getSize();
                exit = new Exit(maze.getEnd(), size);
                exit.loadFromStream(isw);
                drawables.add(exit);
                player = new Player(maze.getStart(), size);
                player.loadFromStream(isw);
                drawables.add(player);
            } finally {
                if (isw != null) {
                    isw.close();
                }
            }
        } catch (IOException e) {
            Log.e("Maze", "Ошибка чтения  из потока");
        }
    }

}

