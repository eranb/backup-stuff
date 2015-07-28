#include "set.h"
#include <unistd.h>

// command struct
struct {
  char *name;
  int nameLen;
  void (*func)(void);
} cmd[] = {
  {"read_set\0", 8, read_set},
  {"print_set\0", 9, print_set},
  {"union_set\0", 9, union_set},
  {"intersect_set\0", 13, intersect_set},
  {"sub_set\0", 7, sub_set},
  {"halt\0", 4, halt},
  {"not_valid\0", 9, NULL},
};

set A, B, C, D, E, F;

// Sets definition 
struct set_st{
  char name;
  set *s;
}sets[] = { 
  {'A', &A}, 
  {'B', &B}, 
  {'C', &C},
  {'D', &D}, 
  {'E', &E},
  {'F', &F},
  {'#', NULL}
};


// getting the parameters that the user entered
void get_parameters(void);

// pulls the parameter from the command line
char* psik(void);

char c;
int cont;

//gets the parameters that the user entered
void get_parameters(void) {
  int i, index = 0, isSet = FALSE, parInt, *tempInts;
  char *par;
  c = '\0';
  parameters.first = NULL;
  parameters.second = NULL;
  parameters.result = NULL;
  notMinus1 = TRUE;
  setOK = TRUE;
  sleep(10);
  while (c != '\n' && c != EOF) {
    par = psik();
    //pulls the parameters that are sets
    for (i = 0; i < 6; i++) {
      if (*par == sets[i].name)
      {
        if (parameters.result != NULL)
        {
          fprintf(stderr, "Too many Sets\n");
          setOK = FALSE;
          return;
        }
        if (*(par + 1) != '\0')
        {
          fprintf(stderr, "Wrong set name!\n");
          setOK = FALSE;
          return;
        }

        if (parameters.first == NULL)
          parameters.first = (set *)(sets[i].s);
        else if (parameters.second == NULL)
          parameters.second = (set *)(sets[i].s);
        else if (parameters.result == NULL)
          parameters.result = (set *)(sets[i].s);

        isSet = TRUE;
      }
    }
    if ((i == 12) && (isSet != TRUE) && (isalpha(*par)))
    {
      fprintf(stderr, "Wrong set name!\n");
      setOK = FALSE;
      while (c != '\n')
        c= getchar();
      return;
    }
    //pulls the parameters that are numbers
    if(!isSet)
    {
      tempInts = realloc(ints, (index + 1) * sizeof(int));
      if (tempInts != NULL)
        ints = tempInts;
      parInt = atof(par);
      *(ints + index) = parInt;
      index++;
      if (parInt == -1)
        notMinus1 = FALSE;
    }
    isSet = FALSE;
  }
}

// pulls the parameter from the command line
char* psik(void) {
  int i, j = 0;
  c = getchar();
  //Skipping \s
  for (i = 0; isspace(c) && c != '\n'; i++)
    c = getchar();
  //checks if it's a digit
  while (c != ',' && c != '\n' && c != ' ')
  {
    ret[j++] = c;
    c = getchar();
  }
  for (i = 0; isspace(c) && c != '\n'; i++)
    c = getchar();
  if (c == ',' ||c == '\n')
  {
    ret[j] = '\0';
  }
  return ret;
}

void halt(void) {
  cont = FALSE;
}

int main(void) { 
  int i;
  cont = TRUE;
  ints = (int *)malloc(sizeof(int));
  while (cont)
    if (scanf("%s", command) == 1)
    {
      for (i = 0; cmd[i].func != NULL; i++)
        if (strcmp(command, cmd[i].name) == 0)
          break;

      get_parameters();
      if (setOK == TRUE)
      { 
        if (cmd[i].func == NULL)
          fprintf(stderr, "Command does not exist: %s\n", command);
        else
          (*(cmd[i].func))();
      }
    }
  return 1;
}

