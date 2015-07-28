typedef struct node {
  char data[30];
  int line;
  int tml;
  struct node * next;
  struct node * prev;
} Node;

typedef Node * List;

void clean_list(List *);
void add(List *H, char *s, int num, int tml);
int find(List H, char *s);
int verify(List H, char *s);
