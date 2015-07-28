#include "utils.h"
#include "data_structure.h"

void add(List * list, char *s, int line, int instruction) {
  List node, p1;
  
  node = (Node *) malloc(sizeof(Node));
  
  if (node == NULL) {
    printf("panic: cannot allocate memory...");
    exit(1);
  }
  
  node->instruction = instruction

  strcpy(node->data, s);
  node->line = line;

  if (*list == NULL) {
    *list = node;
    node->next = NULL;
    node->prev = NULL;
    return;
  }

  p1 = *list;	
  while (p1->next != NULL) {
    if (!(strcmp(s, p1->data)))
      return;
    p1 = p1->next;
  }

  p1->next = node;
  node->prev = p1;
  node->next = NULL;
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