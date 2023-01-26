
package snake.game; 

import javax.swing.JFrame;

public class snakeFrame extends JFrame{

    snakeFrame(){
        //initializing the panel
        this.add(new panel());
        this.setTitle("Snake Game");
        //ensuring that size of window cannot be changed
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        //spawning the frame in the center of the screen
        this.setLocationRelativeTo(null);
    }
    
    
}





































