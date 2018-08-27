/* *****************************************************
 * @(#)MyImage.java   1.0  12/20/06 17:40:34
 * @author Randy Crihfield
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;

class MyImage extends Component {

   private boolean bDEBUG = false;

   private String sName   = null;
   private Image iMyImage = null;
   private Image iScaled  = null;
   private MediaTracker mTrack = null;

   // indicates it's been set to X or O
   private boolean bSet   = false;

   // this is the size of the actual area the picture will appear in
   private int iWidth = 64, iHeight = 48;

   ///////////////////////////////////////////////////////////

   public MyImage(String sFile) {
      if (bDEBUG) 
         System.out.println("Entering MyImage method!");

      mTrack = new MediaTracker(this);
      changeCurrentImage(sFile);
   }

   ///////////////////////////////////////////////////////////

   public void changeCurrentImage(String sFile) {
      if (bDEBUG) 
         System.out.println("Entering changeCurrentImage method!");

      sName = sFile;

      try {
         if (iMyImage != null)
            mTrack.removeImage(iMyImage);

         iMyImage = getToolkit().getImage(sFile);
         mTrack.addImage(iMyImage, 0);
         } 
      catch (Exception eExcept) {
         System.out.println("Unexpected Exception fetching file!");
         eExcept.printStackTrace();
         System.exit(1);
         }

      prepareImage(iMyImage, this);
      }

   ///////////////////////////////////////////////////////////
   // for indicating it's been set to X or O 
   public void setState(boolean bState) {
      bSet = bState;
   }

   ///////////////////////////////////////////////////////////
   // for indicating it's been set to X or O 
   public boolean getState() {
      return(bSet);
   }

   ///////////////////////////////////////////////////////////
   // for filename/image name comparisons
   public String getName() {
      return(sName);
   }

   ///////////////////////////////////////////////////////////

   public void myRePaint(int iWArea, int iHArea) {
      if (bDEBUG) 
         System.out.println("Entering myRePaint method!");

      iWidth = iWArea;
      iHeight = iHArea;

      myPaint();
      }

   ///////////////////////////////////////////////////////////

   public void myPaint() {

      if (iScaled != null)
         iScaled.flush();
      iScaled = null;

      synchronized(this) {
         notify();
         }
      }   

   ///////////////////////////////////////////////////////////

   public void paint(Graphics gGraphic) {
      if (bDEBUG) 
         System.out.println("Entering paint method!");

      update(gGraphic);
      }

   ///////////////////////////////////////////////////////////

   public void update(Graphics gGraphic) {
      if (bDEBUG) 
         System.out.println("Entering update method!");

      int iW = iMyImage.getWidth(this);
      int iH = iMyImage.getHeight(this);

      float fAdj = (float)iHeight / (float)iH;
      if ((int)(fAdj * iW) > iWidth)
         fAdj = (float)iWidth / (float)iW;

      if (iW >= 0 || iH >= 0) {
         if (iScaled == null) {
            try {
               iScaled = iMyImage.getScaledInstance((int)(iW * fAdj), (int)(iH * fAdj),
                  Image.SCALE_REPLICATE);
               }
            catch (Exception e) {
               System.out.println("There was an exception thrown in the scaling!");
               System.out.println(e.getMessage());
               e.printStackTrace();
               System.exit(1);
               }
            }
         }

      int iOriginX = (int)(((float)iWidth / 2.0) - ((float)iW * fAdj) / 2.0);
      if (iScaled != null && gGraphic != null) {
         gGraphic.drawImage(iScaled, iOriginX, 0, this);
         }
      }
   }

