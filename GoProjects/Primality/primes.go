package main

import (
	"fmt"
	"time"
)

func main() {
	start := time.Now()
	fmt.Println(listPrimes(5000000))
	end := time.Now()
	fmt.Printf("Program ran in %d ns\n", end.Sub(start).Nanoseconds())

	start1 := time.Now()
	fmt.Println(isPrime(50000))
	end1 := time.Now()
	fmt.Printf("Program ran in %d ns\n", end1.Sub(start1).Nanoseconds())
}
