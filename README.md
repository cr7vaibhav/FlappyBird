# Flappy Bird

Using this FlappyBird project made by ImKennyYip to learn Java Swing/awt, how to create a game loop, create a jframe and jpanel, draw images on the jpanel, add click handlers to make the flappy bird jump, randomly generate pipes and move them across the screen, detect collisions between the flappy bird and each pipe, and add a running score.


The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).
Create a JFrame window of a size, make it visible and set default close operation

```java
//App.java

import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
        int boardWidth = 360;
        int boardHeight = 640;
        //this resolution dimension of background image

        JFrame frame = new JFrame("Flappy Bird");// main window of the app
        frame.setVisible(true); // controls visibility of the frame
        frame.setSize(boardWidth,boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);// not resizable by user
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // when x button is clicked it will terminate the program
    }
}
```

![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/b82f9a48-cfed-47d2-b9b0-9d52a734b8bd/0d917f57-fa5f-4f29-a342-854b4ab98b4b/Untitled.png)

Now , we need to add a JPanel which we will use as Canvas. With JPanel we will draw our game

```java
//FlappyBird.java
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;//storing all pipes in the game
import java.util.Random;//placing pipes at random locations
import javax.swing.*;

public class FlappyBird extends JPanel{
    int boardWidth=360;
    int boardHeight=640;

    FlappyBird(){
        setPreferredSize(new Dimension(boardWidth,boardHeight));
        setBackground(Color.blue);
    }

}
```

```java
//Contd. App.java
        FlappyBird flappyBird =new FlappyBird();
        frame.add(flappyBird);
        frame.pack();
        // we want the res to be 360/640 not including the title bar
        // pack lets us do that
        frame.setVisible(true);
        //set windows visible after all the settings
        //App.jav is done
```

This is our JPanel in blue

![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/b82f9a48-cfed-47d2-b9b0-9d52a734b8bd/2470e556-86bf-4cc0-8f5e-6aaf106eead4/Untitled.png)

load images

```java
//Images
    Image backgroundImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;

    FlappyBird(){
        setPreferredSize(new Dimension(boardWidth,boardHeight));
        setBackground(Color.blue);
    
        //load images
        backgroundImg=new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        birdImg=new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        topPipeImg=new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottomPipeImg=new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();
        //variable is of Image type but we assigned ImageIcon so we need to use getImage()
```

Draw background

```java
public void paintComponent(Graphics g) {
        //since paintComponent is a part of Jpanel we can use super to call the super class implementation
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // draws background'
        g.drawImage(backgroundImg, 0, 0, boardWidth, boardHeight, null);
    }
```

![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/b82f9a48-cfed-47d2-b9b0-9d52a734b8bd/6d11229e-c295-4501-ba6d-1d81af528e18/Untitled.png)

![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/b82f9a48-cfed-47d2-b9b0-9d52a734b8bd/a75d9810-d2eb-47d5-b076-7f352bad1ddb/Untitled.png)

Lets add the bird to our game

```java
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

        Bird(Image img){
            this.img=img;
        }
    }
```

inside FlappyBird( ) Constructor  

```
// bird
        bird = new Bird(birdImg);
```

within draw()

```java
 // bird
        g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);
```

![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/b82f9a48-cfed-47d2-b9b0-9d52a734b8bd/02cbb3a2-6d6b-40db-bf61-d7b6eb688f63/Untitled.png)

we need to make the bird move but before that we need a game loop

we can se in the paintComponent function that calls draw which only gets called once so we need a game loop that will call this function over and over again per frame usually 60FPS

in game logic we make a Timer named gameloop

```java
// game logic
    Bird bird;

    Timer gameLoop;
```

in FlappyBird constructor we add a game Timer 

```java
// game timer
        gameLoop = new Timer(1000 / 60, this); 
        // 1000.60 = 16.6ms every frame which is 60FPS
```

we implement ActionListener and

```java

public class FlappyBird extends JPanel implements ActionListener {
```

we add unimplemented method action Performed we call repaint() inside action performed which calls the paint component 

```java
@Override
    public void actionPerformed(ActionEvent e) {
        // this will be the actionPerformed every 60 times a second
        repaint();// this will call the paint component
    }

```

Then inside FlappyBird Constructor under game logic we start the game loop timer 

```java
gameLoop = new Timer(1000 / 60, this); // 1000.60 = 16.6ms every frame which is 60FPS
        gameLoop.start();//starts the timer
```

Now to make the bird move we need to add a velocity → change in pos over time

if i want to move the flappy bird upwards towards 0,0 then -y, downwards +y ,to the left -x and to the right +x

as the flappy bird doesn't move left to right but only up and down we only need velocityY

![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/b82f9a48-cfed-47d2-b9b0-9d52a734b8bd/9c650aa8-4b30-4995-9fbe-79f6e57396d9/Untitled.png)

```java
// game logic
    Bird bird;
    int velocityY = -6;
```

```java
public void move() {// here all the x and y pos of our objects are updated
        // bird
        bird.y += velocityY;// updates bird pos by adding velocity to it
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // this will be the actionPerformed every 60 times a second
        move();// before we repaint the screen we call the move()
        repaint();// this will call the paint component
    }
```

this allows the bird to move upwards beyond the background but we are not allowed to do that so we cap the y pos at 0

```java
 public void move() {// here all the x and y pos of our objects are updated
        // bird
        bird.y += velocityY;// updates bird pos by adding velocity to it
        bird.y= Math.max(bird.y,0); //this caps the y pos at 0
    }
```

![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/b82f9a48-cfed-47d2-b9b0-9d52a734b8bd/31b506df-e929-4e2e-a5db-94533f0b9652/Untitled.png)

currently the bird is moving upwards at -6 pixels a frame , so at this rate it is going to keep moving upwards forever 

therefore we now introduce gravity , a downward force it will slow down the bird and eventually fall back down

gravity is a change in velocity overtime , gravity is downwards so +ve 

do day the bird moves upwards -6 for first frame we apply gravity then -4 the -2 then 0 where bird stops then the bird starts to descend

![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/b82f9a48-cfed-47d2-b9b0-9d52a734b8bd/a31d76f3-6ed8-4487-8060-7644707784ce/Untitled.png)

```java
// game logic
    Bird bird;
    int velocityY = -9;
    int gravity = 1;// every frame bird will slow down by one pixel
```

```java
public void move() {// here all the x and y pos of our objects are updated
        // bird
        velocityY += gravity;//updates the velocity after takinbg gravity into account
        bird.y += velocityY;// updates bird pos by adding velocity to it
        bird.y = Math.max(bird.y, 0); // this caps the y pos at 0
    }
```

now we add some key Listeners  so that when we press on a key the bird can jump up again

we implement Key listener and add unimplemented methods