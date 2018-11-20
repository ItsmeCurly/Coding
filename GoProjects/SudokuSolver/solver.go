package main

import (
	"fmt"
	"io/ioutil"
	"log"
	"strconv"
	"strings"
)

var prevValue [][]int
var table [][]int
var orig [][]bool

const InputFileName string = "input.txt"

func main() {
	table = make([][]int, 9)
	orig = make([][]bool, 9)
	prevValue = make([][]int, 9)
	for a := range table {
		table[a] = make([]int, 9)
		orig[a] = make([]bool, 9)
		prevValue[a] = make([]int, 9)
	}

	parseSudoku()

	row, col, solved := 0, 0, false

	for !solved {
		if row == 8 && col == 8 && validValue(8, 8, table[row][col]) {
			solved = true
		}
		for orig[row][col] {
			moveNextSpace(&row, &col)
		}
		if table[row][col] == 0 {
			table[row][col] = 1
		}
		if !validValue(row, col, table[row][col]) || prevValue[row][col] == table[row][col] {
			if table[row][col] >= 9 {
				table[row][col] = 0
				prevValue[row][col] = 0
				moveLastSpace(&row, &col)
				for orig[row][col] {
					moveLastSpace(&row, &col)
				}
				table[row][col]++
			} else {
				table[row][col]++
			}
		} else {
			prevValue[row][col] = table[row][col]
			moveNextSpace(&row, &col)
			printTable()
		}
		//printTable()
	}

}

func validValue(i int, j int, value int) bool {
	rowContain := rowContains(i, j, value)
	colContain := colContains(i, j, value)
	boxContain := boxContains(i, j, value)
	validVal := value > 0 && value <= 9 && !rowContain && !colContain && !boxContain

	return validVal
}

func rowContains(rowNum int, colNum int, value int) bool {
	for i, v := range table[rowNum] {
		if i != colNum && v == value {
			return true
		}
	}
	return false
}

func colContains(rowNum int, colNum int, value int) bool {
	for i := range table {
		if i != rowNum && table[i][colNum] == value {
			return true
		}
	}
	return false
}

func parseSudoku() {
	b, err := ioutil.ReadFile(InputFileName)
	check(err)

	s := string(b)

	rows := strings.Split(s, "\r\n")

	for i, s := range rows {
		cols := strings.Split(s, " ")
		for j, s1 := range cols {
			val, err := strconv.Atoi(s1)
			check(err)
			table[i][j] = val
			prevValue[i][j] = val
			if val != 0 {
				orig[i][j] = true
			} else {
				orig[i][j] = false
			}
		}
	}
}

func boxContains(row int, col int, value int) bool {
	validRows := []int{(row / 3) * 3, (row/3)*3 + 1, (row/3)*3 + 2}
	validCols := []int{(col / 3) * 3, (col/3)*3 + 1, (col/3)*3 + 2}

	for _, v := range validRows {
		for _, u := range validCols {
			if (v != row && u != col) && table[v][u] == value {
				return true
			}
		}
	}

	return false
}

func moveNextSpace(row *int, col *int) {
	if *col == 8 {
		*row++
		*col = 0
	} else {
		*col += 1
	}
}
func moveLastSpace(row *int, col *int) {
	if *col == 0 {
		*row--
		*col = 8
	} else {
		*col--
	}
}

func check(e error) {
	if e != nil {
		log.Fatal(e)
	}
}

func printTable() {
	for _, v := range table {
		for _, v1 := range v {
			fmt.Printf("%d ", v1)
		}
		fmt.Println()

	}
	fmt.Println()
	fmt.Println()
}
