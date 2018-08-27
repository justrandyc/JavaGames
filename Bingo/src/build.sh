#!/bin/sh

echo ""
echo "compiling..."
CLASSPATH="."
javac *.java

echo ""
echo "creating Bingo.jar file..."
jar -cvf ../Bingo.jar *.class

echo ""
echo "flushing redundant class files..."
rm *.class

echo ""
echo "Done!"
echo ""


