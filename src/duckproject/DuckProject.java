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

    int score = 0;
    int highScore = 0;
    boolean beaten = false;

    int numObstacles = 1;
    double minSpeed = 6;
    double maxSpeed = 10;
    // Note that the starting spawn point is at the right edge of the screen
    int closest = 0;
    int farthest = 200;

    ArrayList<Double> speeds = new ArrayList<Double>();
    int jumpHeight;

    ArrayList<obstacle> obstacles = new ArrayList<obstacle>();

    int mercyHorizontal = 40;
    int mercyVertical = 40;

    enum GameState {
        GAMEOVER, RUNNING
    }

    static GameState currentState;

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
                    (width + rand.nextInt((farthest - closest) + 1) + closest),
                    (minSpeed + (maxSpeed - minSpeed) * rand.nextDouble())
            );
            obstacles.add(obstacleInstance);
            System.out.println(obstacles.get(i).speed);
        }
        currentState = GameState.RUNNING;
    }

    public void draw() {
        switch (currentState) {
            case RUNNING:
                drawBackground();
                drawDuck();
                obstacleImg.resize(50, 0);
                createObstacles();
                drawScore();
                break;
            case GAMEOVER:
                drawGameOver();
                break;
        }
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
            if (currentState == GameState.RUNNING) {
                if (duckY >= 300) {
                    jumpHeight = -15;
                    duckY += jumpHeight;
                }
            }
            if (currentState == GameState.GAMEOVER) {
                for (int i = 0; i < numObstacles; i++) {
                    obstacles.get(i).X = 0;
                }
                bgX = 0;
                score = 0;
                currentState = GameState.RUNNING;
            }
        }
    }

    public void createObstacles() {
        imageMode(CENTER);
        for (int i = 0; i < obstacles.size(); i++) {
            // Parallax
            obstacles.get(i).X -= obstacles.get(i).speed;
            if (obstacles.get(i).X < 0) {
                // Point!
                score += 1;
                // Spawning (technically moving it instantaneously)
                obstacles.get(i).X = width; // + rand.nextInt((farthest - closest) + 1) + closest;
            }
            // AABB
            if ((abs(duckX - obstacles.get(i).X) < mercyHorizontal) && (abs(duckY - obstacles.get(i).Y)) < mercyVertical) {
                System.out.println("Game Over!");
                if (score > highScore) {
                    highScore = score;
                    beaten = true;
                } else {
                    beaten = false;
                }
                currentState = GameState.GAMEOVER;
            }
            image(obstacleImg, obstacles.get(i).X, obstacles.get(i).Y);
        }
    }

    public void drawScore() {
        fill(255, 255, 255);
        textAlign(CENTER);
        String scoreString = "Score: " + Integer.toString(score);
        text(scoreString, 600, 100);
    }

    public void drawGameOver() {
        fill(255, 190, 190);
        noStroke();
        rect(width / 2 - 125, height / 2 - 80, 250, 160);
        fill(255, 100, 100);
        textAlign(CENTER);
        text("Game Over!", width / 2, height / 2 - 50);
        text("Your Score: " + score, width / 2, height / 2 - 30);
        text("High Score: " + score, width / 2, height / 2 - 10);
        if (beaten) {
            text("New High Score!", width / 2, height / 2 + 10);
        }
    }
}
