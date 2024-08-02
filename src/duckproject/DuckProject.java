package duckproject;
import processing.core.*;
import java.util.*;

public class DuckProject extends PApplet {
    Random rand = new Random();

    PImage duck;
    PImage background;
    PImage obstacleImg;

    int duckX = 70;
    int duckY = 300;

    int bgSpeed = 8;
    int bgX = 0;
    int bgY = 0;

    int numObstacles = 5;
    // Note that the starting spawn point is at the right edge of the screen
    int closest = 0;
    int farthest = 200;

    ArrayList<Double> speeds = new ArrayList<Double>();
    int jumpHeight;

    int startTime;
    int timer;

    ArrayList<obstacle> obstacles = new ArrayList<obstacle>();

    int mercyHorizontal = 40;
    int mercyVertical = 40;

    public static void main(String[] args) {
        PApplet.main("duckproject.DuckProject");
    }

    public void settings() {
        size(700, 400);
    }

    public void setup() {
        duck = loadImage("Images/Duck.png");
        image(duck, 0, 0);
        background = loadImage("Images/Sky.png");
        obstacleImg = loadImage("Images/Hydrant.png");
        // Filling in the ArrayList of obstacle objects and their speeds
        for (int i = 0; i < numObstacles; i++) {
            obstacle obstacleInstance = new obstacle(
                    width + rand.nextInt((farthest - closest) + 1) + closest,
                    6 + (10 - 6) * rand.nextDouble()
            );
            obstacles.add(obstacleInstance);
            System.out.println(i + "x: " + obstacles.get(i).X);
        }
    }

    public void draw() {
        drawBackground();
        drawDuck();
        obstacleImg.resize(50, 0);
        createObstacles();
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
        bgX -= bgSpeed;
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
        for (int i = 0; i < obstacles.size(); i++) {
            // Parallax
            obstacles.get(i).X -= obstacles.get(i).speed;
            if (obstacles.get(i).X < 0) {
                // Spawning (technically moving it instantaneously)
                obstacles.get(i).X = width; // + rand.nextInt((farthest - closest) + 1) + closest;
            }
            // AABB
            if ((abs(duckX - obstacles.get(i).X) < mercyHorizontal) && (abs(duckY - obstacles.get(i).Y)) < mercyVertical) {
                System.out.println("Game Over!");
            }
            image(obstacleImg, obstacles.get(i).X, obstacles.get(i).Y);
        }
    }
}
