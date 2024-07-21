import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;//storing all pipes in the game
import java.util.Random;//placing pipes at random locations
import javax.swing.*;

public class FlappyBird extends JPanel implements ActionListener,KeyListener {
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

    // game logic
    Bird bird;
    int velocityY = -9;
    int gravity = 1;// every frame bird will slow down by one pixel

    Timer gameLoop;

    FlappyBird() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        // setBackground(Color.blue);

        // load images
        backgroundImg = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        birdImg = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();
        // variable is of Image type but we assigned ImageIcon so we need to use
        // getImage()

        // bird
        bird = new Bird(birdImg);

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

    public void draw(Graphics g) {
        System.out.println("draw");// debug statement
        // background'
        g.drawImage(backgroundImg, 0, 0, boardWidth, boardHeight, null);

        // bird
        g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);
    }

    public void move() {// here all the x and y pos of our objects are updated
        // bird
        velocityY += gravity;//updates the velocity after takinbg gravity into account
        bird.y += velocityY;// updates bird pos by adding velocity to it
        bird.y = Math.max(bird.y, 0); // this caps the y pos at 0
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // this will be the actionPerformed every 60 times a second
        move();// before we repaint the screen we call the move()
        repaint();// this will call the paint component
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyPressed'");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
    }

}
