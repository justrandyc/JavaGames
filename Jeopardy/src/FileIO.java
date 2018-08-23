/* *****************************************************
 * @(#)FileIO.java   1.0  09/20/05 17:40:34
 * @author Randy Crihfield
 */

import java.io.*;
import java.lang.*;
import java.util.*;
 
public class FileIO {

   ///////////////////////////////////////////////////
   // global data value settings

   // temp just for testing
   String sCat[][]   = new String[2][6];
   String sQst[][][] = new String[2][6][5];
   String sAns[][][] = new String[2][6][5];
   int iDDbl[] = new int[3];

   ///////////////////////////////////////////////////
   // constructor, draws the window initially.

   public FileIO() {
      readFile("game.dat");
   }


   //////////////////////////////////////////////////
   // Reads the file
   private void readFile(String sDataFile) {

      Vector vCat  = new Vector();
      Vector vQst  = new Vector();
      Vector vAns  = new Vector();
      Vector vDDbl = new Vector();

      try {
         File fData = new File(sDataFile);

         // no data file, just return
         if (! fData.exists()) {
            System.out.println("Data file game.dat not "
               + "found, using defaults!");
            return;
         }

         // open the stream to read in
         InputStream iStream;
         iStream = (InputStream)new FileInputStream(fData);
         BufferedReader bRead;
         bRead = new BufferedReader(new InputStreamReader(iStream));

         // this is the string read
         String readIn;

         // let's read some strings!
         while ((readIn = bRead.readLine()) != null) {
            readIn.trim();

            // throw out blank lines
            if (readIn.length() == 0) 
               continue;

            // throw out comments
            if (readIn.startsWith("#")) 
               continue;

            // read in categories
            if (readIn.startsWith("C")) {
               vCat.addElement(readIn.substring(4));
            }

            // read in questions
            if (readIn.startsWith("Q")) {
               vQst.addElement(readIn.substring(4));
            }

            // is it a Daily Double?
            if (readIn.startsWith("QD")) {
               vDDbl.addElement(vQst.size() - 1);
            }

            // read in answers
            if (readIn.startsWith("A")) {
               vAns.addElement(readIn.substring(4));
            }
         }

         iStream.close();
      } catch(Exception eExcept) {
         System.out.println("Unexpected problem reading data!");
         eExcept.printStackTrace();
      }

      // process the data here...
      processCat(vCat);
      processQst(vQst);
      processAns(vAns);
      processDDbl(vDDbl);
   }


   //////////////////////////////////////////////////
   // Sets the global category value
   private void processCat(Vector vCat) {
      String sRnd1[] = new String[6];
      String sRnd2[] = new String[6];

      // initialize defaults
      for (int iNum = 0; iNum < 6; iNum++) {
         sRnd1[iNum] = "Category " + (iNum + 1) + "a";
         sRnd2[iNum] = "Category " + (iNum + 1) + "b";
      }

      // now pull in data values for categories
      int iNum = 0;
      try {
         for (int iCat = 0; iCat < 6; iCat++) {
            sRnd1[iCat] = (String)vCat.elementAt(iNum);
            iNum++;
         }
         for (int iCat = 0; iCat < 6; iCat++) {
            sRnd2[iCat] = (String)vCat.elementAt(iNum);
            iNum++;
         }
      }
      catch(Exception eExcept) {
         // just go until list runs out, defaults stay
         // for the rest
      }

      sCat[0] = sRnd1;
      sCat[1] = sRnd2;
   }


   //////////////////////////////////////////////////
   // Sets the global question values
   private void processQst(Vector vQst) {
      String sRnd1[][] = new String[6][5];
      String sRnd2[][] = new String[6][5];

      // first initialize the defaults!
      for (int iCol = 0; iCol < 6; iCol++)
         for (int iRow = 0; iRow < 5; iRow++) {
            sRnd1[iCol][iRow] = "What is the Question?";
            sRnd2[iCol][iRow] = "What is the Question?";
         }

      // now pull in data values for answers
      int iNum = 0;
      try {
         for (int iCol = 0; iCol < 6; iCol++)
            for (int iRow = 0; iRow < 5; iRow++) {
               sRnd1[iCol][iRow] = 
                  (String)vQst.elementAt(iNum);
               iNum++;
            }
         for (int iCol = 0; iCol < 6; iCol++)
            for (int iRow = 0; iRow < 5; iRow++) {
               sRnd2[iCol][iRow] = 
                  (String)vQst.elementAt(iNum);
               iNum++;
            }
      }
      catch(Exception eExcept) {
         // just go until list runs out, defaults stay
         // for the rest
      }

      sQst[0] = sRnd1;
      sQst[1] = sRnd2;
   }


   //////////////////////////////////////////////////
   // Sets the global answer values
   private void processAns(Vector vAns) {
      String sRnd1[][] = new String[6][5];
      String sRnd2[][] = new String[6][5];

      // first initialize the defaults!
      for (int iCol = 0; iCol < 6; iCol++)
         for (int iRow = 0; iRow < 5; iRow++) {
            sRnd1[iCol][iRow] = "The Answer!";
            sRnd2[iCol][iRow] = "The Answer!";
         }

      // now pull in data values for answers
      int iNum = 0;
      try {
         for (int iCol = 0; iCol < 6; iCol++)
            for (int iRow = 0; iRow < 5; iRow++) {
               sRnd1[iCol][iRow] = 
                  (String)vAns.elementAt(iNum);
               iNum++;
            }
         for (int iCol = 0; iCol < 6; iCol++)
            for (int iRow = 0; iRow < 5; iRow++) {
               sRnd2[iCol][iRow] = 
                  (String)vAns.elementAt(iNum);
               iNum++;
            }
      }
      catch(Exception eExcept) {
         // just go until list runs out, defaults stay
         // for the rest
      }

      sAns[0] = sRnd1;
      sAns[1] = sRnd2;
   }


   //////////////////////////////////////////////////
   // Sets the global Daily Double values
   private void processDDbl(Vector vDDbl) {
      int iRnd1[] = new int[3];

      // first initialize the defaults!
      for (int iNum = 0; iNum < 3; iNum ++)
         iRnd1[iNum] = -1;

      try {
         for (int iNum = 0; iNum < 3; iNum ++) {
            String sVal = "" + vDDbl.elementAt(iNum);
            iRnd1[iNum] = Integer.valueOf(sVal).intValue();
         }
      }
      catch(Exception eExcept) {
         // just go until list runs out, defaults stay
         // for the rest
      }

      iDDbl = iRnd1;
   }


   //////////////////////////////////////////////////
   // Returns the category headings
   public String[] getCats(int iVal) {
      return(sCat[iVal]);
   }


   //////////////////////////////////////////////////
   // Returns the question
   public String getQuestion(int iRnd, int iCol, int iRow) {
      return(sQst[iRnd][iCol][iRow]);
   }


   //////////////////////////////////////////////////
   // Returns the answer
   public String getAnswer(int iRnd, int iCol, int iRow) {
      return(sAns[iRnd][iCol][iRow]);
   }


   //////////////////////////////////////////////////
   // Returns the Daily Double!
   public boolean isDailyDouble(int iRnd, int iCol, int iRow) {
      // first determine value
      int iVal = iRnd * 30;
      iVal = iVal + (iCol * 5);
      iVal = iVal + iRow;

      // look for value in double array
      if (iDDbl[0] == iVal 
         || iDDbl[1] == iVal 
         || iDDbl[2] == iVal)
         return(true);

      return(false); 
   }
}
