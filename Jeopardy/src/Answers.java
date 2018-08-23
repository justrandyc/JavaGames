/* *****************************************************
 * @(#)Answers.java   1.0  09/20/05 17:40:34
 * @author Randy Crihfield
 */
 
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;

public class Answers implements 
   ActionListener, MouseListener {

   // for debugging
   boolean bDebug = false;

   ///////////////////////////////////////////////////
   // global data value settings

   private GameBoard gBoard      = null;
   private DoubleBox dDoubleBox  = null;
   private AnswerBox aBox        = null;
   private Players pPlayers      = null;
   private Panel pRounds[]       = null;
   private TextField tWager[]    = new TextField[4];
   private FileIO fIO;
   private Font fBig             = new Font("Serif", Font.BOLD, 32);
   private VWrappingLabel lCheck = null;
   private int iControl          = 0;

   ///////////////////////////////////////////////////
   // constructor, draws the window initially.

   Answers(GameBoard gBrd, Players pPlay) {

      if (bDebug)
         System.out.println("In constructor method");

      gBoard = gBrd;
      pPlayers = pPlay;
      dDoubleBox = new DoubleBox(gBrd);
      aBox = new AnswerBox(gBrd);

      fIO = new FileIO();

      pRounds = new Panel[3];
      pRounds[0] = normal(1);
      pRounds[1] = normal(2);
      pRounds[2] = finalRound();
   }


   //////////////////////////////////////////////////
   // return the panel for use
   public Panel getRound(int iVal) {
      if (bDebug)
         System.out.println("In getRound method");

      return(pRounds[iVal]);
   }


   //////////////////////////////////////////////////
   // make it normal w/squares
   private Panel normal(int iRound) {
      if (bDebug)
         System.out.println("In normal method");

      VWrappingLabel lCell;

      String sCats[] = fIO.getCats(iRound - 1);

      Panel pRound = new Panel(new GridLayout(6,6));
      // adding the categories
      for (int iAcross = 0; iAcross < 6; iAcross ++) {
         lCell = new VWrappingLabel(sCats[iAcross], 
            Canvas.CENTER_ALIGNMENT, Canvas.CENTER_ALIGNMENT);
         lCell.setFont(fBig);
         lCell.setBackground(Color.yellow);
         lCell.setForeground(Color.black);
         pRound.add(lCell);
         }

      // adding the dollar values
      for (int iDown = 1; iDown < 6; iDown ++) {
         for (int iAcross = 0; iAcross < 6; iAcross ++) {
            String sVal = "$" + (100 * iRound * iDown);
            lCell = new VWrappingLabel(sVal,
               Canvas.CENTER_ALIGNMENT, Canvas.CENTER_ALIGNMENT);
            lCell.setFont(fBig);
            lCell.setName(sVal + ":" + (iRound - 1) + 
               ":" + iAcross + ":" + (iDown - 1));
            lCell.addMouseListener(this);
            lCell.setBackground(Color.black);
            lCell.setForeground(Color.white);
            pRound.add(lCell);
         }
      }
 
      return(pRound);
   }  


   //////////////////////////////////////////////////
   // final jeopardy panel
   private Panel finalRound() {
      if (bDebug)
         System.out.println("In finalRound method");

      // panel for the wagers
      Panel pWagers = new Panel(new GridLayout(1,6));
      pWagers.add(new Label(""));
      for (int iNum = 0; iNum < 4; iNum ++) {
          tWager[iNum] = new TextField("0");
          tWager[iNum].setBackground(Color.white);
          tWager[iNum].setForeground(Color.black);
          pWagers.add(tWager[iNum]);
      }
      pWagers.add(new Label(""));

      // panel for the right/wrong buttons
      Panel pRightWrong = new Panel();
      Button bButton = new Button("Right Answer");
      bButton.addActionListener(this);
      bButton.setBackground(Color.black);
      bButton.setForeground(Color.white);
      pRightWrong.add(bButton);
      bButton = new Button("Wrong Answer");
      bButton.addActionListener(this);
      bButton.setBackground(Color.black);
      bButton.setForeground(Color.white);
      pRightWrong.add(bButton);
      
      Panel pRound = new Panel(new BorderLayout());
      pRound.add(new Label(), BorderLayout.NORTH);
      pRound.add(pWagers, Label.CENTER);
      pRound.add(pRightWrong, BorderLayout.SOUTH);

      return(pRound);
   }


   //////////////////////////////////////////////////
   // someone's listening
   public void actionPerformed(ActionEvent eEvent) {
      if (bDebug)
         System.out.println("In actionPerformed method");

      String sAction = eEvent.getActionCommand();
      int iActive = gBoard.getActivePlayer();

      if(sAction.equals("Right Answer")) {
         int iPoints = convert(tWager[iActive].getText());
         int iScore = convert(pPlayers.getScore(iActive));

         iScore = iScore + iPoints;

         pPlayers.setScore(iActive, String.valueOf(iScore));
         gBoard.allDone(iActive);
      }

      if(sAction.equals("Wrong Answer")) {
         int iPoints = convert(tWager[iActive].getText());
         int iScore = convert(pPlayers.getScore(iActive));

         iScore = iScore - iPoints;

         pPlayers.setScore(iActive, String.valueOf(iScore));
         gBoard.allDone(iActive);
      }
   }


   //////////////////////////////////////////////////
   // someone's listening
   public void mouseClicked(MouseEvent mEvent) {
      if (bDebug)
         System.out.println("In mouseClicked method");

      VWrappingLabel lCell = (VWrappingLabel)mEvent.getSource();
      int iActive = gBoard.getActivePlayer();

      // first see if it's the current cell
      if (lCheck != null) {
         // check to see if answer was already given
         if (lCheck.getName().startsWith("$"))
            if (! lCheck.getName().equals(lCell.getName()))
               return;
      }

      lCheck = lCell;

      if (lCell.getName().startsWith("$")) {
         // chop off the dollar amount for comparison,
         // scoring, etc.
         String sDollar = lCell.getName().substring(1);
         String sTokens[] = sDollar.split(":");
         sDollar = sTokens[0];

         int iRnd = Integer.valueOf(sTokens[1]).intValue();
         int iCol = Integer.valueOf(sTokens[2]).intValue();
         int iRow = Integer.valueOf(sTokens[3]).intValue();

         // if name is same as label, it's the first time.
         // set the label to the answer
         if (lCell.getText() != null && lCell.getText().equals("$" + sDollar)) {
             String sQuest = fIO.getAnswer(iRnd, iCol, iRow);
             lCell.setText(sQuest);
             lCell.setBackground(Color.blue);
             // launch modal box
             if (fIO.isDailyDouble(iRnd, iCol, iRow))
                 dDoubleBox.showDailyDouble();
             aBox.setText(sQuest, dDoubleBox);
         } else {
            // it's already showing the answer. Process the
            // mouse response event

            String sPlayer = pPlayers.getPlayer(iActive);
            int iPoints = convert(sDollar);
            int iScore = convert(pPlayers.getScore(iActive));

            if (mEvent.getButton() == MouseEvent.BUTTON1) {
               lCheck = null;
               iControl = iActive;
               iScore = iScore + iPoints;

               // change button label to player name
               lCell.setText(sPlayer);
               lCell.setName(sPlayer);
               lCell.setBackground(Color.green);
               lCell.setForeground(Color.black);
            } else {
               if (mEvent.getButton() == MouseEvent.BUTTON2) {
                  lCheck = null;

                  // reset to last picking person
                  gBoard.setActivePlayer(iControl);

                  // show no one got it
                  lCell.setText(fIO.getQuestion(iRnd, iCol, iRow));
                  lCell.setName("No Answer");
                  lCell.setBackground(Color.gray);
               } else {
                  iScore = iScore - iPoints;
                  lCell.setBackground(Color.red);
               }
            }

            pPlayers.setScore(iActive, String.valueOf(iScore));
            gBoard.refresh();
         }
      }
   }

   //////////////////////////////////////////////////
   // convert String to int for score
   private int convert(String sVal) {
      if (bDebug)
         System.out.println("In convert method");

      return(Integer.valueOf(sVal).intValue());
   }

   //////////////////////////////////////////////////
   // needed overridden to use interface for mouse
   public void mouseEntered(MouseEvent mEvent) {}
   public void mouseExited(MouseEvent mEvent) {}
   public void mousePressed(MouseEvent mEvent) {}
   public void mouseReleased(MouseEvent mEvent) {}
}
