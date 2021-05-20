import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.util.Calendar;
import java.io.File;

/**
 * Main class that creates the clock and holds the entire logic for its animation.
 * @author Nora Bytyci
 * @version 1.0
 */
public class Clock extends JPanel implements ActionListener {
    /**
     * Width of the frame
     */
    public int width = 530;
    /**
     * Height of the frame
     */
    public int height = 530;
    /**
     * Variable that will help repainting the app every second
     */
    public Timer timer = new Timer(1000, this);
    /**
     * Class constructor
     */
    public Clock() {
        JFrame frame = new JFrame("Clock");
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - width / 2, dim.height / 2 - height / 2);
        frame.setSize(width, height);
        frame.add(this);
        frame.setUndecorated(true);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.getRootPane().setOpaque(false);
        frame.getContentPane().setBackground(new Color (0, 0, 0, 0));
        frame.setBackground(new Color (0, 0, 0, 0));
        timer.start();
        playSound();
        FrameDragListener frameDragListener = new FrameDragListener(frame);
        frame.addMouseListener(frameDragListener);
        frame.addMouseMotionListener(frameDragListener);
        frame.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                if(ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    frame.dispose();
                    System.exit(0);
                } 
            }
        });
    }
    
    /**
     * Draws the clock and its elements
     * @param g - Helps drawing the elements
     */
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawClock(g2);
        drawClockHand(g, getValue("hours"), 80, Color.BLACK);
        drawClockHand(g, getValue("minutes"), 120, Color.BLACK);
        drawClockHand(g, getValue("seconds"), 150, Color.RED);
        drawInnerCircle(g2);
        drawNumbers(g2);
    }

    /**
     * Draws each of the clock hands(seconds, minutes, hours) as a rounded rectangle
     * @param g
     * @param rotation - The degree that the element will be rotated for
     * @param size - The width of the hand
     * @param color - The fill color of the hand
     */
    public void drawClockHand(Graphics g, int rotation, int size, Color color) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(color);
        RoundRectangle2D hand = new RoundRectangle2D.Float(250, 250, size, 5, 10, 10);
        g2d.rotate(Math.toRadians(rotation), 250, 250); 
        g2d.draw(hand);
        g2d.fill(hand);
        g2d.dispose();
    }

    /**
     * Draws the clock as a border and sets the inner background as white
     * @param pen
     */
    public void drawClock(Graphics2D pen) {
        pen.setColor(Color.BLACK);
        pen.fillOval(40, 35, 420, 420);
        pen.setColor(Color.WHITE);
        pen.fillOval(50, 45, 400, 400);
    }

    /**
     * Draws a small circle in the center for decoration purposes
     * @param pen
     */
    public void drawInnerCircle(Graphics2D pen) {
        pen.setColor(Color.BLACK);
        pen.fillOval(235, 235, 30, 30);
    }

    /**
     * Draws each hour number from 1 to 12 in their specific position as usually seen in analog clocks
     * @param pen
     */
    public void drawNumbers(Graphics2D pen) {
        pen.setFont(new Font("Calibri", Font.BOLD, 22));
        pen.setColor(Color.BLACK);
        pen.drawString("12", 240, 80);
        pen.drawString("1", 336, 110);
        pen.drawString("2", 395, 175);
        pen.drawString("3", 415, 260);
        pen.drawString("4", 395, 345);
        pen.drawString("5", 330, 405);
        pen.drawString("6", 240, 430);
        pen.drawString("7", 160, 405);
        pen.drawString("8", 95, 345);
        pen.drawString("9", 75, 260);
        pen.drawString("10", 95, 175);
        pen.drawString("11", 160, 110);
    }

    /**
     * Gets the seconds, minutes, hours depending on the type from the computer clock and translates the values into degrees
     * @param type - The type of the value that we want to get. Options are seconds, minutes, hours. 
     * @return The value of the specific type(second, minute, hour) translated in degrees. 
     */
    public int getValue(String type) {
        Calendar cal = Calendar.getInstance();
        switch(type) {
            case "seconds": {
                return 270 + (cal.get(Calendar.SECOND) * 6);
            }
            case "minutes": {
                return 270 + (cal.get(Calendar.MINUTE) * 6);
            }
            case "hours": {
                int hours = cal.get(Calendar.HOUR_OF_DAY);
                if(hours >= 13 && hours <= 24) {
                    hours -= 12;
                }
                return 270 + (hours * 30);
            }
            default: {
                return -1;
            }
        }
    }
    
    /**
     * Repaints the app if timer has fired. This happens every second.
     * @param ev - The event that happened
     */
    public void actionPerformed(ActionEvent ev){
        if(ev.getSource() == timer) {
            repaint();
        }
    }

    /**
     * Helper method that plays in a loop the ticking sound.
     */
    public void playSound() {
        try {
            String soundLocation = System.getProperty("user.dir") + "\\tick.wav";
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundLocation).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch(Exception ex) {
        }
    }
    
    public static void main(String[] args) {
        new Clock();
    }
}