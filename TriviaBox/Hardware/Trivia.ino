#include <Keyboard.h>

// This uses the Teeny 2.0 or LC
// There are 26 digital i/o pins on the LC
// There are 24 digital i/o pins on the 2.0
// we only use 21 + ground, for our 3 controllers

void setup() {
    // Controller 1
    pinMode(0, INPUT_PULLUP);
    pinMode(1, INPUT_PULLUP);
    pinMode(2, INPUT_PULLUP);
    pinMode(3, INPUT_PULLUP);
    pinMode(4, INPUT_PULLUP);
    pinMode(5, INPUT_PULLUP);
    pinMode(6, INPUT_PULLUP);
    // Controller 2
    pinMode(7, INPUT_PULLUP);
    pinMode(8, INPUT_PULLUP);
    pinMode(9, INPUT_PULLUP);
    pinMode(10, INPUT_PULLUP);
    pinMode(11, INPUT_PULLUP);
    pinMode(12, INPUT_PULLUP);
    pinMode(13, INPUT_PULLUP);
    // Controller 3
    pinMode(14, INPUT_PULLUP);
    pinMode(15, INPUT_PULLUP);
    pinMode(16, INPUT_PULLUP);
    pinMode(17, INPUT_PULLUP);
    pinMode(18, INPUT_PULLUP);
    pinMode(19, INPUT_PULLUP);
    pinMode(20, INPUT_PULLUP);
    delay(1000);
    Keyboard.begin();
}

void loop() {
    // Did someone press the button?
    
    // Controller 1
    if (digitalRead(0) == LOW) {
        Keyboard.write('R');
        // wait half a second
        delay(100);
    }
    if (digitalRead(1) == LOW) {
        Keyboard.write('O');
        // wait half a second
        delay(100);
    }
    if (digitalRead(2) == LOW) {
        Keyboard.write('Y');
        // wait half a second
        delay(100);
    }
    if (digitalRead(3) == LOW) {
        Keyboard.write('G');
        // wait half a second
        delay(100);
    }
    if (digitalRead(4) == LOW) {
        Keyboard.write('B');
        // wait half a second
        delay(100);
    }
    if (digitalRead(5) == LOW) {
        Keyboard.write('P');
        // wait half a second
        delay(100);
    }
    if (digitalRead(6) == LOW) {
        Keyboard.write('N');
        // wait half a second
        delay(100);
    }

    // Controller 2
    if (digitalRead(7) == LOW) {
        Keyboard.write('r');
        // wait half a second
        delay(100);
    }
    if (digitalRead(8) == LOW) {
        Keyboard.write('o');
        // wait half a second
        delay(100);
    }
    if (digitalRead(9) == LOW) {
        Keyboard.write('y');
        // wait half a second
        delay(100);
    }
    if (digitalRead(10) == LOW) {
        Keyboard.write('g');
        // wait half a second
        delay(100);
    }
    if (digitalRead(11) == LOW) {
        Keyboard.write('b');
        // wait half a second
        delay(100);
    }
    if (digitalRead(12) == LOW) {
        Keyboard.write('p');
        // wait half a second
        delay(100);
    }
    if (digitalRead(13) == LOW) {
        Keyboard.write('n');
        // wait half a second
        delay(100);
    }

    
    // Controller 3
    if (digitalRead(14) == LOW) {
        Keyboard.write('A');
        // wait half a second
        delay(100);
    }
    if (digitalRead(15) == LOW) {
        Keyboard.write('C');
        // wait half a second
        delay(100);
    }
    if (digitalRead(16) == LOW) {
        Keyboard.write('D');
        // wait half a second
        delay(100);
    }
    if (digitalRead(17) == LOW) {
        Keyboard.write('E');
        // wait half a second
        delay(100);
    }
    if (digitalRead(18) == LOW) {
        Keyboard.write('F');
        // wait half a second
        delay(100);
    }
    if (digitalRead(19) == LOW) {
        Keyboard.write('H');
        // wait half a second
        delay(100);
    }
    if (digitalRead(20) == LOW) {
        Keyboard.write('I');
        // wait half a second
        delay(100);
    }
}
