package ru.gordinmitya.maze;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class GameManager implements InputListener {
    private List<Drawable> drawables = new ArrayList<>();
    private Context context;
    private View view;
    private Player player;
    private Maze maze;
    private Rect rect = new Rect();

    public GameManager(Context context) {
        this.context = context;
        create(31);
    }

    private void create(int size) {
        drawables.clear();

        maze = new Maze(context, size);
        drawables.add(maze);
        player = new Player(context, maze.getStart(), size);
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

        if (player.getX() == 0 || player.getY() == 0) {
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
        int screenSize = Math.min(width, height);
        rect.set(
                (width - screenSize) / 2,
                (height - screenSize) / 2,
                (width + screenSize) / 2,
                (height + screenSize) / 2
        );
    }
}
