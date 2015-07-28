#include <stdio.h>
#include <stdlib.h>

typedef struct node *ptr;
typedef struct node {
  int data;
  ptr next, prev;
} item ;

void ADD2LIST(ptr*,int);

int main() {
  ptr h;
  int num;

  while(scanf("%d",&num) != EOF) {
    ADD2LIST(&h,num);
  }
  return(0);
}


void ADD2LIST(ptr *Hptr, int n) {
  ptr T,p1,p2;
  T = (ptr)malloc(sizeof(item));

  if (!T) {
    printf("cannot allocate memory for id %d",n);
    exit(1);
  }

  T->data = n;

  if (!*Hptr) { 
    *Hptr = T;
    T->next=T->prev=T;
    return;
  }

  // we need to find where to put the new value
  if (!(n < (*Hptr)->data || (*Hptr)->prev->data)) {
    p1 = *Hptr;
    while(p1->data < n) {
      p2=p1;
      p1=p1->next;
    }
  } else {
    p1 = *Hptr;
    p2 = p1->prev;
  }
  p2->next=T;
  p1->prev=T;
  T->next=p1;
  T->prev=p2;
  if ((*Hptr)->data > n)
    *Hptr = T;
}
