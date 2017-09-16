//Added a 99ZZZZ at end of text file for exit
#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>
#include <math.h>


//prototypes
int chToI(char *, int, int);
int chToI2(char *);
void OP0(char *, short int **);
void OP1(char *, short int **);
void OP2(char *, short int **);
void OP3(char *, int *);
void OP4(char *, char *, int *, short int **);
void OP5(char *, char *, int *);
void OP6(char *, char *, int *, short int **);
void OP7(char *, char *, int *);
void OP8(char *, char *, int **, short int **);
void OP9(char *, char *, int **);
void OP10(char *, char *, int **, short int **);
void OP11(char *, char *, int **);
void OP12(char *, int *);
void OP13(char *, int **);
void OP14(char *, int **);
void OP15(char *, int **);
void OP16(char *);
void OP17(char *);
void OP18(char *);
void OP19(char *);
void OP20(char *);
void OP21(char *);
void OP22(char *);
void OP23(char *);
void OP24(char *);
void OP25(char *);
void OP26(char *);
void OP27(char *);
void OP28(char *);
void OP29(char *);
void OP30(char *);
void OP31(char *);
void OP32(char *);
void OP33(char *);
void OP34(char *);
void OP35(char *);
void OP99();

int main() {
  char IR[6]; //instruction register
  char memory[100][6];  //main memory
  char PSW[2];  //true false status
  short int PC = 0; //program counter
	int ACC = 0; //accumulator
  int R0, R1, R2, R3 = 0; //registers
  int *Rg[4] = {&R0, &R1, &R2, &R3};
  short int P0, P1, P2, P3 = 0; //pointers
  short int *Pt[4] = {&P0, &P1, &P2, &P3};
  char input_line[7]; //input from file
  FILE *fp; //file pointer
  fp = fopen ("Program2.txt","r");
  if (!fp) exit(1);
  char ch = 'a';
  int t = 0;
  while(1) {  //get opcodes from file
    while((ch = (char)fgetc(fp)) != EOF) {
      if(ch == '\n') break;
      input_line[t] = ch;
      t++;
    }
    t=0;
    for(int i = 0; i<6; i++) {
      memory[PC][i] = input_line[i];
    }
    if(ch == EOF) break;
    PC++;
  }
  fclose(fp);
  PC = 0;
  while(1) {  //computer loop
    for(int i = 0; i < 6; i++) {
      IR[i] = memory[PC][i];
    }

    int opcode = chToI(IR, 0, 1); //get opcode

    switch(opcode) {  //compute opcode
      case 0:
      OP0(IR, Pt);
      printf("%hi", P0);
      exit(1);
      PC++;
      break;

      case 1:
      OP1(IR, Pt);
      PC++;
      break;

      case 2:
      OP2(IR, Pt);
      PC++;
      break;

      case 3:
      OP3(IR, &ACC, Pt);
      PC++;
      break;

      case 4:
      OP4(IR, memory, &ACC, Pt);
      PC++;
      break;

      case 5:
      OP5(IR, memory, &ACC);
      PC++;
      break;

      case 6:
      OP6(IR, memory, &ACC, Pt);
      PC++;
      break;

      case 7:
      OP7(IR, memory, &ACC);
      PC++;
      break;

      case 8:
      OP8(IR, memory, Rg, Pt);
      PC++;
      break;

      case 9:
      OP9(IR, memory, Rg);
      PC++;
      break;

      case 10:
      OP10(IR, memory, Rg, Pt);
      PC++;
      break;

      case 11:
      OP11(IR, memory, Rg);
      PC++;
      break;

      case 12:
      OP12(IR, &R0);
      PC++;
      break;

      case 13:
      OP13(IR, Rg);
      PC++;
      break;

      case 14:
      OP14(IR, Rg);
      PC++;
      break;

      case 15:
      OP15(IR, Rg);
      PC++;
      break;

      case 16:
      OP16(IR);
      PC++;
      break;

      case 17:
      OP17(IR);
      PC++;
      break;

      case 18:
      OP18(IR);
      PC++;
      break;

      case 19:
      OP19(IR);
      PC++;
      break;

      case 20:
      OP20(IR);
      PC++;
      break;

      case 21:
      OP21(IR);
      PC++;
      break;

      case 22:
      OP22(IR);
      PC++;
      break;

      case 23:
      OP23(IR);
      PC++;
      break;

      case 24:
      OP24(IR);
      PC++;
      break;

      case 25:
      OP25(IR);
      PC++;
      break;

      case 26:
      OP26(IR);
      PC++;
      break;

      case 27:
      OP27(IR);
      PC++;
      break;

      case 28:
      OP28(IR);
      PC++;
      break;

      case 29:
      OP29(IR);
      PC++;
      break;

      case 30:
      OP30(IR);
      PC++;
      break;

      case 31:
      OP31(IR);
      PC++;
      break;

      case 32:
      OP32(IR);
      PC++;
      break;

      case 33:
      OP33(IR);
      PC++;
      break;

      case 34:
      OP34(IR);
      PC++;
      break;

      case 35:
      OP35(IR);
      PC++;
      break;

      case 99:
      OP99();
      PC++;
      break;

      default: printf("Unrecognized Opcode: %d\n", opcode); PC++; break; //decided to let the program continue running
    }
  }
}

//helper
int chToI(char * num, int start, int end) {
	short int finalVal = 0;
  finalVal += num[(end-start) - 1] - 48;
  for (int i = start; i <= end; i++) {
  	finalVal += (num[i] - 48) * (pow(10.0, (double) (end-start)));
  }
  return finalVal;
}
int parseOp1(char *IR) {
  return chToI(IR, 2, 3);
}
int parseOp2(char *IR) {
  return chToI(IR, 4, 5);
}
int parseOp1_2(char *IR) {
  return chToI(IR, 2, 5);
}
int parseOp1Reg(char *IR) {
  return chToI(IR, 3, 3);
}
int parseOp2Reg(char *IR) {
  return chToI(IR, 5, 5);
}
int fetch(char * memory, int m_loc) {
  char * temp = (char *) malloc (sizeof(char) * 6);
  for(int i = 0; i < 6; i++)
    temp[i] = memory[p][i];
  int n = chToI(temp);
  free(temp);
  return n;
}

void printIR(char *IR) {
  for(int i = 0; i < 6; i++)
    printf("%c", IR[i]);
}
//opcodes
void OP0(char * IR, short int **Pt) {
  printf("Opcode 00: Load Pointer Immediate\n");
  printIR(IR);
  *Pt[parseOp1Reg(IR)] = parseOp2(IR);
}
void OP1(char * IR, short int **Pt) {
  printf("Opcode 01: Load Pointer Immediate\n");
  printIR(IR);
  int n = chToI(IR, 3, 3);
  *Pt[n] += chToI(IR, 4, 5);
}
void OP2(char * IR, short int **Pt) {
  printf("Opcode 02: Load Pointer Immediate\n");
  printIR(IR);
  int n = chToI(IR, 3, 3);
  *Pt[n] -= chToI(IR, 4, 5);
}
void OP3(char * IR, int *ACC) {
  printf("Opcode 03: Load Pointer Immediate\n");
  printIR(IR);
  *ACC = chToI(IR, 2, 5);
}
void OP4(char * IR, char * memory, int *ACC, short int **Pt) {
  printf("Opcode 04: Load Pointer Immediate\n");
  printIR(IR);
  int m = *Pt[parseOp1Reg(IR)]
  &ACC = fetch(memory, m);
}
void OP5(char * IR, char * memory, int *ACC) {
  printf("Opcode 05: Load Pointer Immediate\n");
  printIR(IR);
  int m = parseOp1;
  &ACC = fetch(memory, m);
}
void OP6(char * IR, char * memory, int *ACC, short int **Pt) {
  printf("Opcode 06: Load Pointer Immediate\n");
  printIR(IR);
  char c = char*ACC;
  char *temp[6];
  for(int )
}
void OP7(char * IR, char * memory, int *ACC) {}
void OP8(char * IR, char * memory, int **Rg, short int **Pt) {}
void OP9(char * IR, char * memory, int **Rg) {}
void OP10(char * IR, char * memory, int **Rg, short int **Pt) {}
void OP11(char * IR, char * memory, int **Rg) {}
void OP12(char * IR, int *R0) {}
void OP13(char * IR, int **Rg) {}
void OP14(char * IR, int **Rg) {}
void OP15(char * IR, int **Rg) {}
void OP16(char * IR) {}
void OP17(char * IR) {}
void OP18(char * IR) {}
void OP19(char * IR) {}
void OP20(char * IR) {}
void OP21(char * IR) {}
void OP22(char * IR) {}
void OP23(char * IR) {}
void OP24(char * IR) {}
void OP25(char * IR) {}
void OP26(char * IR) {}
void OP27(char * IR) {}
void OP28(char * IR) {}
void OP29(char * IR) {}
void OP30(char * IR) {}
void OP31(char * IR) {}
void OP32(char * IR) {}
void OP33(char * IR) {}
void OP34(char * IR) {}
void OP35(char * IR) {}
void OP99() {
  exit(1);
}
