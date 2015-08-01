#include <stdio.h>
#include <limits.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <math.h>

#define MAXLINE 70

char first_operand[13];
char second_operand[13];
char word[MAXLINE];
char tag[MAXLINE];
char command[5];
char group[3];
char line[MAXLINE];
char last_first_operand[MAXLINE];
char previous_second_operand[MAXLINE];

int in_base(int num, int base, char *result);
void check_group(char *s, char *end);
void get_register(char *second_operand,char *r);
void convert_binary_string_to_base_4_string(char *s, char *array);
void reset_str(char *arr, int len);
void two_complement(char *tmp);
void make_it_12_digits(char *result);
void make_header(FILE *file, int commands, int data);
void make_final_file(int IC, FILE *data_file, FILE *final_file);
void convert_cmd_to_code(char *, char *);
