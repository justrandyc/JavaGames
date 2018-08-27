
/** Written in 2013 by Randy Crihfield to display "bingo"
 * questions for a New Year's Eve game
 */

import java.io.*;
import java.util.*;

public class FileIO {

    Properties pData = new Properties();

    public FileIO(String sDataFile) {

        File fGame = new File(sDataFile);

        if (!fGame.exists()) {
            System.out.println("\nGame file " + sDataFile + 
                " does not exist!\n");
            System.exit(1);
        }

        try {
            pData.load(new FileInputStream(fGame));
        } catch (Exception eExcept) {
            System.out.println("\nException thrown reading data!");
            System.out.println(eExcept.getMessage());
            System.exit(1);
        }
    }

    /**
     * Returns Value for the key
     */
    public String getValue(String sKey) {
        return (pData.getProperty(sKey));
    }

    /**
     * Returns keys from the property file
     */
    public ArrayList getKeys() {
        
        ArrayList<String> lReturn = null;
        lReturn = new ArrayList<String>(pData.stringPropertyNames());
        Collections.shuffle(lReturn);

        return (lReturn);
    }

    /**
     * Dump of Properties for debugging
     */
    public void printData() {
        PrintWriter pWriter = new PrintWriter(System.out);
        pData.list(pWriter);
        pWriter.flush();
    }
}
