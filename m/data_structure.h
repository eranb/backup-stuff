typedef struct node *ptr;
typedef struct node
{
	char data[30];
	int line;
	int is_inst;
	ptr next, prev; 		
} item;

void clean_list(ptr *);
void add_2_list(ptr *H, char *s, int num, int is_inst);
int search_list(ptr H, char *s);
int verify(ptr H, char *s);
