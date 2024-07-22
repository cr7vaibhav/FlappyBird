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
    boolean gameOver = false; // false by default true if Bird falls out or collides with one of the pipes
    double score = 0; // to keep track of score

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
        // this genarates random height pipes
        int randomPipeY = (int) (pipeY - pipeHeight / 4 - Math.random() * (pipeHeight / 2));
        int openingSpace = boardHeight / 4; // openiing Space will be 128 pixels

        Pipe topPipe = new Pipe(topPipeImg);
        topPipe.y = randomPipeY;
        pipes.add(topPipe);

        Pipe bottomPipe = new Pipe(bottomPipeImg);
        bottomPipe.y = topPipe.y + pipeHeight + openingSpace;
        // we need to set the y pos since the default is 0 we need to shift it downwards
        // so we get the topPipe.y top pos + heightofToppipe + opening space so that we
        // can get the pos where bottom pipe starts the bottomPipe.y
        pipes.add(bottomPipe);

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

        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 32));
        if (gameOver) {
            g.drawString("Game Over: " + String.valueOf((int) score), 10, 35);
        } else {
            g.drawString(String.valueOf((int) score), 10, 35);
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

            if (!pipe.passed && bird.x > pipe.x + pipe.width) {// we check if the bird has passed the pipe and the x pos
                                                               // of bird is past the right side of this pipe
                pipe.passed = true;
                score += 0.5; // 0.5 beacuse we have 2 pipes! so 0.5*2 =1, 1 for each set of pipes
            }

            if (collision(bird, pipe)) {
                gameOver = true;
            }
        }

        if (bird.y > boardHeight) {
            gameOver = true;
        }
    }

    public boolean collision(Bird a, Pipe b) {
        return a.x < b.x + b.width && // a's top left corner doesn't reach b's top right corner
                a.x + a.width > b.x && // a's top right corner passes b's top left corner
                a.y < b.y + b.height && // a's top left corner doesn't reach b's bottom left corner
                a.y + a.height > b.y; // a's bottom left corner passes b's top left corner
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // this will be the actionPerformed every 60 times a second
        move();// before we repaint the screen we call the move()
        repaint();// this will call the paint component
        if (gameOver) {
            placePipesTimer.stop();// this will stop adding pipes
            gameLoop.stop();// this will stop repainting and updating frames
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // similar to keyTyped but it can be any key including f5
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            velocityY = -9;
            // when space is pressed the velocityY is set to -9 every press

            if(gameOver){
                // restart the game by reseting the conditions
                bird.y=birdY;
                velocityY=0;
                pipes.clear();
                score=0;
                gameOver=false;
                gameLoop.start();
                placePipesTimer.start();
            }
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
