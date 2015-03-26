prog: p.o s.o
	gcc -g -Wall p.o s.o -o prog
p.o: p.c
	gcc -c -ansi -Wall p.c -o p.o
s.o: s.c c.h
	gcc -c -ansi -Wall s.c -o s.o
