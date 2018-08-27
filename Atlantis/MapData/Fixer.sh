#!/bin/sh

if [ $# -lt 1 ] ;then
    echo ""
    echo "Data file to map is a required parameter!"
    echo ""
    echo "Use GetPoints.sh and make it happen!"
    echo ""
    exit 1
fi

sData=$1

# Upper Left
sXMin=`cat $sData | awk '{print $1}' | cut -d: -f2 | sort -n | head -1`
sXMax=`cat $sData | awk '{print $1}' | cut -d: -f2 | sort -n | tail -1`
sXMax=`expr $sXMax - $sXMin`

sYMin=`cat $sData | awk '{print $2}' | cut -d: -f2 | sort -n | head -1`
sYMax=`cat $sData | awk '{print $2}' | cut -d: -f2 | sort -n | tail -1`
sYMax=`expr $sYMax - $sYMin`

echo "bx100" > foo
echo "by100" >> foo
echo "x$sXMax" >> foo
echo "y$sYMax" >> foo

# get the real points
sXPoints=`cat $sData | awk '{print $1}' | cut -d: -f2`
sYPoints=`cat $sData | awk '{print $2}' | cut -d: -f2`

sXOut="AllX "
for point in $sXPoints ; do
    sXOut="${sXOut} `expr $point - $sXMin`"
done

sYOut="AllY "
for point in $sYPoints ; do
    sYOut="${sYOut} `expr $point - $sYMin`"
done

echo $sXOut >> foo
echo $sYOut >> foo

echo Done!
