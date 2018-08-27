/* *****************************************************
 * @(#)GameBoard.java   2.0  08/16/17 17:40:34
 * @author Randy Crihfield
 *
 * Copyright 2017 Hozer Video Games.  All rights reserved.
 * Use is subject to license terms.
 */
 
import java.awt.*;

public class GameBoard extends Frame {

   ///////////////////////////////////////////////////
   // global data value settings

   private boolean    bDEBUG       = false; 

   private FileIO     fIO          = null;
   private FunkyLabel flQuestion   = null;
   private TextField  tfAnswers[]  = new TextField[6];
   private String     sStoreResp   = null;
   private int        iCorrect     = -1;
   private int        iCurrent     = 0;
   private Player     pPlay        = null;
 
   private Font fBig  = new Font("Serif", Font.BOLD, 96);
   private Font fTiny = new Font("Serif", Font.BOLD, 24);


   ///////////////////////////////////////////////////
   // constructor, draws the window initially.

   GameBoard(Player pPlayers, boolean bBug) {
      if (bBug)
         System.out.println("In GameBoard method");

      bDEBUG = bBug;
      pPlay = pPlayers;

      fIO = new FileIO(bBug);

      setTitle("Randy's Trivia Box - " +
         "WWW.JUSTRANDY.COM");
      setLayout(new BorderLayout());
      setDefaults();

      // show the players
      Panel pPlayerPanel = new Panel(new GridLayout(2, 
         pPlay.getNumPlayers()));
      for (int count = 0; count < pPlay.getNumPlayers(); count ++) { 
         pPlayerPanel.add(pPlay.getNameLabel(count));
      }
      for (int count = 0; count < pPlay.getNumPlayers(); count ++) { 
         pPlayerPanel.add(pPlay.getScoreLabel(count));
      }
      add(pPlayerPanel, BorderLayout.NORTH);

      // show the Question
      add(flQuestion, BorderLayout.CENTER);

      // show the Answers
      Panel pAnswerPanel = new Panel(new GridLayout(2,3));
      for (int count = 0; count < 6; count++)
         pAnswerPanel.add(tfAnswers[count]);
      add(pAnswerPanel, BorderLayout.SOUTH);
      pack();

      setSize(1280, 960);
      setVisible(false);
   }  


   //////////////////////////////////////////////////
   // load up the initial string values
   public void setDefaults() {
      if (bDEBUG)
         System.out.println("In setDefaults method");

      // the Question
      flQuestion = new FunkyLabel(false);
      flQuestion.setFont(fBig);
      flQuestion.setBackground(Color.darkGray);
      flQuestion.setForeground(Color.white);

      // the Answers
      for (int count = 0; count < 6; count++) {
         tfAnswers[count] = new TextField("Answer number " + count, 25);
         tfAnswers[count].setForeground(Color.black);
         tfAnswers[count].setFont(fTiny);
         tfAnswers[count].setEditable(false);
      }
      tfAnswers[0].setBackground(Color.red);
      tfAnswers[0].setForeground(Color.white);
      tfAnswers[1].setBackground(new Color(218, 129, 47));
      tfAnswers[2].setBackground(Color.yellow);
      tfAnswers[3].setBackground(new Color(37, 208, 40));
      tfAnswers[4].setBackground(Color.blue);
      tfAnswers[4].setForeground(Color.white);
      tfAnswers[5].setBackground(new Color(148, 37, 215));
      tfAnswers[5].setForeground(Color.white);

      if (fIO != null) {
         // set to initial question!
         setQuestion(iCurrent);
      }
   }


   //////////////////////////////////////////////////
   // load up a particular value
   public void setQuestion(int iVal) {
      if (bDEBUG)
         System.out.println("In setQuestion method");

      // the Question
      flQuestion.setText(fIO.getQuestion(iVal));

      // set the "correct" answer index
      iCorrect = fIO.getCorrect(iVal);

      // the Answers
      for (int count=0; count < 6; count++)
         tfAnswers[count].setText(fIO.getAnswer(iVal, count));

      // stash the Response
      sStoreResp = fIO.getResponse(iVal);

      if (bDEBUG) {
         System.out.println("Current Answers:");
         for (int count=0; count < 6; count++) {
            System.out.println("  A" + (count + 1) + ": " +
               tfAnswers[count].getText());
         }
         System.out.println("  R: " + sStoreResp);
         System.out.println(" Correct answer is " + 
            (iCorrect + 1));
      }
   }


   //////////////////////////////////////////////////
   // return the intro text, if any
   public String getIntro() {
      if (bDEBUG)
         System.out.println("In getIntro method");

   return(fIO.getIntroText());
   }


   //////////////////////////////////////////////////
   // return the answer chosen
   public String getAnswer(int iVal) {
      if (bDEBUG)
         System.out.println("In getAnswer method");

      return(tfAnswers[iVal].getText());
   }


   //////////////////////////////////////////////////
   // return if answer is right or not
   public boolean isCorrect(int iVal) {
      if (bDEBUG)
         System.out.println("In isCorrect method");

      if (iVal == iCorrect)
         return(true);

      return(false);
   }


   //////////////////////////////////////////////////
   // return the response
   public String getResponse(int iVal) {
      if (bDEBUG)
         System.out.println("In getResponse method");

      if (tfAnswers[iVal].getText() == null || 
         tfAnswers[iVal].getText().equals(""))
         return(null);

      if (iVal != iCorrect) {
         if (isVisible())
            tfAnswers[iVal].setText("");
         return(null);
      }

      return(fIO.getRight() + " " + sStoreResp);
   }


   //////////////////////////////////////////////////
   // so Triva.java knows number of Q's for stat usage

   public int getNumOfQuestions() {
      if (bDEBUG)
         System.out.println("In getNumberOfQuestions method");

      return(fIO.getNumQuestions());
   }


   //////////////////////////////////////////////////
   // advance to next question

   public void nextQuestion() {
      if (bDEBUG)
         System.out.println("In nextQuestion method");

      iCurrent++;

      // do we roll over to the beginning, or not?
      if (iCurrent == fIO.getNumQuestions())
         iCurrent = 0;

      setQuestion(iCurrent);
      pPlay.refreshScores();
   }
}
