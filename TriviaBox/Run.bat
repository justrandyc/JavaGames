
rem For Run options, view this file.
echo off

rem
rem To run the software in DEBUG mode, which tells you all sorts
rem of wild and wacky information as it runs, use the debug
rem option of -DDEBUG=true
rem
rem java -cp Trivia.jar -DDEBUG=true Trivia 3
rem
rem by default, DEBUG=false, you have to override it this way
rem to turn it on.  See src/Trivia.java for code snippet.
rem

rem
rem Blocking is the ability to force players to take turns. It
rem punishes those that just "button mash" until they hit the
rem right response and rewards those who are more cautious and
rem try to read/respond intelligently.  The first person who
rem replies with a wrong answer is "blocked out" until all other
rem players have had one turn, then if everyone has still not
rem answered correctly they are unlocked in a loop, oldest 
rem answering person is enabled and last person to miss is now
rem disabled.  It's again punishment for those who answer too
rem quickly.
rem
rem java -cp Trivia.jar -DBLOCKING=false Trivia 3
rem
rem Blocking also allows for the next question to not be asked
rem until each person has hit "next" which indicates they are
rem ready to proceed.  This keeps people from bringing up the
rem questions before the others are ready.
rem
rem Note that if only one person is playing (one controller in
rem use) that blocking means nothing either way.  This would be
rem ideal if you wanted to set up a kosik with only one box
rem laid out for use.
rem
rem When blocking is OFF then anyone can answer at any time,
rem in theory just mashing down all the buttons.  Also flipping
rem to the next question without warning others.  Maybe you want
rem this, I don't know. By default, blocking is ON, you have to
rem disable it to allow this antisocial behavior!
rem

rem
rem This is the NORMAL way you might run the program
rem
rem java -cp Trivia.jar Trivia 3
rem
rem Finally, there is an integer value allowed (values 1-5) which
rem is meant to tell the software how many controllers you are
rem going to use.  Controllers must be plugged into consecutive
rem slots, ie 1, 2, 5 is not allowed but 1, 2, 3 is.  There is no
rem detection of how many controllers you have plugged in, so this
rem is the interface I decided on.  The default if unset is 1.

java -cp .\Trivia.jar Trivia 1
