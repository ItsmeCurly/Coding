package main

import (
	"fmt"
	"io/ioutil"
	"log"
	"strconv"
	"strings"
	"time"
)

var prevValue [][][]int
var table [][][]int
var orig [][][]bool

const InputFileName string = "input.txt"

func main() {
	parseSudoku()

	for i := range table {
		solve(i)
	}
}

func solve(tableNum int) {
	row, col, solved := 0, 0, false

	start := time.Now()

	for !solved {
		if row == 8 && col == 8 && validValue(tableNum, 8, 8, table[tableNum][row][col]) {
			solved = true
			break
		}
		for orig[tableNum][row][col] {
			moveNextSpace(&row, &col)
		}
		if table[tableNum][row][col] == 0 {
			table[tableNum][row][col] = 1
		}
		if !validValue(tableNum, row, col, table[tableNum][row][col]) || prevValue[tableNum][row][col] == table[tableNum][row][col] {
			if table[tableNum][row][col] >= 9 {
				table[tableNum][row][col] = 0
				prevValue[tableNum][row][col] = 0
				moveLastSpace(&row, &col)
				for orig[tableNum][row][col] {
					moveLastSpace(&row, &col)
				}
				table[tableNum][row][col]++
			} else {
				table[tableNum][row][col]++
			}
		} else {
			prevValue[tableNum][row][col] = table[tableNum][row][col]
			moveNextSpace(&row, &col)
		}
	}
	end := time.Now()
	fmt.Printf("Time taken %f\n", end.Sub(start).Seconds())
	printTable(tableNum)
}

func validValue(tableNum int, i int, j int, value int) bool {
	rowContain := rowContains(tableNum, i, j, value)
	colContain := colContains(tableNum, i, j, value)
	boxContain := boxContains(tableNum, i, j, value)
	validVal := value > 0 && value <= 9 && !rowContain && !colContain && !boxContain

	return validVal
}

func rowContains(tableNum int, rowNum int, colNum int, value int) bool {
	for i, v := range table[tableNum][rowNum] {
		if i != colNum && v == value {
			return true
		}
	}
	return false
}

func colContains(tableNum int, rowNum int, colNum int, value int) bool {
	for i := range table[tableNum] {
		if i != rowNum && table[tableNum][i][colNum] == value {
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

	table = make([][][]int, len(rows)/9)
	orig = make([][][]bool, len(rows)/9)
	prevValue = make([][][]int, len(rows)/9)

	for a := range table {
		table[a] = make([][]int, 9)
		orig[a] = make([][]bool, 9)
		prevValue[a] = make([][]int, 9)
		for b := range table[a] {
			table[a][b] = make([]int, 9)
			orig[a][b] = make([]bool, 9)
			prevValue[a][b] = make([]int, 9)
		}
	}

	for i, s := range rows {
		tableNum, rowNum := i/9, i%9

		if s == "" {
			continue
		}
		cols := strings.Split(s, " ")
		for j, s1 := range cols {
			if s1 == "" {
				continue
			}
			val, err := strconv.Atoi(s1)
			check(err)

			table[tableNum][rowNum][j] = val
			prevValue[tableNum][rowNum][j] = val
			orig[tableNum][rowNum][j] = val != 0
		}
	}
}

func boxContains(tableNum, row int, col int, value int) bool {
	validRows := []int{(row / 3) * 3, (row/3)*3 + 1, (row/3)*3 + 2}
	validCols := []int{(col / 3) * 3, (col/3)*3 + 1, (col/3)*3 + 2}

	for _, v := range validRows {
		for _, u := range validCols {
			check1 := !(v == row && u == col)
			check3 := table[tableNum][v][u] == value

			boxVal := check1 && check3
			if boxVal {
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

func printTable(tableNum int) {
	for _, v := range table[tableNum] {
		for _, v1 := range v {
			fmt.Printf("%d ", v1)
		}
		fmt.Println()
	}
	fmt.Println()
	fmt.Println()
}
