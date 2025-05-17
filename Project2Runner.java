public class Project2Runner {
    
    /*
     * Name: Zaina Kaur
     * Student ID: 501296433
     * 
     * 

     ******** Project Description ********
     * 
     * I have made a snake game for this project because I sort of had a clear idea of what I would
     * need to do to make it and what features I wanted, for example, the snake speeding up everytime
     * it eats the object or changing color to match the object. 
     * 
     * To play, run the program and use the arrow keys on your keyboard to change directions of the snake
     * if you hit a boundry and the game is over you can restart the game. restarting the game will, reset 
     * all settings and you will start from the slowest speed and the score being 0.
     * To pause the game press the space bar and to resume the game press the space bar again
     * 
     * click the restart button or press the 'R' key on your keyboard to restart. 
     *
     * 
     ******** Swing Requirement ********
     * 
     * Describe in 1 paragraph how your program satisfies the requirement that
     * there is at least 3 unique components. Be clear to identify in what
     * files and the lines number (just the starting line is fine) that the
     * components are defined on.
     * 
     * the following is in file panel.java
     * JPanel The main JPanel 
     * JLabel: The score label on line 58
     * JButton(s): pauseButton on Line 64 
     *          restartButton on Line 69
     * 
     ******** 2D Graphics Requirement ********
     *
     * Describe in 1 paragraph how your program satisfies the requirement that
     * there is at least 1 JPanel used for drawing something. Be clear to
     * identify in what files and the line numbers that this panel is defined on.
     * 
     * 
     * the following is in file panel.java
     * paintComponent on line 131
     *          game over text 
     *draw() on line 167
     *          snake body
     *          snake food object 
     * 
     * 
     ******** Event Listener Requirement ********
     *
     * Describe in 1 paragraph how your program satisfies the requirement that
     * there is at least one ActionListener, and there is additionally at least
     * one MouseListener or ActionListener. Be clear to identify in what file
     * and the line numbers that these listeners are defined in.
     * 
     * 
     * 
     * the following is in file panel.java
     * ActionListener: implemented by panel for the things like the timer and the restart button line 69 has its own action listener so that the restart butons fuctionality is seperated from the game logic
     * 
     * KeyAdapter(Keyboard/mouse Listener requirement): Line 263, to handel keybaord input such as, spacebar, 'R', and arrowkeys
     * 
     *
     */

    public static void main(String[] args) {

        Frame frame = new Frame();
        
    }
}


// Note to self 
/*I just realized i should 
* add some logic that makes it so that a player has like 3 lives and after that the game has to restart 
* from the original settings. I will implement this after finals. 
*/
