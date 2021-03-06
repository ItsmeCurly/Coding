//I do apologize if something is not correct in the includes, if I had a compiler I would know for certain, but cannot remember(I look them up as I code)
//requires -std=c99 -lm
#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>
#include <math.h>


//prototypes
int chToI(char *, int);
int chToI2(char *);
void OP0(char *);
void OP1(char *);
void OP2(char *);
void OP3(char *);
void OP4(char *);
void OP5(char *);
void OP6(char *);
void OP7(char *);
void OP8(char *);
void OP9(char *);
void OP10(char *);
void OP11(char *);
void OP12(char *);
void OP13(char *);
void OP14(char *);
void OP15(char *);
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
  short int P0, P1, P2, P3 = 0; //pointers
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
      OP0(IR);
      PC++;
      break;

      case 1:
      OP1(IR);
      PC++;
      break;

      case 2:
      OP2(IR);
      PC++;
      break;

      case 3:
      OP3(IR);
      PC++;
      break;

      case 4:
      OP4(IR);
      PC++;
      break;

      case 5:
      OP5(IR);
      PC++;
      break;

      case 6:
      OP6(IR);
      PC++;
      break;

      case 7:
      OP7(IR);
      PC++;
      break;

      case 8:
      OP8(IR);
      PC++;
      break;

      case 9:
      OP9(IR);
      PC++;
      break;

      case 10:
      OP10(IR);
      PC++;
      break;

      case 11:
      OP11(IR);
      PC++;
      break;

      case 12:
      OP12(IR);
      PC++;
      break;

      case 13:
      OP13(IR);
      PC++;
      break;

      case 14:
      OP14(IR);
      PC++;
      break;

      case 15:
      OP15(IR);
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
void OP0(char * IR) {}
void OP1(char * IR) {}
void OP2(char * IR) {}
void OP3(char * IR) {}
void OP4(char * IR) {}
void OP5(char * IR) {}
void OP6(char * IR) {}
void OP7(char * IR) {}
void OP8(char * IR) {}
void OP9(char * IR) {}
void OP10(char * IR) {}
void OP11(char * IR) {}
void OP12(char * IR) {}
void OP13(char * IR) {}
void OP14(char * IR) {}
void OP15(char * IR) {}
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
