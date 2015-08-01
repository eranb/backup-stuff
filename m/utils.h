#include <stdio.h>
#include <limits.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <math.h>

#define MAX_LINE_SIZE 70

char first_operand[13];
char second_operand[13];
char word[MAX_LINE_SIZE];
char label[MAX_LINE_SIZE];
char command[5];
char group[3];
char line[MAX_LINE_SIZE];

char previous_second_operand[MAX_LINE_SIZE];

int in_base(int num, int base, char *result);
void check_group(char *s, char *end);
void fetch_register(char *second_operand,char *r);
void binary_to_base4(char *s, char *array);
void reset_str(char *arr, int len);
void two_complement(char *tmp);
void make_it_12_digits(char *result);
void make_header(FILE *file, int commands, int data);
void make_final_file(int IC, FILE *data_file, FILE *final_file);
void convert_cmd_to_code(char *, char *);
