#include "utils.h"
#include "data_structure.h"

void add(List * list, char *s, int line, int instruction) {
  List node;
  
  // tyring to find identical value
  if (*list != NULL) {
    node = *list;
    while (node != NULL) {
      if (!strcmp(s,node->data)) return;
      node = node->next;
    }
  }
  
  node = (Node *) malloc(sizeof(Node));
  
  if (node == NULL) {
    printf("panic: cannot allocate memory...");
    exit(1);
  }
  
  node->instruction = instruction;
  node->line = line;
  strcpy(node->data, s);

  node->next = *list;
  if (*list != NULL) (*list)->prev = node;
  *list = node;
  node->prev = NULL;
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