#include "utils.h"
#include "data_structure.h"

// add new elemnts to the list
void add(List * list, char * data, int line, int tml) {
  Node *tmp, *node = ( List ) malloc( sizeof( Node ) );
  
  if ( node == NULL ) {
    puts("panic: cannot allocate memory...");
    exit(1);
  }
  
  // some initialization
  strcpy(node->data, data);
  node->line = line;  
  node->tml = tml;

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

// go over the list and update TML
int set_tml(Node *  node, char *data, int tml) {
  while (node->data != NULL) {
    if (!(strcmp(data, node->data))) node->tml = tml;
    node = node->next;
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

// free entire list, will set the current list to nil
void clean(List * list) {
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