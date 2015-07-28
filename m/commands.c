#include "utils.h"
#include "commands.h"
#include "instructions.h"
#include "data_structure.h"

void exec_cmd(FILE *file, FILE * efile, int iteration_num) {
  extern int my_index;	
  int i,j,num;
  char tmp[4];

  times_to_code = atoi(&word[strlen(word)-1]);
  
  // based on https://en.wikipedia.org/wiki/De_Morgan%27s_laws
  if (!(times_to_code == 1 || times_to_code == 2)) {
    fprintf(efile, "%d: how many times to run the cmd ?\n", line_num); 
    got_error = 1;
    return;
  } else {
    word[strlen(word)-1]='\0';
  }
  
  current_iteration = iteration_num;
  
  convert_cmd_to_code(word, command);

  if (command[0] == '\0') {
    fprintf(efile, "%d: unknown command\n", line_num);
    got_error = 1;
    return;
  }

  // double
  for (j=2, i=0; j<6; j++, i++) current_command[j] = command[i];

  check_group(command, group);
  current_command[0]=group[0];
  current_command[1]=group[1];
  while (isspace(line[my_index])) my_index++;

  i=2;
  j=0;

  for ( ; i<6; i++)
    tmp[j++]=current_command[i];

  num = atoi(tmp);



  switch(num) {
    case 1110: case 1111: { 
      deal_with_no_operands();
      break;
    } case 0: case 10: case 11: {
      mov_addsub();
      break;
    } case 1: { 
      cmp();
      break;
    } case 100: case 101: case 111: case 1000: {		
      not_clr_inc_dec();
      break;
    } case 110: {
      lea();
      break;
    } case 1001: case 1010: case 1011: {
      jmp_bne_red();
      break;
    } case 1100: {
      prn();
      break;
    } case 1101: {
      jsr();
      break;
    }
  }
    current_command[10] = current_command[11] = '0';
  print_command(file);
  return;
}

void get_second_operand(FILE *file) {
  extern int my_index;
  int minus_flag = 0, i = 0, num;
  char tmp[MAXLINE];
  char result[MAXLINE];
  char r[2];
  int c;
  reset_str(r, 2);
  reset_str(tmp, MAXLINE);
  reset_str(result, MAXLINE);
  while (isspace(line[my_index])) my_index++;
  if (line[my_index] == '#') {
    current_command[8] = current_command[9] = '0';		
    my_index++;
    if (line[my_index] == '-') {
      minus_flag = 1;
      my_index++;
    } else if (line[my_index] == '+') {
      my_index++;
    } else if (!isdigit(line[my_index])) {
      fprintf(error_file, "at line: %d, error: was expecting a number, and not %c\n", line_num, line[my_index]);
      return;
    }

    while (isdigit(line[my_index]))
      tmp[i++]=line[my_index++];

    tmp[i] = '\0';
    num = atoi(tmp);
    convert_to_base(num, 2, tmp);
    strcat(tmp, "00");
    complete_to_12(tmp);
    if (minus_flag) two_complement(tmp);
    strcpy(second_operand, tmp);
    i=0;
    minus_flag = 0;
    second_operand_exists = 1;
    the_last_second_operand = 00;
  } else if (line[my_index] == '$') {
    my_index++;
    if (line[my_index] != '$') {
      fprintf(error_file, "at line: %d, error: not an acceptable operand\n", line_num);
      got_error = 1;
      the_last_second_operand = -1;
      return;
    }
    if ((the_last_first_operand == -1)&&(the_last_second_operand == -1)) {
      fprintf(error_file, "at line: %d, error: there was no operand in the last line\n", line_num);
      got_error = 1;
      the_last_second_operand = -1;
      return;
    }
    if (the_last_second_operand == -1)
    {
      group_check = the_last_first_operand;
      strcpy(second_operand, previous_first_operand);
    }
    else
    {
      group_check = the_last_second_operand;
      strcpy(second_operand, previous_second_operand);
    }			
    switch (group_check)
    {
      case 0: current_command[8] = current_command[9] = '0';
      break;
      case 1: current_command[8] = '0';
      current_command[9] = '1';
      break;
      case 3: current_command[8] = current_command[9] = '1';
      break;
    }
    my_index++;
    second_operand_exists = 1;
  }
  else
  {	
    while (!(isspace(line[my_index])) && (line[my_index] != '\0'))
      tmp[i++] = line[my_index++];
    if ((strlen(tmp))==2 && (tmp[0] == 'r') && isdigit(tmp[1]))
    {
      get_register(r, tmp);
      if ((atoi(r)) || (atoi(r) == 0))
      {
        reset_str(second_operand, MAXLINE);
        strcpy(second_operand, r);
        current_command[8]=current_command[9]='1';
        second_operand_exists = 1;
        the_last_second_operand = 3;
      }
    }
    else
    {
      if (current_iteration == 1)
      {
        second_operand[0]='1';
        second_operand[1] = '\0';
        current_command[8] = '0';
        current_command[9] = '1';
        second_operand_exists = 1;
        the_last_second_operand = 1;
      }
      else
      {
        c = get_value_of_tag(tmp, 2);
        if (c == -1)
        {		
          fprintf(error_file, "at line: %d, wrong operand name: %s\n", line_num, tmp);
          got_error = 1;
          return;
        }
        current_command[8] = '0';
        current_command[9] = '1';
        convert_to_base(c, 2, second_operand);
        second_operand_exists = 1;
        the_last_second_operand = 1;
      }
    }		
  }
  my_index++;
  reset_str(previous_second_operand, MAXLINE);
  strcpy(previous_second_operand, second_operand);
  while (isspace(line[my_index])) my_index++;
  if (line[my_index] != '\0')
  {		
    fprintf(error_file, "at line: %d, error:wasnt expecting anything after second operand\n", line_num);
    got_error = 1;
    return;
  }
  return;

}

void get_first_operand(FILE *file) {
  extern int my_index;
  int minus_flag = 0, i = 0, num, c;
  char tmp[MAXLINE];
  char r[2];
  reset_str(tmp, MAXLINE);
  if (line[my_index] == '#') {
    current_command[6] = current_command[7] = '0';		
    my_index++;
    if (line[my_index] == '-') {
      minus_flag = 1;
      my_index++;
    }
    else if (line[my_index] == '+')
      my_index++;
    else if (!isdigit(line[my_index])) {
      fprintf(error_file, "at line: %d, error: was expecting a number, and not %c\n", line_num, line[my_index]);
      return;
    }
    while (isdigit(line[my_index]))
      tmp[i++]=line[my_index++];
    tmp[i] = '\0';
    num = atoi(tmp);
    convert_to_base(num, 2, tmp);
    strcat(tmp, "00");
    complete_to_12(tmp);
    if (minus_flag)
      two_complement(tmp);
    strcpy(first_operand, tmp);
    i=0;
    minus_flag = 0;
    first_operand_exists = 1;
    the_last_first_operand = 0;
  }
  else if (line[my_index] == '$') {
    my_index++;
    if (line[my_index] != '$') {
      fprintf(error_file, "at line: %d, error: not an acceptable operand\n", line_num);
      got_error = 1;
      the_last_first_operand = -1;
      return;
    }
    else if ((!(isspace(line[my_index+1]))) && (!(line[my_index+1] == ','))) {
      fprintf(error_file, "at line: %d, error: not an acceptable operand\n", line_num);
      got_error = 1;
      the_last_first_operand = -1;
      return;
    }
    if ((the_last_first_operand == -1)&&(the_last_second_operand == -1)) {
      fprintf(error_file, "at line: %d, error: there was no operand in the last line\n", line_num);
      got_error = 1;
      the_last_first_operand = -1;
      return;
    }
    if (the_last_first_operand == -1) {
      group_check = the_last_second_operand;
      strcpy(first_operand, previous_second_operand);
    } else {
      group_check = the_last_first_operand;
      strcpy(first_operand, previous_first_operand);
    }			
    switch (group_check) {
      case 0: current_command[6] = current_command[7] = '0';
      break;
      case 1: current_command[6] = '0';
      current_command[7] = '1';
      break;
      case 3: current_command[6] = current_command[7] = '1';
      break;
    }
    my_index++;
    first_operand_exists = 1;
  } else {	
    while (!(isspace(line[my_index])) && (line[my_index] != ','))
      tmp[i++] = line[my_index++];
    if (((strlen(tmp))==2) && (tmp[0] == 'r') && (isdigit(tmp[1]))) {
      get_register(r, tmp);
      if ((atoi(r)) || (atoi(r) == 0)) {
        reset_str(first_operand, MAXLINE);
        strcpy(first_operand, r);
        current_command[6]=current_command[7]='1';
        first_operand_exists = 1;
        the_last_first_operand = 3;
      }
    } else {
      if (current_iteration == 1) {
        first_operand[0]='1';
        first_operand[1] = '\0';
        current_command[6] = '0';
        current_command[7] = '1';
        first_operand_exists = 1;
        the_last_first_operand = 1;
      } else {
        c = get_value_of_tag(tmp, 1);
        if (c == -1) {		
          fprintf(error_file, "at line: %d, wrong operand name: %s\n", line_num, tmp);
          got_error = 1;
          return;
        }
        current_command[6] = '0';
        current_command[7] = '1';
        convert_to_base(c, 2, first_operand);
        first_operand_exists = 1;
        the_last_first_operand = 1;
      }
    }		
  }
  strcpy(previous_first_operand, first_operand);
  while (isspace(line[my_index])) my_index++;
  if (line[my_index] == ',')
    my_index++;
  else {
    fprintf(error_file, "at line : %d, error: wasnt suppose to be anything else than a ',' before the next operand\n", line_num);
    got_error = 1;
    return;
  }
  while (isspace(line[my_index])) my_index++;
  return;

}

void print_command(FILE *file) {	
  extern int IC;
  int i;
  char end_result[MAXLINE];
  char command_adress[MAXLINE];
  char first_adress[MAXLINE];
  char second_adress[MAXLINE];
  int flag_combined = 0;
  reset_str(end_result, MAXLINE);
  reset_str(command_adress, MAXLINE);
  reset_str(first_adress, MAXLINE);
  reset_str(second_adress, MAXLINE);
  complete_to_12(current_command);
  convert_binary_string_to_base_4_string(current_command, end_result);
  reset_str(current_command, strlen(current_command));
  strcpy(current_command, end_result);
  if (!(got_error)) {
    convert_to_base(IC, 4, command_adress);
    fprintf(file, "%s	%s \n", command_adress,  current_command);
    IC++;
  }
  if(strlen(first_operand) == 5 && strlen(second_operand) == 5) {
    flag_combined = 1;		
    reset_str(end_result, MAXLINE);		
    strcat(first_operand, second_operand);
    first_operand[10]=first_operand[11]='0';
    convert_binary_string_to_base_4_string(first_operand, end_result);
    strcpy(first_operand, end_result);
    if (!got_error) {
      convert_to_base(IC, 4, first_adress);
      fprintf(file, "%s	%s \n", first_adress, first_operand);
    }
    IC++;
  } else {	
    if (first_operand_exists) {
      reset_str(end_result, MAXLINE);
      if(strlen(first_operand) == 5) {
        for (i=5; i<12; i++)				
          first_operand[i] = '0';		
      }
      if(flag_first_relocatable) {
        strcat(first_operand, "10");
        strcat(previous_first_operand, "10");
        flag_first_relocatable = 0;
      }
      complete_to_12(first_operand);
      convert_binary_string_to_base_4_string(first_operand, end_result);
      reset_str(first_operand, strlen(first_operand));
      strcpy(first_operand, end_result);
      if (!(got_error)) {
        convert_to_base(IC, 4, first_adress);
        fprintf(file, "%s	%s \n", first_adress, first_operand);
      }
      IC++;
    }
    if (second_operand_exists) {
      reset_str(end_result, MAXLINE);
      if(strlen(second_operand) == 5)
        second_operand[5]=second_operand[6]='0';		
      if(flag_second_relocatable) {
        strcat(second_operand, "10");
        strcat(previous_second_operand, "10");
        flag_second_relocatable = 0;
      }
      complete_to_12(second_operand);
      convert_binary_string_to_base_4_string(second_operand, end_result);
      reset_str(second_operand, strlen(second_operand));
      strcpy(second_operand, end_result);
      if (!(got_error)) {
        convert_to_base(IC, 4, second_adress);
        fprintf(file, "%s	%s \n", second_adress, second_operand);
      }
      IC++;
    }
  }
  if (times_to_code == 2) {
    if (!(got_error)) {
      convert_to_base(IC, 4, command_adress);
      fprintf(file, "%s	%s \n", command_adress, current_command);
    }
    IC++;
    if (flag_combined == 1) {
      if (!(got_error)) {
        convert_to_base(IC, 4, first_adress);
        fprintf(file, "%s	%s \n", first_adress, end_result);
      }
      IC++;
    } else {
      if (first_operand_exists) {
        if (!(got_error)) {	
          convert_to_base(IC, 4, first_adress);	
          fprintf(file, "%s	%s \n", first_adress, first_operand);
        }	
        IC++;
      }
      if (second_operand_exists) {	
        if (!(got_error))	 {
          convert_to_base(IC, 4, second_adress);	
          fprintf(file, "%s	%s \n", second_adress, second_operand);
        }
        IC++;
      }
    }
  }
  first_operand_exists = 0;
  second_operand_exists = 0;
  return;
}

void deal_with_no_operands(FILE *file) {
  extern int my_index;	
  current_command[6]=current_command[7]=current_command[8]=current_command[9]='0';
  the_last_first_operand = -1;
  the_last_second_operand = -1;
  while (!(line[my_index] == '\0' || isspace( line[my_index] ))) {
    fprintf(error_file, "at_line: %d, error: wasnt expecting anything after command\n", line_num);
    got_error = 1;	
    return;
  }
  return;
}

void mov_addsub(FILE *file) {
  get_first_operand(file);
  get_second_operand(file);
  if (current_command[8] == '0' && current_command[9] == '0') {
    fprintf(error_file, "at_line: %d, error: second operand - wrong operand method\n", line_num);
    got_error = 1;
  }
  else if (current_command[8] == '1' && current_command[9] == '0') {
    fprintf(error_file, "at_line: %d, error: second operand - wrong operand method\n", line_num);
    got_error = 1;
  }
  return;
}


void cmp(FILE *file) {
  get_first_operand(file);
  get_second_operand(file);
  return;
}

void not_clr_inc_dec(FILE *file) {
  int i;	
  current_command[6]=current_command[7]='0';
  get_second_operand(file);
  the_last_first_operand = -1;
  for (i=0; i<12; i++)
    first_operand[i]='0';
  if (current_command[8] == '0' && current_command[9] == '0')
  {
    fprintf(error_file, "at_line: %d, error: second operand - wrong operand method\n", line_num);
    got_error = 1;
  }
  else if (current_command[8] == '1' && current_command[9] == '0')
  {	
    fprintf(error_file, "at_line: %d, error: second operand - wrong operand method\n", line_num);
    got_error = 1;
  }
  return;
}

void lea(FILE *file) {
  get_first_operand(file);
  get_second_operand(file);
  if (!(current_command[6] == '0' && current_command[7] == '1')) {
    fprintf(error_file, "at_line: %d, error: first operand - wrong operand method\n", line_num);
    got_error = 1;
  }
  if (current_command[8] == '0' && current_command[9] == '0') {	
    fprintf(error_file, "at_line: %d, error: second operand - wrong operand method\n", line_num);
    got_error = 1;
  }
  else if (current_command[8] == '1' && current_command[9] == '0') {	
    fprintf(error_file, "at_line: %d, error: second operand - wrong operand method\n", line_num);
    got_error = 1;
  }	
  return;
}

void jmp_bne_red(FILE *file) {
  int i;	
  get_second_operand(file);
  the_last_first_operand = -1;
  current_command[6]=current_command[7]='0';
  for (i=0; i<12; i++)
    first_operand[i]='0';
  if (current_command[8] == '0' && current_command[9] == '0')
  {
    fprintf(error_file, "at_line: %d, error: second operand - wrong operand method\n", line_num);
    got_error = 1;
  }
  return;
}

void prn(FILE *file) {
  int i;	
  get_second_operand(file);
  the_last_first_operand = -1;
  current_command[6]=current_command[7]='0';
  for (i=0; i<12; i++)
    first_operand[i]='0';
  return;
}

void jsr(FILE *file) {
  int i;	
  get_second_operand(file);
  the_last_first_operand = -1;
  current_command[6]=current_command[7]='0';
  for (i=0; i<12; i++)
    first_operand[i]='0';
  if (!(current_command[8] == '0' && current_command[9] == '0')) {	
    fprintf(error_file, "at_line: %d, error: second operand - wrong operand method\n", line_num);
    got_error = 1;
  }
  return;
}

int get_value_of_tag(char *s, int op_kind) {
  int c;
  extern List symbol_list, extern_list;
  c = find(symbol_list, s);
  if (c == -1) {
    c = find(extern_list, s);
    if (c != -1) {	
      if ((op_kind == 2) && (first_operand_exists))
        print_extern(s, op_kind, times_to_code);
      else 
        print_extern(s, 1, times_to_code);
      c = 1;
    }
    return c;
  }
  if (op_kind == 1)
    flag_first_relocatable = 1;
  if (op_kind == 2)
    flag_second_relocatable = 2;
  return c;
}
