#include <stdio.h>
#include <unistd.h>
#include <math.h>

// generate new long number ( his size based on the current machine impl )
// with only the odd bits turned on, e.g
// 1010101010101010101010
unsigned long generate_compare_number() {
  int i = 0;
  unsigned long num = 0;                // placeholder to the odd bits number
  int size = (int)sizeof(unsigned int); // the current size in bytes

  // we are pushing two bits ( 0 and 1 ) to the tail of the number
  // (size of the long number / 2) times...
  while(i < size * 4) {
   num = num << 2; 
   num += 1;
   i++;
  }
  // addeding 0 to the tail
  num = num << 1;
  return num;
}

// this function generate base number to compare to it ( via generate_compresent_number)
// then use bitwise opp AND to generate new number.
// then we convert the first two bits to zero and we check if the number if
// changed, if it changed we know that one bit was turned on else we do the
// same just with 4 bits... ( then with 6,8,10...)
// it returns the number of odd bits that was tured on in the input
// long
int count_odd_bits(unsigned long test) {
  unsigned long and = generate_compare_number() & test; // bitwise AND between the two
  unsigned long before; // we'll use it to check if the number changed or not 
  int count = 0;
  int bits_to_zero = 2; // how many bits we need to zero

  // see description above
  while(and != 0) {
    before = and;
    and = and >> bits_to_zero;
    and = and << bits_to_zero;
    if (and != before) {
      count++;
    }
    bits_to_zero += 2;
  }
  return count;
}

// this function ask the user for a number to calculate the number of odd bits
int main() {
  unsigned long test;
  printf("Please enter number to check: ");
  scanf("%lu",&test);
  printf("\nnumber of odd bits (in %lu) are: %d\n",test,count_odd_bits(test));
  return(0);
}
