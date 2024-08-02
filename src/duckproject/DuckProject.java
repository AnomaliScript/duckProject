package duckproject;
import processing.core.*;
import java.awt.event.*;

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
    int jumpHeight;

    public static void main(String[] args) {
        PApplet.main("duckproject.DuckProject");
    }

    public void settings() {
        size(700, 400);
    }

    public void setup() {
        duck = loadImage("Images/Duck.png");
        // Translucent
        image(duck, 0, 0);
        tint(255, 126);
        background = loadImage("Images/Sky.png");
        obstacle = loadImage("Images/Hydrant.png");
    }

    public void draw() {
        drawBackground();
        drawDuck();
        createObstacles();
        obstacle.resize(50, 0);
    }

    public void drawDuck() {
        imageMode(CENTER);
        image(duck, duckX, duckY);
        if (duckY <= 300) {
            // Higher the number, higher the gravity
            jumpHeight += 1;
            duckY += jumpHeight;
        }
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

    public void keyPressed() {
        if (key == ' ' || keyCode == UP) {
            if (duckY >= 300) {
                jumpHeight = -15;
                duckY += jumpHeight;
            }
        }
    }

    public void createObstacles() {
        imageMode(CENTER);
        image(obstacle, obstacleX, obstacleY);
        // Parallax
        obstacleX -= (speed * 1.5);
        if (obstacleX < 0) {
            // Spawning
            obstacleX = width;
        }

    }
}
