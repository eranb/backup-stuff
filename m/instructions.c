#include "utils.h"
#include "instructions.h"
#include "data_structure.h"

//Definition of unique "struct" for dealing with all the instruction
struct {
  char *name;
  int value;
} instructions[]={
  {".data", 1},
  {".string", 2},
  {".entry", 3},
  {".extern", 4},
  {"no-op", 5}
};

//three kinds of list
List extern_list = NULL;
List entry_list = NULL;
List symbol_list = NULL;

//Different kinds of files 
FILE *ext_file;
FILE *ent_file;
FILE *error_file;
FILE *data_file;
FILE *temp_file;
FILE *final_file;
FILE *as_file;

extern int got_error;
extern int iteration;
extern int line_num;

//functions which check the type of the instructions and refer to the suitable function who deals with this specific instructions
void do_instructions(FILE *file) {	
  int i=0, instration=5;
  while (strcmp(instructions[i].name, "no-op")) {
    if (strcmp(instructions[i].name, word) == 0) {
      instration = instructions[i].value;
      break;
    }
    i++;
  }
  
  switch(instration) {
    case 1:
      do_data(file);
      break;
    case 2:
      do_string(file);
      break;
    case 3:
      if(iteration == 1) do_entry(file);
      break;
    case 4: if(iteration == 1)
      do_extern(file);
      break;
    default:
      fprintf(error_file, "%d: unknown instruction...\n", line_num);
      got_error = 1;
      break;
  }
}

//deals with data instructions
void do_data(FILE *file) {
  extern int my_index, DC;
  int first_time = 1, i=0, minus = 0;
  char number[MAX_LINE_SIZE], result[MAX_LINE_SIZE];

  while (line[my_index] != '\0') {	
    while(isspace(line[my_index])) my_index++;
    if (first_time) { 
      if (line[my_index] == '\0') {
        fprintf(error_file, "%d: should be at least one number...\n", line_num);
        got_error = 1;
        return;
      } else if (line[my_index] == ',') {
        fprintf(error_file, "%d: should be at least one number before ','...\n", line_num);
        got_error = 1;
        return;
      }
      first_time=0;
    }
    
    if (line[my_index] == '-') {
      minus = 1;
      my_index++;
    } else if (line[my_index] == '+')
      my_index++;
    else if (line[my_index] == ',')
      my_index++;			
    while( (!isspace(line[my_index])) && (line[my_index] != '\0') && (line[my_index] != ',')) {			
      if(!isdigit(line[my_index])) {
        fprintf(error_file, "%d: expected a number...\n", line_num);
        got_error = 1;
        return;
      }
      number[i++] = line[my_index++];
    }

    number[i] = '\0';

    in_base(atoi(number), 2, result);
    make_it_12_digits(result);

    if (minus) two_complement(result);
    reset_str(first_operand, MAX_LINE_SIZE);

    binary_to_base4(result, first_operand);

    if ( (got_error != 1) && (iteration == 2) )
      fprintf(data_file, "%s\n", first_operand);

    i=0;
    minus = 0;

    while (isspace(line[my_index])) my_index++;

    if(line[my_index] == ',') my_index++;
    while (isspace(line[my_index])) my_index++;
    DC++;
  }
}

//deals with string instructions
void do_string(FILE *file) {
  extern int my_index, DC;	
  int num;
  char result[MAX_LINE_SIZE];
  reset_str(first_operand, MAX_LINE_SIZE);
  
  while(isspace(line[my_index])) my_index++; 

  if (line[my_index] == '\0') {
    fprintf(error_file, "%d: should be at least one character...\n", line_num);
    got_error = 1;
    return;
  } else if (line[my_index] == '\"')
    my_index++;				
  else {		
    fprintf(error_file, "%d: expected new line...", line_num);
    got_error = 1;
    return;
  }
  
  while (line[my_index] != '\"') {	
    while (isspace(line[my_index])) my_index++;
    if (!isalpha(line[my_index])) {
      fprintf(error_file, "%d: didn't expected %c...\n", line_num, line[my_index]);
      got_error = 1;
      return;
    }
    
    num = line[my_index];
    in_base(num, 2, result);
    make_it_12_digits(result);
    binary_to_base4(result, first_operand);
    
    if ((got_error != 1)&&(iteration == 2)) fprintf(data_file, "%s\n", first_operand);
    my_index++;
    DC++;
  }
  
  if (line[my_index] == '\"') {
    my_index++;
    num = 0;
    in_base(num, 2, result);
    make_it_12_digits(result);
    binary_to_base4(result, first_operand);
    if ((got_error != 1)&&(iteration == 2))
      fprintf(data_file, "%s\n", first_operand);
    DC++;
  } else {
    fprintf(error_file, "%d: should be '\"' at the last of the line...", line_num);
    got_error = 1;
    return;
  }
  
  while (line[my_index] != '\0') {
    fprintf(error_file, "%d: unexpected %c ( should not be after \" )", line_num, line[my_index]);
    got_error = 1;
  }
}

//deals with entry instructions
void do_entry(FILE *file) {
  extern int my_index;
  int i=0;
  char tmp[30];

  reset_str(tmp, 30);
  while (isspace(line[my_index])) my_index++;
  while(isalpha(line[my_index])) tmp[i++] = line[my_index++];
  while (isspace(line[my_index])) my_index++;

  if (line[my_index] != '\0') {
    fprintf(error_file, "%d: expected end of line, after label...\n", line_num);
    got_error = 1;
    return;
  }
  add(&entry_list, tmp, 0, 1);
}

//deals with extern instructions
void do_extern(FILE *file) {
  extern int my_index, IC, iteration;
  int i=0;
  char tmp[30];
  reset_str(tmp, 30);

  while (isspace(line[my_index])) my_index++;
  while((isalpha(line[my_index]))||(isdigit(line[my_index]))) tmp[i++] = line[my_index++];
  while (isspace(line[my_index])) my_index++;

  if (line[my_index] != '\0') {
    fprintf(error_file, "%d: expected end of line, after label...\n", line_num);
    got_error = 1;
    return;
  }

  if(iteration == 1) add(&extern_list, tmp, IC, 1);
}

void write_extern(char *s, int operation, int times) {
  extern int IC;
  int i;

  char tmp[30];
  char line_num[30];

  reset_str(tmp, 30);
  reset_str(line_num, 30);

  i = IC + operation;
  in_base(i, 2, tmp);
  make_it_12_digits(tmp);
  binary_to_base4(tmp, line_num);
  fprintf(ext_file, "%s:	%d\n", s, (atoi(line_num)));
  update_tml(extern_list, s,2);

  if (times == 2) {
    if (operation == 1)
      i += 2;
    else
      i += 3;

    reset_str(tmp, 30);
    reset_str(line_num, 30);
    in_base(i, 2, tmp);
    make_it_12_digits(tmp);
    binary_to_base4(tmp, line_num);
    fprintf(ext_file, "%s:	%d\n", s, atoi(line_num));
  }
}

void write_entry(char *string, int c) {
  extern int IC;
  char tmp[30], line_num[30];

  reset_str(tmp, 30);
  reset_str(line_num, 30);

  in_base(c, 2, tmp);
  make_it_12_digits(tmp);
  binary_to_base4(tmp, line_num);
  fprintf(ent_file, "%s:	%d\n", string, (atoi(line_num)));
  update_tml(entry_list, string,2);
}

//add symbol to symbol list
void add_symbol(char * string, int inst_or_command, int value) {
  if (find(symbol_list, string) == -1) {
    if (inst_or_command == 2)
      add(&symbol_list, string, value, 0);
    if (inst_or_command == 1)
      add(&symbol_list, string, value, 1);
  }
}

//search string in entry list
void search_in_entry_list(char *s) {
  if (find(entry_list, s) != -1)
    write_entry(s, find(symbol_list, s));
}

//initialize the lists
void clean_lists() {
  clean(&symbol_list);
  clean(&entry_list);
  clean(&extern_list);
}

void normalize_line_numbers() {
  extern int IC;	
  List p = symbol_list;

  while (!(p == NULL || p->next == NULL)) {
    if (p->tml == 1) p->line += IC;
    p = p->next;
  }
}

int check_entry_list() {
  List p = entry_list;

  while (!(p == NULL || p->next == NULL)) {
    if (p->tml != 2) return 2;
    p = p->next;
  }
  return 1;
}

int check_extern_list() {
  List p = extern_list;

  while (!(p == NULL || p->next == NULL)) {
    if (p->tml != 2) return 2;
    p = p->next;
  }

  return 1;
}
