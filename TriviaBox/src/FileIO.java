/* *****************************************************
 * @(#)FileIO.java   2.0  08/16/17 17:40:34
 * @author Randy Crihfield
 *
 * Copyright 2017 Hozer Video Games.  All rights reserved.
 * Use is subject to license terms.
 */

import java.io.*;
import java.util.*;
 
public class FileIO {

   ///////////////////////////////////////////////////
   // global data value settings

   private boolean bDEBUG     = false;

   private String  sQst[]     = null;
   private int     iCorrect[] = null;
   private String  sAns[][]   = null;
   private String  sRes[]     = null;
   private String  sIntro     = null;
   private Vector  vRight     = null;
   private Vector  vRightWrk  = null;
   private Vector  vWrong     = null;
   private Vector  vWrongWrk  = null;

   ///////////////////////////////////////////////////
   // constructor

   public FileIO(boolean bBug) {
      if (bBug)
         System.out.println("In FileIO method");

      bDEBUG = bBug;

      readIntro("intro.dat");
      readData("game.dat");
      readInterjections("interject.dat");
   }


   //////////////////////////////////////////////////
   // Reads the intro file if it exists
   private void readIntro(String sDataFile) {
      if (bDEBUG)
         System.out.println("In readIntro method");

      try {
         File fData = new File(sDataFile);

         // no data file, that's ok, just get outta here
         if (! fData.exists()) {
            System.out.println("Data file " + sDataFile + " not "
               + "found!");
            
            return;
         }

         // open the stream to read in
         InputStream iStream;
         iStream = (InputStream)new FileInputStream(fData);
         BufferedReader bRead;
         bRead = new BufferedReader(new InputStreamReader(iStream));

         // this is the string read
         String readIn;

         // let's read the whole file
         while ((readIn = bRead.readLine()) != null) {
            if (sIntro == null)
               sIntro = readIn;
            else
               sIntro = sIntro + " " + readIn;
         }

         iStream.close();
      } catch(Exception eExcept) {
         System.out.println("Unexpected problem reading inter. data!");
         eExcept.printStackTrace();
      }
   }


   //////////////////////////////////////////////////
   // Reads the question/answer/response file
   private void readData(String sDataFile) {
      if (bDEBUG)
         System.out.println("In readData method");

      Vector vQst = new Vector();
      Vector vCor = new Vector();
      Vector vAns = new Vector();
      Vector vRes = new Vector();

      int iCounter = 0;

      try {
         File fData = new File(sDataFile);

         // no data file, we must die
         if (! fData.exists()) {
            System.out.println("Data file " + sDataFile + " not "
               + "found!");
            System.exit(1);
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

            // read in questions
            if (readIn.startsWith("Q")) {
               vQst.addElement(readIn.substring(4));
               // watch out for short datafile values!
               if (iCounter > 0 && iCounter < 6) {
                  for (int iNum = 0; iNum < (6-iCounter); iNum++) {
                     vAns.addElement("     ");
                  }
                  vRes.addElement("     ");
               }
               iCounter = 0;
            }

            // read in correct setting
            if (readIn.startsWith("C")) {
               vCor.addElement(readIn.substring(4));
            }

            // read in answers
            if (readIn.startsWith("A")) {
               // watch out for more than 6!
               if (iCounter != 6) {
                  vAns.addElement(readIn.substring(4));
                  iCounter++;
               }
            }

            // read in responses
            if (readIn.startsWith("R")) {
               vRes.addElement(readIn.substring(4));
            }
         }

         iStream.close();
      } catch(Exception eExcept) {
         System.out.println("Unexpected problem reading data!");
         eExcept.printStackTrace();
      }

      // process the data here...
      processQst(vQst);
      processCor(vCor);
      processAns(vAns);
      processRes(vRes);
   }


   //////////////////////////////////////////////////
   // Reads the right/wrong file
   private void readInterjections(String sDataFile) {
      if (bDEBUG)
         System.out.println("In readInterjection method");

      vRight = new Vector();
      vWrong = new Vector();

      int iCounter = 0;

      try {
         File fData = new File(sDataFile);

         // no data file, that's ok, just get outta here
         if (! fData.exists()) {
            System.out.println("Data file " + sDataFile + " not "
               + "found!");
            
            vRight.addElement("Correct!");
            vWrong.addElement("Sorry!");
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

            // read in Right interjections
            if (readIn.startsWith("R")) 
               vRight.addElement(readIn.substring(6));

            // read in Wrong interjections
            if (readIn.startsWith("W")) 
               vWrong.addElement(readIn.substring(6));
         }

         iStream.close();
      } catch(Exception eExcept) {
         System.out.println("Unexpected problem reading inter. data!");
         eExcept.printStackTrace();
      }
   }


   //////////////////////////////////////////////////
   // Sets the global Question values
   private void processQst(Vector vQst) {
      if (bDEBUG)
         System.out.println("In processQst method");

      int iLen = vQst.size();

      sQst = new String[iLen];

      for (int count = 0; count < iLen; count++)
         sQst[count] = (String)vQst.elementAt(count);
   }


   //////////////////////////////////////////////////
   // Sets the global "correct" values
   private void processCor(Vector vCor) {
      if (bDEBUG)
         System.out.println("In processCor method");

      int iLen = vCor.size();

      iCorrect = new int[iLen];

      try {
         for (int count = 0; count < iLen; count++)
            iCorrect[count] = 
               Integer.parseInt((String)vCor.elementAt(count)) - 1;
      }
      catch (Exception eExcept) {
         System.out.println("ERROR: Troubles converting to ints!");
         System.out.println("  Check COR: values in data file.");
      }
   }


   //////////////////////////////////////////////////
   // Sets the global Answer values
   private void processAns(Vector vAns) {
      if (bDEBUG)
         System.out.println("In processAns method");

      int iLen = vAns.size();

      sAns = new String[(iLen / 6) + 1][6];

      for (int count = 0; count < iLen; count++)
         sAns[count / 6][count % 6] = (String)vAns.elementAt(count);
   }


   //////////////////////////////////////////////////
   // Sets the global Result values
   private void processRes(Vector vRes) {
      if (bDEBUG)
         System.out.println("In processRes method");

      int iLen = vRes.size();

      sRes = new String[iLen];

      for (int count = 0; count < iLen; count++)
         sRes[count] = (String)vRes.elementAt(count);
   }


   //////////////////////////////////////////////////
   // Returns the intro text, if any
   public String getIntroText() {
      if (bDEBUG)
         System.out.println("In getIntroText method");

      return(sIntro);
   }


   //////////////////////////////////////////////////
   // Returns the number of questions
   public int getNumQuestions() {
      if (bDEBUG)
         System.out.println("In getNumQuestions method");

      return(sQst.length);
   }


   //////////////////////////////////////////////////
   // Returns the question
   public String getQuestion(int iNum) {
      if (bDEBUG)
         System.out.println("In getQuestion method");

      return(sQst[iNum]);
   }


   //////////////////////////////////////////////////
   // Returns the "correct" selection index
   public int getCorrect(int iNum) {
      if (bDEBUG)
         System.out.println("In getCorrect method");

      return(iCorrect[iNum]);
   }


   //////////////////////////////////////////////////
   // Returns the answer
   public String getAnswer(int iNum, int iRsp) {
      if (bDEBUG)
         System.out.println("In getAnswer method");

      return(sAns[iNum][iRsp]);
   }


   //////////////////////////////////////////////////
   // Returns the response
   public String getResponse(int iNum) {
      if (bDEBUG)
         System.out.println("In getResponse method");

      return(sRes[iNum]);
   }


   //////////////////////////////////////////////////
   // Returns a random RIGHT interjection
   public String getRight() {
      if (bDEBUG)
         System.out.println("In getAnswer method");

      // create/refill if need be from master list
      if (vRightWrk == null || vRightWrk.size() == 0) {
         vRightWrk = new Vector(vRight.size());
         vRightWrk = (Vector)vRight.clone();
      }

      // has to be at least ONE in there!  pick one by random
      // chance and then remove it from the list...
      int iRand = (int)Math.round(Math.random() * 
         (vRightWrk.size() - 1));
      String sReturn = (String)vRightWrk.elementAt(iRand);
      vRightWrk.removeElementAt(iRand);

      return(sReturn);
   }


   //////////////////////////////////////////////////
   // Returns a random WRONG interjection
   public String getWrong() {
      if (bDEBUG)
         System.out.println("In getAnswer method");

      // create/refill if need be from master list
      if (vWrongWrk == null || vWrongWrk.size() == 0) {
         vWrongWrk = new Vector(vWrong.size());
         vWrongWrk = (Vector)vWrong.clone();
      }

      // has to be at least ONE in there!  pick one by random
      // chance and then remove it from the list...
      int iRand = (int)Math.round(Math.random() * 
         (vWrongWrk.size() - 1));
      String sReturn = (String)vWrongWrk.elementAt(iRand);
      vWrongWrk.removeElementAt(iRand);

      return(sReturn);
   }
}
