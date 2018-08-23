/* *****************************************************
 * @(#)DoubleBox.java   1.0  09/20/05 17:40:34
 * @author Randy Crihfield
 */
 
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;

public class DoubleBox extends Dialog
   implements MouseListener {

   ///////////////////////////////////////////////////
   // global data value settings

   private boolean bDebug = false;

   private VWrappingLabel lText = null;
   private GameBoard gBoard     = null;

   ///////////////////////////////////////////////////
   // constructor, draws the window initially.

   DoubleBox (GameBoard gBrd) {
      super(gBrd, "Daily Double", true);
      if (bDebug)
         System.out.println("In constructor method");

      gBoard = gBrd;
      Font fBig = new Font("Serif", Font.BOLD, 72);

      lText = new VWrappingLabel("Placeholder",
         Canvas.CENTER_ALIGNMENT, Canvas.CENTER_ALIGNMENT);
      lText.setFont(fBig);
      lText.setBackground(Color.blue);
      lText.addMouseListener(this);
      add(lText);
   }


   //////////////////////////////////////////////////
   // it's showtime!
   public void showDailyDouble() {
      if (bDebug)
         System.out.println("In showDailyDouble method");

      setSize(gBoard.getSize());

      lText.setText("DAILY DOUBLE!");

      gBoard.setVisible(false);
      setVisible(true);
   }


   //////////////////////////////////////////////////
   // someone's listening
   public void mouseClicked(MouseEvent mEvent) {
      if (bDebug)
         System.out.println("In mouseClicked method");

      setVisible(false);
   }

   //////////////////////////////////////////////////
   // needed overridden to use interface for mouse
   public void mouseEntered(MouseEvent mEvent) {}
   public void mouseExited(MouseEvent mEvent) {}
   public void mousePressed(MouseEvent mEvent) {}
   public void mouseReleased(MouseEvent mEvent) {}
}
