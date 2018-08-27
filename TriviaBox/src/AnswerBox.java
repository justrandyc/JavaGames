/* *****************************************************
 * @(#)AnswerBox.java   2.0  08/16/17 17:40:34
 * @author Randy Crihfield
 *
 * Copyright 2017 Hozer Video Games.  All rights reserved.
 * Use is subject to license terms.
 */
 
import java.awt.*;
import javax.swing.*;

public class AnswerBox extends Dialog {

   ///////////////////////////////////////////////////
   // global data value settings

   private boolean bDEBUG       = false;

   private FunkyLabel lContents = null;
   private boolean bIsIntro     = false;
   private String sDialogText   = "";
   private String sNext         = "\n\n" +
      "Press 'Next' to continue";

   private Font fBig = new Font("Serif", Font.BOLD, 40);

   ///////////////////////////////////////////////////
   // constructor, draws the window initially.

   AnswerBox (boolean bBug, JFrame fParent) {
      super(fParent, "Randy's Trivia Box", false);

      if (bBug)
         System.out.println("In AnswerBox Constructor");

      bDEBUG = bBug;

      setLayout(new BorderLayout());

      lContents = new FunkyLabel(false);
      lContents.setFont(fBig);
      lContents.setBackground(Color.blue);
      lContents.setForeground(Color.white);
      add(lContents, BorderLayout.CENTER);
   }

   //////////////////////////////////////////////////
   // set the text like a message box
   public void setText(String sNewText) {

      if (bDEBUG)
         System.out.println("in AnswerBox.setText method");

      sDialogText = sNewText;

      lContents.setText(sDialogText + sNext);
      bIsIntro = true;
   }

   //////////////////////////////////////////////////
   // set the text from a response
   public void setText(String sPlay, String sChoice,
      String sNewText) {

      if (bDEBUG)
         System.out.println("in AnswerBox.setText method");

      sDialogText = sNewText;

      lContents.setText("==> " + sPlay + " <==\n\n" +
         sDialogText + sNext);
      bIsIntro = false;
   }

   //////////////////////////////////////////////////
   // set the text for the "next" buttons
   public void setNext(String sNewText) {

      if (bDEBUG)
         System.out.println("in AnswerBox.setNext method");

      lContents.setText(sDialogText + 
         "\n\nWaiting on players: " + sNewText);
   }

   //////////////////////////////////////////////////
   // determines if its a real answer box or intro box
   public boolean isIntro() {
      return(bIsIntro);
   }
   
}
