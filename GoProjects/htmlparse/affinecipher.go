package main

import (
	"fmt"
	"io/ioutil"
)

const (
	FILENAME = "input.txt"
)

func main() {

}

func readFile() {
	f, err := ioutil.ReadFile(FILENAME)
	checkError(err)

	defer f.close()

}

func checkError(e error) {
	fmt.Println(e)
}
