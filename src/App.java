import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
        int boardWidth = 360;
        int boardHeight = 640;
        //this resolution dimension of background image

        JFrame frame = new JFrame("Flappy Bird");// main window of the app
        // frame.setVisible(true); // controls visibility of the frame
        frame.setSize(boardWidth,boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);// not resizable by user
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// when x button is clicked it will terminate the program

        FlappyBird flappyBird =new FlappyBird();
        frame.add(flappyBird);
        frame.pack();// we want the res to be 360/640 not including the title bar pack lets us do that
        flappyBird.requestFocus();//request that this component gets the input focus
        frame.setVisible(true);//set windows visible after all the settings
    }
}
