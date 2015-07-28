assembler: main.o utils.o instructions.o commands.o lists.o
	gcc -g -Wall main.o utils.o instructions.o commands.o lists.o -o assembler
lists.o: lists.c
	gcc -g -c -Wall lists.c -o lists.o
instructions.o: instructions.c 
	gcc -g -c -Wall instructions.c -o instructions.o
commands.o: commands.c
	gcc -g -c -Wall commands.c -o commands.o
utils.o: utils.c
	gcc -g -c -Wall utils.c -o utils.o
main.o: main.c
	gcc -g -c -Wall main.c -o main.o

