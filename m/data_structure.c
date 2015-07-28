#include "utils.h"
#include "data_structure.h"

void add(List * list, char * data, int line, int itype) {
  Node *tmp, *node = ( List ) malloc( sizeof( Node ) );
  
  if ( node == NULL ) {
    printf("panic: cannot allocate memory...");
    exit(1);
  }
  
  // some initialization
  strcpy(node->data, data);
  node->line = line;  
  node->instruction = itype;

  // if we are on an empty list
  if (*list == NULL) {
    *list=node;
    node->next = NULL;
    node->prev = NULL;
  } else {
    // finding where to put it...
    tmp = *list;	
    while (tmp->next != NULL) {
      if (!(strcmp(data, tmp->data))) return;
      tmp = tmp->next;
    }

    // setup next and prev
    tmp->next = node;
    node->next = NULL;
    node->prev = tmp;
  }
}	

int verify(Node *  node, char *s) {
  while (p->data != NULL) {
    if (!(strcmp(s, p->data))) p->instruction = 2;
    p=p->next;
  }
  return 0;
}



// find shit in list, if it can't find it, it returns -1
// otherwise retruns its postion 
int find(Node * node, char * data) {  
  while (node != NULL && node->data != NULL) {
    if (!(strcmp(data, node->data)))return node->line;
    node=node->next;
  }
  
  return -1;
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