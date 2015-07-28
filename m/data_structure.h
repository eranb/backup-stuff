typedef struct node {
  char data[30];
  int line;
  int tml;
  struct node * next;
  struct node * prev;
} Node;

typedef Node * List;

void clean(List *);
void add(List *,char *,int,int);
int find(List,char *);
int set_tml(List, chaupdate_tml);
