#include "utils.h"
#include "data_structure.h"

void add(List * H, char *s, int line, int is_inst) {
  List T, p1;
  
  T=(List) malloc(sizeof(Node));
  
  if (T == NULL) {
    printf("panic: cannot allocate memory...");
    exit(1);
  }
  if (is_inst)
    T->instruction = 1;
  else
    T->instruction = 0;
  strcpy(T->data, s);
  T->line = line;

  if ((*H) == NULL) {
    *H=T;
    T->next = NULL;
    T->prev = NULL;
    return;
  }

  p1 = *H;	
  while (p1->next != NULL) {
    if (!(strcmp(s, p1->data)))
      return;
    p1 = p1->next;
  }

  p1->next = T;
  T->prev = p1;
  T->next = NULL;
}	

int search_list(List H, char *s) {
  List p;
  if (!H)
    return -1;
  p=H;
  while (p->data != NULL)
  {
    if (!(strcmp(s, p->data)))
      return p->line;
    p=p->next;
  }
  return -1;
}

int verify(List H, char *s) {
  List p;
  if (!H) return 0;
  p=H;

  while (p->data != NULL) {
    if (!(strcmp(s, p->data))) p->instruction = 2;
    p=p->next;
  }
  return 0;
}

void clean_list(List * list) {
  Node * node = *list;
  
  if(*list == NULL)
    return;
  
  while (node != NULL){
    node = (*list)->next;
    free(*list);
    *list = node;    
  }
}