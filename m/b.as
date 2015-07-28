.extern L3
.entry LOOP
MAIN: mov2 r2, r4
	cmp1 #-4, $$
LOOP: jmp1 r1
	clr1 LOOP
	mov1 r5, L3
	mov1 LOOP, STR
STR: .string "afkjnd"
DAT: .data 6, 8, -9
