import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;//storing all pipes in the game
import java.util.Random;//placing pipes at random locations
import javax.swing.*;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    int boardWidth = 360;
    int boardHeight = 640;

    // Images
    Image backgroundImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;

    // Bird
    int birdX = boardWidth / 8;
    int birdY = boardHeight / 2;

    int birdWidth = 34;
    int birdHeight = 24;

    class Bird {// class to hold bird values
        int x = birdX;
        int y = birdY;
        int width = birdWidth;
        int height = birdHeight;
        Image img;

        Bird(Image img) {
            this.img = img;
        }
    }

    // Pipes
    int pipeX = boardWidth; // pipe will start form top and right
    int pipeY = 0;
    int pipeWidth = 64; // scaled by 1/6
    int pipeHeight = 521;

    class Pipe {
        int x = pipeX;
        int y = pipeY;
        int width = pipeWidth;
        int height = pipeHeight;
        Image img;
        boolean passed = false; // to check if flappy bird has passed the pipe yet

        // whenever we make a pipe object we need to pass in an image since we have two
        // pipes top and bottom

        Pipe(Image img) {
            this.img = img;
        }
    }

    // game logic
    Bird bird;
    int velocityX = -4; // rate at which the pipe moves to the left, this simulates the bird moving to
                        // the right
    int velocityY = 0;
    int gravity = 1;// every frame bird will slow down by one pixel

    ArrayList<Pipe> pipes;// since we have many pipes in this game we store them in array list
    Random random = new Random();// new randomn number generator

    Timer gameLoop;
    Timer placePipesTimer;

    FlappyBird() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        // setBackground(Color.blue);

        setFocusable(true); // make sure FlappyBird class takes in our key events
        addKeyListener(this);// checks the 3 functions of keyListner

        // load images
        backgroundImg = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        birdImg = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();
        // variable is of Image type but we assigned ImageIcon so we need to use
        // getImage()

        // bird
        bird = new Bird(birdImg);

        pipes = new ArrayList<Pipe>(); // creates Arraylist of pipes

        // place pipes timer
        placePipesTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placePipes(); // timer that will call placePipes every 1.5 seconds
            }
        });
        placePipesTimer.start();

        // game timer
        gameLoop = new Timer(1000 / 60, this); // 1000.60 = 16.6ms every frame which is 60FPS
        gameLoop.start();// starts the timer
    }

    public void paintComponent(Graphics g) {
        // since paintComponent is a part of Jpanel we can use super to call the super
        // class implementation
        super.paintComponent(g);
        draw(g);
    }

    public void placePipes() {
        // Math.random() give a value (0-1_ which is * by pipeHeight/2 -> (0-256)
        // pipeHeight/4 -> 128 and pipeY is 0
        // 0-128-(0-256) --> range is --> (pipeHeight/4 - 3/4 pipeHeight)
        int randomPipeY = (int) (pipeY - pipeHeight / 4 - Math.random() * (pipeHeight / 2));
        // this genarates random height pipes
        Pipe topPipe = new Pipe(topPipeImg);
        topPipe.y=randomPipeY;
        pipes.add(topPipe);
    }

    public void draw(Graphics g) {
        // System.out.println("draw");// debug statement

        // background
        g.drawImage(backgroundImg, 0, 0, boardWidth, boardHeight, null);

        // bird
        g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);

        // pipes
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }

    }

    public void move() {// here all the x and y pos of our objects are updated
        // bird
        velocityY += gravity;// updates the velocity after takinbg gravity into account
        bird.y += velocityY;// updates bird pos by adding velocity to it
        bird.y = Math.max(bird.y, 0); // this caps the y pos at 0

        // pipes
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.x += velocityX; // everyframe move each pipe over by velocityX to the left
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // this will be the actionPerformed every 60 times a second
        move();// before we repaint the screen we call the move()
        repaint();// this will call the paint component
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // similar to keyTyped but it can be any key including f5
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            velocityY = -9;
            // when space is pressed the velocityY is set to -9 every press
        }
    }

    // only using keyPressed not using the other 2 here
    @Override
    public void keyTyped(KeyEvent e) {
        // keyTyped is type on a key that has a charecter like 'A' 'a' '1' '@' not f5
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // when u press on a key and u let go and key goes back up
    }

}
