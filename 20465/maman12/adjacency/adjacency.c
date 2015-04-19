#include <stdio.h>

#define N 11
#define TRUE 1
#define FALSE 0

typedef int adj_mat[N][N];

// takes NxN matrix that represent a tree and two indexes
// and should return true if there is a path between both
// indexes.
int path(adj_mat A,int u, int v){
  int i;

  // out of borders
  if (u >= N || v >= N)
    return FALSE;

  // if its a child
  if (A[u-1][v-1])
    return TRUE;

  // recursivly searching if its one of our parents
  for (i = 0; i < N; i++)
    if (A[i][v-1])
      if (path(A,u,i+1))
        return TRUE;
  
  return FALSE;
}

int main() {
  adj_mat A;
  int i,j,u,v;

  printf("Please supply values for %d X %d matrix:\n",N,N);

  for (i = 0; i < N; i++)
    for (j = 0;  j < N; j++)
      scanf("%d",&A[i][j]);

  printf("Please supply two values (ints) to check if the second is offspring of the first:\n");
  scanf("%d %d",&u,&v);

  if (path(A,u,v)) {
    printf("\nFound a path from %d to %d :)\n",u,v);
    return(0);
  } else {
    printf("\nPath between %d and %d was not found :(\n",u,v);
    return(1);
  }
}
