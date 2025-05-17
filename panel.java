import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

// main game panel. It extends the JPanel and implements ActionListener 
public class panel extends JPanel implements ActionListener {

    // Constants 
    static final int width = 800; // game window width
    static final int height = 800; // game window height 
    static final int unit = 30; // size of each snake segment 
    static final int unitObj = 20; // size of the object the snake eats 
    static final int gameUnit = (width*height)/unit; // Max length of snake
    static final int delay = 120; //Initial delay(slowest speed for snake
    int currentDelay = delay; // the current snake speed
    static final int minDelay = 60; // Max speed for snake
    static final int speedIncrament = 5; // speed increase incrament for snake after every bite it takes 
    final int x[] = new int[gameUnit]; // x-coordinates of the snake
    final int y[] = new int[gameUnit]; // y-coordinates of the snake


    //Variables 
    int bodyParts = 6; // initial length of the snake
    int score = 0; // initial score
    int objLocationX; // x location of object that is eaten
    int objLocationY; // y location of above object 
    char direction= 'R'; // initial direction of snake movement is towards the right of the screen
    boolean running = false; // indicating if game is running
    Timer timer; // controls game updates 
    Random random; // random number generator for the food object placement 
    boolean paused = false; // tells us if the game is paused or running
    boolean gameOver = false; // tells us if the game is over. So snake hit a wall or itsself 
    Color currentObjColor; // Current color of the food object 
    Color snakeHeadColor = Color.PINK; // color of the snake head 
    

    // Swing Components 
    JLabel scoreLabel; // this label displays the current score 
    JButton pauseButton; // Use spacebar to use pause and resume game. The button is more of a label that tells you whether the game is paused or resumed 
    JButton restartButton; //button to restart the game once it is over 



    panel(){
        random = new Random(); // initializing random number generator 
        this.setPreferredSize(new Dimension(width, height)); //panel size
        this.setBackground(Color.BLACK);  // panel color
        this.setFocusable(true); //keyboard focus 
        this.addKeyListener(new myKeyAdapter()); // For keboard input


        // setting up the score label
        scoreLabel = new JLabel("Score: 0"); 
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 30));


        //setting up the pause button 
        pauseButton = new JButton("Pause"); 
        this.add(pauseButton);


        //setting up the restart button
        restartButton = new JButton("Restart"); 
        restartButton.addActionListener(new ActionListener(){
            @Override 
            public void actionPerformed(ActionEvent e) {
                restart(); // calls the restart method if restart button is pressed 
            }
        });

        // the panel on the bottom of the game panel. This stores all the buttons 
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(pauseButton); // add pause button
        buttonPanel.add(restartButton); // add restart button 

        this.setLayout(new BorderLayout()); //use border layout 
        this.add(buttonPanel, BorderLayout.SOUTH); // add the buttonPanel to the bottom 

        start(); // this will jump to start and start the game
    }
    public void start() {
        Obj(); // calling Obj and placing initial food 
        running = true; // running is now true
        currentDelay = delay; // delay is equal to the original delay of 120 in the start 
        timer = new Timer( currentDelay, this); // game timer 
        timer.start();
    }

    public void restart() {
        // setting game to default settings except for running which is true as when we restart the game it has started
        bodyParts = 6;
        score = 0;
        direction = 'R';
        running = true;
        gameOver = false;
        currentDelay = delay;
        pauseButton.setText("Pause");

        for (int i = 0; i < bodyParts; i++) { // reset snake position
            x[i] = width/2 - i*unit; 
            y[i] = height/2;
        }

        // if((x[0] == objLocationX) && (y[0] == objLocationY)) {
        //     bodyParts++;
        //     score ++;
        //     Obj();
        // }

        Obj(); // generate new object

        // restart the timer if it is not running 
        if (!timer.isRunning()) {
            timer.start();
        }

        // making sure keybaord inputs are working 
        this.requestFocusInWindow();
        repaint(); // refreshing display 

    }


    // main rendering method called by swing 
    public void paintComponent(Graphics g) {
        super.paintComponent(g); 
        draw(g);


        // what the screen will show if game is over 
        if(gameOver) {
            g.setColor(new Color(0, 0, 0, 180)); // background is black 
            g.fillRect(0, 0, width, height); // cover the entire panel 
            
            // game over text
            g.setColor(Color.RED); // red font 
            g.setFont(new Font("Arial", Font.PLAIN, 75));
            g.drawString("GAME OVER", width/4, height/2);
            
            //prompt to restart game 
            g.setColor(Color.WHITE); // white font for these instructions 
            g.setFont(new Font("Arial", Font.PLAIN, 30));
            g.drawString("Press RESTART or 'R' to play again", width/6, height/2 + 50);
        }
        
    }

    // when game needs to be pause 
    public void pause() {
        paused = !paused; // pause flag to true 
        if (paused) { // stop game timer to pause game 
            timer.stop(); 
            pauseButton.setText("Resume"); // update button label
        } else {
            timer.start(); // else keep game timer on and update button label to Pause 
            pauseButton.setText("Pause");
        }
    }

    // draw game elements like the snake 
    public void draw(Graphics g) {
        // drawing the object snake eats 
        g.setColor(currentObjColor); //  setting current color of food 
        g.fillRect(objLocationX, objLocationY, unitObj, unitObj); // drawing the food as a rectangle 

        // drawing snake
        for (int i = 0; i < bodyParts; i++) {
            if (i == 0) { // Snake Head 
                g.setColor(snakeHeadColor); //snake head color is specia as it does not change 
                g.fillRoundRect(x[i], y[i], unit, unit, 10, 10); 
            } else { // snake body
                g.setColor(currentObjColor); //current food color = snake body color 
                g.fillRect(x[i], y[i], unit, unit); // draw snake body using rectangles 
            }
        }

        g.setColor(Color.WHITE); // score font color 
        g.setFont(new Font("Arial", Font.BOLD, 25)); // score font properties  
        g.drawString("Score: " + score, 10, 30); // score and its poition on the game panel 

    }

    // moving the snake by shifting each individual part of the body 
    public void move() {
        for(int i = bodyParts; i >0; i--) {
        /*
         * x[] and y[] store the arrays so the loop works backwards so each 
         * part of the body takes the postion ahead of it
         */
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        // after the above loop switch directions 
        switch(direction) {
            case 'U':
            y[0] -= unit; // if going up the y[0] is subtracted 
            break;

            case 'R':
            x[0] += unit; // going right so add x[0] 
            break;

            case 'D': // moving down 
            y[0] += unit;
            break;

            case 'L': // moving left 
            x[0] -= unit;
            break;
        }
    }

    public void Obj(){
        objLocationX = random.nextInt((int)(width/unit))*unit; // making the x location of the object random 
        objLocationY = random.nextInt((int)(height/unit))*unit; // making the y location of the object random 

        currentObjColor = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));

    }

    public void ObjEaten() {
        // Check if snake head overlaps with object (accounting for different sizes)
        if ((x[0] < objLocationX + unitObj) && 
            (x[0] + unit > objLocationX) && 
            (y[0] < objLocationY + unitObj) && 
            (y[0] + unit > objLocationY)) {
            bodyParts++;
            score++;

            if (currentDelay > minDelay) {
                currentDelay -= speedIncrament;
                timer.setDelay(currentDelay);
            }
            Obj();
        }
    }

    public void Collide() {
        // checking if snake hits a wall
        if ((y[0] < 0) || 
        (y[0] > height) || 
        (x[0] < 0) || 
        (x[0] > width)  ) {
            running = false; // make running = false 
            gameOver = true; // make gameOver = true so that gameOver options can popUp
        }

        // checking if snake hits its own body
        for (int i = bodyParts; i > 0; i--) {
            if((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
                gameOver = true;
            }
        }
    }
    public class myKeyAdapter extends KeyAdapter {
        @Override 
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()) {
                case KeyEvent.VK_SPACE:
                pause();
                break;

                case KeyEvent.VK_R:
                restart();
                break;

                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') direction = 'R'; // preventing snake from going left when right is clicked 
                    break;

                case KeyEvent.VK_LEFT:
                if(direction != 'R') { // preventing snake from going right when left is clicked 
                    direction = 'L';                   
                }
                break;

                case KeyEvent.VK_UP:
                if(direction != 'D') { // oreventing snake from going down when up is clicked 
                    direction = 'U';                   
                }
                break;

                case KeyEvent.VK_DOWN:
                if(direction != 'U') { // preventing snake from from going up when down is clicked 
                    direction = 'D'; 
                }
                break;
            }
        }
    }

    @Override // game loop 
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if(running) { // if game is running
            move(); // then update move for new snake position, objEaten to check for if object is eaten, and collide() to check for if the snake has collided with an object it is not suppoed to collide into 
            ObjEaten();
            Collide();
        }

        repaint(); // refresh the display 
    }   
}