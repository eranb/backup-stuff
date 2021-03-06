#include "utils.h"
#include "commands.h"
#include "instructions.h"
#include "data_structure.h"

void exec_cmd(FILE *file, FILE * efile, int iteration_num) {
  extern int my_index;	
  int i,j;
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
  
  command_to_code(word, command);

  if (command[0] == '\0') {
    fprintf(efile, "%d: unknown command\n", line_num);
    got_error = 1;
    return;
  }

  for (j=2, i=0; j<6; j++, i++) current_command[j] = command[i];

  check_group(command, group);
  current_command[0]=group[0];
  current_command[1]=group[1];
  while (isspace(line[my_index])) my_index++;

  for ( i=2, j=0; i<6; i++)
    tmp[j++]=current_command[i];

  switch(atoi(tmp)) {
    case 0: case 10: case 11: {
      mov();
      break;
    } case 1: { 
      cmp();
      break;
    } case 100: case 101: case 111: case 1000: {		
      inc();
      break;
    } case 1110: case 1111: { 
      no_operands();
      break;
    }case 110: {
      lea();
      break;
    } case 1001: case 1010: case 1011: {
      jmp();
      break;
    } case 1101: {
      jsr();
      break;
    } case 1100: {
      prn();
      break;
    } 
  }
  
  current_command[10] = current_command[11] = '0';
  write_code(file);
  return;
}

void get_second_operand(FILE *file) {
  extern int my_index;
  int minus = 0, i = 0, c;
  char tmp[MAX_LINE_SIZE], result[MAX_LINE_SIZE], r[2];
  
  reset_str(r, 2);
  reset_str(tmp, MAX_LINE_SIZE);
  reset_str(result, MAX_LINE_SIZE);
  
  while (isspace(line[my_index])) my_index++;
  
  if (line[my_index] == '#') {
    current_command[8] = current_command[9] = '0';
    my_index++;
    if (line[my_index] == '-') {
      minus = 1;
      my_index++;
    } else if (line[my_index] == '+') {
      my_index++;
    } else if (!isdigit(line[my_index])) {
      fprintf(error_file, "%d: expected number ( not '%c')\n", line_num, line[my_index]);
      return;
    }

    while (isdigit(line[my_index])) tmp[i++]=line[my_index++];

    tmp[i] = '\0';
    
    in_base(atoi(tmp), 2, tmp);
    strcat(tmp, "00");
    make_it_12_digits(tmp);
    if (minus) two_complement(tmp);
    strcpy(second_operand, tmp);
    i=0;
    minus = 0;
    got_second_operand = 1;
    last_second_operand = 00;
  } else if (line[my_index] == '$') {
    my_index++;
    if (line[my_index] != '$') {
      fprintf(error_file, "%d: expected a second $\n", line_num);
      got_error = 1;
      last_second_operand = -1;
      return;
    }
    
    if ((last_first_operand == -1) && (last_second_operand == -1)) {
      fprintf(error_file, "%d: can't find the previous argument\n", line_num);
      got_error = 1;
      last_second_operand = -1;
      return;
    }
    
    if (last_second_operand == -1) {
      group_check = last_first_operand;
      strcpy(second_operand, previous_first_operand);
    } else {
      group_check = last_second_operand;
      strcpy(second_operand, previous_second_operand);
    }
    
    switch (group_check) {
      case 0: current_command[8] = current_command[9] = '0';
      break;
      case 1: current_command[8] = '0';
      current_command[9] = '1';
      break;
      case 3: current_command[8] = current_command[9] = '1';
      break;
    }
    
    my_index++;
    got_second_operand = 1;
  } else {
    while (!(isspace(line[my_index])) && (line[my_index] != '\0'))
      tmp[i++] = line[my_index++];
    
    if ((strlen(tmp))==2 && (tmp[0] == 'r') && isdigit(tmp[1])) {
      fetch_register(r, tmp);
      if (atoi(r) || atoi(r) == 0) {
        reset_str(second_operand, MAX_LINE_SIZE);
        strcpy(second_operand, r);
        current_command[8]=current_command[9]='1';
        got_second_operand = 1;
        last_second_operand = 3;
      }
    } else {
      if (current_iteration == 1) {
        second_operand[0]='1';
        second_operand[1] = '\0';
        current_command[8] = '0';
        current_command[9] = '1';
        got_second_operand = 1;
        last_second_operand = 1;
      } else {
        c = get_value_of_label(tmp, 2);
        if (c == -1) {
          fprintf(error_file, "%d: bad operand '%s'\n", line_num, tmp);
          got_error = 1;
          return;
        }
        
        current_command[8] = '0';
        current_command[9] = '1';
        in_base(c, 2, second_operand);
        got_second_operand = 1;
        last_second_operand = 1;
      }
    }		
  }
  
  my_index++;
  reset_str(previous_second_operand, MAX_LINE_SIZE);
  strcpy(previous_second_operand, second_operand);
  while (isspace(line[my_index])) my_index++;
  
  if (line[my_index] != '\0') {		
    fprintf(error_file, "%d: can't take 3 arguments...\n", line_num);
    got_error = 1;
    return;
  }
  return;
}

void fetch_first_operand(FILE *file) {
  extern int my_index;
  int minus = 0, i = 0, c;
  char tmp[MAX_LINE_SIZE],r[2];
  
  reset_str(tmp, MAX_LINE_SIZE);
  
  if (line[my_index] == '#') {
    current_command[6] = current_command[7] = '0';		
    my_index++;
    if (line[my_index] == '-') {
      minus = 1;
      my_index++;
    } else if (line[my_index] == '+')
      my_index++;
    else if (!isdigit(line[my_index])) {
      fprintf(error_file, "%d: '%c' isn't a number...\n", line_num, line[my_index]);
      return;
    }
    
    while (isdigit(line[my_index]))
      tmp[i++]=line[my_index++];
    
    tmp[i] = '\0';
    
    in_base(atoi(tmp), 2, tmp);
    strcat(tmp, "00");
    make_it_12_digits(tmp);
    
    if (minus) two_complement(tmp);
    
    strcpy(first_operand, tmp);
    
    i=0;
    minus = 0;
    first_operand_exists = 1;
    last_first_operand = 0;
  } else if (line[my_index] == '$') {
    my_index++;
    if (line[my_index] != '$') {
      fprintf(error_file, "%d: invalid operand\n", line_num);
      got_error = 1;
      last_first_operand = -1;
      return;
    } else if ((!(isspace(line[my_index+1]))) && (!(line[my_index+1] == ','))) {
      fprintf(error_file, "%d: invalid operand\n", line_num);
      got_error = 1;
      last_first_operand = -1;
      return;
    }
    
    if ((last_first_operand == -1)&&(last_second_operand == -1)) {
      fprintf(error_file, "%d: missing operand in the last line...\n", line_num);
      got_error = 1;
      last_first_operand = -1;
      return;
    }
    
    if (last_first_operand == -1) {
      group_check = last_second_operand;
      strcpy(first_operand, previous_second_operand);
    } else {
      group_check = last_first_operand;
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
      fetch_register(r, tmp);
      if ((atoi(r)) || (atoi(r) == 0)) {
        reset_str(first_operand, MAX_LINE_SIZE);
        strcpy(first_operand, r);
        current_command[6]=current_command[7]='1';
        first_operand_exists = 1;
        last_first_operand = 3;
      }
    } else {
      if (current_iteration == 1) {
        first_operand[0]='1';
        first_operand[1] = '\0';
        current_command[6] = '0';
        current_command[7] = '1';
        first_operand_exists = 1;
        last_first_operand = 1;
      } else {
        c = get_value_of_label(tmp, 1);
        if (c == -1) {		
          fprintf(error_file, "%d: unknown operand '%s'\n", line_num, tmp);
          got_error = 1;
          return;
        }
        current_command[6] = '0';
        current_command[7] = '1';
        in_base(c, 2, first_operand);
        first_operand_exists = 1;
        last_first_operand = 1;
      }
    }		
  }
  strcpy(previous_first_operand, first_operand);
  while (isspace(line[my_index])) my_index++;
  if (line[my_index] == ',')
    my_index++;
  else {
    fprintf(error_file, "%d: expected ',' not %c\n", line_num,line[my_index]);
    got_error = 1;
    return;
  }
  while (isspace(line[my_index])) my_index++;
  return;
}

void write_code(FILE *file) {	
  extern int IC;
  int i, tzozamen = 0; // togther in Yeidsh
  char results[MAX_LINE_SIZE], addressing[MAX_LINE_SIZE], address[MAX_LINE_SIZE];
  char second_address[MAX_LINE_SIZE];

  reset_str(results, MAX_LINE_SIZE);
  reset_str(addressing, MAX_LINE_SIZE);
  reset_str(address, MAX_LINE_SIZE);
  reset_str(second_address, MAX_LINE_SIZE);

  make_it_12_digits(current_command);
  binary_to_base4(current_command, results);

  reset_str(current_command, strlen(current_command));
  strcpy(current_command, results);

  if (!got_error) {
    in_base(IC, 4, addressing);
    fprintf(file, "%s	%s \n", addressing,  current_command);
    IC++;
  }
  
  if(strlen(first_operand) == 5 && strlen(second_operand) == 5) {
    tzozamen = 1;		
    reset_str(results, MAX_LINE_SIZE);		
    strcat(first_operand, second_operand);
    first_operand[10]=first_operand[11]='0';
    binary_to_base4(first_operand, results);
    strcpy(first_operand, results);
    if (!got_error) {
      in_base(IC, 4, address);
      fprintf(file, "%s	%s \n", address, first_operand);
    }
    IC++;
  } else {	
    if (first_operand_exists) {
      reset_str(results, MAX_LINE_SIZE);
      
      if(strlen(first_operand) == 5)
        for (i=5; i<12; i++)				
          first_operand[i] = '0';
      
      
      if(first_relocatable) {
        strcat(first_operand, "10");
        strcat(previous_first_operand, "10");
        first_relocatable = 0;
      }
      
      make_it_12_digits(first_operand);
      binary_to_base4(first_operand, results);
      reset_str(first_operand, strlen(first_operand));
      strcpy(first_operand, results);
      
      if (!got_error) {
        in_base(IC, 4, address);
        fprintf(file, "%s	%s \n", address, first_operand);
      }
      IC++;
    }
    
    if (got_second_operand) {
      reset_str(results, MAX_LINE_SIZE);
      if(strlen(second_operand) == 5)
        second_operand[5]=second_operand[6]='0';		
      if(second_relocatable) {
        strcat(second_operand, "10");
        strcat(previous_second_operand, "10");
        second_relocatable = 0;
      }
      make_it_12_digits(second_operand);
      binary_to_base4(second_operand, results);
      reset_str(second_operand, strlen(second_operand));
      strcpy(second_operand, results);
      if (!got_error) {
        in_base(IC, 4, second_address);
        fprintf(file, "%s	%s \n", second_address, second_operand);
      }
      IC++;
    }
  }
  
  if (times_to_code == 2) {
    if (!got_error) {
      in_base(IC, 4, addressing);
      fprintf(file, "%s	%s \n", addressing, current_command);
    }
    
    IC++;
    
    if (tzozamen == 1) {
      if (!got_error) {
        in_base(IC, 4, address);
        fprintf(file, "%s	%s \n", address, results);
      }
      IC++;
    } else {
      if (first_operand_exists) {
        if (!got_error) {	
          in_base(IC, 4, address);	
          fprintf(file, "%s	%s \n", address, first_operand);
        }	
        IC++;
      }
      if (got_second_operand) {	
        if (!got_error) {
          in_base(IC, 4, second_address);	
          fprintf(file, "%s	%s \n", second_address, second_operand);
        }
        IC++;
      }
    }
  }
  first_operand_exists = 0;
  got_second_operand = 0;
  return;
}

void no_operands(FILE *file) {
  extern int my_index;	
  current_command[6]=current_command[7]=current_command[8]=current_command[9]='0';
  last_first_operand = -1;
  last_second_operand = -1;
  
  while (!(line[my_index] == '\0' || isspace( line[my_index] ))) {
    fprintf(error_file, "%d: expected end of line...\n", line_num);
    got_error = 1;	
    return;
  }
  
  return;
}

void mov(FILE *file) {
  fetch_first_operand(file);
  get_second_operand(file);
  if (  (current_command[8] == '0' && current_command[9] == '0')
      ||(current_command[8] == '1' && current_command[9] == '0')) {
    fprintf(error_file, "%d: wrong second operand...\n", line_num);
    got_error = 1;
  }
}

void cmp(FILE *file) {
  fetch_first_operand(file);
  get_second_operand(file);
  return;
}

void inc(FILE *file) {
  int i;	
  current_command[6]=current_command[7]='0';
  get_second_operand(file);
  last_first_operand = -1;
  for (i=0; i<12; i++)
    first_operand[i]='0';
  if (  (current_command[8] == '0' && current_command[9] == '0')
      ||(current_command[8] == '1' && current_command[9] == '0')) {
    fprintf(error_file, "%d: wrong second operand...\n", line_num);
    got_error = 1;
  }
}

void lea(FILE *file) {
  fetch_first_operand(file);
  get_second_operand(file);
  if (!(current_command[6] == '0' && current_command[7] == '1')) {
    fprintf(error_file, "%d: wrong first operand...\n", line_num);
    got_error = 1;
  }
  if ((current_command[8] == '0' && current_command[9] == '0')
      ||(current_command[8] == '1' && current_command[9] == '0')) {	
    fprintf(error_file, "%d: wrong second operand...\n", line_num);
    got_error = 1;
  }
}

void jmp(FILE *file) {
  int i;
  get_second_operand(file);
  last_first_operand = -1;
  current_command[6]=current_command[7]='0';

  for (i=0; i<12; i++) first_operand[i]='0';

  if (current_command[8] == '0' && current_command[9] == '0') {
    fprintf(error_file, "%d: wrong second operand...\n", line_num);
    got_error = 1;
  }
}

void prn(FILE *file) {
  int i;
  get_second_operand(file);
  last_first_operand = -1;
  current_command[6] = current_command[7] = '0';
  for (i=0; i<12; i++) first_operand[i]='0';
}

void jsr(FILE *file) {
  int i;	
  get_second_operand(file);
  last_first_operand = -1;
  current_command[6]=current_command[7]='0';
  for (i=0; i<12; i++) first_operand[i]='0';
  if (!(current_command[8] == '0' && current_command[9] == '0')) {	
    fprintf(error_file, "%d: wrong second operand\n", line_num);
    got_error = 1;
  }
}

int get_value_of_label(char *s, int operation_type) {
  extern List symbol_list, extern_list;
  int c = find(symbol_list, s);
  
  if (c == -1) {
    c = find(extern_list, s);
    if (c != -1) {
      if ((operation_type == 2) && (first_operand_exists))
        write_extern(s, operation_type, times_to_code);
      else 
        write_extern(s, 1, times_to_code);
      c = 1;
    }
    return c;
  }
  if (operation_type == 1) first_relocatable = 1;
  if (operation_type == 2) second_relocatable = 2;
  return c;
}
