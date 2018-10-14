package main

import (
	"errors"
	"fmt"
	"log"
	"os"
	"unicode"
)

var (
	buffer     []byte
	nexttoken  int
	nextchar   byte
	inputIndex = 0
	charclass  = 0
	lexeme     = make([]byte, 25)
	lexlength  = 0
	IdVariant  = 0
)

const (
	LETTER  = 0
	DIGIT   = 1
	UNKNOWN = 2

	//TOKEN CODES

	LEFT_PAREN            = 20
	RIGHT_PAREN           = 21
	AMPERSAND             = 22
	BAR                   = 23
	EXCLAMATION           = 24
	LESS_THAN             = 25
	GREATER_THAN          = 26
	IDENTIFIER            = 27
	INT_LIT               = 28
	BOOL_LIT              = 29
	NOTEQUALTO            = 30
	GREATER_THAN_EQUAL_TO = 31
	LESSTHANEQUALTO       = 32
	EQUALTO               = 33
	EOF                   = 99
)

func main() {
	file, err := os.OpenFile("in.txt", os.O_RDWR, 0666)

	if err != nil {
		log.Fatal(err)
	}

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

	fmt.Println("Number of bytes read: ", bytesread)
	fmt.Println("Bytestream to string: ", string(buffer))
	fmt.Println("Bytestream: ", buffer)

	getCharFromStream()
	for len(buffer) > inputIndex {
		getLexeme()
		getBool_Expr()
	}
}

func lookup(b byte) {
	switch b {
	case '(':
		addCharToLex()
		nexttoken = LEFT_PAREN
		break
	case ')':
		addCharToLex()
		nexttoken = RIGHT_PAREN
		break
	case '&':
		addCharToLex()
		nexttoken = AMPERSAND
		break
	case '|':
		addCharToLex()
		getCharFromStream()
		if nextchar == '|' {
			addCharToLex()
			getCharFromStream()
			nexttoken = BAR
			break
		}
		panic(errors.New("not a valid operator"))
	case '!':
		addCharToLex()
		nexttoken = EXCLAMATION
		break
	case '<':
		addCharToLex()
		getCharFromStream()
		if nextchar == '>' {
			addCharToLex()
			nexttoken = NOTEQUALTO
			break
		}
		nexttoken = LESS_THAN
		break
	case '>':
		addCharToLex()
		nexttoken = GREATER_THAN
		break
	case '=':
		addCharToLex()
		getCharFromStream()
		if nextchar == '=' {
			addCharToLex()
			nexttoken = EQUALTO
			break
		}
		panic(errors.New("not a valid operator"))
	case 0:
		addCharToLex()
		nexttoken = EOF
		charclass = EOF
		break
	}
}

func addCharToLex() {
	lexeme[lexlength] = nextchar
	lexlength += 1
}

func getCharFromStream() {
	nextchar = buffer[inputIndex]
	inputIndex += 1
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

//returns the token
func getLexeme() {
	lexlength = 0

	for i := range lexeme {
		lexeme[i] = 0
	}

	//get rid of the spaces before the lex
	for unicode.IsSpace(rune(nextchar)) {
		getCharFromStream()
	}

	switch charclass {
	//parse identifiers
	case LETTER:
		addCharToLex()
		getCharFromStream()
		for charclass == LETTER || charclass == DIGIT {
			addCharToLex()
			getCharFromStream()
		}
		if checkIdentifier() {
			nexttoken = IDENTIFIER
			IdVariant = IDENTIFIER
		} else {
			nexttoken = BOOL_LIT
			IdVariant = BOOL_LIT
		}

		break
		//parse integer literals
	case DIGIT:
		addCharToLex()
		getCharFromStream()
		if charclass == LETTER {
			panic(errors.New("not a valid identifier"))
		}
		for charclass == DIGIT {
			addCharToLex()
			getCharFromStream()
		}
		if charclass == LETTER {
			panic(errors.New("not a valid identifier"))
		}

		nexttoken = IDENTIFIER
		IdVariant = INT_LIT
		break
		//parse operators and other characters
	case UNKNOWN:
		lookup(nextchar)
		getCharFromStream()
		break
		//parse EOF, specified by 0 in input stream
	case EOF:
		nexttoken = EOF
		lexeme[0] = 'E'
		lexeme[1] = 'O'
		lexeme[2] = 'F'
		lexeme[3] = 0
		break
	}
	fmt.Printf("Next token is %d, readable variant is %s\n", nexttoken, lexeme)
}

/*
returns if the identifier input is a boolean literal
*/
func checkIdentifier() bool {
	if (lexeme[0] == 't' && lexeme[1] == 'r' && lexeme[2] == 'u' && lexeme[3] == 'e') ||
		(lexeme[0] == 'f' && lexeme[1] == 'a' && lexeme[2] == 'l' && lexeme[3] == 's' && lexeme[4] == 'e') {
		return false
	}
	return true
}

/*
Start of recursive descent parser
*/

//<bool_expr> ::= <and_term> { || <and_term> }
func getBool_Expr() {
	fmt.Println("Entering <bool_expr>")

	getAndTerm()

	for nexttoken == BAR {
		getLexeme()
		getAndTerm()
	}
	fmt.Println("Exiting <bool_expr>")
}

//<and_term> ::= <bool_factor> { & <bool_factor> }
func getAndTerm() {
	fmt.Println("Entering <and_term>")

	getBoolFactor()

	for nexttoken == AMPERSAND {
		getLexeme()
		getBoolFactor()
	}

	fmt.Println("Exiting <and_term>")
}

//<bool_factor> ::= <bool_literal> | !<bool_factor> |
//                 ( <bool_expr> ) | <relation_expr>
func getBoolFactor() {
	fmt.Println("Entering <bool_factor>")

	if nexttoken == BOOL_LIT {
		getBoolLiteral()
		getLexeme()
	} else if nexttoken == EXCLAMATION {
		getLexeme()
		getBoolFactor()
	} else if nexttoken == LEFT_PAREN {
		getLexeme()
		getBool_Expr()
		if nexttoken == RIGHT_PAREN {
			getLexeme()
		} else {
			panic(errors.New("no right parentheses"))
		}
	} else {
		getRelationExpr()
	}
	fmt.Println("Exiting <bool_factor>")

}

//<relation_expr> ::= <id> { <relop> <id> }
func getRelationExpr() {
	fmt.Println("Entering <relation_expr>")

	getId()

	for nexttoken == LESS_THAN || nexttoken == GREATER_THAN || nexttoken == GREATER_THAN_EQUAL_TO || nexttoken == LESSTHANEQUALTO || nexttoken == EQUALTO || nexttoken == NOTEQUALTO {
		getLexeme()
		getId()
	}
	fmt.Println("Exiting <relation_expr>")
}

//<id> ::= letter { letter | digit }
func getId() {
	if nexttoken == IDENTIFIER {
		fmt.Println("Entering <id>")
		if IdVariant == IDENTIFIER {
			fmt.Printf("ID is %s\n", lexeme)
		} else {
			fmt.Printf("Integer is %s\n", lexeme)
		}
		getLexeme()
		fmt.Println("Exiting <id>")
	} else {
		panic(errors.New("not an identifier, exiting"))
	}
}

//<bool_literal> ::= true | false
func getBoolLiteral() {
	fmt.Println("Entering <bool_lit>")

	getLexeme()

	fmt.Println("Exiting <bool_lit>")
}
