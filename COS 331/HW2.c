//I do apologize if something is not correct in the includes, if I had a compiler I would know for certain, but cannot remember(I look them up as I code)
//requires -std=c99 -lm
#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>
#include <math.h>


//prototypes
int chToI(char *, int);
int chToI2(char *);
void OP0(char *, short int **);
void OP1(char *, short int **);
void OP2(char *, short int **);
void OP3(char *, int *, short int **);
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
  fp = fopen ("Program1.txt","r");
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
    char * op2 = (char *)malloc(2*sizeof(char));
    op2[0] = memory[PC][4];
    op2[1] = memory[PC][5];
    printf("%c%c", op2[0], op2[1]);
    exit(1);
    if(ch == EOF) break;
    PC++;
  }
  PC = 0;
  while(1) {  //computer loop
    for(int i = 0; i < 6; i++) {
      IR[i] = memory[PC][i];
    }

    int opcode = chToI2(IR); //get opcode

    switch(opcode) {  //compute opcode
      case 0:
      OP0(IR, Pt);
      PC++;
      break;

      case 1:
      //OP1(IR);
      PC++;
      break;

      case 2:
      //OP2(IR);
      PC++;
      break;

      case 3:
      //OP3(IR);
      PC++;
      break;

      case 4:
      //OP4(IR);
      PC++;
      break;

      case 5:
      //OP5(IR);
      PC++;
      break;

      case 6:
      //OP6(IR);
      PC++;
      break;

      case 7:
      //OP7(IR);
      PC++;
      break;

      case 8:
      //OP8(IR);
      PC++;
      break;

      case 9:
      //OP9(IR);
      PC++;
      break;

      case 10:
    //  OP10(IR);
      PC++;
      break;

      case 11:
      //OP11(IR);
      PC++;
      break;

      case 12:
      //OP12(IR);
      PC++;
      break;

      case 13:
      //OP13(IR);
      PC++;
      break;

      case 14:
      //OP14(IR);
      PC++;
      break;

      case 15:
      //OP15(IR);
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
  fclose(fp);
}

int chToI(char * num, int length) { //not sure if this returns the correct opcode
	short int finalVal = 0;
  finalVal += num[length - 1] - 48;
  for (int i = length - 1; i >= 0; i++) {
  	finalVal += (num[i - 1] - 48) * (pow(10.0, (double) i));
  }
  return finalVal;
}

int chToI2(char * num) {  //if first doesn't work use this algorithm for correct opcode
  int x = ((int)num[0] - 48) * 10;
  int y = ((int)num[1] - 48);
  return x+y;
}
//opcodes
void OP0(char * IR, short int **Pt) {
  char chn = IR[3];
  int n = (int)chn;
  char * op2 = (char *)malloc(2*sizeof(char));
  op2[0] = IR[4];
  op2[1] = IR[5];
  *Pt[n] = chToI2(op2);
}
void OP1(char * IR, short int **Pt) {}
void OP2(char * IR, short int **Pt) {}
void OP3(char * IR, int *ACC, short int **Pt) {}
void OP4(char * IR, char * memory, int *ACC, short int **Pt) {}
void OP5(char * IR, char * memory, int *ACC) {}
void OP6(char * IR, char * memory, int *ACC, short int **Pt) {}
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
