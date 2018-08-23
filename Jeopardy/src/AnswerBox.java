/* *****************************************************
 * @(#)AnswerBox.java   1.0  09/20/05 17:40:34
 * @author Randy Crihfield
 */
 
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;

public class AnswerBox extends Dialog
   implements MouseListener {

   ///////////////////////////////////////////////////
   // global data value settings

   private boolean bDebug = false;

   private VWrappingLabel lContents = null;
   private GameBoard gBoard         = null;

   ///////////////////////////////////////////////////
   // constructor, draws the window initially.

   AnswerBox (GameBoard gBrd) {
      super(gBrd, "Current Question", false);
      if (bDebug)
         System.out.println("In constructor method");

      gBoard = gBrd;
      Font fBig = new Font("Serif", Font.BOLD, 72);

      lContents = new VWrappingLabel("Placeholder",
         Canvas.CENTER_ALIGNMENT, Canvas.CENTER_ALIGNMENT);
      lContents.setFont(fBig);
      lContents.setBackground(Color.blue);
      lContents.addMouseListener(this);
      add(lContents);
   }


   //////////////////////////////////////////////////
   // set the text
   public void setText(String sNewText, DoubleBox dBox) {
      if (bDebug)
         System.out.println("In setText method");

      // Daily Double box might be there already!
      // wait for it to go away
      while (dBox.isVisible()) {}

      setSize(gBoard.getSize());

      lContents.setText(sNewText);

      gBoard.setVisible(false);
      setVisible(true);

      if (bDebug)
         System.out.println("Exit setText method");
   }


   //////////////////////////////////////////////////
   // someone's listening
   public void mouseClicked(MouseEvent mEvent) {
      if (bDebug)
         System.out.println("In mouseClicked method");

      setVisible(false);
      gBoard.setVisible(true);
   }

   //////////////////////////////////////////////////
   // needed overridden to use interface for mouse
   public void mouseEntered(MouseEvent mEvent) {}
   public void mouseExited(MouseEvent mEvent) {}
   public void mousePressed(MouseEvent mEvent) {}
   public void mouseReleased(MouseEvent mEvent) {}
}
