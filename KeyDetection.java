import java.awt.event.*;

import javax.swing.JFrame;

public class KeyDetection extends JFrame implements KeyListener{
    private long last_key_time = System.currentTimeMillis();
    private final long COOLDOWN = 300; //ms input cooldown

    private int jump_ctr = 0;

    KeyDetection()
    {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Creates Frame for object <- Required for key detection
        this.setSize(0,0);
        this.setLayout(null);
        this.setVisible(true);
     
        this.addKeyListener(this); //Creates key detection for object
    }
    
    @Override
    public void keyTyped(KeyEvent key)
    {
    }

    @Override
    public void keyPressed(KeyEvent key)
    {
        long current_key_time = System.currentTimeMillis();

        if (current_key_time - last_key_time >= COOLDOWN) //Has a greater accuracy than Thread.sleep() with a debounce
        {
            jump_ctr++;
            last_key_time = System.currentTimeMillis();
        }
    }

    @Override
    public void keyReleased(KeyEvent key)
    {
    }

    public int get_jump()
    {
        return jump_ctr;
    }
}