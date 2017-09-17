//Added a 99ZZZZ at end of text file for exit
#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>
#include <math.h>
//EITHER IGNORE THE 99S IN THE MEMORY OR SET IT BACK TO 0 TO READ IT CORRECTLY ON FETCH LATER


//prototypes
int chToI(char *, int, int);
char *iToCh(int);
int parseOp1(char *);
int parseOp2(char *);
int parseOp1_2(char *);
int parseOp1Reg(char *);
int ParseOp2Reg(char *);
int fetch(char [][6], int m_loc);
void store(char [][6], int line, int num);
void printIR(char *);
void OP0(char *, short int **);
void OP1(char *, short int **);
void OP2(char *, short int **);
void OP3(char *, int *);
void OP4(char *, char [][6], int *, short int **);
void OP5(char *, char [][6], int *);
void OP6(char *, char [][6], int *, short int **);
void OP7(char *, char [][6], int *);
void OP8(char *, char [][6], int **, short int **);
void OP9(char *, char [][6], int **);
void OP10(char *, char [][6], int **, short int **);
void OP11(char *, char [][6], int **);
void OP12(char *, int *);
void OP13(char *, int **);
void OP14(char *, int *, int **);
void OP15(char *, int *, int **);
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
  char *opcodes[99];
  char memory[100][6];  //main memory
  char PSW[2] = {'F', 'F'};  //true false status
  short int PC = 0; //program counter
	int ACC = 0; //accumulator
  int R0 = 0, R1 = 0, R2 = 0, R3 = 0; //registers
  int * Rg[4] = {&R0, &R1, &R2, &R3};
  short int P0 = 0, P1 = 0, P2 = 0, P3 = 0; //pointers
  short int *Pt[4] = {&P0, &P1, &P2, &P3};
  char input_line[7]; //input from file

  FILE *fp; //file pointer
  fp = fopen ("Program2.txt","r");
  if (!fp) exit(1);
  char ch;
  int t = 0;

  for(int i = 0; i < 6; i++)
    IR[i] = '0';

  for(int i = 0; i < 100; i++)
    for(int j = 0; j < 6; j++)
      memory[i][j] = '0';

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
  *Pt[0] = 10;
  while(1) {  //computer loop
    for(int i = 0; i < 6; i++) {
      IR[i] = memory[PC][i];
    }
    int opcode = chToI(IR, 0, 1); //get opcode
    switch(opcode) {  //compute opcode
      case 0:
      OP0(IR, Pt);
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
      OP3(IR, &ACC);
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
      OP14(IR, &ACC, Rg);
      PC++;
      break;

      case 15:
      OP15(IR, &ACC, Rg);
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
    printf("\n");
  }
}

//helper
int chToI(char * num, int start, int end) {
	int finalVal = 0;
  for (int i = end; i >= start; i--) {
  	finalVal += ((int)num[i] - 48) * (pow(10.0, (double) (end - i)));
  }
  return finalVal;
}

char * iToCh(int num) {
  static char temp[6];
  for(int i = 0; i < 6; i++)
    temp[i] = '0';
  int m = num;
  for (int i = 5; m>0 && i>=0; i--, m/=10) {
    temp[i] = (char)(((int)'0') + m%10);
  }
  return temp;
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
  return (int)IR[3] - 48;
}
int parseOp2Reg(char *IR) {
  return (int)IR[5] - 48;
}
int fetch(char memory[][6], int m_loc) {
  char temp[6];
  printf("Fetch at line: %d\n", m_loc);
  for(int i = 0; i < 6; i++)
    temp[i] = memory[m_loc][i];
  int n = chToI(temp, 0, 3);  //can change
  return n;
}
void store(char memory[100][6], int m_loc, int num) {
  char * temp = iToCh(num);
  printf("Store to line: %d\n", m_loc);
  for(int i = 0; i < 6; i++)
    memory[m_loc][i] = temp[i];
  for(int i = 0; i < 2; i++)
    memory[m_loc][i] = '9';
}

void printIR(char *IR) {
  printf("IR contains: ");
  for(int i = 0; i < 6; i++)
    printf("%c", IR[i]);
  printf("\n");
}

//opcodes
void OP0(char * IR, short int **Pt) {
  if(IR[2] != 'P') return;
  printf("Opcode 00: Load Pointer Immediate\n");
  printIR(IR);
  *Pt[parseOp1Reg(IR)] = parseOp2(IR);
}
void OP1(char * IR, short int **Pt) {
  printf("Opcode 01: Add to Pointer Immediate\n");
  printIR(IR);
  *Pt[parseOp1Reg(IR)] += parseOp2(IR);
}
void OP2(char * IR, short int **Pt) {
  printf("Opcode 02: Subtract From Pointer Immediate\n");
  printIR(IR);
  *Pt[parseOp1Reg(IR)] += parseOp2(IR);
}
void OP3(char * IR, int *ACC) {
  printf("Opcode 03: Load Accumulator Immediate\n");
  printIR(IR);
  *ACC = parseOp1_2(IR);
}
void OP4(char * IR, char memory[][6], int *ACC, short int **Pt) {
  printf("Opcode 04: Load Accumulator Register Addressing\n");
  printIR(IR);
  int m_l = *Pt[parseOp1Reg(IR)];
  *ACC = fetch(memory, m_l);
}
void OP5(char * IR, char memory[][6], int *ACC) {
  printf("Opcode 05: Load Accumulator Direct Addressing\n");
  printIR(IR);
  int m_l = parseOp1(IR);
  *ACC = fetch(memory, m_l);
}
void OP6(char * IR, char memory[][6], int *ACC, short int **Pt) {
  printf("Opcode 06: Store Accumulator Register Addressing\n");
  printIR(IR);
  int m_l = *Pt[parseOp1Reg(IR)];
  store(memory, m_l, *ACC);
}
void OP7(char * IR, char memory[][6], int *ACC) {
  printf("Opcode 07: Store Accumulator Direct Addressing\n");
  printIR(IR);
  int m_l = parseOp1(IR);
  store(memory, m_l, *ACC);
}
void OP8(char * IR, char memory[][6], int **Rg, short int **Pt) {
  printf("Opcode 08: Store Register to Memory: Register Addressing\n");
  printIR(IR);
  int r = *Rg[parseOp1Reg(IR)];
  int m_l = *Pt[parseOp2Reg(IR)];
  store(memory, m_l, r);
}
void OP9(char * IR, char memory[][6], int **Rg) {
  printf("Opcode 09: Store Register to Memory: Direct Addressing\n");
  printIR(IR);
  int r = *Rg[parseOp1Reg(IR)];
  int m_l = parseOp2(IR);
  store(memory, m_l, r);
}
void OP10(char * IR, char memory[][6], int **Rg, short int **Pt) {
  printf("Opcode 10: Load Register from memory: Register Addressing\n");
  printIR(IR);
  int * rPt = Rg[parseOp1Reg(IR)];
  int m_l = *Pt[parseOp2Reg(IR)];
  *rPt = fetch(memory, m_l);
}
void OP11(char * IR, char memory[][6], int **Rg) {
  printf("Opcode 11: Load register from memory: Direct Addressing\n");
  printIR(IR);
  int * rPt = Rg[parseOp1Reg(IR)];
  int m_l = parseOp2(IR);
  *rPt = fetch(memory, m_l);
}
void OP12(char * IR, int *R0) {
  printf("Opcode 12: Load Register R0 Immediate\n");
  printIR(IR);
  *R0 = parseOp1_2(IR);
}
void OP13(char * IR, int **Rg) {
  printf("Opcode 13: Register to Register Transfer\n");
  printIR(IR);
  int *r1Pt = Rg[parseOp1Reg(IR)];
  int *r2Pt = Rg[parseOp2Reg(IR)];
  *r1Pt = *r2Pt;
}
void OP14(char * IR, int *ACC, int **Rg) {
  printf("Opcode 14: Load Accumulator from Register\n");
  printIR(IR);
  int * rPt = Rg[parseOp1Reg(IR)];
  *ACC = *rPt;
  //printf("%d\n", *ACC);
}
void OP15(char * IR, int *ACC, int **Rg) {
  printf("Opcode 15: Load Register from Accumulator\n");
  printIR(IR);
  int * rPt = Rg[parseOp1Reg(IR)];
  *rPt = *ACC;
}
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
