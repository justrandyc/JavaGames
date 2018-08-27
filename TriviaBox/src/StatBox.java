/* *****************************************************
 * @(#)StatBox.java   2.0  08/16/17 17:40:34
 * @author Randy Crihfield
 *
 * Copyright 2017 Hozer Video Games.  All rights reserved.
 * Use is subject to license terms.
 */
 
import java.awt.*;
import javax.swing.*;

public class StatBox extends Dialog {

   ///////////////////////////////////////////////////
   // global data value settings

   private boolean bDEBUG = false;

   private Font fSmall    = new Font("Serif", Font.BOLD, 20);

   private Trivia tParent = null;
   private Panel pPlayers = null;
   private Label lTotal   = null;
   private Label lSoFar   = null;
   private Player pPlay   = null;


   ///////////////////////////////////////////////////
   // constructor, draws the window initially.

   StatBox (boolean bBug, JDialog fParent, Player pThePlayers) {
      super(fParent, "Randy's Trivia Box - " +
         "WWW.JUSTRANDY.COM", false);

      if (bBug)
         System.out.println("In StatBox method");

      pPlay = pThePlayers;

      tParent = (Trivia)fParent;
      bDEBUG = bBug;

      setLayout(new BorderLayout());
      pPlayers = new Panel(new GridLayout(pPlay.getNumPlayers() + 1, 6));

      Panel pTotals = new Panel(new GridLayout(2,1));

      lSoFar = makeLabel("");
      pTotals.add(lSoFar);
      lTotal = makeLabel("");
      pTotals.add(lTotal);

      refresh();

      add(pPlayers, BorderLayout.CENTER);
      add(pTotals, BorderLayout.SOUTH);
   }


   ///////////////////////////////////////////////////
   // quick little make label method

   private Label makeLabel(String sContents) {
      if (bDEBUG)
         System.out.println("In makeLabel method");

      Label lReturn = new Label(sContents, Label.CENTER);
      lReturn.setFont(fSmall);
      lReturn.setForeground(Color.blue);
      lReturn.setBackground(Color.white);

      return(lReturn);
   }


   ///////////////////////////////////////////////////
   // this does all the real contents work

   public void refresh() {
      if (bDEBUG)
         System.out.println("In refresh method");

      pPlayers.removeAll();

      // headings
      pPlayers.add(makeLabel("Player:"));
      pPlayers.add(makeLabel("Right:"));
      pPlayers.add(makeLabel("% Right"));
      pPlayers.add(makeLabel("Wrong:"));
      pPlayers.add(makeLabel("% Wrong"));
      pPlayers.add(makeLabel("SCORE"));

      for (int count = 0; count < pPlay.getNumPlayers(); count ++) {
         int iRight = tParent.getStat(count, 0);
         int iWrong = tParent.getStat(count, 1);
         int iResponses = iRight + iWrong;
         if (iResponses == 0)
            iResponses = 1;

         pPlayers.add(makeLabel(pPlay.getNameLabel(count).getText()));
         pPlayers.add(makeLabel("" + iRight));
         pPlayers.add(makeLabel("" + (iRight * 100 / iResponses) + "%"));
         pPlayers.add(makeLabel("" + iWrong));
         pPlayers.add(makeLabel("" + (iWrong * 100 / iResponses) + "%"));
         pPlayers.add(makeLabel(pPlay.getScoreLabel(count).getText()));
      }

      // all player stats...

      int iSoFar = tParent.getNumSoFar();
      int iTotal = tParent.getNumTotal();

      lSoFar.setText("Number of Questions so far: " + iSoFar);
      lTotal.setText("Total number of Questions: " + iTotal);
   }
}
