import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;//storing all pipes in the game
import java.util.Random;//placing pipes at random locations
import javax.swing.*;

public class FlappyBird extends JPanel {
    int boardWidth = 360;
    int boardHeight = 640;

    // Images
    Image backgroundImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;

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
    }

    public void paintComponent(Graphics g) {
        // since paintComponent is a part of Jpanel we can use super to call the super
        // class implementation
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // draws background'
        g.drawImage(backgroundImg, 0, 0, boardWidth, boardHeight, null);
    }

}
