
import java.io.*;

public class Nation {

    String sName    = "Undefined";
    int[]  iXPoints = null;
    int[]  iYPoints = null;
    int    iPairs   = 0;
    int    iMaxX    = 0;
    int    iMaxY    = 0;
    int    iBoundX  = 0;
    int    iBoundY  = 0;

    public Nation(String sInstance) {
        sName = sInstance;

        try {
            String sFile = "MapData/" + sInstance + ".txt";
            FileInputStream fStream = new FileInputStream(sFile);
            DataInputStream dStream = new DataInputStream(fStream);
            BufferedReader bRead = 
                new BufferedReader(new InputStreamReader(dStream));

            String sLine = null;
            while ((sLine = bRead.readLine()) != null) {

                if (sLine.startsWith("bx")) {
                    iBoundX = Integer.parseInt(sLine.substring(2));
                }

                if (sLine.startsWith("by")) {
                    iBoundY = Integer.parseInt(sLine.substring(2));
                }

                if (sLine.startsWith("x")) {
                    iMaxX = Integer.parseInt(sLine.substring(1));
                }

                if (sLine.startsWith("y")) {
                    iMaxY = Integer.parseInt(sLine.substring(1));
                }

                if (sLine.startsWith("AllX")) {
                    String[] sTemp = sLine.substring(5).split(" ");
                    iPairs = sTemp.length;
                    iXPoints = new int[iPairs];
                    for (int i = 0; i < iPairs; i++) {
                        iXPoints[i] = Integer.parseInt(sTemp[i]);
                    }
                }

                if (sLine.startsWith("AllY")) {
                    String[] sTemp = sLine.substring(5).split(" ");
                    iYPoints = new int[iPairs];
                    for (int i = 0; i < iPairs; i++) {
                        iYPoints[i] = Integer.parseInt(sTemp[i]);
                    }
                }
            }

            dStream.close();

        } catch (Exception eExcept) {
            System.out.println("Unexpected error reading " +
                sInstance + ".txt!");
            System.exit(1);
        }

    }

    public String getName() {
        return (sName);
    }

    public int getXBound() {
        return (iBoundX);
    }

    public int getYBound() {
        return (iBoundY);
    }

    public int getMaxX() {
        return (iMaxX);
    }

    public int getMaxY() {
        return (iMaxY);
    }

    public int getPairs() {
        return (iPairs);
    }

    public int[] getXVals() {
        return (iXPoints);
    }

    public int[] getYVals() {
        return (iYPoints);
    }
}
