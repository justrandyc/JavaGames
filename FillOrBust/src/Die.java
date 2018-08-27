/* *****************************************************
 * @(#)Die.java   1.0  11/20/06 17:40:34
 * @author Randy Crihfield
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;

class Die extends Component {

    private String sAvailable[] = {
        "die_one.gif",
        "die_two.gif",
        "die_three.gif",
        "die_four.gif",
        "die_five.gif",
        "die_six.gif" };

    private String sUnAvailable[] = {
        "die_one_g.gif",
        "die_two_g.gif",
        "die_three_g.gif",
        "die_four_g.gif",
        "die_five_g.gif",
        "die_six_g.gif" };

    private boolean bDEBUG = false;
    private boolean bAvail = false;

    private Image iMyImage = null;
    private Image iScaled  = null;
    private MediaTracker mTrack = null;

    // this is the size of the actual area the picture will appear in
    private int iWidth = 64, iHeight = 48;

    private GameBoard gBoard = null;
    private int iVal;
    private String sPath = "images" +
        System.getProperty("file.separator") +
        "dice" +
        System.getProperty("file.separator");

    ///////////////////////////////////////////////////////////
    // the constructor itself
    public Die(GameBoard fValue) {
        iVal = doValue();
        gBoard = fValue;

        mTrack = new MediaTracker(this);
        changeCurrentImage(sPath + sAvailable[iVal - 1]);
    }

    // reports if die is availiable to roll again or not
    public boolean isAvailable() {
        return(bAvail);
    }
    public void setAvailable(boolean bState) {
        bAvail = bState;

        if (! bAvail)
            changeCurrentImage(sPath + sUnAvailable[iVal - 1]);
    }

    // allows for image to be repainted
    public void repaint(int iX, int iY) {
        myRePaint(iX, iY);
    }

    // does the roll, updates as it changes the value
    public void roll() {
        if (! bAvail) 
            return;

        // dice tumble at least 4 at at max 16 times
        int iNumOfBounces = (int)Math.round(Math.random() * 12) + 4;

        for (int iTumble = 0; iTumble < iNumOfBounces; iTumble ++) {
            iVal = doValue();
            changeCurrentImage(sPath + sAvailable[iVal - 1]);
            gBoard.reDrawDice();
            Dimension dSize = getSize();
            setSize((int)dSize.getWidth() + 1,
                (int)dSize.getHeight() + 1);
        }
    }

    // returns the die value for processing
    public int getValue() {
        return(iVal);
    }

    // generates a random value from 1-6
    private int doValue() {
        return((int)Math.round(Math.random() * 5) + 1);
    }

    ///////////////////////////////////////////////////////////

    public void changeCurrentImage(String sFile) {
        if (bDEBUG) 
            System.out.println("Entering changeCurrentImage method!");

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
                    iScaled = iMyImage.getScaledInstance(
                        (int)(iW * fAdj), (int)(iH * fAdj),
                        Image.SCALE_REPLICATE);
                }
                catch (Exception e) {
                    System.out.println("There was an exception" +
                        " thrown in the scaling!");
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

