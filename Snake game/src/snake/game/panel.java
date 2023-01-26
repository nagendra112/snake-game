
package snake.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Random;
import javax.swing.*;

public class panel extends JPanel implements ActionListener{
    
    //height ans width of the panel in pixels
    static int width = 1200;
    static int height = 800;
    //unit size of a single grid unit
    static int unit = 50;
    
    //timer to constantly check the state of the game
    Timer timer;
    //random variable to spawn the food
    Random random;
    
    //coordinates for the food
    int fx, fy;
    
    int foodeaten;
    
    int high_score = 0;
    //initial length of the snake
    int body = 3;
    //flag to chexk if game is running 
    boolean flag = false;
    //initial direction of the snake
    char dir = 'R';
    
    static int delay = 160;
    static int gridsize = (width*height)/(unit*unit); //288
    
    int x_snake[] = new int[gridsize];
    int y_snake[] = new int[gridsize];
    
    panel(){
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.BLACK);
        //for taking keyboard input directly
        this.setFocusable(true);
        //taking input from keyboard
        this.addKeyListener(new MyKey());
        random = new Random();
        Game_Start(); 
    }
    
    public void Game_Start(){
        spawnfood();
        flag = true;
        timer = new Timer(delay, this);
        timer.start();
    }
    
    public void spawnfood(){
        fx = random.nextInt((int)(width/unit)) * unit; //random integer between 0 - 1200 and multiple of 50
        fy = random.nextInt((int)(height/unit)) * unit;
    }
    
    public void paintComponent(Graphics graphic){
        super.paintComponent(graphic);
        draw(graphic);
    }
    
    public void draw(Graphics graphic){
        if(flag){
            //drawing the food
            graphic.setColor(Color.ORANGE);
            graphic.fillOval(fx, fy, unit, unit);
            
            //drawing the snake body
            for(int i=0; i<body; i++){
                //head of the snake
                if(i==0){
                    graphic.setColor(Color.blue);
                    graphic.fillRect(x_snake[0], y_snake[0], unit, unit);
                }
                else{
                    graphic.setColor(Color.lightGray);
                    graphic.fillRect(x_snake[i], y_snake[i], unit, unit);
                }
            }
            //score display
            graphic.setColor(Color.CYAN);
            graphic.setFont(new Font("Comic Sans", Font.BOLD, 40));
            FontMetrics fm = getFontMetrics(graphic.getFont());
            graphic.drawString("Score : " + foodeaten, (width - fm.stringWidth("Score : " + foodeaten))/2, graphic.getFont().getSize());
        }
        else{
            gameover(graphic);
        }
    }  
    
        public void gameover(Graphics graphic){
            
            //game over display
            graphic.setColor(Color.RED);
            graphic.setFont(new Font("Comic Sans", Font.BOLD, 80));
            FontMetrics fm2 = getFontMetrics(graphic.getFont());
            graphic.drawString("GAME OVER", (width - fm2.stringWidth("GAME OVER"))/2, height/2-300);
            
            //final score display
            graphic.setColor(Color.WHITE);
            graphic.setFont(new Font("Comic Sans", Font.BOLD, 80));
            FontMetrics fm4 = getFontMetrics(graphic.getFont());
            graphic.drawString("YOUR SCORE : "+foodeaten, (width - fm4.stringWidth("YOUR SCORE      "))/2, height/2);
            
            
            //high score display
            high_score = Math.max(foodeaten, high_score);
            graphic.setColor(Color.yellow);
            graphic.setFont(new Font("Comic Sans", Font.BOLD, 80));
            FontMetrics fm5 = getFontMetrics(graphic.getFont());
            graphic.drawString("HIGH SCORE : " + high_score, (width - fm5.stringWidth("HIGH SCORE :   "))/2, height/2-150);
            
            //for replay
            graphic.setColor(Color.CYAN);
            graphic.setFont(new Font("Comic Sans", Font.BOLD, 80));
            FontMetrics fm3 = getFontMetrics(graphic.getFont());
            graphic.drawString("PRESS R TO REPLAY", (width - fm3.stringWidth("PRESS R TO REPLAY"))/2, height/2+150);
        }
    
       public void move(){
           //updating the body
           for(int i=body; i>0; i--){
              x_snake[i] = x_snake[i-1];
              y_snake[i] = y_snake[i-1];
          }
          //update the head
          switch(dir){
              case 'U':
                  y_snake[0] = y_snake[0] - unit;
                  break;
              case 'D':
                  y_snake[0] = y_snake[0] + unit;
                  break;
              case 'L':
                  x_snake[0] = x_snake[0] - unit;
                  break;
              case 'R':
                  x_snake[0] = x_snake[0] + unit;
                  break;
          }
       } 
        
      public void check(){
          //to check collision with its own body
          for(int i=body; i>0; i--){
              if((x_snake[0] == x_snake[i]) && (y_snake[0] == y_snake[i])){
                  flag = false;
              }
          }
         
          //to check collision with the walls
          if(x_snake[0]<0){
              flag = false;
          }
          else if(x_snake[0]>=width){
              flag = false;
          }
          else if(y_snake[0]<0){
              flag = false;
          }
          else if(y_snake[0]>=height){
              flag = false;
          }
          
          if(!flag){
              timer.stop();
          }
      }
        
      public  void food(){
          if((x_snake[0] == fx) && (y_snake[0] == fy)){
              body++;
              foodeaten++;
              spawnfood();
          }
      }
     
      
    public class MyKey extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(dir!='R'){
                        dir = 'L';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(dir!='D'){
                        dir = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(dir!='U'){
                        dir = 'D';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(dir!='L'){
                        dir = 'R';
                    }
                    break;
                case KeyEvent.VK_R:
                    if(!flag){
                        foodeaten=0;
                        body = 3;
                        dir = 'R';
                        Arrays.fill(x_snake, 0);
                        Arrays.fill(y_snake, 0);
                        Game_Start();
                    }
            }
        }
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        if(flag){
            move();
            food();
            check();
        }
        repaint();
    }
}




































