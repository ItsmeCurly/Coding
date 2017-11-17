//-lm

#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>
#include <math.h>

#define null NULL;

//prototypes

int chToI(char *, int, int);
char *iToCh(int);
int parseOp1(char *);
int parseOp2(char *);
int parseOp1_2(char *);
int parseOp1Reg(char *);
int parseOp2Reg(char *);
int fetch(char [][6], int);
void store(char [][6], int line, int);
void printMemory(char [][6]);
void printRegisters(int **);
void printPointers(short int **);
void printAccumulator(int);
void printPSW(char *);
void printIR(char *);
void printfError(char);

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
void OP16(char *, int *);
void OP17(char *, int *);
void OP18(char *, int *, int **);
void OP19(char *, int *, int **);
void OP20(char *, char [][6], int *, short int **);
void OP21(char *, char [][6], int *);
void OP22(char *, char [][6], int *, short int **);
void OP23(char *, char [][6], int *);
void OP24(char *, char [][6], char *, int *, short int **);
void OP25(char *, char [][6], char *, int *, short int **);
void OP26(char *, char [][6], char *, int *, short int **);
void OP27(char *, char *, int *);
void OP28(char *, char *, int *);
void OP29(char *, char *, int *);
void OP30(char *, char *, int *, int **);
void OP31(char *, char *, int *, int **);
void OP32(char *, char *, int *, int **);
void OP33(char *, char *, int *);
void OP34(char *, char *, int *);
void OP35(char *, int *);
void OP99(bool *);

//structs
struct PCB {
  struct PCB *Next_PCB, *Last_PCB;
  int PID;
  int ACC;
  int R0, R1, R2, R3;
  short int P0, P1, P2, P3;
  char PSW[2];
  int BAR, EAR, LR;
  int IC; //timeslice
};

//global variables
int DEFAULTIC = 5;

//main function
int main(int argc, char * argv[]) {
  struct PCB *ptr, *tmp; // ptr is the head, tmp is the tail
  if(argc == 1) {
    printf("No programs called\n");
    exit(1);
  }
  else if(argc > 11) {
    printf("Too many programs\n");
    exit(1);
  }

  ptr = (struct PCB *) malloc(sizeof(struct PCB));
  ptr -> Next_PCB = NULL;

  ptr -> PID = 0;
  ptr -> BAR = 0;
  ptr -> LR = 99;
  ptr -> EAR = ptr -> BAR;

  ptr -> ACC = 0;

  ptr -> R0 = 0;
  ptr -> R1 = 0;
  ptr -> R2 = 0;
  ptr -> R3 = 0;

  ptr -> P0 = 0;
  ptr -> P1 = 0;
  ptr -> P2 = 0;
  ptr -> P3 = 0;

  ptr -> PSW[0] = 'F';
  ptr -> PSW[1] = 'F';

  ptr -> IC = DEFAULTIC;

  tmp = ptr;
  for(int k = 1; k < argc - 1; k++) {
    tmp -> Next_PCB = (struct PCB *) malloc(sizeof(struct PCB));
    tmp -> Next_PCB -> Next_PCB = NULL;
    tmp -> Next_PCB -> Last_PCB = tmp;
    tmp -> Next_PCB -> PID = k;
    tmp -> Next_PCB -> BAR = 0 + k * 100;
    tmp -> Next_PCB -> LR = 99 + k * 100;
    tmp -> Next_PCB -> EAR = 0;

    tmp -> Next_PCB -> ACC = 0;

    tmp -> Next_PCB -> R0 = 0;
    tmp -> Next_PCB -> R1 = 0;
    tmp -> Next_PCB -> R2 = 0;
    tmp -> Next_PCB -> R3 = 0;

    tmp -> Next_PCB -> P0 = 0;
    tmp -> Next_PCB -> P1 = 0;
    tmp -> Next_PCB -> P2 = 0;
    tmp -> Next_PCB -> P3 = 0;

    tmp -> Next_PCB -> PSW[0] = 'F';
    tmp -> Next_PCB -> PSW[1] = 'F';

    tmp -> Next_PCB -> IC = DEFAULTIC;
    tmp = tmp -> Next_PCB;
  }

  //initialize and declare

  char IR[6]; //instruction register

  char memory[1000][6];  //main memory
  char PSW[2] = {'F', 'F'};  //true false status
  int ACC = 0; //accumulator
  int R0 = 0, R1 = 0, R2 = 0, R3 = 0; //registers
  int * Rg[4] = {&R0, &R1, &R2, &R3};
  short int P0 = 0, P1 = 0, P2 = 0, P3 = 0; //pointers
  short int *Pt[4] = {&P0, &P1, &P2, &P3};
  char input_line[6]; //input from file

  //for here
  char ch;
  int t = 0;
  int program_line = 0;


  //reset all variables for certain process within argv
  for(int i = 0; i < 1000; i++) {
    int j = 0;
    for(;j<2;j++)
      memory[i][j] = '9';
    for(;j<6;j++)
      memory[i][j] = 'Z';
  } //instantiate memory to '99ZZZZ'
  printf("Loading Processes\n");
  //get opcodes from file
  for(int l = 1; l < argc; l++) {
    FILE *fp;
    fp = fopen(argv[l], "r");
    if(!fp) {
      printfError('f');
      continue;
    }
    program_line = 0;

    while(1) {
      if(program_line > 99) {
        printfError('s'); //segmentation fault - memory too far
        printf("1\n");
        continue;
      }

      int j = 0;

      for(;j<2;j++)
        input_line[j] = '9';
      for(;j<6;j++)
        input_line[j] = 'Z';

      while((ch = (char)fgetc(fp)) != EOF) {
        if(ch == '\n' || t > 6) {
          if(t > 6) printfError('s');
          break;
        }
        input_line[t] = ch;
        t++;
      }
      t=0;
      for(int k = 0; k<6; k++) {
        memory[program_line + (l-1) * 100][k] = input_line[k];
      }
      if(ch == EOF) break;
      program_line++;
    }
    fclose(fp);
  }


  struct PCB * currentPCB = ptr;

  int EA;
  bool leave = false;

  int PC = 0; //program counter
  int IC = 0;
  while(1) { //OS loop

    //GET NEXTPCB VARS
    PC = currentPCB -> EAR;
    //printf("%d\n", PC);
    IC = 0;

    ACC = currentPCB -> ACC;

    R0 = currentPCB -> R0;
    R1 = currentPCB -> R1;
    R2 = currentPCB -> R2;
    R3 = currentPCB -> R3;

    P0 = currentPCB -> P0;
    P1 = currentPCB -> P1;
    P2 = currentPCB -> P2;
    P3 = currentPCB -> P3;

    PSW[0] = currentPCB -> PSW[0];
    PSW[1] = currentPCB -> PSW[1];
    //END GET NEXTPCB VARS

    printf("Current Process PID: %d\n\n", currentPCB -> PID);

    while(IC < currentPCB -> IC) {
      EA = currentPCB -> BAR + PC;

      // printf("%d\n", PC);
      // printf("%d\n", IC);
      // printf("%d\n", currentPCB -> IC);
      for(int i = 0; i < 6; i++)
        IR[i] = memory[EA][i];

      leave = false; //to break while without exit(1)
      int opcode = chToI(IR, 0, 1); //get opcode
      switch(opcode) {  //compute opcode
        case 0: OP0(IR, Pt); PC++; IC++; break;

        case 1: OP1(IR, Pt); PC++; IC++; break;

        case 2: OP2(IR, Pt); PC++; IC++; break;

        case 3: OP3(IR, &ACC); PC++; IC++; break;

        case 4: OP4(IR, memory, &ACC, Pt); PC++; IC++; break;

        case 5: OP5(IR, memory, &ACC); PC++; IC++; break;

        case 6: OP6(IR, memory, &ACC, Pt); PC++; IC++; break;

        case 7: OP7(IR, memory, &ACC); PC++; IC++; break;

        case 8: OP8(IR, memory, Rg, Pt); PC++; IC++; break;

        case 9: OP9(IR, memory, Rg); PC++; IC++; break;

        case 10: OP10(IR, memory, Rg, Pt); PC++; IC++; break;

        case 11: OP11(IR, memory, Rg); PC++; IC++; break;

        case 12: OP12(IR, &R0); PC++; IC++; break;

        case 13: OP13(IR, Rg); PC++; IC++; break;

        case 14: OP14(IR, &ACC, Rg); PC++; IC++; break;

        case 15: OP15(IR, &ACC, Rg); PC++; IC++; break;

        case 16: OP16(IR, &ACC); PC++; IC++; break;

        case 17: OP17(IR, &ACC); PC++; IC++; break;

        case 18: OP18(IR, &ACC, Rg); PC++; IC++; break;

        case 19: OP19(IR, &ACC, Rg); PC++; IC++; break;

        case 20: OP20(IR, memory, &ACC, Pt); PC++; IC++; break;

        case 21: OP21(IR, memory, &ACC); PC++; IC++; break;

        case 22: OP22(IR, memory, &ACC, Pt); PC++; IC++; break;

        case 23: OP23(IR, memory, &ACC); PC++; IC++; break;

        case 24: OP24(IR, memory, PSW, &ACC, Pt); PC++; IC++; break;

        case 25: OP25(IR, memory, PSW, &ACC, Pt); PC++; IC++; break;

        case 26: OP26(IR, memory, PSW, &ACC, Pt); PC++; IC++; break;

        case 27: OP27(IR, PSW, &ACC); PC++; IC++; break;

        case 28: OP28(IR, PSW, &ACC); PC++; IC++; break;

        case 29: OP29(IR, PSW, &ACC); PC++; IC++; break;

        case 30: OP30(IR, PSW, &ACC, Rg); PC++; IC++; break;

        case 31: OP31(IR, PSW, &ACC, Rg); PC++; IC++; break;

        case 32: OP32(IR, PSW, &ACC, Rg); PC++; IC++; break;

        case 33: OP33(IR, PSW, &PC); PC++; IC++; break;

        case 34: OP34(IR, PSW, &PC); PC++; IC++; break;

        case 35: OP35(IR, &PC); PC++; IC++; break;

        case 99: OP99(&leave); PC++; IC++; break;

        default: printf("Unrecognized Opcode: %d\n", opcode); PC++; IC++; break; //decided to let the program continue running
      }
      printf("\n");
      if(leave) {
        printf("Terminating process PID: %d\n\n", currentPCB -> PID);
        if(currentPCB -> Last_PCB != NULL)
          currentPCB -> Last_PCB -> Next_PCB = currentPCB -> Next_PCB;
        else //currentPCB is ptr
          ptr = currentPCB -> Next_PCB;
        if(currentPCB -> Next_PCB != NULL)
          currentPCB -> Next_PCB -> Last_PCB = currentPCB -> Last_PCB;

        break;
      }
    }

    //program is finished, context switch
    //STORE IF NOT TERMINATED
    if(!leave) {
      printf("Switching processes\n\n");

      currentPCB -> ACC = ACC;

      currentPCB -> R0 = R0;
      currentPCB -> R1 = R1;
      currentPCB -> R2 = R2;
      currentPCB -> R3 = R3;

      currentPCB -> P0 = P0;
      currentPCB -> P1 = P1;
      currentPCB -> P2 = P2;
      currentPCB -> P3 = P3;

      currentPCB -> EAR = PC;

      currentPCB -> PSW[0] = PSW[0];
      currentPCB -> PSW[1] = PSW[1];
    }
    //END STORE


    if(currentPCB -> Next_PCB != NULL) currentPCB = currentPCB -> Next_PCB;
    else if(ptr != NULL) currentPCB = ptr;
    else break;
    //end context switch
  }
  //printMemory(memory);
  //printRegisters(Rg);
  //printAccumulator(ACC);
  exit(1);
}

//error method
void printfError(char error) {
  switch(error) {
    case 's': printf("Seg fault(core dumped)\n"); break;
    case 'n': printf("Null pointer exception\n"); break;
    case 'f': printf("F not found exception\n"); break;
    case 'o': printf("Incorrect operand supplied to opcode\n"); break;
    default: printf("Unknown error occurred\n"); break;
  }
  //printf("Exiting...\n");
  //exit(1);
  //can exit here or not
  printf("\n");
}
//helper methods
int chToI(char * num, int start, int end) { //helper converts char to int
	int finalVal = 0;
  for (int i = end; i >= start; i--)
  	finalVal += ((int)num[i] - 48) * (pow(10.0, (double) (end - i)));
  return finalVal;
}

char * iToCh(int num) { //helper converts int to char [] ie 99 = ['0', '0', '0', '0', '9', '9']
  static char temp[6];
  for(int i = 0; i < 6; i++)
    temp[i] = '0';
  int m = num;
  for (int i = 5; m>0 && i>=0; i--, m/=10)
    temp[i] = (char)(((int)'0') + m%10);
  return temp;
}
int parseOp1(char *IR) {  //parse op1 for int
  return chToI(IR, 2, 3);
}
int parseOp2(char *IR) {  //parse op2 for int
  return chToI(IR, 4, 5);
}
int parseOp1_2(char *IR) {  //parse op1 and op2 for int
  return chToI(IR, 2, 5);
}
int parseOp1Reg(char *IR) { //parse op1 for reg number
  return (int)IR[3] - 48;
}
int parseOp2Reg(char *IR) { //parse op2 for reg number
  return (int)IR[5] - 48;
}
int fetch(char memory[][6], int m_loc) {  //fetch something from memory
  char temp[6];
  printf("Fetch at line: %d\n", m_loc);
  for(int i = 0; i < 6; i++)
    temp[i] = memory[m_loc][i];
  int n = chToI(temp, 2, 5);
  return n;
}
void store(char memory[100][6], int m_loc, int num) { //store something in memory
  char * temp = iToCh(num);
  printf("Store to line %d: %d\n", m_loc, num);
  for(int i = 0; i < 6; i++)
    memory[m_loc][i] = temp[i];
  for(int i = 0; i < 2; i++)
    memory[m_loc][i] = '9';
}
void printMemory(char memory[][6]) {
  for(int i = 0; i < 1000; i++) {
    printf("%d ", i);
    for(int j = 0; j < 6; j++)
      printf("%c", memory[i][j]);
    printf("\n");
  }
  printf("\n");
}
void printRegisters(int ** Rg) {
  for(int i = 0; i < 4; i++) {
    printf("%d ", *Rg[i]);
  }
  printf("\n");
}
void printPointers(short int ** Pt) {
  for(int i = 0; i < 4; i++) {
    printf("%hi ", *Pt[i]);
  }
  printf("\n");
}
void printAccumulator(int ACC) {
  printf("%d ", ACC);
  printf("\n");
}
void printPSW(char * PSW) {
  for(int i = 0; i < 2; i ++)
    printf("%c ", PSW[i]);
  printf("\n");
}
void printIR(char *IR) {  //print the IR
  printf("IR contains: ");
  for(int i = 0; i < 6; i++)
    printf("%c", IR[i]);
  printf("\n");
}
//end helper methods

//opcodes
//every opcode implements a certain error checking method to check if the operands are correct before executing the opcode
void OP0(char * IR, short int **Pt) {
  printf("Opcode 00: Load Pointer Immediate\n");
  printIR(IR);

  if(IR[2] != 'P' || !((int)IR[3] - 48 >= 0 && (int)IR[3] - 48 <= 3)) {
    printfError('o');
    return;
  }
  if(!(chToI(IR, 4, 5) >= 0 && chToI(IR, 4, 5) <= 99)) {
    printfError('o');
    return;
  }
  *Pt[parseOp1Reg(IR)] = parseOp2(IR);
}
void OP1(char * IR, short int **Pt) {
  printf("Opcode 01: Add to Pointer Immediate\n");
  printIR(IR);

  if(IR[2] != 'P' || !((int)IR[3] - 48 >= 0 && (int)IR[3] - 48 <= 3)) {
    printfError('o');
    return;
  }
  if(!(chToI(IR, 4, 5) >= 0 && chToI(IR, 4, 5) <= 99)) {
    printfError('o');
    return;
  }
  *Pt[parseOp1Reg(IR)] += parseOp2(IR);
}
void OP2(char * IR, short int **Pt) {
  printf("Opcode 02: Subtract From Pointer Immediate\n");
  printIR(IR);
  if(IR[2] != 'P' || !((int)IR[3] - 48 >= 0 && (int)IR[3] - 48 <= 3)) {
    printfError('o');
    return;
  }
  if(!(chToI(IR, 4, 5) >= 0 && chToI(IR, 4, 5) <= 99)) {
    printfError('o');
    return;
  }
  *Pt[parseOp1Reg(IR)] += parseOp2(IR);
}
void OP3(char * IR, int *ACC) {
  printf("Opcode 03: Load Accumulator Immediate\n");
  printIR(IR);

  if(!(chToI(IR, 2, 5) >= 0 && chToI(IR, 2, 5) <= 9999)) {
    printfError('o');
    return;
  }
  *ACC = parseOp1_2(IR);
}
void OP4(char * IR, char memory[][6], int *ACC, short int **Pt) {
  printf("Opcode 04: Load Accumulator Register Addressing\n");
  printIR(IR);

  if(IR[2] != 'P' || !((int)IR[3] - 48 >= 0 && (int)IR[3] - 48 <= 3)) {
    printfError('o');
    return;
  }
  int m_l = *Pt[parseOp1Reg(IR)];
  *ACC = fetch(memory, m_l);
}
void OP5(char * IR, char memory[][6], int *ACC) {
  printf("Opcode 05: Load Accumulator Direct Addressing\n");
  printIR(IR);
  if(!(chToI(IR, 2, 3) >= 0 && chToI(IR, 2, 3) <= 99)) {
    printfError('o');
    return;
  }
  int m_l = parseOp1(IR);
  *ACC = fetch(memory, m_l);
}
void OP6(char * IR, char memory[][6], int *ACC, short int **Pt) {
  printf("Opcode 06: Store Accumulator Register Addressing\n");
  printIR(IR);

  if(IR[2] != 'P' || !((int)IR[3] - 48 >= 0 && (int)IR[3] - 48 <= 3)) {
    printfError('o');
    return;
  }
  int m_l = *Pt[parseOp1Reg(IR)];
  store(memory, m_l, *ACC);
}
void OP7(char * IR, char memory[][6], int *ACC) {
  printf("Opcode 07: Store Accumulator Direct Addressing\n");
  printIR(IR);

  if(!(chToI(IR, 2, 3) >= 0 && chToI(IR, 2, 3) <= 99)) {
    printfError('o');
    return;
  }
  int m_l = parseOp1(IR);
  store(memory, m_l, *ACC);
}
void OP8(char * IR, char memory[][6], int **Rg, short int **Pt) {
  printf("Opcode 08: Store Register to Memory: Register Addressing\n");
  printIR(IR);

  if(IR[2] != 'R' || !((int)IR[3] - 48 >= 0 && (int)IR[3] - 48 <= 3)) {
    printfError('o');
    return;
  }
  if(IR[4] != 'P' || !((int)IR[5] - 48 >= 0 && (int)IR[5] - 48 <= 3)) {
    printfError('o');
    return;
  }
  int r = *Rg[parseOp1Reg(IR)];
  int m_l = *Pt[parseOp2Reg(IR)];
  store(memory, m_l, r);
}
void OP9(char * IR, char memory[][6], int **Rg) {
  printf("Opcode 09: Store Register to Memory: Direct Addressing\n");
  printIR(IR);
  if(IR[2] != 'R' || !((int)IR[3] - 48 >= 0 && (int)IR[3] - 48 <= 3)) {
    printfError('o');
    return;
  }
  int r = *Rg[parseOp1Reg(IR)];
  int m_l = parseOp2(IR);
  store(memory, m_l, r);
}
void OP10(char * IR, char memory[][6], int **Rg, short int **Pt) {
  printf("Opcode 10: Load Register from memory: Register Addressing\n");
  printIR(IR);

  if(IR[2] != 'R' || !((int)IR[3] - 48 >= 0 && (int)IR[3] - 48 <= 3)) {
    printfError('o');
    return;
  }
  if(IR[4] != 'P' || !((int)IR[5] - 48 >= 0 && (int)IR[5] - 48 <= 3)) {
    printfError('o');
    return;
  }
  int * rPt = Rg[parseOp1Reg(IR)];
  int m_l = *Pt[parseOp2Reg(IR)];
  *rPt = fetch(memory, m_l);
}
void OP11(char * IR, char memory[][6], int **Rg) {
  printf("Opcode 11: Load register from memory: Direct Addressing\n");
  printIR(IR);

  if(IR[2] != 'R' || !((int)IR[3] - 48 >= 0 && (int)IR[3] - 48 <= 3)) {
    printfError('o');
    return;
  }
  if(!(chToI(IR, 4, 5) >= 0 && chToI(IR, 4, 5) <= 99)) {
    printfError('o');
    return;
  }
  int * rPt = Rg[parseOp1Reg(IR)];
  int m_l = parseOp2(IR);
  *rPt = fetch(memory, m_l);
}
void OP12(char * IR, int *R0) {
  printf("Opcode 12: Load Register R0 Immediate\n");
  printIR(IR);

  if(!(chToI(IR, 2, 5) >= 0 && chToI(IR, 2, 5) <= 9999)) {
    printfError('o');
    return;
  }
  *R0 = parseOp1_2(IR);
}
void OP13(char * IR, int **Rg) {
  printf("Opcode 13: Register to Register Transfer\n");
  printIR(IR);

  if(IR[2] != 'R' || !((int)IR[3] - 48 >= 0 && (int)IR[3] - 48 <= 3)) {
    printfError('o');
    return;
  }
  if(IR[4] != 'R' || !((int)IR[5] - 48 >= 0 && (int)IR[5] - 48 <= 3)) {
    printfError('o');
    return;
  }
  int *r1Pt = Rg[parseOp1Reg(IR)];
  int *r2Pt = Rg[parseOp2Reg(IR)];
  *r1Pt = *r2Pt;
}
void OP14(char * IR, int *ACC, int **Rg) {
  printf("Opcode 14: Load Accumulator from Register\n");
  printIR(IR);

  if(IR[2] != 'R' || !((int)IR[3] - 48 >= 0 && (int)IR[3] - 48 <= 3)) {
    printfError('o');
    return;
  }
  int * rPt = Rg[parseOp1Reg(IR)];
  *ACC = *rPt;
}
void OP15(char * IR, int *ACC, int **Rg) {
  printf("Opcode 15: Load Register from Accumulator\n");
  printIR(IR);

  if(IR[2] != 'R' || !((int)IR[3] - 48 >= 0 && (int)IR[3] - 48 <= 3)) {
    printfError('o');
    return;
  }
  int * rPt = Rg[parseOp1Reg(IR)];
  *rPt = *ACC;
}
void OP16(char * IR, int *ACC) {
  printf("Opcode 16: Add Accumulator Immediate\n");
  printIR(IR);

  if(!(chToI(IR, 2, 5) >= 0 && chToI(IR, 2, 5) <= 9999)) {
    printfError('o');
    return;
  }
  *ACC += chToI(IR, 2, 5);
}
void OP17(char * IR, int *ACC) {
  printf("Opcode 17: Subtract Accumulator Immediate\n");
  printIR(IR);

  if(!(chToI(IR, 2, 5) >= 0 && chToI(IR, 2, 5) <= 9999)) {
    printfError('o');
    return;
  }
  *ACC -= chToI(IR, 2, 5);
}
void OP18(char * IR, int *ACC, int **Rg) {
  printf("Opcode 18: Add contents of Register to Accumulator\n");
  printIR(IR);

  if(IR[2] != 'R' || !((int)IR[3] - 48 >= 0 && (int)IR[3] - 48 <= 3)) {
    printfError('o');
    return;
  }
  int * rPt = Rg[parseOp1Reg(IR)];
  *ACC += *rPt;
}
void OP19(char * IR, int *ACC, int **Rg) {
  printf("Opcode 19: Subtract contents of Register to Accumulator\n");
  printIR(IR);

  if(IR[2] != 'R' || !((int)IR[3] - 48 >= 0 && (int)IR[3] - 48 <= 3)) {
    printfError('o');
    return;
  }
  int * rPt = Rg[parseOp1Reg(IR)];
  *ACC -= *rPt;
}
void OP20(char * IR, char memory [][6], int *ACC, short int **Pt) {
  printf("Opcode 20: Add Accumulator Register Addressing\n");
  printIR(IR);

  if(IR[2] != 'P' || !((int)IR[3] - 48 >= 0 && (int)IR[3] - 48 <= 3)) {
    printfError('o');
    return;
  }
  int m_l = *Pt[parseOp1Reg(IR)];
  *ACC += fetch(memory, m_l);
}
void OP21(char * IR, char memory[][6], int *ACC) {
  printf("Opcode 21: Add Accumulator Direct Addressing\n");
  printIR(IR);

  if(!(chToI(IR, 2, 3) >= 0 && chToI(IR, 2, 3) <= 99)) {
    printfError('o');
    return;
  }
  int m_l = parseOp1(IR);
  *ACC += fetch(memory, m_l);
}
void OP22(char * IR, char memory[][6], int *ACC, short int **Pt) {
  printf("Opcode 23: Subtract Accumulator Direct Addressing\n");
  printIR(IR);
  if(IR[2] != 'P' || !((int)IR[3] - 48 >= 0 && (int)IR[3] - 48 <= 3)) {
    printfError('o');
    return;
  }
  int m_l = *Pt[parseOp1Reg(IR)];
  *ACC -= fetch(memory, m_l);
}
void OP23(char * IR, char memory[][6], int *ACC) {
  printf("Opcode 23: Subtract Accumulator Direct Addressing\n");
  printIR(IR);

  if(!(chToI(IR, 2, 3) >= 0 && chToI(IR, 2, 3) <= 99)) {
    printfError('o');
    return;
  }
  int m_l = parseOp1(IR);
  *ACC -= fetch(memory, m_l);
}
void OP24(char * IR, char memory[][6], char * PSW, int *ACC, short int **Pt) {
  printf("Opcode 24: Compare Equal Register Addressing\n");
  printIR(IR);

  if(IR[2] != 'P' || !((int)IR[3] - 48 >= 0 && (int)IR[3] - 48 <= 3)) {
    printfError('o');
    return;
  }
  int m_l = *Pt[parseOp1Reg(IR)];
  int temp = fetch(memory, m_l);
  if(*ACC == temp) PSW[0] = 'T';
  else PSW[0] = 'F';
}
void OP25(char * IR, char memory[][6], char * PSW, int *ACC, short int **Pt) {
  printf("Opcode 25: Compare Less Register Addressing\n");
  printIR(IR);

  if(IR[2] != 'P' || !((int)IR[3] - 48 >= 0 && (int)IR[3] - 48 <= 3)) {
    printfError('o');
    return;
  }
  int m_l = *Pt[parseOp1Reg(IR)];
  int temp = fetch(memory, m_l);
  if(*ACC < temp) PSW[0] = 'T';
  else PSW[0] = 'F';
}
void OP26(char * IR, char memory[][6], char * PSW, int *ACC, short int **Pt) {
  printf("Opcode 26: Compare Greater Register Addressing\n");
  printIR(IR);

  if(IR[2] != 'P' || !((int)IR[3] - 48 >= 0 && (int)IR[3] - 48 <= 3)) {
    printfError('o');
    return;
  }
  int m_l = *Pt[parseOp1Reg(IR)];
  int temp = fetch(memory, m_l);

  if(*ACC > temp) PSW[0] = 'T';
  else PSW[0] = 'F';
}
void OP27(char * IR, char * PSW, int *ACC) {
  printf("Opcode 27: Compare Greater Immediate\n");
  printIR(IR);

  if(!(chToI(IR, 2, 5) >= 0 && chToI(IR, 2, 5) <= 9999)) {
    printfError('o');
    return;
  }

  int temp = parseOp1_2(IR);
  if(*ACC > temp) PSW[0] = 'T';
  else PSW[0] = 'F';
}
void OP28(char * IR, char * PSW, int *ACC) {
  printf("Opcode 28: Compare Equal Immediate\n");
  printIR(IR);

  if(!(chToI(IR, 2, 5) >= 0 && chToI(IR, 2, 5) <= 9999)) {
    printfError('o');
    return;
  }

  int temp = parseOp1_2(IR);
  if(*ACC == temp) PSW[0] = 'T';
  else PSW[0] = 'F';
}
void OP29(char * IR, char * PSW, int *ACC) {
  printf("Opcode 29: Compare Less Immediate\n");
  printIR(IR);

  if(!(chToI(IR, 2, 5) >= 0 && chToI(IR, 2, 5) <= 9999)) {
    printfError('o');
    return;
  }
  int temp = parseOp1_2(IR);
  if(*ACC < temp) PSW[0] = 'T';
  else PSW[0] = 'F';
}
void OP30(char * IR, char * PSW, int *ACC, int ** Rg) {
  printf("Opcode 32: Compare Register Equal\n");
  printIR(IR);

  if(IR[2] != 'R' || !((int)IR[3] - 48 >= 0 && (int)IR[3] - 48 <= 3)) {
    printfError('o');
    return;
  }
  int * temp = Rg[parseOp1Reg(IR)];
  if(*ACC == *temp) PSW[0] = 'T';
  else PSW[0] = 'F';
}
void OP31(char * IR, char * PSW, int *ACC, int ** Rg) {
  printf("Opcode 31: Compare Register Less\n");
  printIR(IR);

  if(IR[2] != 'R' || !((int)IR[3] - 48 >= 0 && (int)IR[3] - 48 <= 3)) {
    printfError('o');
    return;
  }
  int * temp = Rg[parseOp1Reg(IR)];
  if(*ACC == *temp) PSW[0] = 'T';
  else PSW[0] = 'F';
}
void OP32(char * IR, char * PSW, int *ACC, int ** Rg) {
  printf("Opcode 32: Compare Register Greater\n");
  printIR(IR);

  if(IR[2] != 'R' || !((int)IR[3] - 48 >= 0 && (int)IR[3] - 48 <= 3)) {
    printfError('o');
    return;
  }
  int * temp = Rg[parseOp1Reg(IR)];
  if(*ACC == *temp) PSW[0] = 'T';
  else PSW[0] = 'F';
}
void OP33(char * IR, char * PSW, int *PC) {
  printf("Opcode 33: Branch Conditional True\n");
  printIR(IR);

  if(!(chToI(IR, 2, 3) >= 0 && chToI(IR, 2, 3) <= 99)) {
    printfError('o');
    return;
  }
  if(PSW[0] == 'T') *PC = parseOp1(IR) - 1;
}
void OP34(char * IR, char * PSW, int *PC) {
  printf("Opcode 34: Branch Conditional False\n");
  printIR(IR);

  if(!(chToI(IR, 2, 3) >= 0 && chToI(IR, 2, 3) <= 99)) {
    printfError('o');
    return;
  }
  if(PSW[0] == 'F') *PC = parseOp1(IR) - 1;
}
void OP35(char * IR, int *PC) {
  printf("Opcode 35: Branch Unconditional\n");
  printIR(IR);

  if(!(chToI(IR, 2, 3) >= 0 && chToI(IR, 2, 3) <= 99)) {
    printfError('o');
    return;
  }
  *PC = parseOp1(IR) - 1;
}
//does not leave immediately, rather sets a boolean to false to not stop entire program and go to next process
void OP99(bool *leave) {
  printf("Opcode 99: Halt");
  *leave = true;
}
