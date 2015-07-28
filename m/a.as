.extern L3
.extern W
main: mov2 K, W
	add1 r2, STR
	mov1 $$, $$
LOOP:	jmp1 L3
	prn1 #-5
	sub1 $$, r4
STR:	.string "abcdef"
	inc1 r0
	mov2 $$, r3
	bne1 L3
END:	stop1
LENGTH:	.data 6, -9, 15
K:	.data 2

