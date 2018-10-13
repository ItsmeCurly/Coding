package main

import (
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
	currentindex int = 0
	charclass    int = 0
	lexeme           = make([]byte, 25)
	lexlength    int = 0
)

const (
	LETTER  = 0
	DIGIT   = 1
	UNKNOWN = 2

	//TOKEN CODES

	LEFT_PAREN  = 20
	RIGHT_PAREN = 21
	AMPERSAND   = 22
	BAR         = 23
	EXCLAM      = 24
	LESSTHAN    = 25
	GREATERTHAN = 26
	IDENTIFIER  = 27
	INT_LIT     = 28
	BOOL_LIT    = 29
)

func main() {
	file, err := os.OpenFile("in.txt", os.O_RDWR, 0666)

	if err != nil {
		log.Fatal(err)
	}
	f = file
	defer file.Close()

	fileinfo, err := file.Stat()

	if err != nil {
		log.Fatal(err)
	}
	filesize := fileinfo.Size() + 1

	buffer = make([]byte, filesize)
	bytesread, err := file.Read(buffer)
	if err != nil {
		log.Fatal(err)
	}

	fmt.Println("bytes read: ", bytesread)
	fmt.Println("bytestream to string: ", string(buffer))

	getChar()
	for len(buffer) > currentindex {
		getLex()
		getBool_Expr()
		fmt.Printf("token is %d, lexeme is %s\n", nexttoken, lexeme)
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
		getChar()
		if nextchar == '|' {
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
		nexttoken = LESSTHAN
		break
	case '>':
		addChar()
		nexttoken = GREATERTHAN
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
	} else {
		charclass = UNKNOWN
	}
}

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
		getChar()
		for charclass == LETTER || charclass == DIGIT {
			addChar()
			getChar()
		}
		nexttoken = IDENTIFIER
		break
	case UNKNOWN:
		lookup(nextchar)
		getChar()
		break
	}

}

func getBool_Expr() {
	fmt.Println("Enter <bool_expr>")

	getAndTerm()

	for nexttoken == BAR {
		getLex()
		getAndTerm()
	}
	fmt.Println("Exit <bool_expr>")
}

func getAndTerm() {
	fmt.Println("Enter <and_term>")

	getBoolFactor()

	for nexttoken == AMPERSAND {
		getLex()
		getBoolFactor()
	}
}

func getBoolFactor() {

}

func getRelationExpr() {

}

func getId() {

}

func getBoolLiteral() {

}
