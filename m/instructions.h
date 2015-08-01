void do_instructions(FILE *file);
void do_data(FILE *file);
void do_string(FILE *file);
void do_entry(FILE *file);
void do_extern(FILE *file);
void print_extern(char *s, int num, int times);
void print_entry(char *s, int c);
void cleanlists();
void add_symbol(char *s, int inst_or_command, int value);
void search_in_entry_list(char *s);
int get_value_of_label(char *s, int a);
void normalize_line_numbers();
int check_entry_list();
int check_extern_list();
