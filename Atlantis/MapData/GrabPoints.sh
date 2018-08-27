#!/bin/sh

while [ 1 ] ;do
   read -n 1 -s sKey > /dev/null
   if [ "$sKey" = "x" ] ;then
      exit 0
   fi
   xdotool getmouselocation
done
