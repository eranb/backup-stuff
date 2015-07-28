#include "utils.h"
#include "data_structure.h"

void add(List * list, char *s, int line, int is_inst) {
  Node *node, *tmp;
  
  node=(List) malloc(sizeof(Node));
  
  if (node == NULL) {
    printf("panic: cannot allocate memory...");
    exit(1);
  }
  
  strcpy(node->data, s);
  node->line = line;
  
  if (is_inst)
    node->instruction = 1;
  else
    node->instruction = 0;

  if (*list == NULL) {
    *list=node;
    node->next = NULL;
    node->prev = NULL;
  } else {
    tmp = *list;	
    while (tmp->next != NULL) {
      if (!(strcmp(s, tmp->data)))
        return;
      tmp = tmp->next;
    }

    tmp->next = node;
    node->next = NULL;
    node->prev = tmp;
    
  }

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
  Node * node;

  if(!*list) return;

  node = *list;
  
  if(node->next == NULL) {
    free(node);
  } else {
    do {	
      node = *list;
      *list = node->next;
      free(node);
    } while (!*list);
    
  }
}