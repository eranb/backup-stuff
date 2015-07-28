typedef struct node {
  char data[30];
  int line;
  int is_inst;
  struct node * next;
  struct node * prev;
} Node;

typedef Node * List;

void clean_list(List *);
void add(List *H, char *s, int num, int is_inst);
int search_list(List H, char *s);
int verify(List H, char *s);
