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
	nextToken  int
	nextChar   byte
	inputIndex = 0
	charClass  = 0
	lexeme     = make([]byte, 25)
	lexLength  = 0
	IdVariant  = 0
)

const (
	//CHARACTER TYPES

	ALPHA    = 0
	NUMER    = 1
	UNKNOWN  = 2
	TERMINAL = 99

	//TOKEN CODES

	LeftParen          = 20
	RightParen         = 21
	Ampersand          = 22
	Bar                = 23
	Exclamation        = 24
	LessThan           = 25
	GreaterThan        = 26
	Identifier         = 27
	IntLit             = 28
	BoolLit            = 29
	NotEqualTo         = 30
	GreaterThanEqualTo = 31
	LessThanEqualTo    = 32
	EqualTo            = 33
)

func main() {
	file, err := os.OpenFile("input\\in.txt", os.O_RDWR, 0666)

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

	initByteStream()

	getLexeme()
	getBool_Expr()
}

func initByteStream() {
	getCharFromStream()
}

func unknown(b byte) {
	switch b {
	case '(':
		compute()
		nextToken = LeftParen
		break
	case ')':
		compute()
		nextToken = RightParen
		break
	case '&':
		compute()
		nextToken = Ampersand
		break
	case '|':
		compute()
		if nextChar == '|' {
			compute()
			nextToken = Bar
			break
		}
		panic(errors.New("not a valid operator"))
	case '!':
		compute()
		nextToken = Exclamation
		break
	case '<':
		compute()
		if nextChar == '>' {
			compute()
			nextToken = NotEqualTo
			break
		}
		nextToken = LessThan
		break
	case '>':
		compute()
		nextToken = GreaterThan
		break
	case '=':
		compute()
		if nextChar == '=' {
			compute()
			nextToken = EqualTo
			break
		}
		panic(errors.New("not a valid operator"))
	case 0:
		charClass = TERMINAL
		break
	}
}

func addCharToLex() {
	lexeme[lexLength] = nextChar
	lexLength += 1
}

func getCharFromStream() {
	nextChar = buffer[inputIndex]
	inputIndex += 1
	if unicode.IsNumber(rune(nextChar)) {
		charClass = NUMER
	} else if unicode.IsLetter(rune(nextChar)) {
		charClass = ALPHA
	} else if nextChar == 0 {
		charClass = TERMINAL
	} else {
		charClass = UNKNOWN
	}
}

func compute() {
	addCharToLex()
	getCharFromStream()
}

//returns the token
func getLexeme() {
	lexLength = 0

	for i := range lexeme {
		lexeme[i] = 0
	}

	//get rid of the spaces before the lex
	for unicode.IsSpace(rune(nextChar)) {
		getCharFromStream()
	}

	switch charClass {
	//parse identifiers
	case ALPHA:
		compute()
		for charClass == ALPHA || charClass == NUMER {
			compute()
		}
		if checkIdentifier() {
			nextToken = Identifier
			IdVariant = Identifier
		} else {
			nextToken = BoolLit
			IdVariant = BoolLit
		}
		break

		//parse integer literals
	case NUMER:
		compute()

		for charClass == NUMER || charClass == ALPHA {
			if charClass == ALPHA {
				panic(errors.New("not a valid identifier"))
			}
			compute()
		}

		nextToken = Identifier
		IdVariant = IntLit
		break

		//parse operators and other characters
	case UNKNOWN:
		unknown(nextChar)
		break

		//parse TERMINAL, specified by 0 in input stream
	case TERMINAL:
		nextToken = TERMINAL
		lexeme[0] = '$'
		lexeme[3] = 0
		break
	}
	fmt.Printf("Next token is %d, readable variant is %s\n", nextToken, lexeme)
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

	for nextToken == Bar {
		getLexeme()
		getAndTerm()
	}
	fmt.Println("Exiting <bool_expr>")
}

//<and_term> ::= <bool_factor> { & <bool_factor> }
func getAndTerm() {
	fmt.Println("Entering <and_term>")

	getBoolFactor()

	for nextToken == Ampersand {
		getLexeme()
		getBoolFactor()
	}

	fmt.Println("Exiting <and_term>")
}

//<bool_factor> ::= <bool_literal> | !<bool_factor> |
//                 ( <bool_expr> ) | <relation_expr>
func getBoolFactor() {
	fmt.Println("Entering <bool_factor>")

	if nextToken == BoolLit {
		getBoolLiteral()
		getLexeme()
	} else if nextToken == Exclamation {
		getLexeme()
		getBoolFactor()
	} else if nextToken == LeftParen {
		getLexeme()
		getBool_Expr()
		if nextToken == RightParen {
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

	for nextToken == LessThan || nextToken == GreaterThan || nextToken == GreaterThanEqualTo || nextToken == LessThanEqualTo || nextToken == EqualTo || nextToken == NotEqualTo {
		getLexeme()
		getId()
	}
	fmt.Println("Exiting <relation_expr>")
}

//<id> ::= letter { letter | digit }
func getId() {
	if nextToken == Identifier {
		fmt.Println("Entering <id>")
		if IdVariant == Identifier {
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
