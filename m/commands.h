int current_iteration = 1;
int first_operand_exists = 0;
int second_operand_exists = 0;
int second_operand_exists_again = 0;
int flag_second_relocatable = 0;
int flag_first_relocatable = 0;
extern int got_error;
extern FILE *error_file;
extern int line_num;

void print_command();
void deal_with_no_operands();
void mov();
void cmp();
void not_clr_inc_dec();
void lea();
void jmp_bne_red();
void prn();
void jsr();

char group_check;
char the_last_first_operand = 0;
char the_last_second_operand = 0;
char current_command[13];

int times_to_code = 0;

void exec_cmd(FILE *,FILE *,int);
void get_first_operand(FILE *file);
void get_second_operand(FILE *file);
