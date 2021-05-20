import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Helper class that listens for frame dragging and moves the frame around the screen.
 * @author Nora Bytyci
 * @version 1.0
 */
public class FrameDragListener extends MouseAdapter {
    /**
     * Frame in question.
     */
    private final JFrame frame;
    /**
     * Initialization of the coordinates of the mouse.
     */
    private Point mouseDownCompCoords = null;

    /**
     * Class constructor
     * @param frame
     */
    public FrameDragListener(JFrame frame) {
        this.frame = frame;
    }

    /**
     * Reseting the coordinates when the user stops moving the frame.
     * @param e - The mouse event that fired
     */
    public void mouseReleased(MouseEvent e) {
        mouseDownCompCoords = null;
    }

    /**
     * Setting the coordinates when the user starts moving the frame.
     * @param e - The mouse event that fired
     */
    public void mousePressed(MouseEvent e) {
        mouseDownCompCoords = e.getPoint();
    }

    /**
     * Setting the location of the frame depending on the current coordinates.
     */
    public void mouseDragged(MouseEvent e) {
        Point currCoords = e.getLocationOnScreen();
        frame.setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
    }
}