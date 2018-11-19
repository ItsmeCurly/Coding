package main

import (
	"io/ioutil"
	"log"
	"strconv"
	"strings"
)

var table [][]int

const InputFileName string = "input.txt"

type CurrentSpace struct {
	Row   int
	Col   int
	Value int
}

func main() {
	table = make([][]int, 9)
	for a := range table {
		table[a] = make([]int, 9)
	}

	parseSudoku()

	currentRow, currentCol, solved := 0, 0, false

	for !solved {
		for table[currentRow][currentCol] != 0 {
			moveNextSpace(&currentRow, &currentCol)
		}
		table[currentRow][currentCol] = 1
		if !validValue(currentRow, currentCol, table[currentRow][currentCol]) {
			if table[currentRow][currentCol] == 8 {
				currentCol = 1
			} else {
				table[currentRow][currentCol]++
			}
		}
		if validValue(8, 8, table[currentRow][currentCol]) {
			solved = true
		}
	}

}

func validValue(i int, j int, value int) bool {
	if validRow(i, value) && validCol(j, value) && validSpace() {
		return true
	}
	return false
}

func validRow(rowNum int, value int) bool {
	return checkArray(table[rowNum], value)
}

func validCol(colNum int, value int) bool {
	for i := range table {
		if table[i][colNum] == value {
			return true
		}
	}
	return false
}

func existBox(rowNum int, colNum int, toCheck int) bool {

}

func checkArray(a []int, toCheck int) bool {
	for _, v := range a {
		if v == toCheck {
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
		}
	}
}

func validSpace(row int, col int, value int) bool {
	validRows := []int{(row / 3) * 3, (row/3)*3 + 1, (row/3)*3 + 2}
	validCols := []int{(col / 3) * 3, (col/3)*3 + 1, (col/3)*3 + 2}

	for _, v := range validRows {
		for _, u := range validCols {
			if table[v][u] == value {
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
		*col++
	}
}

func check(e error) {
	if e != nil {
		log.Fatal(e)
	}
}
