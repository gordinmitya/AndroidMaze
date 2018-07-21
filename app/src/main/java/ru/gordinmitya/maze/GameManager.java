package ru.gordinmitya.maze;


import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class GameManager implements InputListener {
    private List<Drawable> drawables = new ArrayList<>();
    private View view;
    private Exit exit;
    private Player player;
    private Maze maze;
    private Rect rect = new Rect();
    private int screenSize;

    public GameManager() {
        create(5);
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
    public void onMove(MovementDirection direction) {
        int diffX = 0;
        int diffY = 0;

        switch (direction) {
            case LEFT:
                diffX = -1;
                break;
            case UP:
                diffY = -1;
                break;
            case RIGHT:
                diffX = 1;
                break;
            case DOWN:
                diffY = 1;
                break;
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

        if (exit.equals(player)) {
            create(maze.getSize() + 5);
        }

        view.invalidate();
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
}
