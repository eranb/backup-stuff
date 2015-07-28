all: set22

set22: set.o main.o
	gcc set.o main.o -o main

set.o: set.c set.h
	gcc -c set.c

main.o:  main.c  set.h
	gcc -c main.c
