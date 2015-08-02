#include "utils.h"

struct {
  char *name;
  char *binary_val;
} cmd_type[]={
  {"mov", "0000"},
  {"cmp", "0001"},
  {"add", "0010"},
  {"sub", "0011"},
  {"not", "0100"},
  {"clr", "0101"},
  {"lea", "0110"},
  {"inc", "0111"},
  {"dec", "1000"},
  {"jmp", "1001"},
  {"bne", "1010"},
  {"red", "1011"},
  {"prn", "1100"},
  {"jsr", "1101"},
  {"rts", "1110"},
  {"stop", "1111"},
  {"not a command", NULL}
};

struct {
  char *name;
  char *binary_val;
} register_type[]={
  {"r0", "00000"},
  {"r1", "00001"},
  {"r2", "00010"},
  {"r3", "00011"},
  {"r4", "00100"},
  {"r5", "00101"},
  {"r6", "00110"},
  {"r7", "00111"},
  {"not a register", NULL}
};

void command_to_code(char *string, char *end) {
  int i=0;
  
  while (strcmp(cmd_type[i].name, "not a command")) {
    if (strcmp(cmd_type[i].name, string) == 0) {
      strcpy(end, cmd_type[i].binary_val);
      return;
    }
    i+=1;
  }
  
  for (i=0; i<strlen(end); i++) end[i]='\0';
  return;
}

int in_base(int num, int base, char *result) {
  char digits[10]={'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
  int tmp[60];
  char converted_number[60];
  int index=0, i=0;
  while (num != 0) {
    tmp[index] = num%base;
    num = num/base;
    index++;
  }
  index--;
  while (index>=0) {
    converted_number[i] = digits[tmp[index]];
    i++;
    index--;
  }
  converted_number[i] = '\0';
  return strcpy(result, converted_number);
}

void check_group(char *string, char *end) {
  switch(atoi(string)) {
    case 0: case 1:	case 10: case 11: case 110: 
      end[1] = '0';
      end[0] = '1';
      break;
    case 1110: case 1111:
      end[0] = '0';
      end[1] = '0';
      break;
    case 100: case 101: case 111: case 1000: case 1001: 
    case 1010: case 1011: case 1100: case 1101:
      end[0] = '0';
      end[1] = '1';
      break;
    default:
      end[0] = '\0';
      end[1] = '\0';
      break;
  }
}

void fetch_register(char *operand, char *s) {
  int i=0;
  while (strcmp(register_type[i].name, "not a register")) {
    if (strcmp(register_type[i].name, s) == 0) {
      strcpy(operand, register_type[i].binary_val);
      return;
    }
    i++;
  }
  for (i=0; i<strlen(operand); i++) operand[i]='\0';
}

void binary_to_base4(char *s, char *array) {
  int i=0, j, eindex, my_num;
  char temp[3];
  
  temp[2] = '\0';
  
  for (eindex=0; s[eindex]!='\0'; eindex++);
  eindex--;

  for (j=0; j<=eindex; j+=2, i++) {
    temp[0]=s[j];
    temp[1]=s[j+1];
    my_num=atoi(temp);
    switch (my_num) {
      case 0: 
        array[i]='0';
        break;
      case 1: 
        array[i]='1';
        break;
      case 10: 
        array[i]='2';
        break;
      case 11: 
        array[i]='3';
        break;
    }
  }
}

void reset_str(char *arr, int len) {
  int i;
  for (i=0; i<len; i++) arr[i] = '\0';
}

void two_complement(char *tmp) {
  int i = strlen(tmp) - 1;
  while (tmp[i] == '0') i--;
  i--;
  for ( ; i>=0; i--) {	
    if (tmp[i] == '1')
      tmp[i] = '0';
    else
      tmp[i] = '1';
  }
}

void make_it_12_digits(char *result) {
  int i = strlen(result), j=0;
  char tmp[12];
  reset_str(tmp, 12);
  for ( ; j <= 11-i; j++) tmp[j] = '0';
  strcat(tmp, result);
  strcpy(result, tmp);
}	

void make_header(FILE *file, int commands, int data) {
  char command_num[MAX_LINE_SIZE], data_num[MAX_LINE_SIZE];
  int num = commands - 100;
  
  in_base(num, 4, command_num);
  in_base(data, 4, data_num);
  fprintf(file, "%s	%s\n", command_num, data_num);
}

void make_file(int IC, FILE *data_file, FILE *final_file) {
  char data_line[MAX_LINE_SIZE], address[MAX_LINE_SIZE];

  reset_str(address, MAX_LINE_SIZE);
  reset_str(data_line, MAX_LINE_SIZE);
  rewind(data_file);

  while (!feof(data_file)) {
    reset_str(data_line, MAX_LINE_SIZE);		
    fgets(data_line, MAX_LINE_SIZE, data_file);
    in_base(IC, 4, address);
    if (data_line[0] != '\0') fprintf(final_file, "%s	%s", address, data_line);
    IC++;
  }
}