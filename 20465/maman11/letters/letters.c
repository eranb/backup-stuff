#include <stdio.h>

// returns 1 if a given char is a upper case letter else 0
int is_upper_case(int *c) {
  return (*c < 91 && *c > 64);
}

// returns 1 if a given char is a lower case letter else 0
int is_lower_case(int *c) {
  return (*c < 123 && *c > 96);
}

// returns 1 if a given char is a letter (a-z or A-Z) else 0
int is_letter(int *c) {
  return is_lower_case(c) || is_upper_case(c);
}

// coverts a given char to its lower case form
void to_upper(int *c) {
  if (is_lower_case(c)) *c -= 32;
}

// coverts a given char to its lower case form
void to_lower(int *c) {
  if (is_upper_case(c)) *c += 32;
}

// this is the main program that takes the user input and converts it.
// based on the rules described in question 2 in Maman11.
int main() {
  int c;                     // will hold the last char that we read from the user.
  int newSentenceMode = 1;   // indicates if we are in a new sentance.
  int doubleQuotesMode = 0;  // indicates if we are inside double quotes.

  // read characters until we reach end of file...
  while ((c = getchar()) != EOF)
  {
    // if the char is \n or . the next char is a new sentance
    if (c == 10 || c == 46) {
      newSentenceMode = 1;
    // if its a digit we move to the next char
    } else if (c > 47 && c < 58) {
      continue;
    // if its " we want to mark it
    } else if ( c ==  34 ) { // double quotes
      doubleQuotesMode = !doubleQuotesMode;
    // if we are inside ".." we want to print upper case letters
    } else if (doubleQuotesMode) {
      if (newSentenceMode && is_letter(&c)) newSentenceMode = 0;
      to_upper(&c);
    // all the rest, we'll print lower case letters unless it a new sentance
    } else {
      if (newSentenceMode && is_letter(&c)) {
        newSentenceMode = 0;
        to_upper(&c);
      } else {
        to_lower(&c);
      }
    }

    // print to stdout
    printf("%c",c);

    // if its \n we print it one more
    if (c == 10) printf("%c",c);
  }

  return 0;
}
