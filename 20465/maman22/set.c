#include "set.h"

/*The musk that helps to work with the sets */
char mask[] = {1 << 7, 1 << 6, 1 << 5, 1 << 4, 1 << 3, 1 << 2, 1 << 1, 1}; 

/*This function adds new numbers into the set*/
void add(set *S, int x)
{
  /*checks if the number is out of range */
  if (x < -1 || x > 127)
  {
    printf("%d is out of range\n", x);
    return;
  }
  /*checks if the number is already exists in the set */
  if ((S->A)[x / 8] != ((S->A)[x / 8] | (1 << (7 - x % 8))))
  {
    (S->n)++;
    (S->A)[x / 8] |= (1 << (7 - x % 8));
  }
}

/*This function is the implementation  of the command read_set, that get the set and inserts into him the numbers that the user entered*/
void read_set(void)
{
  /*Checks if the user entered the right number of parameters*/
  if (parameters.first == NULL)
  {
    printf("Set's name is missing\n");
    return;
  }
  if (parameters.second != NULL || parameters.result != NULL)
  {
    printf("Too many sets' names given\n");
    return;
  }
  if (notMinus1)
  {
    printf("The last number among the parameters should be -1.\n");
    return;
  }
  /*the program uses only the first parametes according the command*/
  set *S = (parameters.first);
  int *i, k;
  /*creating the set*/
  (S->A) = (char *)malloc(16 * sizeof(char));
  char *j;
  j = (S->A);
  /*represent the number of numbers that the set have*/
  S->n = 0; 
  /*Initializes the set*/
  for (k = 0; k < 16; k++)
    *(j++) = '\0';
  /*adds the number into the set*/
  for(i = ints; *i > -1; i++)
    add(S, *i);
}

/*This function is the implementation  of the command print_set, that get the set and prints the numbers that he have*/
void print_set(void)
{
  /*Checks if the user entered the right number of parameters*/
  if (parameters.first == NULL)
  {
    printf("Set's name is missing\n");
    return;
  }
  set *S = (parameters.first);
  int i, j, printed = 0, p;
  char c;
  int empty = TRUE;
  /*checks if the set isn't empty*/
  for (i = 0; i < 16; i++)
    if ((S->A)[i] != '\0')
      empty = FALSE;
  if (empty)
    printf("This set is empty!");
  /*scans the set and prints the numbers - only 16 numbers in line*/
  else
  { 
    /*scans every bit in the array*/
    for (i = 0; i < 16; i++)
    {
      c = (S->A)[i];
      for (j = 0; j < 8; j++)
      {
        /*checks if the bit that represents the number is 0 or 1*/
        p = c & mask[j];
        if (p != 0)
        {
          /*checks if there less than 16 numbers in the line*/
          if (printed % 16 == 0)
            printf("\n");
          printf("%d, ", i * 8 + j);
          printed++;
        }
      }
    }
  }
  printf("\n");
}

/*This function is the implementation  of the command union_set, that gets 3 sets and puts the union of the two sets into ther third set*/
void union_set (void)
{
  /*Checks if the user entered the right number of parameters*/
  if (parameters.result == NULL)
  {
    printf("Wrong parameters! there should be given 3 names of sets.\n");
    return;
  }
  set *X = (parameters.first);
  set *Y = (parameters.second);
  set *Z = (parameters.result);
  (Z->A) = (char *)malloc(16 * sizeof(char));
  int i;
  /*saves the union in the thirs set*/
  for(i = 0; i < 16; i++)
  {
    (Z->A)[i] = (X->A)[i] | (Y->A)[i];
  }
}

/*This function is the implementation  of the command sub_set, that gets 3 sets and puts the subtraction of second set from the first set into the third set*/
void sub_set (void)
{
  /*Checks if the user entered the right number of parameters*/
  if (parameters.result == NULL)
  {
    printf("Wrong parameters! there should be given 3 names of sets.\n");
    return;
  }
  set *X = (parameters.first);
  set *Y = (parameters.second);
  set *Z = (parameters.result);
  (Z->A) = (char *)malloc(16 * sizeof(char));
  char a, b, nb, c;
  int i;
  /*saves the subtraction in the thirs set*/
  for(i = 0; i < 16; i++)
  {
    a = (X->A)[i];
    b = (Y->A)[i];
    nb = ~b;
    c = a & nb;
    (Z->A)[i] = c;
  }
}

/*gets 3 sets and puts the intersect of the two sets into ther third set*/
void intersect_set (void)
{
  /*Checks if the user entered the right number of parameters*/;
  if (parameters.result == NULL)
  {
    printf("Wrong parameters! there should be given 3 names of sets.\n");
    return;
  }
  set *X = (parameters.first);
  set *Y = (parameters.second);
  set *Z = (parameters.result);
  (Z->A) = (char *)malloc(16 * sizeof(char));
  char a, b, c;
  int i;
  /*saves the intersect in the thirs set*/
  for(i = 0; i < 16; i++)
  {
    a = (X->A)[i];
    b = (Y->A)[i];
    c = a & b;
    (Z->A)[i] = c;
  }
}

