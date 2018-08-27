/* *****************************************************
 * @(#)FunkyLabel.java   2.0  08/16/17 17:40:34
 * @author Randy Crihfield
 *
 * Copyright 2017 Hozer Video Games.  All rights reserved.
 * Use is subject to license terms.
 */

import java.awt.*;
import java.util.*;


public class FunkyLabel extends Canvas {

   private boolean bDEBUG = false;

   protected String text;
   protected float m_nHAlign;
   protected float m_nVAlign;
   protected int baseline;
   protected FontMetrics fm;


   public FunkyLabel(boolean bBug) {
      this(bBug, "");

      if (bBug)
         System.out.println("In FunkyLabel (1) method");

      bDEBUG = bBug;
   }

   public FunkyLabel(boolean bBug, String sContents) {
      this(bBug, sContents, Canvas.CENTER_ALIGNMENT,
         Canvas.CENTER_ALIGNMENT);

      if (bBug)
         System.out.println("In FunkyLabel (2) method");

      bDEBUG = bBug;
   }

   public FunkyLabel(boolean bBug, String sContents,
      float nHoriz, float nVert) {
      if (bBug)
         System.out.println("In FunkyLabel (3) method");

      bDEBUG = bBug;

      setText(sContents);
      setHAlignStyle(nHoriz);
      setVAlignStyle(nVert);
   }


   public float getHAlignStyle() { 
      if (bDEBUG)
         System.out.println("In getHAlignStyle method");

      return m_nHAlign; 
   }

   public float getVAlignStyle() {
      if (bDEBUG)
         System.out.println("In getVAlignStyle method");

      return m_nVAlign; 
   }

   public String getText() {
      if (bDEBUG)
         System.out.println("In getText method");

      return text;
   }

   public void setHAlignStyle(float a) {
      if (bDEBUG)
         System.out.println("In setHAlignStyle method");

      m_nHAlign = a;
      invalidate();
   }

   public void setVAlignStyle(float a) {
      if (bDEBUG)
         System.out.println("In setVAlignStyle method");

      m_nVAlign = a;
      invalidate();
   }

   public void setText(String s) {
      if (bDEBUG)
         System.out.println("In setText method");

      text = s;
      repaint();
   }


   public String paramString() {
      if (bDEBUG)
         System.out.println("In paramString method");

      return "";
   }

   public void paint(Graphics g) {
      if (bDEBUG)
         System.out.println("In paint method");

      if (text != null) {
         Dimension d;
         int currentY = 0;
         Vector lines;

         fm = getFontMetrics(getFont());
         baseline = fm.getMaxAscent();

         d = getSize();

         lines = breakIntoLines (text, d.width);

         if (m_nVAlign == Canvas.CENTER_ALIGNMENT) {
            int center = (d.height / 2) - 1;
            currentY = center - ( (lines.size() / 2) * fm.getHeight() );
         } else 
            if (m_nVAlign == Canvas.BOTTOM_ALIGNMENT) {
               currentY = d.height - ( lines.size() * fm.getHeight() );
            }

         Enumeration elements = lines.elements();
         while (elements.hasMoreElements()) {
            drawAlignedString(g, 
              (String)(elements.nextElement()), 
              0, currentY, d.width);
            currentY += fm.getHeight();
         }

         fm = null;
      }
   }


   protected Vector breakIntoLines (String s, int width) {
      if (bDEBUG)
         System.out.println("In breakIntoLines method");

      int fromIndex = 0;
      int pos = 0;
      int bestpos;
      String largestString;
      Vector lines = new Vector();

      while (fromIndex != -1) {
         while (fromIndex < text.length() 
            && text.charAt(fromIndex) == ' ') {
            ++fromIndex;

            if (fromIndex >= text.length())
               break; 
         }

         pos = fromIndex;
         bestpos = -1;
         largestString = null;

         while (pos >= fromIndex) {
            boolean bHardNewline = false;
            int newlinePos = text.indexOf('\n', pos);
            int spacePos   = text.indexOf(' ', pos);

            if (newlinePos != -1 && ((spacePos == -1) || 
               (spacePos != -1 && newlinePos < spacePos))) {
               pos = newlinePos;
               bHardNewline = true;
            } else {
               pos = spacePos;
               bHardNewline = false;
            }

            if (pos == -1) {
               s = text.substring(fromIndex);
            } else {
               s = text.substring(fromIndex, pos);
            }

            if (fm.stringWidth(s) < width) {
               largestString = s;
               bestpos = pos;

               if (bHardNewline)
                  bestpos++;
               if (pos == -1 || bHardNewline)
                  break;
            } else {
               break;
            }

            ++pos;
         }

         if (largestString == null) {
            int totalWidth = 0;
            int oneCharWidth = 0;

            pos = fromIndex;

            while (pos < text.length()) {
               oneCharWidth = fm.charWidth(text.charAt(pos));
               if ((totalWidth + oneCharWidth) >= width)
                  break;
               totalWidth += oneCharWidth;
               ++pos;
            }

            lines.addElement (text.substring(fromIndex, pos));
            fromIndex = pos;
         } else {
            lines.addElement (largestString);
            fromIndex = bestpos;
         }
      }

      return lines;
   }


   protected void drawAlignedString(Graphics g, 
      String s, int x, int y, int width) {
      if (bDEBUG)
         System.out.println("In drawAlignedString method");

      int drawx;
      int drawy;

      drawx = x;
      drawy = y + baseline;

      if (m_nHAlign != Canvas.LEFT_ALIGNMENT) {
         int sw;

         sw = fm.stringWidth(s);

         if (m_nHAlign == Canvas.CENTER_ALIGNMENT) {
            drawx += (width - sw) / 2;
         }
         else
            if (m_nHAlign == Canvas.RIGHT_ALIGNMENT) {
               drawx = drawx + width - sw;
            }
         }

      g.drawString(s, drawx, drawy);
   }
}
