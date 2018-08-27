/* *****************************************************
 * @(#)GameBoard.java   1.0  12/20/06 17:40:34
 * @author Randy Crihfield
 */
 
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class GameBoard extends Frame implements ActionListener,
   MouseListener, ComponentListener {

   ///////////////////////////////////////////////////
   // global data value settings

   private boolean bDEBUG       = false; 

   private Players pPlayers     = null;
   private int iActivePlayer    = 0;

   private Panel pCenterPanel   = null;
   private MyImage myImage[]    = null;
 
   private Button bPlayer[]     = new Button[2];

   private int iCellX = 0, iCellY = 0;
   private String sPath = "images" + 
      System.getProperty("file.separator");
   private String sPlayPath = "players" + 
      System.getProperty("file.separator");


   ///////////////////////////////////////////////////
   // constructor, draws the window initially.

   GameBoard (ActionListener alParent, Players pPlay) {
      if (bDEBUG)
         System.out.println("In GameBoard method");

      pPlayers = pPlay;

      setTitle("Hollywood Squares!");
      setBackground(Color.blue);
      setForeground(Color.white);
      setLayout(new BorderLayout());

      // show the Round data
      Panel pTopPanel = new Panel();

      Label lTop = new Label("Hollywood Squares!", Label.CENTER);
      lTop.setBackground(Color.blue);
      lTop.setForeground(Color.white);
      pTopPanel.add(lTop, BorderLayout.CENTER);
      add(pTopPanel, BorderLayout.NORTH);


      // show the Tic Tac Toe values
      pCenterPanel = new Panel(new GridLayout(3,3,10,10));
      createImageArray();
      add(pCenterPanel, BorderLayout.CENTER);

      // show the Player data, Control buttons
      Panel pBottomPanel = new Panel(new GridLayout(2,1));

      Panel pPlayerInfo = new Panel();
      pPlayerInfo.setLayout(new GridLayout(1,2));
      for (int iNum = 0; iNum < 2; iNum ++) {
         bPlayer[iNum] = new Button(pPlayers.getPlayer(iNum));
         bPlayer[iNum].setBackground(Color.blue);
         bPlayer[iNum].setForeground(Color.blue);
         bPlayer[iNum].addActionListener(this);
         pPlayerInfo.add(bPlayer[iNum]);
      }
      // initial player selected
      bPlayer[0].setForeground(Color.white);
 
      Panel pControlPanel = new Panel();

      Button bButton = new Button("Help");
      bButton.addActionListener(alParent);
      bButton.setBackground(Color.green);
      bButton.setForeground(Color.black);
      pControlPanel.add(bButton);

      bButton = new Button("Exit");
      bButton.addActionListener(alParent);
      bButton.setBackground(Color.red);
      bButton.setForeground(Color.black);
      pControlPanel.add(bButton);

      pBottomPanel.add(pPlayerInfo);
      pBottomPanel.add(pControlPanel);

      add(pBottomPanel, BorderLayout.SOUTH);

      pack();
      addComponentListener(this);

      setSize(640, 480);
      setLocation(pPlay.getLocation());
      setVisible(true);
   }  


   //////////////////////////////////////////////////
   // load up the images
   public void createImageArray() {
      if (bDEBUG)
         System.out.println("In createCenterPanel method");

      myImage = new MyImage[9];

      // this is the source dir for images...
      File fSrc = new File(sPath);
      if (! fSrc.isDirectory()) {
         System.out.println("Illegal top directory! ");
         System.out.println(sPath + 
            " is not a directory or does not exist!");
         System.exit(1);
         }

      // this is the source dir for player pics...
      File fPlay = new File(sPlayPath);
      if (! fPlay.isDirectory())
         for (int iCount = 0; iCount < 9; iCount ++) 
            myImage[iCount] = new MyImage(sPath + "default.jpg");
      else {
         // get a "dir" listing of the players subdir
         Vector vPics = new Vector();
         vPics.clear();
         String sPics[] = fPlay.list();
         for (int iCount = 0; iCount < sPics.length; iCount ++)
             vPics.add(sPics[iCount]);

         // choose out 9 random pics (or use default)
         for (int iCount = 0; iCount < 9; iCount ++) {
            // there are not enough pics...
            if (vPics.size() == 0)
               myImage[iCount] = new MyImage(sPath + "default.jpg");
            else {
               // there IS a pic there... 
               int iRand = (int)Math.round(Math.random() *
                  (vPics.size() - 1));
               String sTName = (String)vPics.elementAt(iRand);
               myImage[iCount] = new MyImage(sPlayPath + sTName);
               vPics.removeElementAt(iRand);
            }
         }
      }

      
      for (int iCount = 0; iCount < 9; iCount ++) {
         myImage[iCount].addMouseListener(this);
         pCenterPanel.add(myImage[iCount]);
      }
   }

   //////////////////////////////////////////////////
   // catch those events!
   public void actionPerformed(ActionEvent eEvent) {
      if (bDEBUG)
         System.out.println("In actionPerformed method");

      String sAction = eEvent.getActionCommand();

      // unhighlight current player
      bPlayer[iActivePlayer].setForeground(Color.blue);

      if(sAction.equals(pPlayers.getPlayer(0))) {
         iActivePlayer = 0;
      }

      if(sAction.equals(pPlayers.getPlayer(1))) {
         iActivePlayer = 1;
      }

      // highlight current player
      bPlayer[iActivePlayer].setForeground(Color.white);
   }


   //////////////////////////////////////////////////
   // someone's listening to the mouse!
   public void mouseClicked(MouseEvent mEvent) {
      if (bDEBUG)
         System.out.println("In mouseClicked method");

      // normally, we would, but not when correcting
      // an X to O mistake!
      boolean bSwitchPlayers = true;

      MyImage mCell = (MyImage)mEvent.getSource();

      if (mEvent.getButton() == MouseEvent.BUTTON1)
         changeImage(mCell, "X.jpg");
      else {
         if (mEvent.getButton() == MouseEvent.BUTTON3)
            changeImage(mCell, "O.jpg");
         else {
            // don't switch active player
            bSwitchPlayers = false;
            // unset cell state so it can be set to X or O
            mCell.setState(false);

            // switch the X or O state
            if (mCell.getName().equals(sPath + "X.jpg"))
               changeImage(mCell, "O.jpg");
            else
               if (mCell.getName().equals(sPath + "O.jpg"))
                  changeImage(mCell, "X.jpg");
            // else do nothing!  We don't want to set it if
            // it's not already an X or O!
         }
      }

      if (bSwitchPlayers) {
         // now unset active player...
         bPlayer[iActivePlayer].setForeground(Color.blue);

         // switch active player...
         if (iActivePlayer == 0) {
            iActivePlayer = 1;
         } else {
            iActivePlayer = 0;
         }

         // highlight new active player...
         bPlayer[iActivePlayer].setForeground(Color.white);
      }
   }

   //////////////////////////////////////////////////
   // change the image to an X or O
   private void changeImage(MyImage mCell, String sVal) {
      if (bDEBUG)
         System.out.println("In changeImage method");

      if (! mCell.getState()) {

         mCell.setState(true);

         //mCell.removeMouseListener(this);
         mCell.changeCurrentImage(sPath + sVal); 

         reDraw();
         Dimension dSize = mCell.getSize();
         mCell.setSize((int)dSize.getWidth() + 1, (int)dSize.getHeight() + 1);
      }
   }

   //////////////////////////////////////////////////
   // needed overridden to use interface for mouse
   public void mouseEntered(MouseEvent mEvent) {}
   public void mouseExited(MouseEvent mEvent) {}
   public void mousePressed(MouseEvent mEvent) {}
   public void mouseReleased(MouseEvent mEvent) {}


   //////////////////////////////////////////////////
   // this is in case someone graps the corner or sides and changes
   // our display area... 
   public void componentResized(ComponentEvent eEvent) {
      if (bDEBUG)
         System.out.println("In componentResized method");

      reDraw();
   }

   ///////////////////////////////////////////////////
   // This does the redrawing for us...
   private void reDraw() {
      if (bDEBUG)
         System.out.println("In reDraw method");

      Dimension dSize = pCenterPanel.getSize();
      iCellX = (int)(dSize.getWidth() / 3);
      iCellY = (int)(dSize.getHeight() / 3);
      for (int iCount = 0; iCount < 9; iCount ++)
         myImage[iCount].myRePaint(iCellX, iCellY);
      }  
 
   //////////////////////////////////////////////////
   // needed overridden to use interface for component 
   public void componentMoved(ComponentEvent eEvent) {}  
   public void componentShown(ComponentEvent eEvent) {}  
   public void componentHidden(ComponentEvent eEvent) {}
}
