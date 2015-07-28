#include "utils.h"
#include "instructions.h"
#include "main.h"

int main(int argc, char *argv[]) {
  char * baseName;

  if (argc != 2) {
    printf("Usage: %s file-name\n",argv[0]);
    return 1;
  } else {
    baseName = argv[1];
  }

  init_files(baseName);
  run();
  cleanup(baseName);

  return 0;
}

void init_files(char * baseName) {
  temp_file   = init_file(with_extension(baseName,"temp"),"w+");
  ext_file    = init_file(with_extension(baseName, "ext"),"w+");
  ent_file    = init_file(with_extension(baseName, "ent"),"w+");
  error_file  = init_file(with_extension(baseName,"err"),"w+");
  data_file   = init_file(with_extension(baseName,"dat"),"w+");
  final_file  = init_file(with_extension(baseName,"ob"),"w+");
  as_file     = init_file(with_extension(baseName,"as"),"r");
}

int run() {
  eval(0);

  if (got_error == 0) {
    normalize_line_numbers();
    make_header(final_file, IC, DC);
    rewind(as_file);
    iteration = 2;
    IC = 100;

    eval(1);

    if (got_error == 0) make_final_file(IC, data_file, final_file);
  }

  if ( check_entry_list() == 2 ) {
    fprintf(error_file, "one of the entries instructions is not defined\n");
    got_error = 1;
  }

  if ( check_extern_list() == 2 ) {
    fprintf(error_file, "one of the externs instructions is not defined\n");
    got_error = 1;
  }

  cleanlists();

  return 1;
}

int eval(int time) {
  int ic, dc, tmp, i = 0;

  reset_str(line, MAXLINE);
  reset_str(word, MAXLINE);
  reset_str(tag, MAXLINE);
  reset_str(command, 5);
  reset_str(group, 3);

  while (!feof(as_file)) {                       
    reset_str(line, MAXLINE);
    fgets(line, MAXLINE, as_file);
    tmp = strlen(line) - 1;
    line[tmp] = '\0';
    line_num++;

    if (line[0] != '\0') {
      i = read_word(i);                   
      if (word[i] != ';') {
        if (word[i] == ':') {
          if (time == 0) {
            ic = IC;
            dc = DC;
            word[i] = '\0';
            reset_str(tag, MAXLINE);
            strcpy(tag, word);
            i = read_word(i);
          } else {
            word[i] = '\0'; 
            search_in_entry_list(word);     
            i = read_word(i);
          }
        }

        if (word[0] == '.') {
          if (time == 0) {
            add_symbol(tag, 1, dc);
            do_instructions(temp_file);
            reset_str(line, MAXLINE);
            my_index = i = 0;
          } else {
            do_instructions(final_file);
            my_index = i = 0;
          }
        } else {
          if (time == 0) {
            exec_cmd(temp_file, error_file, iteration);
            add_symbol(tag, 2, ic);
            reset_str(line, MAXLINE);
            my_index = i = 0;
          } else {
            exec_cmd(final_file,error_file,iteration);
            my_index = i = 0;
          }
        }
      }
    }
  }       
  return 0;
}

int read_word(int i) {       
  i=0;
  while (isspace(line[my_index])) my_index++;             
  for ( ; isgraph(line[my_index]); my_index++, i++)
    word[i]=line[my_index];
  word[i] = '\0';
  return i-1;
}

void cleanup(char * baseName) {
  remove(with_extension(baseName,"temp"));
  remove(with_extension(baseName,"dat"));

  if (got_error) {
    remove(with_extension(baseName,"ob"));
    remove(with_extension(baseName,"ent"));
    remove(with_extension(baseName,"ext"));
  } else {
    if (!check_entry_list()) remove(with_extension(baseName,"ent"));
    if (!check_extern_list()) remove(with_extension(baseName,"ext"));
    remove(with_extension(baseName,"err"));
  }
}

char * with_extension(char * baseName, char * ext) {
  char * tmp = (char *)malloc(30);
  if (!tmp) {
    printf("failed to allocate memory...");
    exit(1);
  }
  sprintf(tmp,"%s.%s",baseName,ext);
  return tmp;
}

FILE * init_file(char * fileName,char * mode) {
  FILE * file = fopen(fileName,mode);
  if (!file) {
    printf("Failed to open file %s",fileName);
    exit(1);
  }
  return file;
}


