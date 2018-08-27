#!/bin/sh

PATH="/usr/jdk/latest/bin:${path}"
export PATH

javac *.java

java Atlantis
