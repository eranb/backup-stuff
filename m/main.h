int run();
void cleanup();
void initialize(char *);
int eval(int);
char * with_extension(char *, char *);
FILE * init_file(char *, char *);

int read_word(int i);
void init_files();
int my_index = 0 ;
int line_num = 0;
int got_error = 0;
int iteration = 1;
int IC = 100, DC = 0;

FILE *temp_file;
FILE *ext_file;
FILE *ent_file;
FILE *error_file;
FILE *data_file;
FILE *final_file;
FILE *as_file;

int time_to_code;
void which_command(FILE *, int);
