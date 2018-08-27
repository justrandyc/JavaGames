import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

public class DrawCountry extends JButton {

    Nation nCountry = null;
    int    iArmies  = 0;
    Shape  sShape   = null;

    public DrawCountry(Nation nNation) {
        super();

        nCountry = nNation;
        setActionCommand(nCountry.getName());
        updateArmies(0);

        // These statements enlarge the button so that it
        // becomes a circle rather than an oval.
//        Dimension dSize = getPreferredSize();
//        dSize.width = dSize.height = Math.max(dSize.width, dSize.height);
//        setPreferredSize(dSize);

        // This call causes the JButton not to paint the background.
        // This allows us to paint a round background.
        setContentAreaFilled(false);
    }

    // Paint the background and label.
    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            // You might want to make the highlight color
            // a property of the DrawCountry class.
            g.setColor(Color.lightGray);
        } else {
            g.setColor(getBackground());
        }
        g.fillPolygon(nCountry.getXVals(), nCountry.getYVals(),
            nCountry.getPairs());

        // This call will paint the label and the focus rectangle.
        super.paintComponent(g);
    }

    // Paint the border of the button using a simple stroke.
    protected void paintBorder(Graphics g) {
        g.setColor(getForeground());
        g.drawPolygon(nCountry.getXVals(), nCountry.getYVals(),
            nCountry.getPairs());
    }

    // Hit detection.
    public boolean contains(int x, int y) {
        // If the button has changed size, make a new shape object.
        if (sShape == null || !sShape.getBounds().equals(getBounds())) {
            sShape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
        }
        return sShape.contains(x, y);
    }

    // change in army count
    public void updateArmies(int iDelta) {
        iArmies = iArmies + iDelta;

        setText("<html>" + nCountry.getName() + "<br>" + iArmies + 
            " Armies" + "</html>");
    }
}
