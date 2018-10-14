package main

import (
	"errors"
	"fmt"
	"log"
	"os"
	"unicode"
)

var (
	buffer       []byte
	f            *os.File
	nexttoken    int
	nextchar     byte
	currentindex = 0
	charclass    = 0
	lexeme       = make([]byte, 25)
	lexlength    = 0
)

const (
	LETTER  = 0
	DIGIT   = 1
	UNKNOWN = 2

	//TOKEN CODES

	LEFT_PAREN         = 20
	RIGHT_PAREN        = 21
	AMPERSAND          = 22
	BAR                = 23
	EXCLAM             = 24
	LESSTHAN           = 25
	GREATERTHAN        = 26
	IDENTIFIER         = 27
	INT_LIT            = 28
	BOOL_LIT           = 29
	NOTEQUALTO         = 30
	GREATERTHANEQUALTO = 31
	LESSTHANEQUALTO    = 32
	EQUALTO            = 33
	EOF                = 99
)

func main() {
	file, err := os.OpenFile("in.txt", os.O_RDWR, 0666)

	if err != nil {
		log.Fatal(err)
	}
	f = file
	defer f.Close()

	fileinfo, err := file.Stat()

	if err != nil {
		log.Fatal(err)
	}
	filesize := fileinfo.Size() + 2

	buffer = make([]byte, filesize)
	bytesread, err := file.Read(buffer)
	if err != nil {
		log.Fatal(err)
	}

	fmt.Println("bytes read: ", bytesread)
	fmt.Println("bytestream to string: ", string(buffer))
	fmt.Println("bytestream: ", buffer)

	getChar()
	for len(buffer) > currentindex {
		getLex()
		//getBool_Expr()
	}
}

func lookup(b byte) int {
	switch b {
	case '(':
		addChar()
		nexttoken = LEFT_PAREN
		break
	case ')':
		addChar()
		nexttoken = RIGHT_PAREN
		break
	case '&':
		addChar()
		nexttoken = AMPERSAND
		break
	case '|':
		addChar()
		checkChar()
		if nextchar == '|' {
			getChar()
			addChar()
		}
		nexttoken = BAR
		break
	case '!':
		addChar()
		nexttoken = EXCLAM
		break
	case '<':
		addChar()
		checkChar()
		if nextchar == '>' {
			getChar()
			addChar()
			nexttoken = NOTEQUALTO
			break
		}
		nexttoken = LESSTHAN
		break
	case '>':
		addChar()
		nexttoken = GREATERTHAN
		break
	case 0:
		addChar()
		nexttoken = EOF
		charclass = EOF
		break
	}
	return nexttoken
}

func addChar() {
	lexeme[lexlength] = nextchar
	lexlength += 1
	lexeme[lexlength] = 0
}

func getChar() {
	nextchar = buffer[currentindex]
	currentindex += 1
	if unicode.IsNumber(rune(nextchar)) {
		charclass = DIGIT
	} else if unicode.IsLetter(rune(nextchar)) {
		charclass = LETTER
	} else if nextchar == 0 {
		charclass = EOF
	} else {
		charclass = UNKNOWN
	}
}

func checkChar() {
	nextchar = buffer[currentindex]
	if unicode.IsNumber(rune(nextchar)) {
		charclass = DIGIT
	} else if unicode.IsLetter(rune(nextchar)) {
		charclass = LETTER
	} else {
		charclass = UNKNOWN
	}
}

//returns the token
func getLex() {
	lexlength = 0

	for i := range lexeme {
		lexeme[i] = 0
	}

	for unicode.IsSpace(rune(nextchar)) {
		getChar()
	}

	switch charclass {
	case LETTER:
		addChar()
		checkChar()
		for charclass == LETTER || charclass == DIGIT {
			getChar()
			addChar()
			checkChar()
		}
		nexttoken = IDENTIFIER
		break
	case UNKNOWN:
		lookup(nextchar)
		getChar()
		break
	case EOF:
		nexttoken = EOF
		lexeme[0] = 'E'
		lexeme[1] = 'O'
		lexeme[2] = 'F'
		lexeme[3] = 0
		getChar()
		break
	}
	fmt.Printf("Next token is %d, readable variant is %s\n", nexttoken, lexeme)
}

//<bool_expr> ::= <and_term> { || <and_term> }
func getBool_Expr() {
	fmt.Println("Enter <bool_expr>")

	getAndTerm()

	for nexttoken == BAR {
		getLex()
		getAndTerm()
	}
	fmt.Println("Exit <bool_expr>")
}

//<and_term> ::= <bool_factor> { & <bool_factor> }
func getAndTerm() {
	fmt.Println("Enter <and_term>")

	getBoolFactor()

	for nexttoken == AMPERSAND {
		getLex()
		getBoolFactor()
	}

	fmt.Println("Exit <and_term>")
}

//<bool_factor> ::= <bool_literal> | !<bool_factor> |
//                 ( <bool_expr> ) | <relation_expr>
func getBoolFactor() {
	fmt.Println("Enter <bool_factor>")

	if nexttoken == BOOL_LIT {
		getBoolLiteral()
		getLex()
	} else if nexttoken == EXCLAM {
		getLex()
		getBoolFactor()
	} else if nexttoken == LEFT_PAREN {
		getLex()
		getBool_Expr()
		if nexttoken == RIGHT_PAREN {
			getLex()
		} else {
			log.Fatal(errors.New("no right parenthesis"))
		}
	} else {
		getRelationExpr()
	}
	fmt.Println("Exit <bool_factor>")

}

//<relation_expr> ::= <id> { <relop> <id> }
func getRelationExpr() {
	fmt.Println("Enter <relation_expr>")

	getId()

	for nexttoken == LESSTHAN || nexttoken == GREATERTHAN || nexttoken == GREATERTHANEQUALTO || nexttoken == LESSTHANEQUALTO || nexttoken == EQUALTO || nexttoken == NOTEQUALTO {
		getLex()
		getId()
	}
	fmt.Println("Exit <relation_expr>")
}

//<id> ::= letter { letter | digit }
func getId() {
	if nexttoken == IDENTIFIER {
		fmt.Println("Enter <id>")

		fmt.Printf("ID is %s\n", lexeme)

		getLex()
		fmt.Println("Exit <id>")
	} else {
		log.Fatal(errors.New("not an identifier, exiting"))
	}
}

//<bool_literal> ::= true | false
func getBoolLiteral() {
	fmt.Println("Enter <bool_lit>")

	getLex()

	fmt.Println("Exit <bool_lit>")
}
