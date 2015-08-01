int current_iteration = 1;
int first_operand_exists = 0;
int got_second_operand = 0;
int got_second_operand_again = 0;
int second_relocatable = 0;
int first_relocatable = 0;
extern int got_error;
extern FILE *error_file;
extern int line_num;

void write_code();
void no_operands();
void mov();
void cmp();
void inc();
void lea();
void jmp();
void prn();
void jsr();

char group_check;
char last_first_operand = 0;
char last_second_operand = 0;
char current_command[13];

int times_to_code = 0;

void exec_cmd(FILE *,FILE *,int);
void fetch_first_operand(FILE *file);
void get_second_operand(FILE *file);
char previous_first_operand[MAX_LINE_SIZE];
void convert_cmd_to_code(char *, char *);