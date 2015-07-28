#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <stdio.h>
#include <stdarg.h>

#define MAX_PAR_LEN 3

enum {FALSE, TRUE};

void read_set(void);
void print_set(void);
void union_set(void);
void intersect_set(void);
void sub_set(void);
void halt(void);

typedef struct st
{
  char *A;
  int n;
} set;

typedef struct tnode{
  set *first;
  set *second;
  set *result;
} params;

params parameters;

char command[100];
char ret[MAX_PAR_LEN]; 
int *ints;
int notMinus1; 
int setOK;
