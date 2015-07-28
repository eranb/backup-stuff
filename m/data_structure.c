#include "utils.h"
#include "data_structure.h"

extern FILE *error_file;
extern int line_num;

void printup(ptr H, FILE *file) {
  ptr p;
  if (!H)
    return;
  p=H;
  while (p != NULL)
  {
    fprintf(file, "line: %d, the tag: %s \n", p->line, p->data);
    p=p->next;
  }
}

void freelist(ptr *Hptr) {
  ptr p;
  if(!(*Hptr))
    return;
  p=*Hptr;
  if(p->next == NULL) {
    free(p);
    return;
  } do {	
    p=*Hptr;
    *Hptr=p->next;
    free(p);
  } while (!(*Hptr));
}

void add_2_list(ptr *H, char *s, int line, int is_inst) {
  ptr T, p1;
  T=(ptr) malloc(sizeof(item));
  if (T == NULL) {
    fprintf(error_file, "at_line: %d, error: cannot allocate memory for %s", line_num, s);
    exit(0);
  }
  if (is_inst)
    T->is_inst = 1;
  else
    T->is_inst = 0;
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


int search_list(ptr H, char *s) {
  ptr p;
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

int verify(ptr H, char *s) {
  ptr p;
  if (!H) return 0;
  p=H;

  while (p->data != NULL) {
    if (!(strcmp(s, p->data))) p->is_inst = 2;
    p=p->next;
  }
  return 0;
}
