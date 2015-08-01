#include "utils.h"
#include "instructions.h"
#include "data_structure.h"

struct {
  char *name;
  int value;
} instructions[]={
  {".data", 1},
  {".string", 2},
  {".entry", 3},
  {".extern", 4},
  {"nope", 5}
};

List extern_list = NULL;
List entry_list = NULL;
List symbol_list = NULL;

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

void do_instructions(FILE *file) {	
  int i=0, j, inst_kind=5;
  while (strcmp(instructions[i].name, "nope")) {
    j=strcmp(instructions[i].name, word);
    if (j == 0) {
      inst_kind = instructions[i].value;
      break;
    }
    i+=1;
  }
  
  switch(inst_kind) {
    case 1: do_data(file);
            break;
    case 2: do_string(file);
            break;
    case 3: if(iteration == 1)
              do_entry(file);
            break;
    case 4: if(iteration == 1)
              do_extern(file);
            break;
    default: fprintf(error_file, "at line: %d, error: no such instruction\n", line_num);
             got_error = 1;
             break;
  }
  return;
}


/* a function that deals with ".data" instruction. prints all the numbers following on the same line as a base 4 strings. */
void do_data(FILE *file) {
  extern int my_index, DC;
  int first_time_flag = 1, i=0, minus = 0;
  char number[MAXLINE];
  char result[MAXLINE];
  unsigned int num;	
  while (line[my_index] != '\0') /* for every number in the line */
  {	
    while(isspace(line[my_index]))	/* skip spaces */
      my_index++;
    if (first_time_flag)	/* make sure theres at least one number */		{ 
      if (line[my_index] == '\0')
      {
        fprintf(error_file, "at line: %d, error: expected at least one number\n", line_num);
        got_error = 1;
        return;
      }
      else if (line[my_index] == ',')
      {
        fprintf(error_file, "at line: %d, error: expected at least one number before ','\n", line_num);
        got_error = 1;
        return;
      }
      first_time_flag=0;
    }
    if (line[my_index] == '-') /* if its a minus number, we'd have to use complementation to 2 */
    {
      minus = 1;
      my_index++;
    }
    else if (line[my_index] == '+')
      my_index++;
    else if (line[my_index] == ',')
      my_index++;			
    while( (!isspace(line[my_index])) && (line[my_index] != '\0') && (line[my_index] != ',')) /* get the next number, digit by digit */
    {			
      if(!isdigit(line[my_index]))
      {
        fprintf(error_file, "at line: %d, error: supposed to be a number\n", line_num);
        got_error = 1;
        return;
      }
      number[i++] = line[my_index++];
    }
    /* converting the number we got to a base 4 string, 12 bits long */

    number[i] = '\0';
    num = atoi(number);
    in_base(num, 2, result);
    make_it_12_digits(result);
    if (minus)
      two_complement(result);
    reset_str(first_operand, MAXLINE);
    convert_binary_string_to_base_4_string(result, first_operand);
    if ((got_error != 1)&&(iteration == 2))
      fprintf(data_file, "%s\n", first_operand);
    i=0;
    minus = 0;	
    while (isspace(line[my_index])) my_index++;
    if(line[my_index] == ',')
      my_index++;
    while (isspace(line[my_index])) my_index++;
    DC++;
  }
  return;			
}

/* a function that deals with ".string" instruction. prints all the letters following on the same line as a base 4 strings. */
void do_string(FILE *file) {
  extern int my_index, DC;	
  int num;
  char result[MAXLINE];
  reset_str(first_operand, MAXLINE);
  while(isspace(line[my_index]))	
    my_index++; 
  if (line[my_index] == '\0') /* if there is no string at all */
  {
    fprintf(error_file, "at line: %d, error: expected at least one character\n", line_num);
    got_error = 1;
    return;
  }
  else if (line[my_index] == '\"')	/* make sure we start with " */
    my_index++;				
  else
  {		
    fprintf(error_file, "at line: %d, error: was expecting a \"\n", line_num);	/* if its something else than " */
    got_error = 1;
    return;
  }
  while (line[my_index] != '\"')		/* printing all the string */
  {	
    while (isspace(line[my_index]))	
      my_index++;		
    if (!isalpha(line[my_index]))
    {
      fprintf(error_file, "at line: %d, error: was not expecting %c\n", line_num, line[my_index]);
      got_error = 1;
      return;
    }
    num = (int) line[my_index];
    in_base(num, 2, result);
    make_it_12_digits(result);
    convert_binary_string_to_base_4_string(result, first_operand);
    if ((got_error != 1)&&(iteration == 2))
      fprintf(data_file, "%s\n", first_operand);
    my_index++;
    DC++;
  }
  if (line[my_index] == '\"') /* making sure there is an " at the end, and printing an \0 to finish the string */
  {
    my_index++;
    num = 0;
    in_base(num, 2, result);
    make_it_12_digits(result);
    convert_binary_string_to_base_4_string(result, first_operand);
    if ((got_error != 1)&&(iteration == 2))
      fprintf(data_file, "%s\n", first_operand);
    DC++;
  }
  else  /* if there was no " at the ned */
  {
    fprintf(error_file, "at line: %d, error: expected a '\"' at the end", line_num);
    got_error = 1;
    return;
  }
  while (line[my_index] != '\0') /* if after the " there are more chars */
  {
    fprintf(error_file, "at line: %d, error: %c wasnt supposed to be enterd after the \"", line_num, line[my_index]);
    got_error = 1;
  }
  return;			
}

void do_entry(FILE *file) {
  extern int my_index;
  int i=0;
  char tmp[30];
  reset_str(tmp, 30);
  while (isspace(line[my_index])) my_index++;

  while(isalpha(line[my_index]))
    tmp[i++] = line[my_index++];
  while (isspace(line[my_index])) my_index++;
  if (line[my_index] != '\0')
  {
    fprintf(error_file, "at line: %d, error: wasn't suppose to be anything after the label name\n", line_num);
    got_error = 1;
    return;
  }
  add(&entry_list, tmp, 0, 1);
}

void do_extern(FILE *file) {
  extern int my_index, IC, iteration;
  int i=0;
  char tmp[30];
  reset_str(tmp, 30);
  while (isspace(line[my_index])) my_index++;
  while((isalpha(line[my_index]))||(isdigit(line[my_index])))
    tmp[i++] = line[my_index++];
  while (isspace(line[my_index])) my_index++;
  if (line[my_index] != '\0') {
    fprintf(error_file, "at line: %d, error: wasn't suppose to be anything after the label name\n", line_num);
    got_error = 1;
    return;
  }

  if(iteration == 1) add(&extern_list, tmp, IC, 1);
}

void print_extern(char *s, int op_kind, int times) {
  extern int IC;
  int i;
  char tmp[30];
  char line_num[30];
  reset_str(tmp, 30);
  reset_str(line_num, 30);
  i = IC + op_kind;
  in_base(i, 2, tmp);
  make_it_12_digits(tmp);
  convert_binary_string_to_base_4_string(tmp, line_num);
  fprintf(ext_file, "%s:	%d\n", s, (atoi(line_num)));
  update_tml(extern_list, s,2);

  if (times == 2) {
    if (op_kind == 1)
      i += 2;
    else
      i += 3;
    reset_str(tmp, 30);
    reset_str(line_num, 30);
    in_base(i, 2, tmp);
    make_it_12_digits(tmp);
    convert_binary_string_to_base_4_string(tmp, line_num);
    fprintf(ext_file, "%s:	%d\n", s, (atoi(line_num)));
  }
  return;
}

void print_entry(char *s, int c) {
  extern int IC;
  char tmp[30];
  char line_num[30];
  reset_str(tmp, 30);
  reset_str(line_num, 30);
  in_base(c, 2, tmp);
  make_it_12_digits(tmp);
  convert_binary_string_to_base_4_string(tmp, line_num);
  fprintf(ent_file, "%s:	%d\n", s, (atoi(line_num)));
  update_tml(entry_list, s,2);
  return;
}

void add_symbol(char *s, int inst_or_command, int value) {
  char c;
  c = find(symbol_list, s);

  if (c == -1) {
    if (inst_or_command == 2)
      add(&symbol_list, s, value, 0);
    if (inst_or_command == 1)
      add(&symbol_list, s, value, 1);
  }
}

void search_in_entry_list(char *s) {
  int c;
  c = find(entry_list, s);
  if (c != -1) {
    c = find(symbol_list, s);
    print_entry(s, c);
  }
}

void cleanlists() {
  clean(&symbol_list);
  clean(&entry_list);
  clean(&extern_list);
}

void normalize_line_numbers() {
  extern int IC;	
  List p = symbol_list;
  if (!p) return;

  while (p->next != NULL) {
    if (p->tml == 1) p->line += IC;
    p=p->next;
  }
  return;
}

int check_entry_list() {
  List p = entry_list;
  if (!p) return 0;

  while (p->next != NULL) {
    if (p->tml != 2) return 2;
    p=p->next;
  }
  return 1;
}

int check_extern_list() {
  List p = extern_list;
  if (!p) return 0;

  while (p->data != NULL) {
    if (p->tml != 2) return 2;
    p=p->next;
  }

  return 1;
}
