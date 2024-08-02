package duckproject;
import processing.core.*;

public class DuckProject extends PApplet {
    PImage duck;
    PImage background;
    PImage obstacle;

    int duckX = 70;
    int duckY = 300;

    int bgX = 0;
    int bgY = 0;

    int obstacleX;
    int obstacleY = 330;

    float speed = 8;

    public static void main(String[] args) {
        PApplet.main("duckproject.DuckProject");
    }

    public void settings() {
        size(700, 400);
    }

    public void setup() {
        duck = loadImage("Images/Duck.png");
        background = loadImage("Images/Sky.png");
        obstacle = loadImage("Images/Hydrant.png");
    }

    public void draw() {
        drawBackground();
        drawDuck();
    }

    public void drawDuck() {
        imageMode(CENTER);
        image(duck, duckX, duckY);
    }

    public void drawBackground() {
        imageMode(CORNER);
        image(background, bgX, bgY);
        image(background, bgX + background.width, 0);
        bgX -= speed;
        if (bgX <= (background.width*-1)) {
            bgX = 0;
        }
    }
}
