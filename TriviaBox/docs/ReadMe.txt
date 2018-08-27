
To run, its as easy as 

   java -cp Trivia.jar Trivia 1

Or with some options

   java -cp Trivia.jar -DDEBUG=true Trivia 1

DEBUG is false by default, if you set it to true you get all kinds
of debugging messages so you can see where in the code things are.

   java -cp Trivia.jar -DBLOCKING=false Trivia

BLOCKING is true by default; blocking is when a player answers wrong
and then must wait for the other players before they can guess again.
This is for the name of "fairness" so those who just button mash
can't guess away until they get it right.  No fun in that!

   java -cp Trivia.jar Trivia 3

Would allow for 3 controllers.  The computer is player "zero" and
is always there.  The number of controllers can be 0 to 6. Keyboard
is always the "last" player and I'll allow for 4 wired controllers.



