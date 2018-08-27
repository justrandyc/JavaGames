/* *****************************************************
 * @(#)Cards.java   1.0  11/20/06 17:40:34
 * @author Randy Crihfield
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;

class Cards extends Component {

    private boolean bDEBUG = false;

    private String sCardBack = "cardback.GIF";
    private String sBlankCard = "blankcard.GIF";

    // make sure these correspond 1 to 1!
    public static int BONUS300      = 0;
    public static int BONUS400      = 1;
    public static int BONUS500      = 2;
    public static int NODICE        = 3;
    public static int FILL1000      = 4;
    public static int MUSTBUST      = 5;
    public static int VENGEANCE     = 6;
    public static int DOUBLETROUBLE = 7;

    private String sCardFiles[] = {
        "bonus_300.GIF",
        "bonus_400.GIF",
        "bonus_500.GIF",
        "nodice.GIF",
        "fill1000.GIF",
        "mustbust.GIF",
        "vengeance.GIF",
        "double.GIF" };

    // The deck has 12 300's, 10 400's, 8 500's, etc.
    // so, create a deck of integer chars and randomly
    // snag one, convert it to an int (the card type),
    // and shorten the string unil there are no cards
    // left!
    private String sDeck = 
        "000000000000" + 
        "1111111111" +
        "22222222" +
        "33333333" +
        "444444" +
        "5555" +
        "6666" +
        "77";

    private int iType = 0;
    private Image iMyImage = null;
    private Image iScaled  = null;
    private MediaTracker mTrack = null;
    private String sPath   = "images" +
        System.getProperty("file.separator") +
        "cards" +
        System.getProperty("file.separator");

    // this is the size of the actual area the picture will appear in
    private int iWidth = 640, iHeight = 480;
    private GameBoard gBoard = null;

    ///////////////////////////////////////////////////////////

    public Cards(GameBoard fValue, boolean bCardBack) {
        if (bDEBUG) 
            System.out.println("Entering Cards method!");

        gBoard = fValue;

        mTrack = new MediaTracker(this);
        if (bCardBack)
            changeCurrentImage(sCardBack);
        else
            changeCurrentImage(sBlankCard);
    }

    ///////////////////////////////////////////////////////////

    public void blankCard() {
        changeCurrentImage(sBlankCard);
    }

    ///////////////////////////////////////////////////////////

    public void newCard() {
        // get index which is 0 to length of remaining deck!
        int iIndex = (int)Math.round(Math.random() * sDeck.length());
        iIndex = iIndex % sDeck.length();

        // convert int character to real int!
        iType = Integer.valueOf(
            String.valueOf(sDeck.charAt(iIndex))
            ).intValue();

        // cut the character out of the string
        if (sDeck.length() == 1)
            sDeck = "";
        else {
            // it's the first card in the deck
            if (iIndex == 0)
                sDeck = sDeck.substring(1);
            else 
                if ((iIndex + 1) == sDeck.length())
                    sDeck = sDeck.substring(0, iIndex);
                else
                    sDeck = sDeck.substring(0, iIndex) +
                        sDeck.substring(iIndex + 1);
        }

        // display the card chosen...
        changeCurrentImage(sCardFiles[iType]);
    }

    ///////////////////////////////////////////////////////////

    // which card is it, after all...
    public int getType() {
        return(iType);
    }

    ///////////////////////////////////////////////////////////

    public void changeCurrentImage(String sFile) {
        if (bDEBUG) 
            System.out.println("Entering changeCurrentImage method!");

        try {
            if (iMyImage != null)
                mTrack.removeImage(iMyImage);

            iMyImage = getToolkit().getImage(sPath + sFile);
            mTrack.addImage(iMyImage, 0);
        } 
        catch (Exception eExcept) {
            System.out.println("Unexpected Exception fetching file!");
            eExcept.printStackTrace();
            System.exit(1);
        }

        prepareImage(iMyImage, this);

        gBoard.reDrawCards();
        Dimension dSize = getSize();
        setSize((int)dSize.getWidth() + 1,
            (int)dSize.getHeight() + 1);
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

        int iOriginX = (int)(((float)iWidth / 2.0) -
            ((float)iW * fAdj) / 2.0);
        if (iScaled != null && gGraphic != null) {
            gGraphic.drawImage(iScaled, iOriginX, 0, this);
        }
    }
}

